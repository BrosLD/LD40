package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.LudumDare40;
import ld.bros.game.main.*;

import java.text.DecimalFormat;

public class GameOverState extends State<GameStateManager> {

    private TextureRegion background;

    private boolean toggle;
    private TextureRegion end;
    private final float BLINK = 1.7f;
    private float timer = 0f;

    public static final DecimalFormat SCORE_FORMAT = new DecimalFormat("###,###,###,###");
    private final String YOUR_SCORE = "YOUR SCORE:";
    private String score;

    private BitmapFont font;

    private Color MARK_COLOR = Color.BLACK;
    private Color baseColor = Color.WHITE;
    private Color currColor = new Color(baseColor);

    private OrthographicCamera camera;

    public GameOverState(GameStateManager manager) {
        super(manager);

        background = Res.get().getGameOverScreen();

        end = Res.get().getGameOverOverScreen();

        camera = new OrthographicCamera(LudumDare40.WIDTH, LudumDare40.HEIGHT);
        camera.setToOrtho(false);
    }

    @Override
    public void enter() {
        Hud hud = (Hud) Bundle.get().get("hud");
        score = SCORE_FORMAT.format(hud.getScore());

        font = Res.get().font("medium");
    }

    @Override
    public void update(float delta) {
        if(Controls.jumpTapped()) {
            toggle = !toggle;
        }

        if(toggle) {
            timer += delta;
            if(timer > BLINK) {
                timer = 0f;
            }

            // calc current color value
            float percentage = timer / BLINK;

            currColor.r = MARK_COLOR.r + percentage * (baseColor.r - MARK_COLOR.r);
            currColor.g = MARK_COLOR.g + percentage * (baseColor.g - MARK_COLOR.g);
            currColor.b = MARK_COLOR.b + percentage * (baseColor.b - MARK_COLOR.b);
            currColor.a = 1;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);

        batch.draw(background, 0, 0, LudumDare40.WIDTH, LudumDare40.HEIGHT);

        font.draw(batch, YOUR_SCORE, 20, LudumDare40.HEIGHT - 20);
        font.draw(batch, score, 20, LudumDare40.HEIGHT - 20 - 35);

        if(toggle) {
            batch.setColor(currColor);
            batch.draw(end, 0, 0, LudumDare40.WIDTH, LudumDare40.HEIGHT);
            batch.setColor(1,1,1,1);
        }
    }
}
