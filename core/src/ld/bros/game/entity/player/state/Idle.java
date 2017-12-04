package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Res;

public class Idle extends PlayerState {

    public Idle(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.5f,
                Res.get().getPlayerAtlas().findRegions( "Idle"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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

        if(Controls.pick() && manager.canPickUp()) {
            // pick up
            manager.push(new Pick(manager));
        }

        if(Controls.callTapped()) {
            // call sheep
            manager.set(new Call(manager));
        }

        checkPickUp();
        handleFacing();
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
