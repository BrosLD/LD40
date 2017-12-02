package ld.bros.game.entity.player.state;

import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class Idle extends PlayerState {

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

        if(Controls.pick() && manager.canPickUp()) {
            // pick up
            manager.push(new Pick(manager));
        }

        if(Controls.callTapped()) {
            // call sheep
            manager.set(new Call(manager));
        }

        super.update(delta);
    }

    @Override
    public String toString() {
        return "Idle";
    }
}
