package ld.bros.game.main;

import java.util.Deque;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class GameStateManager implements StateManager {

    private Deque<State<?>> states;

    private OrthographicCamera camera;

    public GameStateManager() {
        states = new LinkedList<State<?>>();
    }

    @Override
    public void update(float delta) {
        states.peekFirst().update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        states.peekFirst().render(batch);
    }

    @Override
    public void removeFirst() {
        states.removeFirst();
        states.peekFirst().enter();
    }

    @Override
    public void set(State<?> s) {
        if(!states.isEmpty())
            states.removeFirst();

        push(s);
    }

    @Override
    public void push(State<?> s) {
        states.addFirst(s);
        states.peekFirst().enter();
    }

    @Override
    public State<?> current() {
        return states.peekFirst();
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}
