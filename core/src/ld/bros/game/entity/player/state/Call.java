package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Res;

public class Call extends PlayerState {

    private float callTimer = 0f;

    public Call(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getPlayerAtlas().findRegions("Idle"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void enter() {
        manager.callSheep();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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
