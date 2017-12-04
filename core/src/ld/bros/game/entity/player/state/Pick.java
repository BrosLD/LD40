package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Res;

public class Pick extends PlayerState {

    private float timer = 0f;

    public Pick(Player manager) {
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

        timer += delta;
        if(timer >= manager.PICK_UP_TIME) {
            timer = 0f;

            // player should pick up corresponding entity
            manager.pickUp();

            // back to previous state
            manager.removeFirst();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(manager.image, manager.pos.x, manager.pos.y);
    }

    @Override
    public String toString() {
        return "Pick";
    }
}
