package com.lksoft.yugen.desktop.dev;

import com.lksoft.yugen.desktop.IScriptCompiler;

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
public class ScriptCompiler implements IScriptCompiler {

    /**
     * Compile all scripts to class files
     */
    @Override
    public void compileScripts() {
        // Scan for .java files
        List<File> files = new ArrayList<>();
        File root = new File(".");
        scanJavaFiles(root, files);

        // Make output folder
        File output = new File("_bin");
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
    private void scanJavaFiles(File root, List<File> files) {
        for( File f : root.listFiles() ){
            if( f.isDirectory() ) {
                scanJavaFiles(f, files);
            } else {
                if( f.getAbsolutePath().endsWith(".java") ){
                    files.add(f);
                }
            }
        }
    }
}
