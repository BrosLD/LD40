package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Res;

public class Run extends PlayerState {

    public Run(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.2f,
                Res.get().getPlayerAtlas().findRegions("Run"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // apply gravity
        manager.vel.y = manager.getGravity();

        // handle user input
        if(Controls.left())
            manager.vel.x = -manager.getSpeed();
        else if(Controls.right())
            manager.vel.x = manager.getSpeed();
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
        return "Run";
    }
}
