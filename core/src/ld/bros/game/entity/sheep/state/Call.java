package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Utils;

public class Call extends EntityState<Sheep> {

    private float target;
    private int direction;

    private boolean done;

    public Call(Sheep manager) {
        super(manager);

        target = manager.targetX - manager.pos.x;
        direction = Utils.direction(target);
    }

    @Override
    public void update(float delta) {
        manager.vel.x = direction * manager.speed;
        manager.vel.y = manager.GRAVITY;

        manager.move();

        if(manager.hitWall()) {
            // stop walking. Back to idle-state
            manager.set(new Idle(manager));
        }

        if(direction == 1 && manager.pos.x > manager.targetX) {
            done = true;
        } else if(direction == -1 && manager.pos.x < manager.targetX) {
            done = true;
        }

        if(done) {
            manager.pos.x = manager.targetX;
            manager.vel.x = 0f;
            manager.set(new Idle(manager));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "Call";
    }
}
