package com.lksoft.yugen.tools.texturepacker;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Array;
import com.kotcrab.vis.ui.util.TableUtils;
import com.kotcrab.vis.ui.util.dialog.Dialogs;
import com.kotcrab.vis.ui.widget.*;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserListener;

import java.io.File;

/**
 * Created by Lake on 05/08/2016.
 */
public class TexturePackerWindow extends VisWindow {
    // Editor screen
    private TexturePackerScreen editorScreen;

    // GUI components
    private VisTextField srcFolderTxt;
    private VisTextButton srcFolderBtn;
    private VisTextField dstFolderTxt;
    private VisTextButton dstFolderBtn;
    private VisTextField nameTxt;

    private VisTextButton processBtn;

    // Settings
    TexturePacker.Settings settings = new TexturePacker.Settings();


    /**
     * Create playback window
     * @param texturePackerScreen
     */
    public TexturePackerWindow(TexturePackerScreen texturePackerScreen) {
        super("Texture packer");
        this.editorScreen = texturePackerScreen;
        TableUtils.setSpacingDefaults(this);

        top();
        add(new VisLabel("Pack name:")).padRight(5);
        add(nameTxt = new VisTextField("texturepack"));
        row();
        add(new VisLabel("Source folder:")).padRight(5);
        VisTable r = new VisTable();
        r.add(srcFolderTxt = new VisTextField()).growX();
        r.add(srcFolderBtn = new VisTextButton("..."));
        add(r).growX();
        row();
        add(new VisLabel("Dest folder:")).padRight(5);
        r = new VisTable();
        r.add(dstFolderTxt = new VisTextField()).growX();
        r.add(dstFolderBtn = new VisTextButton("..."));
        add(r).growX();
        row();

        srcFolderTxt.setDisabled(true);
        dstFolderTxt.setDisabled(true);
        add(processBtn = new VisTextButton("Process")).colspan(2).center();

        processBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                process(srcFolderTxt.getText(), dstFolderTxt.getText(), nameTxt.getText());
            }
        });

        srcFolderBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
                chooser.setSelectionMode(FileChooser.SelectionMode.DIRECTORIES);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDirectory(new File("."));
                chooser.setListener(new FileChooserListener() {

                    @Override
                    public void selected(Array<FileHandle> files) {
                        String absPath = files.first().file().getAbsolutePath();
                        srcFolderTxt.setText(absPath);
                    }

                    @Override
                    public void canceled() {
                    }
                });

                getStage().addActor(chooser);
            }
        });

        dstFolderBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                final FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
                chooser.setSelectionMode(FileChooser.SelectionMode.DIRECTORIES);
                chooser.setMultiSelectionEnabled(false);
                chooser.setDirectory(new File("."));
                chooser.setListener(new FileChooserListener() {

                    @Override
                    public void selected(Array<FileHandle> files) {
                        String absPath = files.first().file().getAbsolutePath();
                        dstFolderTxt.setText(absPath);
                    }

                    @Override
                    public void canceled() {
                    }
                });

                getStage().addActor(chooser);
            }
        });

        // Pack images
        settings = new TexturePacker.Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.stripWhitespaceX = true;
        settings.stripWhitespaceY = true;
        settings.useIndexes = false;
    }

    /**
     * Create atlas
     * @param srcPath
     * @param dstPath
     */
    private void process(String srcPath, String dstPath, String name) {
        TexturePacker.process(settings, srcPath, dstPath, name);
        Dialogs.showOKDialog(getStage(), "Success", "Texture created.");
    }
}
