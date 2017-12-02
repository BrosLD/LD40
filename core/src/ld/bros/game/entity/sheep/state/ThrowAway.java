package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;

public class ThrowAway extends EntityState<Sheep> {

    private boolean hitWall;

    public ThrowAway(Sheep manager) {
        super(manager);
    }

    @Override
    public void enter() {
        manager.vel.y = manager.THROW_Y_FORCE;

        manager.vel.x = manager.currThrowXForce;
    }

    @Override
    public void update(float delta) {
        if(hitWall)
            manager.vel.x = 0f;

        manager.vel.y += manager.THROW_DAMPING * delta;
//        if(manager.vel.y < manager.THROW_DAMPING) {
//            manager.vel.y = manager.THROW_DAMPING;
//        }

        manager.move();

        hitWall = manager.hitWall();

        if(manager.hitFloor()) {
            manager.vel.set(0f, 0f);
            manager.set(new Idle(manager));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "ThrowAway";
    }
}
