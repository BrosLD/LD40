package ld.bros.game.entity.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.Entity;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.state.Idle;
import ld.bros.game.main.Res;
import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;

import java.util.ArrayDeque;
import java.util.Deque;

public class Player extends Entity implements StateManager {

    // PROPERTIES
    public TextureRegion image;
    public float speed = 5f;

    //
    private Deque<State<?>> states;

    public Player(EntityManager manager) {
        super(manager);

        states = new ArrayDeque<State<?>>();
        set(new Idle(this));

        image = Res.get().quick("dummy/dummy_player.png");

        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    @Override
    public void update(float delta) {

//        // apply gravity
//        vel.y = -9f;
//
//        // handle user input
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
//            vel.x = -speed;
//        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
//            vel.x = speed;
//        else
//            vel.x = 0f;
//
//        // apply velocity
//        move();

        current().update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
//        batch.draw(image, pos.x, pos.y);

        current().render(batch);
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
}
