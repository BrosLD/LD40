package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Res;
import ld.bros.game.main.Utils;

public class Call extends SheepState {

    private float target;
    private int direction;

    private boolean done;

    public Call(Sheep manager) {
        super(manager);

        target = manager.targetX - manager.pos.x;
        direction = Utils.direction(target);

        animation = new Animation<TextureRegion>(
                0.2f,
                Res.get().getSheepAtlas().findRegions("Run"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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

        handleFacing();
    }

    @Override
    public String toString() {
        return "Call";
    }
}
