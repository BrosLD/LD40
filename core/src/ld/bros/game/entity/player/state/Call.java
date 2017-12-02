package ld.bros.game.entity.player.state;

import ld.bros.game.entity.player.Player;

public class Call extends PlayerState {

    private float callTimer = 0f;

    public Call(Player manager) {
        super(manager);
    }

    @Override
    public void enter() {
        manager.callSheep();
    }

    @Override
    public void update(float delta) {
        callTimer += delta;
        if(callTimer > manager.CALL_TIME) {
            callTimer = 0f;

            // call finished, back to idle-state
            manager.set(new Idle(manager));
        }
    }

    @Override
    public String toString() {
        return "Call";
    }
}
