package com.lksoft.yugen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stallman on 18/08/2016.
 */
public class ScriptCompiler {

    /**
     * Compile all scripts to class files
     */
    public static void compileScripts() {
        // Scan for .java files
        List<File> files = new ArrayList<>();
        FileHandle root = Gdx.files.internal(".");
        scanJavaFiles(root, files);

        // Make output folder
        File output = Gdx.files.internal("bin").file();
        output.mkdir();

        // Compile
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Iterable<String> options = Arrays.asList( new String[] { "-d", output.getAbsolutePath()} );
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromFiles(files);
        compiler.getTask(null, fileManager, null, options, null, compilationUnits).call();

        try {
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all java files in assets
    private static void scanJavaFiles(FileHandle root, List<File> files) {
        for( FileHandle f : root.list() ){
            if( f.isDirectory() ) {
                scanJavaFiles(f, files);
            } else {
                if( f.extension().equalsIgnoreCase("java") ){
                    files.add(f.file());
                }
            }
        }
    }
}
