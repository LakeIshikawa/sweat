package com.lksoft.sweat.desktop;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.lksoft.sweat.Resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
            File input = new File(Resources.RESOURCES_FOLDER);
            File output = new File(Resources.BIN_FOLDER);
            output.mkdirs();

            // Compile scripts
            Class<IScriptCompiler> cls = ClassReflection.forName("com.lksoft.sweat.desktop.dev.ScriptCompiler");
            IScriptCompiler compiler = cls.newInstance();
            compiler.compileScripts(output);

            // Pack atlases
            AtlasBuilder builder = new AtlasBuilder();
            builder.buildAtlases(input, output);

            // Copy resousrces
            copyFiles(new File(Resources.RESOURCES_FOLDER), output, "", ".frm");
            copyFiles(new File(Resources.RESOURCES_FOLDER), output, "", ".anm");
            copyFiles(new File(Resources.RESOURCES_FOLDER), output, "", ".scn");
            copyFiles(new File(Resources.RESOURCES_FOLDER), output, "", ".json");

            return true;
        } catch (ReflectionException e){
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Copy preserving folder structure
    private void copyFiles(File from, File to, String path, String extension) throws IOException {
        for( File f : from.listFiles() ){
            if( f.isDirectory() ) copyFiles(f, to, path+f.getName()+"/", extension);
            else if( f.getName().endsWith(extension) ){
                File output = new File(to, path + f.getName());
                output.getParentFile().mkdirs();
                Files.copy(Paths.get(f.toURI()), Paths.get(output.toURI()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
