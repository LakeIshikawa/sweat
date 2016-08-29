package com.lksoft.sweat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.lksoft.sweat.SweatGame;

public class DesktopLauncher {

	public static void main (String[] args) {
		DesktopLauncher launcher = new DesktopLauncher();
        launcher.launch();
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
            Class<IScriptCompiler> cls = ClassReflection.forName("com.lksoft.sweat.desktop.dev.ScriptCompiler");
            IScriptCompiler compiler = cls.newInstance();
            compiler.compileScripts();

            System.out.println("Starting YUGEN in debug mode");
            new LwjglApplication(new SweatGame(true), config);
        } catch (ReflectionException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new SweatGame(false), config);
        } catch (InstantiationException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new SweatGame(false), config);
        } catch (IllegalAccessException e) {
            System.out.println("Starting YUGEN in normal mode");
            new LwjglApplication(new SweatGame(false), config);
        }
    }
}
