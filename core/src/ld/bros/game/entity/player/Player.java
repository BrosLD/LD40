package ld.bros.game.entity.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.CallMarker;
import ld.bros.game.entity.Entity;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.state.Idle;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;
import ld.bros.game.main.TextDisplayer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Player extends Entity implements StateManager {

    // PROPERTIES
    private float speed = 5f;

    private float jumpSpeed = 10f;
    public final float MAX_JUMP_TIME = 0.25f;
    public final float MAX_DOUBLE_JUMP_TIME = 0.17f;
    public final float MAX_HIGH_TIME = 0.08f;

    // 0 for now as there is no animation yet
    public final float PICK_UP_TIME = 0f;
    public final float THROW_TIME = 0f;

    private final float GRAVITY = -9f;

    public boolean facingRight;

    public final float CALL_TIME = 0f;

    // modifies speed values.
    private final int MAX_NUMBER_OF_SHEEP = 6;

    //
    private Deque<State<?>> states;

    private List<Sheep> pickedSheep;

    private CallMarker callMarker;

    public Player(EntityManager manager) {
        super(manager);

        states = new ArrayDeque<State<?>>();
        set(new Idle(this));

        width = 32f;
        height = 64f;

        pickedSheep = new ArrayList<Sheep>();
    }

    @Override
    public void update(float delta) {
        current().update(delta);
        TextDisplayer.get().print("Player::" + current());
        TextDisplayer.get().print("  - FacingRight: " + facingRight);
        TextDisplayer.get().print("  - #Sheeps: " + getNumberOfSheep());

        if(dead)
            manager.resetPlayer();
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

    public boolean onGround() {
        return vertical;
    }

    public void pickUp() {
        Entity other = manager.collisionWithEntity(this);
        if(other != null && other instanceof Sheep) {
            Sheep s = (Sheep) other;

            // check if sheep can be picked up
            if(s.canBePickedUp(this))
                s.pickUp(this);
                pickedSheep.add(s);
        }
    }

    public boolean canPickUp() {

        if(getNumberOfSheep() >= MAX_NUMBER_OF_SHEEP) {
            // we already carry to much Sheep
            return false;
        }

        Entity other = manager.collisionWithEntity(this);
        if(other != null && other instanceof Sheep) {
            Sheep s = (Sheep) other;

            // check if sheep can be picked up
            return s.canBePickedUp(this);
        }

        return false;
    }

    public void throwEntities() {
        for(int i = 0; i < pickedSheep.size(); i++) {
            pickedSheep.get(i).throwAway();
        }

        pickedSheep.clear();
    }

    public int getNumberOfSheep() {
        return pickedSheep.size();
    }

    public void removeSheep(Sheep sheep) {
        pickedSheep.remove(sheep);
    }

    public void callSheep() {
        List<Entity> entities = manager.getEntityList();
        for(int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if(e instanceof Sheep) {
                Sheep s = (Sheep) e;
                s.callSheep(this.pos.x);
            }
        }

        // create call marker on current position
        if(callMarker != null) {
            manager.remove(callMarker);
        }
        callMarker = new CallMarker(manager, pos.cpy(), facingRight);
    }

    public float getSpeed() {
        float r = speed * calcModifier();
        return Math.max(r, 1f);
    }

    public float getJumpSpeed() {
        float r = jumpSpeed * calcModifier();;
        return Math.max(r, 1f);
    }

    public float getGravity() {
        return GRAVITY * (1 + (getNumberOfSheep() / (float)MAX_NUMBER_OF_SHEEP));
    }

    private float calcModifier() {
        return (1 - (getNumberOfSheep() / (float)MAX_NUMBER_OF_SHEEP));
    }
}
