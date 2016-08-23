package com.lksoft.yugen.tools.settingseditor;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import com.lksoft.yugen.Yugen;
import com.lksoft.yugen.stateless.Settings;

import java.io.File;

/**
 * Created by Lake on 05/08/2016.
 */
public class SettingsWindow extends VisWindow {
    // Editor screen
    private SettingsEditorScreen editorScreen;

    // Current settings
    private Settings settings;

    // GUI components
    private VisTextField mainFsmField;
    private VisTextButton fsmBrowseButton;
    private VisSelectBox<Settings.Key>[] p1Keys = new VisSelectBox[10];
    private VisTextButton p1Btns[] = new VisTextButton[10];
    private VisSelectBox<Settings.Key>[] p2Keys = new VisSelectBox[10];
    private VisTextButton p2Btns[] = new VisTextButton[10];


    private String[] labels = new String[]{
        "UP:", "DOWN:", "LEFT:", "RIGHT:", "B1:","B2:","B3:","B4:","B5:","B6:"
    };

    /**
     * Create playback window
     * @param settingsEditorScreen
     */
    public SettingsWindow(SettingsEditorScreen settingsEditorScreen) {
        super("Settings");
        this.editorScreen = settingsEditorScreen;
        TableUtils.setSpacingDefaults(this);

        top();
        VisTable r = new VisTable();
        r.add(new VisLabel("Launch fsm:")).padRight(5);
        r.add(mainFsmField = new VisTextField()).growX();
        r.add(fsmBrowseButton = new VisTextButton("..."));
        add(r).colspan(4).growX();
        row();
        add("P1").colspan(2).center();
        add("P2").colspan(2).center();
        row();

        fsmBrowseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
                chooser.setSelectionMode(FileChooser.SelectionMode.FILES);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDirectory(new File("."));
                FileTypeFilter filter = new FileTypeFilter(false);
                filter.addRule("Fsm script file", "java");
                chooser.setFileTypeFilter(filter);
                chooser.setListener(new FileChooserListener() {

                    @Override
                    public void selected(Array<FileHandle> files) {
                        String absPath = files.first().file().getAbsolutePath();
                        String rootPath = new File(".").getAbsolutePath();
                        mainFsmField.setText(absPath.replace(rootPath+File.separator, ""));
                        settings.setMainFsm(mainFsmField.getText());
                        settings.write(new File(Yugen.SETTINGS_FILE));
                    }

                    @Override
                    public void canceled() {
                    }
                });

                getStage().addActor(chooser);
            }
        });

        // Keys
        for( int i=0; i<p1Keys.length; i++){
            add(new VisLabel(labels[i])).padRight(5);
            r = new VisTable();
            r.add(p1Keys[i] = new VisSelectBox<>()).growX();
            p1Keys[i].setItems(Settings.Key.values());
            final int finalI1 = i;
            p1Keys[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setKey(settings.getP1Keys(), finalI1, p1Keys[finalI1].getSelected(), p1Keys);
                }
            });
            r.add(p1Btns[i] = new VisTextButton("..."));
            add(r).growX();
            p1Btns[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    inputKey(settings.getP1Keys(), finalI1, p1Keys);
                }
            });
            add(new VisLabel(labels[i])).padRight(5);
            r = new VisTable();
            r.add(p2Keys[i] = new VisSelectBox<>()).growX();
            p2Keys[i].setItems(Settings.Key.values());
            final int finalI = i;
            p2Keys[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    setKey(settings.getP2Keys(), finalI, p2Keys[finalI].getSelected(), p2Keys);
                }
            });
            r.add(p2Btns[i] = new VisTextButton("..."));
            add(r).growX();
            p2Btns[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    inputKey(settings.getP2Keys(), finalI1, p2Keys);
                }
            });
            row();
        }
    }

    /**
     * Get key from keypress
     */
    private void inputKey(Settings.KeySettings keys, int i, VisSelectBox<Settings.Key>[] boxes) {
        PressKeyDialog dialog = new PressKeyDialog(this);
        dialog.show(getStage(), keys, i, boxes);
    }

    /**
     * Set key to specified key settings
     * @param keys
     * @param i
     * @param key
     */
    public void setKey(Settings.KeySettings keys, int i, Settings.Key key, VisSelectBox<Settings.Key>[] boxes) {
        switch (i){
            case 0: keys.up = key; break;
            case 1: keys.down = key; break;
            case 2: keys.left = key; break;
            case 3: keys.right = key; break;
            case 4: keys.b1 = key; break;
            case 5: keys.b2 = key; break;
            case 6: keys.b3 = key; break;
            case 7: keys.b4 = key; break;
            case 8: keys.b5 = key; break;
            case 9: keys.b6 = key; break;
        }

        if( boxes[i].getSelected() != key ){
            boxes[i].setSelected(key);
        }

        settings.write(new File(Yugen.SETTINGS_FILE));
    }

    /**
     * Set settings
     * @param settings
     */
    public void setSettings(Settings settings){
        this.settings = settings;

        if( settings == null ){
            mainFsmField.setText("");

            for( int i=0; i<p1Keys.length; i++ ){
                p1Keys[i].setSelected(null);
                p2Keys[i].setSelected(null);
            }
        } else {
            mainFsmField.setText(settings.getMainFsm());

            p1Keys[0].setSelected(settings.getP1Keys().up);
            p1Keys[1].setSelected(settings.getP1Keys().down);
            p1Keys[2].setSelected(settings.getP1Keys().left);
            p1Keys[3].setSelected(settings.getP1Keys().right);
            p1Keys[4].setSelected(settings.getP1Keys().b1);
            p1Keys[5].setSelected(settings.getP1Keys().b2);
            p1Keys[6].setSelected(settings.getP1Keys().b3);
            p1Keys[7].setSelected(settings.getP1Keys().b4);
            p1Keys[8].setSelected(settings.getP1Keys().b5);
            p1Keys[9].setSelected(settings.getP1Keys().b6);

            p2Keys[0].setSelected(settings.getP2Keys().up);
            p2Keys[1].setSelected(settings.getP2Keys().down);
            p2Keys[2].setSelected(settings.getP2Keys().left);
            p2Keys[3].setSelected(settings.getP2Keys().right);
            p2Keys[4].setSelected(settings.getP2Keys().b1);
            p2Keys[5].setSelected(settings.getP2Keys().b2);
            p2Keys[6].setSelected(settings.getP2Keys().b3);
            p2Keys[7].setSelected(settings.getP2Keys().b4);
            p2Keys[8].setSelected(settings.getP2Keys().b5);
            p2Keys[9].setSelected(settings.getP2Keys().b6);
        }
    }
}
