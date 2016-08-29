package com.lksoft.sweat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.lksoft.sweat.stateless.AnimationPack;
import com.lksoft.sweat.stateless.FsmReader;

import java.io.IOException;

/**
 * Created by Lake on 21/08/2016.
 */
public class Resources {

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
            pack = AnimationPack.read(Gdx.files.internal(path));
            animationPacks.put(path, pack);
        } else {
            pack.use();
        }

        return pack;
    }
}
