package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Res;

public class ThrowAway extends SheepState {

    private boolean hitWall;

    public ThrowAway(Sheep manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getSheepAtlas().findRegions("PickedUp"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void enter() {
        manager.vel.y = manager.THROW_Y_FORCE;

        manager.vel.x = manager.currThrowXForce;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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

        handleFacing();
    }

    @Override
    public String toString() {
        return "ThrowAway";
    }
}
