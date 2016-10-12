package com.lksoft.sweat.desktop;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

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
            // Compile scripts
            Class<IScriptCompiler> cls = ClassReflection.forName("com.lksoft.sweat.desktop.dev.ScriptCompiler");
            IScriptCompiler compiler = cls.newInstance();
            compiler.compileScripts();

            // Pack atlases
            AtlasBuilder builder = new AtlasBuilder();
            builder.buildAtlases();
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
