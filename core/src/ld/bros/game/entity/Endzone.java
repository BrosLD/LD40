package ld.bros.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import ld.bros.game.entity.player.Player;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.Res;
import ld.bros.game.main.TextDisplayer;

public class Endzone extends Entity {

    private Animation<TextureRegion> animation;
    private float animationTimer;

    private TiledDrawable frame;

    public Endzone(EntityManager manager) {
        super(manager);

        animation = new Animation<TextureRegion>(
                1f,
                Res.get().getEndzoneAtlas().findRegions("Idle"),
                Animation.PlayMode.LOOP
        );

        // to draw frame repeated over width and height
        frame = new TiledDrawable();
    }

    @Override
    public void update(float delta) {
        animationTimer += delta;

        Entity e;
        if((e = manager.collisionWithEntity(this)) != null) {
            TextDisplayer.get().print("In Endzone: " + e.getClass().getSimpleName());

            if(e instanceof Sheep) {
                // sheep in endzone! Touchdown
                manager.sheepInEndzone((Sheep)e);
            }

            if(e instanceof Player) {
                // player in endzone!
                manager.playerInEndzone((Player)e);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        frame.setRegion(animation.getKeyFrame(animationTimer, true));
        frame.draw(batch, pos.x, pos.y, width, height);
    }
}
