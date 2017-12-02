package ld.bros.game.entity.player.state;

import ld.bros.game.entity.player.Player;

public class Throwing extends PlayerState {

    private float throwTimer = 0f;

    public Throwing(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        // stop moving
        // manager.vel.set(0f, 0f);

        throwTimer += delta;
        if(throwTimer >= manager.THROW_TIME) {
            throwTimer = 0f;

            // player should throw now
            manager.throwEntities();

            manager.removeFirst();
        }
    }

    @Override
    public String toString() {
        return "Throwing";
    }
}
