package com.github.simkuenzi.lone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.simkuenzi.lone.LoneFighterGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1680;
		config.height = 1050;
		config.fullscreen = true;
		config.forceExit = false;
		new LwjglApplication(new LoneFighterGame(), config);
	}
}
