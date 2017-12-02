package ld.bros.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.main.Res;

public class Player extends Entity {

    private TextureRegion image;
    private float speed = 5f;

    public Player(EntityManager manager) {
        super(manager);

        image = Res.get().quick("dummy/dummy_player.png");

        width = image.getRegionWidth();
        height = image.getRegionHeight();
    }

    @Override
    public void update(float delta) {

        // apply gravity
        vel.y = -9f;

        // handle user input
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            vel.x = -speed;
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            vel.x = speed;
        else
            vel.x = 0f;

        // apply velocity
        move();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(image, pos.x, pos.y);
    }
}
