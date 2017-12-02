package ld.bros.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

    protected EntityManager manager;

    public Vector2 pos;
    public Vector2 vel;

    protected float width;
    protected float height;

    // whether we hit something horizontal or vertical
    protected boolean horizontal;
    protected boolean vertical;

    public Entity(EntityManager manager) {
        this.manager = manager;
        this.manager.add(this);

        pos = new Vector2();
        vel = new Vector2();
    }

    public abstract void update(float delta);

    protected void move() {

        vertical = false;
        horizontal = false;

        int y_limit = (int)Math.abs(vel.y);
        int x_limit = (int)Math.abs(vel.x);

        // check in x direction
        for(int vx = 0; vx < x_limit; vx++) {


            // left and right
            if(!manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y)) {
                pos.x += direction(vel.x);
            }
            // upper-slopes \ and /
            else if(manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y)
                    && (!manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y+1))
                    || !manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y+2)) {
                pos.y += 1;
            }
            // lower-slopes (going down)
            else if(manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y)
                    && manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y-1)
                    && !manager.checkCollision(this, (int)pos.x + direction(vel.x), (int)pos.y-2)) {
                pos.y -= 1;
            }
            else {
                vel.x = 0f;
                horizontal = true;
                break;
            }
        }

        // check in y direction
        for(int vy = 0; vy < y_limit; vy++) {
            if(!manager.checkCollision(this, (int)pos.x, (int)pos.y + direction(vel.y))) {
                pos.y += direction(vel.y);
            } else {
                vel.y = 0f;
                vertical = true;
                break;
            }
        }
    }

    public abstract void render(SpriteBatch batch);

    private int direction(float val) {
        if(val > 0) return 1;
        if(val < 0) return -1;
        return 0;
    }
}
