package com.lksoft.sweat.desktop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lake on 12/10/2016.
 */
public class AtlasBuilder {

    /**
     * Pack all individual images to atlases.
     * The atlases will have the same path/name as the
     * folder containing the pngs, and will reside in the _sweat/_bin folder
     */
    public void buildAtlases() {
        // Scan for image directories
        List<File> atlasDirs = new ArrayList<>();
        File root = new File(".");
        scanImageFolders(root, atlasDirs);

        // Make output folder
        File output = new File("_sweat/_bin");
        output.mkdirs();

        // Pack 'em
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 4096;
        settings.maxHeight = 4096;
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        settings.stripWhitespaceX = true;
        settings.stripWhitespaceY = true;
        settings.useIndexes = false;

        for( File atlasDir : atlasDirs ){
            TexturePacker.processIfModified(
                    settings,
                    atlasDir.getAbsolutePath(),
                    new File(output, atlasDir.getParent()).getAbsolutePath(),
                    atlasDir.getName());
        }
    }

    // Get all java files in assets
    private void scanImageFolders(File root, List<File> files) {
        for( File f : root.listFiles() ){
            if( f.isDirectory() && !f.getName().startsWith("_") ) {
                scanImageFolders(f, files);
            } else {
                try {
                    String mimetype = Files.probeContentType(f.toPath());
                    if( mimetype != null && mimetype.split("/")[0].equalsIgnoreCase("image")) {
                        files.add(f.getParentFile());
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
