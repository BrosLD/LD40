package ld.bros.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

// class for handling controls in a central manner
public class Controls {

    public static boolean left() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    public static boolean right() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    public static boolean jump() {
        return Gdx.input.isKeyPressed(Input.Keys.X);
    }

    public static boolean jumpTapped() {
        return Gdx.input.isKeyJustPressed(Input.Keys.X);
    }

    public static boolean pick() {
        return Gdx.input.isKeyPressed(Input.Keys.C);
    }
}
