package com.lksoft.yugen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.lksoft.yugen.YugenGame;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

public class DesktopLauncher {

    @Argument(required = true)
    private String fsm;

	public static void main (String[] args) {
		DesktopLauncher launcher = new DesktopLauncher();
        launcher.parseArgs(args);
        launcher.launch();
	}

    // Parse cmd args
    private void parseArgs(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            // parse the arguments.
            parser.parseArgument(args);

        } catch( CmdLineException e ) {
            // if there's b1 problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.print("Usage: Yugen");
            parser.printSingleLineUsage(System.err);
            System.err.println();
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: Yugen "+parser.printExample(OptionHandlerFilter.ALL));
            System.exit(1);
        }
    }

    /**
     * Launch desktop engine
     */
    private void launch() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;

        // Get script compiler!
        try {
            Class<IScriptCompiler> cls = ClassReflection.forName("com.lksoft.yugen.desktop.dev.ScriptCompiler");
            IScriptCompiler compiler = cls.newInstance();
            compiler.compileScripts();

            System.out.println("Starting YUGEN in debug mode");
            new LwjglApplication(new YugenGame(fsm, true), config);
        } catch (ReflectionException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new YugenGame(fsm, false), config);
        } catch (InstantiationException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new YugenGame(fsm, false), config);
        } catch (IllegalAccessException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new YugenGame(fsm, false), config);
        }
    }
}
