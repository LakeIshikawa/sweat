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

        // Set hits
        midpunch.damage = 20;
        midpunch.guardflags = "SC";
        midpunch.pausetime = 12;
        midpunch.damageAnimType = FighterHit.DamageAnimType.MEDIUM;
        midpunch.damageAnimHeight = FighterHit.DamageAnimHeight.HIGH;
        midpunch.ground_velocity = -7f;
        midpunch.ground_slidetime = 10;
        midpunch.air_velocity = -5f;
    }
}