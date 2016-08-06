package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Created by Lake on 08/06/2016.
 */
public class StageDefReader {

    // File
    private FileHandle stgFile;

    /**
     * Creates b1 reader for b1 stage file
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
        StageDef stageDef = new StageDef();
        String[] lines = stgFile.readString().split("\\n");
        for( String line : lines ){
            line = line.trim();
            if( line.startsWith("name") ){
                stageDef.setName(line.split("=")[1].trim());
            }
            else if( line.startsWith("author") ){
                stageDef.setAuthor(line.split("=")[1].trim());
            }
            else if( line.startsWith("camera.width") ){
                stageDef.setCameraW(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("camera.height") ){
                stageDef.setCameraH(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("camera.offsetY") ){
                stageDef.setCameraOffsetY(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("fighters.height") ){
                stageDef.setFightersHeight(Float.parseFloat(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.right") ){
                stageDef.setAreaR(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.left") ){
                stageDef.setAreaL(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.top") ){
                stageDef.setAreaT(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("area.bottom") ){
                stageDef.setAreaB(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("p1.start.x") ){
                stageDef.setP1StartX(Integer.parseInt(line.split("=")[1].trim()));
            }
            else if( line.startsWith("p2.start.x") ){
                stageDef.setP2StartX(Integer.parseInt(line.split("=")[1].trim()));
            }
        }

        // Infer resources
        stageDef.setAtlasFile(new FileHandle(stgFile.pathWithoutExtension() + ".atlas"));
        stageDef.setFrmFile(new FileHandle(stgFile.pathWithoutExtension() + ".frm"));
        stageDef.setAnmFile(new FileHandle(stgFile.pathWithoutExtension() + ".anm"));

        // Load animationPack
        TextureAtlas atlas = new TextureAtlas(stageDef.getAtlasFile());
        Frames frames = new Frames(atlas, stageDef.getFrmFile());
        AnimationPack animationPack = new AnimationPackReader(stageDef.getAnmFile()).read(frames);
        stageDef.setAnimationPack(animationPack);

        // Sprites
        SpriteDef curSprite = null;
        for( String line : lines ) {
            line = line.trim();

            if (line.startsWith("[Sprite")) {
                String name = line.substring(8, line.indexOf("]"));
                curSprite = new SpriteDef(name);
                stageDef.addSpriteDef(curSprite);
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
                curSprite.setResource(animationPack.getAnimationDef(line.split("=")[1].trim()));
            }
        }

        return stageDef;
    }
}
