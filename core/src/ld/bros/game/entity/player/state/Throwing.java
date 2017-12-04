package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Res;

public class Throwing extends PlayerState {

    private float throwTimer = 0f;

    public Throwing(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getPlayerAtlas().findRegions("Idle"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

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
