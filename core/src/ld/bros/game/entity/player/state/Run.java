package ld.bros.game.entity.player.state;

import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;

public class Run extends PlayerState {

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

        if(Controls.pick() && manager.canPickUp()) {
            // pick up
            manager.push(new Pick(manager));
        }

        super.update(delta);
    }

    @Override
    public String toString() {
        return "Run";
    }
}
