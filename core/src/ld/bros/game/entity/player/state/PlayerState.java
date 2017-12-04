package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Utils;

public abstract class PlayerState extends EntityState<Player> {

    protected Animation<TextureRegion> animation;
    protected float animationTimer;

    public PlayerState(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        animationTimer += delta;
    }

    protected void handleFacing() {
        // check facing direction of player
        int direction = Utils.direction(manager.vel.x);
        if(direction == -1) {
            manager.facingRight = false;
        } else if(direction == 1) {
            manager.facingRight = true;
        }
    }

    protected void checkPickUp() {
        // check if player will throw sheeps
        if(!Controls.pick() && manager.getNumberOfSheep() > 0) {
            manager.push(new Throwing(manager));
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion frame = animation.getKeyFrame(animationTimer, true);
        if(manager.facingRight) {
            // flip image
            batch.draw(
                    frame,
                    manager.pos.x + frame.getRegionWidth(),
                    manager.pos.y,
                    -frame.getRegionWidth(),
                    frame.getRegionHeight()
            );
        } else {
            // draw regular
            batch.draw(
                    frame,
                    manager.pos.x,
                    manager.pos.y
            );
        }
    }
}
