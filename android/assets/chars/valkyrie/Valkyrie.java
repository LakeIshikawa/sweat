package chars.valkyrie;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationPack;
import shared.Fighter;
import shared.FighterHit;

/**
 * Valkyrie fighter
 */
public class Valkyrie extends Fighter {

    // Initialization
    public Valkyrie(){
        scale = 0.8f;
        loadAnimationPack("chars/valkyrie/valkyrie.anm");

        // Middle punch
        midpunchHit.damage = 20;
        midpunchHit.guardflags = "SC";
        midpunchHit.pausetime = 12;
        midpunchHit.damageAnimType = FighterHit.DamageAnimType.MEDIUM;
        midpunchHit.damageAnimHeight = FighterHit.DamageAnimHeight.HIGH;
        midpunchHit.ground_velocity = -7f;
        midpunchHit.ground_slidetime = 10;
        midpunchHit.air_velocity = -5f;
    }
}