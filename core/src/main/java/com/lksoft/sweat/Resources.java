package com.lksoft.sweat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.sweat.stateless.AnimationPack;
import com.lksoft.sweat.stateless.FsmReader;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lake on 21/08/2016.
 */
public class Resources {
    public final static String BIN_FOLDER = "sweat/bin/";
    public final static String RESOURCES_FOLDER = "resources/";

    // Map of FSM classes
    private static ObjectMap<String, Class> fsmClasses = new ObjectMap<>();

    // Loaded animation packs
    private static ObjectMap<String, AnimationPack> animationPacks = new ObjectMap<>();

    /**
     * Load FSM class from .fsm file.
     * This converts the .fsm files to java code and
     * runtime-compiles it into a class. Oh yeah.
     * @param fsmFile
     * @return
     * @throws IOException
     */
    public static Class loadFSMClass(FileHandle fsmFile) throws IOException {
        Class fsmClass = fsmClasses.get(fsmFile.path());
        if( fsmClass == null ) {
            fsmClass = FsmReader.get().read(fsmFile);
            fsmClasses.put(fsmFile.path(), fsmClass);
        }
        return fsmClass;
    }

    /**
     * Load an animation pack
     * @param path Path of the .anm file
     * @return Loaded animation pack
     */
    public static AnimationPack loadAnimationPack(String path){
        AnimationPack pack = animationPacks.get(path);
        if( pack == null || pack.isDisposed() ){
            pack = AnimationPack.read(Gdx.files.internal(BIN_FOLDER + path));
            animationPacks.put(path, pack);
        } else {
            pack.use();
        }

        return pack;
    }

    /**
     * Convert RESOURCES file path to BIN file path
     * @param res
     * @return
     */
    public static FileHandle toBin(FileHandle res){
        String relPath = new File(Resources.RESOURCES_FOLDER).toURI().relativize(res.file().toURI()).getPath();
        return Gdx.files.internal(Resources.BIN_FOLDER + relPath);
    }

    /**
     * Convert RESOURCES file path to BIN file path
     * @param bin
     * @return
     */
    public static FileHandle toRes(FileHandle bin){
        String relPath = new File(Resources.BIN_FOLDER).toURI().relativize(bin.file().toURI()).getPath();
        return Gdx.files.internal(Resources.RESOURCES_FOLDER + relPath);
    }

    /**
     * Convert BIN file path to ABSTRACT file path
     * @param bin
     */
    public static FileHandle binToAbs(FileHandle bin){
        String relPath = new File(Resources.BIN_FOLDER).toURI().relativize(bin.file().toURI()).getPath();
        return Gdx.files.internal(relPath);
    }

    /**
     * Convert RESOURCES file path to ABSTRACT file path
     * @param res
     */
    public static FileHandle resToAbs(FileHandle res){
        String relPath = new File(Resources.RESOURCES_FOLDER).toURI().relativize(res.file().toURI()).getPath();
        return Gdx.files.internal(relPath);
    }
}
