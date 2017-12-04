package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Res;

public class Jump extends PlayerState {

    private float jumpTimer;
    private float highTimer;
    private boolean reachedHigh;

    public Jump(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getPlayerAtlas().findRegions("Jump"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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
                if(jumpTimer >= manager.MAX_JUMP_TIME) {
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
                // set to Fall-state
                manager.set(new Fall(manager));
            }
        }

        manager.move();

        checkPickUp();
        handleFacing();
    }

    @Override
    public String toString() {
        return "Jump";
    }
}
