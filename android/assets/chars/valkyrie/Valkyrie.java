package chars.valkyrie;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationPack;
import shared.Fighter;

/**
 * Valkyrie fighter
 */
public class Valkyrie extends Fighter {

    // Initialization
    public Valkyrie(){
        scale = 0.8f;
        loadAnimationPack("chars/valkyrie/valkyrie.anm");
    }
}