package chars.yui;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.lksoft.yugen.stateful.Fsm;
import com.lksoft.yugen.stateless.AnimationPack;
import shared.Fighter;

/**
 * Valkyrie fighter
 */
public class Yui extends Fighter {

    // Initialization
    public Yui(){
        scale = 1.0f;
        loadAnimationPack("chars/yui/yui.anm");
    }
}