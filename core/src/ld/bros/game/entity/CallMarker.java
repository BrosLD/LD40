package ld.bros.game.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.main.Res;

public class CallMarker extends Entity {

    private final float MARKER_TIME = 1.5f;
    private float markerTimer;

    private Animation<TextureRegion> animation;
    private float animationTimer;

    private boolean facingRight;

    private final float ALPHA = 0.5f;

    public CallMarker(EntityManager manager, Vector2 pos, boolean facingRight) {
        super(manager);

        animation = new Animation<TextureRegion>(
                0.5f,
                Res.get().getPlayerAtlas().findRegions( "Idle"),
                Animation.PlayMode.LOOP
        );

        this.pos.set(pos);
        this.facingRight = facingRight;
    }

    @Override
    public void update(float delta) {
        animationTimer += delta;

        markerTimer += delta;
        if(markerTimer > MARKER_TIME) {
            manager.remove(this);
        }
    }

    @Override
    public void render(SpriteBatch batch) {

        batch.setColor(1f, 1f, 1f, ALPHA);
        TextureRegion frame = animation.getKeyFrame(animationTimer, true);
        if(facingRight) {
            // flip image
            batch.draw(
                    frame,
                    pos.x + frame.getRegionWidth(),
                    pos.y,
                    -frame.getRegionWidth(),
                    frame.getRegionHeight()
            );
        } else {
            // draw regular
            batch.draw(
                    frame,
                    pos.x,
                    pos.y
            );
        }
        batch.setColor(1f, 1f, 1f, 1f);
    }
}
