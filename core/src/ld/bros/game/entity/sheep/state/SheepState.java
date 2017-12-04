package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.EntityState;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Utils;

public abstract class SheepState extends EntityState<Sheep> {

    protected Animation<TextureRegion> animation;
    protected float animationTimer;

    public SheepState(Sheep manager) {
        super(manager);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        animationTimer += delta;
    }

    protected void handleFacing() {
        // check facing direction of player
        int direction = Utils.direction(manager.vel.x);
        if(direction == -1) {
            manager.facingRight = false;
        } else if(direction == 1) {
            manager.facingRight = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
                animation.getKeyFrame(animationTimer, true),
                manager.pos.x,
                manager.pos.y
        );
    }
}
