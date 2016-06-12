package com.lksoft.yugen.stateless;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Lake on 08/06/2016.
 */
public class StageDefReader {

    // File
    private FileHandle stgFile;

    /**
     * Creates a reader for a stage file
     * @param stgFile
     */
    public StageDefReader(FileHandle stgFile){
        this.stgFile = stgFile;
    }

    /**
     * Read the stage layout
     * @return A stage layout
     */
    public StageDef read(){
        StageDef layout = new StageDef();
        String[] lines = stgFile.readString().split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.startsWith("name") ){
                layout.setName(line.split("=")[1].trim());
            }
            else if( line.startsWith("author") ){
                layout.setAuthor(line.split("=")[1].trim());
            }
            else if( line.startsWith("atlas") ){
                layout.setAtlasFile(new FileHandle(stgFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("frm") ){
                layout.setFrmFile(new FileHandle(stgFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("anm") ){
                layout.setAnmFile(new FileHandle(stgFile.parent() + "\\" + line.split("=")[1].trim()));
            }
            else if( line.startsWith("camera.width") ){
                layout.setCameraW(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("camera.height") ){
                layout.setCameraH(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("camera.offsetY") ){
                layout.setCameraOffsetY(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.right") ){
                layout.setAreaR(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.left") ){
                layout.setAreaL(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.top") ){
                layout.setAreaT(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.bottom") ){
                layout.setAreaB(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("p1.start.x") ){
                layout.setP1StartX(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("p2.start.x") ){
                layout.setP2StartX(Integer.parseInt(line.split("=")[1].trim()));
            }
        }

        // Load animations
        TextureAtlas atlas = new TextureAtlas(layout.getAtlasFile());
        Frames frames = new Frames(atlas, layout.getFrmFile());
        Animations animations = new Animations(frames, layout.getAnmFile());
        layout.setAnimations(animations);

        // Sprites
        SpriteDef curSprite = null;
        for( String line : lines ) {
            line = line.trim();

            if (line.startsWith("[Sprite")) {
                String name = line.substring(8, line.indexOf("]"));
                curSprite = new SpriteDef(name);
                layout.addSpriteDef(curSprite);
            }
            else if( line.startsWith("start") ){
                String[] xy = line.split("=")[1].trim().split(",");
                curSprite.setStartX(Integer.parseInt(xy[0]));
                curSprite.setStartY(Integer.parseInt(xy[1]));
            }
            else if( line.startsWith("scrollFactor") ){
                String[] xy = line.split("=")[1].trim().split(",");
                curSprite.setScrollFactorX(Float.parseFloat(xy[0]));
                curSprite.setScrollFactorY(Float.parseFloat(xy[1]));
            }
            else if( line.startsWith("layer") ){
                curSprite.setLayer(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("resource") ){
                curSprite.setResource(animations.getAnimationSequence(line.split("=")[1].trim()));
            }
        }

        return layout;
    }
}
