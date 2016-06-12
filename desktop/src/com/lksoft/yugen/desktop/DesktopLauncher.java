package com.lksoft.yugen.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lksoft.yugen.Yugen;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

import java.io.IOException;

public class DesktopLauncher {

    @Option(name = "--stage", depends = {"--p1", "--p2"})
    private String stage;

    @Option(name = "--p1", depends = {"--stage", "--p2"})
    private String p1;

    @Option(name = "--p2", depends = {"--stage", "--p1"})
    private String p2;


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
            // if there's a problem in the command line,
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
        config.height = 720;

        if( stage != null ) {
            new LwjglApplication(new Yugen(stage, p1, p2), config);
        } else {
            new LwjglApplication(new Yugen(), config);
        }
    }
}
