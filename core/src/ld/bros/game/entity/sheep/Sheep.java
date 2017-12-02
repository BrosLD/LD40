package ld.bros.game.entity.sheep;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.Entity;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.Player;
import ld.bros.game.entity.sheep.state.Idle;
import ld.bros.game.entity.sheep.state.PickedUp;
import ld.bros.game.entity.sheep.state.ThrowAway;
import ld.bros.game.main.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class Sheep extends Entity implements StateManager {

    // PROPERTIES
    public TextureRegion image;

    public boolean pickedUp;
    public final float GRAVITY = -9f;

    public final float THROW_X_FORCE = 7f;
    public float currThrowXForce;
    public final float THROW_Y_FORCE = 6f;
    public final float THROW_DAMPING = -25f;

    //
    private Player player;

    private int sheepLayerNumber;
    private float pickedUpDelayedVelocity = 4f;
    private float pickedUpOffset = 3f;

    private Deque<State<?>> states;

    public Sheep(EntityManager manager) {
        super(manager);

        states = new ArrayDeque<State<?>>();
        set(new Idle(this));

        image = Res.get().quick("dummy/dummy_sheep.png");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        current().update(delta);

        TextDisplayer.get().print(sheepLayerNumber + "# Sheep::" + current());
    }

    @Override
    public void render(SpriteBatch batch) {
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

    // when player picks up this sheep
    public void pickUp(Player player) {
        pickedUp = true;
        this.player = player;
        sheepLayerNumber = player.getNumberOfSheep();

        set(new PickedUp(this));

        // align position to player
        alignToPlayer();
    }

    public void alignToPlayer() {
        pos.x = player.pos.x;
        pos.y = player.height + player.pos.y + pickedUpOffset;

        pos.y += height * sheepLayerNumber;
    }

    // when player throws this sheep
    public void throwAway() {
        pickedUp = false;

        // if player is looking to the right, throw sheep to the right hand side
        if(player.facingRight) {
            currThrowXForce = THROW_X_FORCE;
        } else {
            currThrowXForce = -THROW_X_FORCE;
        }

        player = null;

        set(new ThrowAway(this));
    }

    public void addOffset() {
        // add little offset in x direction to achieve 'tower-collapsing' effect
        float dir = Utils.direction(player.vel.x);
        vel.x = (dir * pickedUpDelayedVelocity) * -1 * sheepLayerNumber;
    }

    public boolean hitWall() {
        return horizontal;
    }

    public boolean hitFloor() {
        return vertical;
    }
}
