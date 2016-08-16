package com.lksoft.yugen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lksoft.yugen.YugenGame;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionHandlerFilter;

public class DesktopLauncher {

    @Argument
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
            System.err.print("Usage: YugenGame");
            parser.printSingleLineUsage(System.err);
            System.err.println();
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            // print option sample. This is useful some time
            System.err.println("  Example: image-undistorter "+parser.printExample(OptionHandlerFilter.ALL));
            System.exit(1);
        }
    }

    /**
     * Launch desktop engine
     */
    private void launch() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 800;

        new LwjglApplication(new YugenGame(fsm), config);
    }
}
