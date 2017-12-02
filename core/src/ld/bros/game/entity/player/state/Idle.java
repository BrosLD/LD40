package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class Idle extends EntityState<Player> {

    public Idle(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        // apply gravity
        manager.vel.y = manager.GRAVITY;
        manager.vel.x = 0f;

        if(Controls.left() || Controls.right()) {
            // switch to run-state
            manager.set(new Run(manager));
        }

        // check if we're jumping
        if(Controls.jumpTapped()) {
            manager.set(new Jump(manager));
        }

        manager.move();

        // if not on ground -> falling
        if(!manager.onGround()) {
            manager.set(new Fall(manager));
        }
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