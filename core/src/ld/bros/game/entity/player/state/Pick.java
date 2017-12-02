package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.player.Player;

public class Pick extends EntityState<Player> {

    private float timer = 0f;

    public Pick(Player manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
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
