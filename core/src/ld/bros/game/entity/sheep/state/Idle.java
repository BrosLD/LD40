package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;

public class Idle extends EntityState<Sheep> {

    public Idle(Sheep manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        manager.vel.y = manager.GRAVITY;

//        if(manager.pickedUp) {
//            manager.set(new PickedUp(manager));
//        }

        manager.move();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
