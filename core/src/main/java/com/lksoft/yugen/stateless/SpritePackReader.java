package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Lake on 04/08/2016.
 */
public class SpritePackReader {

    // File
    private FileHandle frmFile;

    /**
     * Creates a reader for a spriteDef pack
     * @param frmFile
     */
    public SpritePackReader(FileHandle frmFile){
        this.frmFile = frmFile;
    }

    /**
     * Read the spriteDef pack
     * @return A spriteDef pack
     */
    public SpritePack read(TextureAtlas atlas){
        SpritePack result = new SpritePack(atlas);

        // Parse the spr file and create sprite definitions
        String text = frmFile.readString();

        String[] lines = text.split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.isEmpty() ) continue;

            String[] split = line.split("\\s+");

            // Find region
            TextureAtlas.AtlasRegion region = atlas.findRegion(split[0]);
            if( region != null ){
                SpriteDef spriteDef = new SpriteDef(region, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                result.addSpriteDef(spriteDef);
            }
        }
        return result;
    }
}
