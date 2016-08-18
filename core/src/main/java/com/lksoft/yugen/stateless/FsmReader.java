package com.lksoft.yugen.stateless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by Stallman on 17/08/2016.
 */
public class FsmReader {
    private static FsmReader instance = new FsmReader();
    public static FsmReader get(){ return instance; }

    // Class loader
    private URLClassLoader loader;

    private FsmReader(){
        try {
            loader = new URLClassLoader(new URL[]{Gdx.files.internal("bin/").file().toURI().toURL()});
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse .java and create FSM class!
     * @return
     */
    public Class read(FileHandle classFile) throws IOException {
        try {
            return loader.loadClass(classFile.path().replace("/", "."));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
