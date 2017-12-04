package ld.bros.game.entity.sheep.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Res;
import ld.bros.game.main.Utils;

public class PickedUp extends SheepState {

    private Vector2 target;

    public PickedUp(Sheep manager) {
        super(manager);

        target = new Vector2();

        animation = new Animation<TextureRegion>(
                0f,
                Res.get().getSheepAtlas().findRegions("PickedUp"),
                Animation.PlayMode.LOOP
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(manager.pickedUp) {

//            manager.alignToPlayer();
//            manager.addOffset();

            // calculate target position
            target.x = manager.getPlayer().pos.x;
            target.y = manager.getPlayer().height + manager.getPlayer().pos.y + manager.pickedUpOffset;

            target.y += manager.height * manager.sheepLayerNumber;

            // apply offset in x direction to achieve 'tower-collapsing' effect
            float dir = Utils.direction(manager.getPlayer().vel.x);
            target.x += (dir * manager.pickedUpDelayedVelocity) * -1 * manager.sheepLayerNumber;

            // velocity is vector between current position and target position
            manager.vel.set(target.sub(manager.pos));
            manager.vel.x = Math.round(manager.vel.x);
            manager.vel.y = Math.round(manager.vel.y);
        }

        manager.move();

        // if sheep hit something it falls down automatically
        if(manager.hitWall()) {
            manager.fallDown();
        }

        //handleFacing();
        // facing depends on player facing in this state
        int direction = Utils.direction(manager.getPlayer().vel.x);
        if(direction == -1) {
            manager.facingRight = false;
        } else if(direction == 1) {
            manager.facingRight = true;
        }
    }

    @Override
    public String toString() {
        return "PickedUp";
    }
}
