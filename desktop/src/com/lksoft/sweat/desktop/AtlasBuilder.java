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
 *
 * Builds atlases from image folders in the project
 */
class AtlasBuilder {

    /**
     * Pack all individual images to atlases.
     * The atlases will have the same path/name as the
     * folder containing the pngs, and will reside in the _sweat/_bin folder
     */
    void buildAtlases(File binFolder) {
        // Scan for image directories
        List<File> atlasDirs = new ArrayList<>();
        File root = new File(".");
        scanImageFolders(root, atlasDirs);

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
            // Check if anything was modified
            if( atlasModified(atlasDir, binFolder, settings) ) {
                TexturePacker.process(
                        settings,
                        atlasDir.getAbsolutePath(),
                        new File(binFolder, atlasDir.getParent()).getAbsolutePath(),
                        atlasDir.getName());
            }
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

    // Check if atlas images were modified
    private boolean atlasModified(File atlasDir, File outputDir, TexturePacker.Settings settings) {
        File atlasFile = new File(outputDir, atlasDir.getPath() + settings.atlasExtension);

        // If atlas dir was modified, atlas was modified
        if( atlasDir.lastModified() > atlasFile.lastModified() ) return true;

        // Check every file recursively
        return fileModified(atlasDir, atlasFile.lastModified());
    }

    // Check if file (dir or image) was modified later than the atlas last modification
    private boolean fileModified(File f, long atlasModified) {
        if( f.isDirectory() ){
            // Check folder modification
            if( f.lastModified() > atlasModified ) return true;
            // Check every file modification
            for( File c : f.listFiles() ){
                if( fileModified(c, atlasModified) ) return true;
            }
        }
        return f.lastModified() > atlasModified;
    }

}
