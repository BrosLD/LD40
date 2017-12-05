package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import ld.bros.game.LudumDare40;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.Res;
import ld.bros.game.main.State;

public class Transition extends State<GameStateManager> {

    enum Type {
        INTRO, STAY, OUTRO
    }

    private State<GameStateManager> from;
    private State<GameStateManager> to;

    private final float INTRO = 0.4f;
    private final float STAY = 0.2f;
    private final float OUTRO = INTRO;
    private Type curr;
    private float timer;

    private TextureRegion background;

    public Transition(GameStateManager manager, State<GameStateManager> from, State<GameStateManager> to) {
        super(manager);

        this.from = from;
        this.to = to;

        background = Res.get().pixel();

        curr = Type.INTRO;
    }

    @Override
    public void update(float delta) {
        timer += delta;
        switch (curr) {
            case INTRO:
                if(timer >= INTRO) {
                    timer = 0f;
                    curr = Type.STAY;
                }
                break;
            case STAY:
                if(timer >= STAY) {
                    timer = 0f;
                    curr = Type.OUTRO;
                }
                break;
            case OUTRO:
                if(timer >= OUTRO) {
                    timer = 0f;
                    // set to as current state
                    manager.set(to);
                }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (curr) {
            case INTRO:
                if(from != null)
                    from.render(batch);

                float h = MathUtils.lerp(0, LudumDare40.HEIGHT, timer/INTRO);
                batch.draw(
                        background,
                        0,
                        0,
                        LudumDare40.WIDTH,
                        h);
                break;
            case STAY:
                batch.draw(background, 0, 0, LudumDare40.WIDTH, LudumDare40.HEIGHT);
                break;
            case OUTRO:
                to.render(batch);
                float h2 = MathUtils.lerp(LudumDare40.HEIGHT, 0, timer/OUTRO);
                float y2 = MathUtils.lerp(0, LudumDare40.HEIGHT, timer/OUTRO);
                batch.draw(background, 0, y2, LudumDare40.WIDTH, h2);
        }
    }
}
