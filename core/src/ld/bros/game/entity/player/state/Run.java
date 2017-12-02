package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class Run extends EntityState<Player> {

    public Run(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        // apply gravity
        manager.vel.y = manager.GRAVITY;

        // handle user input
        if(Controls.left())
            manager.vel.x = -manager.speed;
        else if(Controls.right())
            manager.vel.x = manager.speed;
        else {
            // switch to Idle-state
            manager.set(new Idle(manager));
        }

        // check if we're jumping
        if(Controls.jumpTapped()) {
            manager.set(new Jump(manager));
        }

        // apply velocity
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
        return "Run";
    }
}
