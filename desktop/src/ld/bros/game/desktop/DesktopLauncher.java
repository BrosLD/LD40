package ld.bros.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ld.bros.game.LudumDare40;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = LudumDare40.WIDTH;
		config.height = LudumDare40.HEIGHT;
		config.title = LudumDare40.TITLE;

		new LwjglApplication(new LudumDare40(), config);
	}
}
