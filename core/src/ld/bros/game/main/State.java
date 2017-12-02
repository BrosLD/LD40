package ld.bros.game.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State<T extends StateManager> {

    protected T manager;

    public State(T manager) {
        this.manager = manager;
    }

    public void enter() {

    }

    public void update(float delta) {

    }

    public void render(SpriteBatch batch) {

    }

    public void finish() {

    }
}
