package com.lksoft.sweat.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.lksoft.sweat.Resources;
import com.lksoft.sweat.Sweat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Stallman on 17/08/2016.
 */
public class FsmReader {
    private static FsmReader instance = new FsmReader(Sweat.i.isDebug());
    public static FsmReader get(){ return instance; }

    // Class loader
    private ClassLoader loader;

    private FsmReader(boolean debug){
        if( debug ) {
            try {
                loader = new URLClassLoader(new URL[]{Gdx.files.internal(Resources.BIN_FOLDER).file().toURI().toURL()});
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            loader = getClass().getClassLoader();
        }
    }

    /**
     * Parse .java and create FSM class!
     * @return
     */
    public Class read(FileHandle classFile) throws IOException {
        try {
            return loader.loadClass(classFile.pathWithoutExtension().replace("/", "."));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
