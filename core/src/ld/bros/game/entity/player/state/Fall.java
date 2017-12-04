package ld.bros.game.entity.player.state;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.Controls;
import ld.bros.game.main.Res;

public class Fall extends PlayerState {

    private boolean doubleJump;

    public Fall(Player manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0,
                Res.get().getPlayerAtlas().findRegions("Jump"),
                Animation.PlayMode.NORMAL
        );
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // handle user input
        if(Controls.left())
            manager.vel.x = -manager.getSpeed();
        else if(Controls.right())
            manager.vel.x = manager.getSpeed();
        else
            manager.vel.x = 0f;

        manager.vel.y = manager.getGravity();
        manager.move();

        // stop falling if we're on ground
        if(manager.onGround()) {
            manager.set(new Idle(manager));
        }

        // double jump
        if(!doubleJump && Controls.jumpTapped()) {
            manager.push(new DoubleJump(manager));
            doubleJump = true;
        }

        checkPickUp();
        handleFacing();
    }

    @Override
    public String toString() {
        return "Fall";
    }
}
