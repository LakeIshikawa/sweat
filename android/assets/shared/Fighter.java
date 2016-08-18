package shared;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;
import com.lksoft.yugen.stateful.Fsm;

/**
 * Fighter base class
 */
public class Fighter extends Fsm<Fighter, FighterState, FighterHit> {
    float speed_walk_fwd = 6.0f;
    float speed_walk_bwd = -4.0f;
    float speed_air_fwd = 3.0f;
    float speed_air_bwd = -2.0f;
    float speed_jump_up = 15.0f;
    float speed_run_fwd = 9.0f;
    Vector2 speed_backhop = new Vector2(-9.0f, 5.0f);

    float standing_friction = 0.4f;
    float crouching_friction = 0.65f;
    float air_gravity_y = -0.6f;

    @Override
    public FighterState getInitialState(){
        return FighterState.IDLE;
    }
}

/**
 * Fighter state machine
 */
enum FighterState implements State<Fighter> {
    IDLE() {
        public void enter(Fighter fighter){
            fighter.setAnimation(fighter.getAnimation("idle"));
        }
    };

    public void update(Fighter entity) {}
    public void enter(Fighter entity){}
    public void exit(Fighter entity){}
    public boolean onMessage(Fighter entity, Telegram telegram) {return false;}
}
