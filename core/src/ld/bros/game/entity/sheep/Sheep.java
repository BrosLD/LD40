package ld.bros.game.entity.sheep;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.Entity;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.Player;
import ld.bros.game.entity.sheep.state.*;
import ld.bros.game.main.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class Sheep extends Entity implements StateManager {

    private static int id = 0;
    private int localId;

    // PROPERTIES
    public TextureRegion image;

    public boolean pickedUp;
    public final float GRAVITY = -9f;

    public final float THROW_X_FORCE = 7f;
    public float currThrowXForce;
    public final float THROW_Y_FORCE = 6f;
    public final float THROW_DAMPING = -25f;

    public int sheepLayerNumber;
    public float pickedUpDelayedVelocity = 4f;
    public float pickedUpOffset = 3f;

    public float speed = 4f;

    public float targetX;

    public boolean facingRight;

    //
    private Player player;

    private Deque<State<?>> states;

    public Sheep(EntityManager manager) {
        super(manager);

        localId = ++id;

        states = new ArrayDeque<State<?>>();
        set(new Idle(this));

        image = Res.get().quick("dummy/dummy_sheep.png");
        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    @Override
    public void update(float delta) {
        current().update(delta);

        if(localId == 1) {
            TextDisplayer.get().print("Sheep::" + current());
            TextDisplayer.get().print("  - Position: " + pos);
            TextDisplayer.get().print("  - Target  : " + targetX);
            TextDisplayer.get().print("  - HitWall : " + hitWall());
            TextDisplayer.get().print("  - HitFloor: " + hitFloor());
        }
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
        alignToPlayer();
    }

    /**
     * Whether this Sheep can be picked up or not.
     */
    public boolean canBePickedUp(Player player) {
        // align position to player
        float newX = player.pos.x;
        float newY = player.height + player.pos.y + pickedUpOffset;
        newY += pos.y += height * sheepLayerNumber;

        boolean check = manager.checkCollision(this, (int)newX, (int)newY);
        if(check) {
            // there will be a collision -> not able to pick up this sheep
            return false;
        }

        return true;
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

    public void fallDown() {
        pickedUp = false;

        // remove this sheep from players list
        player.removeSheep(this);
        player = null;

        set(new Fall(this));
    }

    public boolean hitWall() {
        return horizontal;
    }

    public boolean hitFloor() {
        return onGround;
    }

    public Player getPlayer() {
        return player;
    }

    public void callSheep(float targetX) {
        this.targetX = targetX;
        set(new Call(this));
    }
}
