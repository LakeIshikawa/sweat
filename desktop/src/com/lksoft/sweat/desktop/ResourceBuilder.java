package com.lksoft.sweat.desktop;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.io.File;

/**
 * Created by Lake on 12/10/2016.
 */
public class ResourceBuilder {

    /**
     * Build all needed resources if we are in debug mode.
     * That is, compile all .java files and pack all atlases.
     *
     * @return true if we are in debug mode
     */
    public boolean buildResources(){
        try {
            // Create bin folder
            File output = new File("_sweat/_bin");
            output.mkdirs();

            // Compile scripts
            Class<IScriptCompiler> cls = ClassReflection.forName("com.lksoft.sweat.desktop.dev.ScriptCompiler");
            IScriptCompiler compiler = cls.newInstance();
            compiler.compileScripts(output);

            // Pack atlases
            AtlasBuilder builder = new AtlasBuilder();
            builder.buildAtlases(output);
            return true;
        } catch (ReflectionException e){
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }
}
