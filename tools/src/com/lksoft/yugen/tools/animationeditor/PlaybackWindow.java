package com.lksoft.yugen.tools.animationeditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by Lake on 05/08/2016.
 */
public class PlaybackWindow extends VisWindow {
    // Editor screen
    private AnimationEditorScreen editorScreen;

    /**
     * Create playback window
     * @param animationEditorScreen
     */
    public PlaybackWindow(AnimationEditorScreen animationEditorScreen) {
        super("Playback");
        this.editorScreen = animationEditorScreen;
        TableUtils.setSpacingDefaults(this);

        final VisTextButton play = new VisTextButton("PLAY", "toggle");
        add(play).growX();

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if( play.isChecked() ) {
                    editorScreen.play();
                } else {
                    editorScreen.stop();
                }
            }
        });
    }
}
