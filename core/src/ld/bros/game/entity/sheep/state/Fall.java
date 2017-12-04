package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Res;

public class Fall extends SheepState {

    public Fall(Sheep manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getSheepAtlas().findRegions("Idle"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void enter() {
        manager.sheepLayerNumber = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        manager.vel.y = manager.GRAVITY;
        manager.move();

        // sheep on ground -> back to idle
        if(manager.hitFloor()) {
            manager.set(new Idle(manager));
        }

        handleFacing();
    }

    @Override
    public String toString() {
        return "Fall";
    }
}
