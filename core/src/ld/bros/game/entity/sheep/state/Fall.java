package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;

public class Fall extends EntityState<Sheep> {

    public Fall(Sheep manager) {
        super(manager);
    }

    @Override
    public void enter() {
        manager.sheepLayerNumber = 0;
    }

    @Override
    public void update(float delta) {
        manager.vel.y = manager.GRAVITY;
        manager.move();

        // sheep on ground -> back to idle
        if(manager.hitFloor()) {
            manager.set(new Idle(manager));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "Fall";
    }
}
