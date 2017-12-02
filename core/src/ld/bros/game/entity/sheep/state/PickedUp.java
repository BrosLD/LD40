package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;

public class PickedUp extends EntityState<Sheep> {

    public PickedUp(Sheep manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        if(manager.pickedUp) {
            manager.alignToPlayer();
            manager.addOffset();
        } else {
            // not picked up anymore for some reasons -> back to idle
            manager.set(new Idle(manager));
        }

        manager.move();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "PickedUp";
    }
}
