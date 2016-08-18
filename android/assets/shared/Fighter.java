package shared;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.yugen.stateful.Fsm;

/**
 * Fighter base class
 */
public class Fighter extends Fsm<Fighter, State<Fighter>, FighterHit> {
    // Constants
    protected enum FightPosition {
        STANDING("S"),
        CROUCHING("C"),
        AIR("A");

        public String idchar;
        FightPosition(String idchar){
            this.idchar = idchar;
        }
    }

    // Physics
    protected enum Physics{
        NONE,
        STANDING,
        CROUCHING,
        AIR
    }

    // Default hits
    protected FighterHit midpunch = new FighterHit();

    // Default parameters
    protected float speed_walk_fwd = 6.0f;
    protected float speed_walk_bwd = -4.0f;
    protected float speed_air_fwd = 3.0f;
    protected float speed_air_bwd = -2.0f;
    protected float speed_jump_up = 15.0f;
    protected float speed_run_fwd = 9.0f;
    protected Vector2 speed_backhop = new Vector2(-9.0f, 5.0f);
    protected float standing_friction = 0.4f;
    protected float crouching_friction = 0.65f;
    protected float air_gravity_y = -0.6f;

    // FightPosition
    protected FightPosition fightPosition;
    protected Physics physics;

    // References
    protected Fsm opponent;

    // Temp
    protected int slidetime;

    // Initialization
    public Fighter() {
        setActive(false);
    }

    @Override
    public State<Fighter> getInitialState(){
        return FighterState.IDLE;
    }

    @Override
    public void statelessUpdate(){
        // Physics
        switch (physics){
            case STANDING:
                vel.x = vel.x * standing_friction;
                if(Math.abs(vel.x) < 0.1) { vel.x = 0; }
                break;

            case CROUCHING:
                vel.x = vel.x * crouching_friction;
                if(Math.abs(vel.x) < 0.1) { vel.x = 0; }
                break;

            case AIR:
                vel.y = vel.y + air_gravity_y;
                if(pos.y < 0.0) {
                    vel.y = 0;
                    pos.y = 0;
                }
                break;
        }

        // Normal attacks
        boolean standing = fightPosition == FightPosition.STANDING;
        boolean crouching = fightPosition == FightPosition.CROUCHING;
        if( isCtrl() && standing && getAnimation("midpunch") != null && keyPress("B1") ){
            changeState(FighterState.MIDPUNCH);
            return;
        }

        // Hit check
        if( isHit() ){
            // Block
            boolean standBlock = keyHold("B") && standing && getHit().guardflags.contains("S");
            boolean crouchBlock = keyHold("B") && crouching && getHit().guardflags.contains("C");
            if( standBlock ){
                // TODO Stand block
            }
            else if( crouchBlock ){
                // TODO Crouch block
            }
            else {
                // -- DAMAGE
                // Pause players
                pause(getHit().pausetime);
                opponent.pause(getHit().pausetime);

                // Air damage
                if( fightPosition == FightPosition.AIR ){
                    setAnimation("damageFall");
                    vel.x = getHit().air_velocity * (flip ? -1 : 1);
                    vel.y = 0;
                    clearHit();
                    changeState(FighterState.AIRDAMAGE);
                    return;
                }

                // Falls
                if( getHit().fall ){
                    setAnimation("damageFall");
                    vel.x = getHit().air_velocity * (flip ? -1 : 1);
                    vel.y = 15f;
                    clearHit();
                    changeState(FighterState.AIRDAMAGE);
                    return;
                }

                // Ground damage
                vel.x = getHit().ground_velocity * (flip ? -1 : 1);
                slidetime = getHit().ground_slidetime;

                String animName = "damage" + fightPosition.idchar + getHit().damageAnimHeight.idchar + getHit().damageAnimType.idchar;
                setAnimation(animName);
                clearHit();
                changeState(FighterState.GROUNDDAMAGE);
                return;
            }
        }
    }
}

/**
 * Fighter state machine
 */
enum FighterState implements State<Fighter> {
    IDLE() {
        public void enter(Fighter f){
            // Save opponent
            if( f.opponent == null ) {
                f.opponent = (f == f.getFSM("p1")) ? f.getFSM("p2") : f.getFSM("p1");
            }

            f.setAnimation("idle");
            f.fightPosition = Fighter.FightPosition.STANDING;
            f.physics = Fighter.Physics.STANDING;
            f.setCtrl(true);
            f.setLayer(5);
        }

        public void update(Fighter f) {
            // Turn
            if( !f.facing(f.opponent) ){
                f.changeState(TURN);
                return;
            }

            // Walk
            if( f.keyHold("F") ^ f.keyHold("B") ){
                f.changeState(WALK);
                return;
            }

            // Jump
            if( f.keyHold("U") ){
                f.changeState(JUMPSTART);
                return;
            }

            // Crouch
            if( f.keyHold("D") ){
                f.changeState(STAND2CROUCH);
                return;
            }
        }
    },

    WALK {
        public void enter(Fighter f) {
            f.physics = Fighter.Physics.NONE;
        }

        public void update(Fighter f) {
            // Fwd
            if( f.keyHold("F") && !f.keyHold("B") ){
                f.vel.x = f.speed_walk_fwd * (f.flip ? -1 : 1);
                f.setAnimation("walkfwd");
            }

            // Bwd
            if( f.keyHold("B") && !f.keyHold("F") ){
                f.vel.x = f.speed_walk_bwd * (f.flip ? -1 : 1);
                f.setAnimation("walkbwd");
            }

            // Turn
            if( !f.facing(f.opponent) ){
                f.changeState(TURN);
                return;
            }

            // Stop walking
            if( !(f.keyHold("F") ^ f.keyHold("B")) ){
                f.changeState(IDLE);
            }

            // Jump
            if( f.keyHold("U") ){
                f.changeState(JUMPSTART);
                return;
            }

            // Crouch
            if( f.keyHold("D") ){
                f.changeState(STAND2CROUCH);
                return;
            }
        }
    },

    TURN {
        public void enter(Fighter f){
            f.setAnimation("turn");
        }

        public void update(Fighter f) {
            if( f.getAnimCycles() == 1 ){
                f.flip = !f.flip;
                f.changeState(IDLE);
                return;
            }
        }
    },

    JUMPSTART {
        public void enter(Fighter f){
            f.setAnimation("jumpstart");
        }

        public void update(Fighter f) {
            // Jump up
            if( f.getAnimCycles() == 1 ){
                // Set proper animation
                if( f.keyHold("F") ) f.setAnimation("jumpingFwd");
                else if ( f.keyHold("B") ) f.setAnimation("jumpingBwd");
                else f.setAnimation("jumping");

                // Jump
                f.vel.y = f.speed_jump_up;
                f.changeState(JUMPING);
                return;
            }
        }
    },

    JUMPING {
        public void enter(Fighter f){
            f.fightPosition = Fighter.FightPosition.AIR;
            f.physics = Fighter.Physics.AIR;
        }
        public void update(Fighter f) {
            // Controls
            if( f.keyHold("F") ){
                f.vel.x  = f.speed_air_fwd * (f.flip ? -1 : 1);
            }
            if( f.keyHold("B") ){
                f.vel.x  = f.speed_air_bwd * (f.flip ? -1 : 1);
            }

            // Landing
            if( f.pos.y < 0 ){
                f.changeState(LANDING);
                return;
            }
        }
    },

    LANDING {
        public void enter(Fighter f){
            f.setAnimation("landing");
        }

        public void update(Fighter f) {
            if( f.getAnimCycles() == 1 ){
                f.changeState(IDLE);
            }
        }
    },

    STAND2CROUCH {
        public void enter(Fighter f){
            f.fightPosition = Fighter.FightPosition.CROUCHING;
            f.physics = Fighter.Physics.CROUCHING;
            f.setAnimation("stand2crouch");
        }
        public void update(Fighter f) {
            if( f.getAnimCycles() == 1 ){
                f.changeState(CROUCHING);
                return;
            }
        }
    },

    CROUCHING {
        public void enter(Fighter f){
            f.setAnimation("crouching");
        }

        public void update(Fighter f) {
            if( !f.keyHold("D") ){
                f.changeState(CROUCH2STAND);
                return;
            }
        }
    },

    CROUCH2STAND {
        public void enter(Fighter f){
            f.setAnimation("crouch2stand");
        }

        public void update(Fighter f) {
            if( f.getAnimCycles() == 1 ){
                f.changeState(IDLE);
                return;
            }
        }
    },

    MIDPUNCH {
        public void enter(Fighter f){
            f.setLayer(6);
            f.setAnimation("midpunch");
            f.vel.x = 0;
            f.setCtrl(false);
            f.setAttackHit(f.midpunch);
        }

        public void update(Fighter f) {
            if( f.getAnimCycles() == 1 ){
                f.changeState(IDLE);
                return;
            }
        }
    },

    GROUNDDAMAGE {
        public void enter(Fighter f){
            f.physics = Fighter.Physics.NONE;
        }
        public void update(Fighter f) {
            if( f.getStatetime() == f.slidetime ){
                f.changeState(IDLE);
            }
        }
    },

    AIRDAMAGE {
        public void enter(Fighter f){
            f.fightPosition = Fighter.FightPosition.AIR;
            f.physics = Fighter.Physics.AIR;
        }
        public void update(Fighter f) {
            if( f.pos.y < 0 ){
                f.changeState(LANDING);
                return;
            }
        }
    }
    ;

    public void enter(Fighter entity){}
    public void update(Fighter entity) {}
    public void exit(Fighter entity){}
    public boolean onMessage(Fighter entity, Telegram telegram) {return false;}
}
