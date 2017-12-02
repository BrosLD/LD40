package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Utils;

public abstract class PlayerState extends EntityState<Player> {

    public PlayerState(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // check if player will throw sheeps
        if(!Controls.pick() && manager.getNumberOfSheep() > 0) {
            manager.push(new Throwing(manager));
        }

        // check facing direction of player
        int direction = Utils.direction(manager.vel.x);
        if(direction == -1) {
            manager.facingRight = false;
        } else if(direction == 1) {
            manager.facingRight = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }
}
