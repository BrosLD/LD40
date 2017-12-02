package ld.bros.game.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface StateManager {

    void update(float delta);

    void render(SpriteBatch batch);

    void removeFirst();

    void set(State<?> s);

    void push(State<?> s);

    State<?> current();
}
