package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class DoubleJump extends EntityState<Player> {

    private float jumpTimer;
    private float highTimer;
    private boolean reachedHigh;

    public DoubleJump(Player manager) {
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

        if(!reachedHigh) {
            if(Controls.jump()) {
                // check if player can jump any higher/longer
                jumpTimer += delta;
                if(jumpTimer >= manager.MAX_DOUBLE_JUMP_TIME) {
                    reachedHigh = true;
                    highTimer = 0f;
                }

                // apply jump speed
                manager.vel.y = manager.jumpSpeed;
            } else {
                // jump button released - jump ends
                reachedHigh = true;
            }
        } else {
            // reached high - stay some time in midair (better feeling)
            manager.vel.y = 0f;

            highTimer += delta;
            if(highTimer >= manager.MAX_HIGH_TIME) {
                // back to Fall-state
                manager.removeFirst();
            }
        }

        manager.move();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "DoubleJump";
    }
}
