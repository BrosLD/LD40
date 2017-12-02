package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class Fall extends EntityState<Player> {

    public Fall(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        // handle user input
        if(Controls.left())
            manager.vel.x = -manager.speed;
        else if(Controls.right())
            manager.vel.x = manager.speed;
        else
            manager.vel.x = 0f;

        manager.vel.y = manager.GRAVITY;
        manager.move();

        // stop falling if we're on ground
        if(manager.onGround()) {
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
