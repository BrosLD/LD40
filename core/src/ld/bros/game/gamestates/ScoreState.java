package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import ld.bros.game.LudumDare40;
import ld.bros.game.main.*;

import java.text.DecimalFormat;

public class ScoreState extends State<GameStateManager> {

    public static final DecimalFormat SCORE_FORMAT = new DecimalFormat("###,###,###,###");

    private final long TIME_BONUS = 10;
    private final long SHEEP_MULTIPLIER = 5;
    private final long SHEEP_BONUS = 10;

    private TextureRegion background;

    private OrthographicCamera camera;
    private Matrix4 previous;

    private long targetScore;
    private long currentScore;

    private final float TICK = 0.04f;
    private float timer;

    private boolean done;

    private String scoreLabel;
    private BitmapFont font;
    private float y = LudumDare40.HEIGHT / 2f + 35f;

    private Hud hud;

    private State from;

    public ScoreState(GameStateManager manager, State from) {
        super(manager);

        this.from = from;

        background = Res.get().getScoreBackground();
        camera = new OrthographicCamera(LudumDare40.WIDTH, LudumDare40.HEIGHT);
        camera.setToOrtho(false);

        font = Res.get().font("large");
    }

    @Override
    public void enter() {
        hud = (Hud) Bundle.get().get("hud");
        int numSheep = hud.getNumSheepClear();
        int remainingTime = hud.getRemainingTime();

        targetScore = remainingTime * TIME_BONUS + (remainingTime * numSheep) * SHEEP_MULTIPLIER + numSheep * SHEEP_BONUS;
    }

    @Override
    public void update(float delta) {

        if(!done) {
            timer += delta;
            if (timer > TICK) {
                timer = 0f;

                currentScore += 1000L;
                if (currentScore >= targetScore) {
                    currentScore = targetScore;

                    done = true;
                }
            }
        }

        if(done && Controls.jumpTapped()) {
            // apply score to hud
            hud.setScore(hud.getScore() + targetScore);
            // save hud back to bundle
            Bundle.get().put("hud", hud);

            // return to previous state
            manager.removeFirst();
        }

        if(Controls.jump()) {
            currentScore = targetScore;
            done = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        if(from != null)
            from.render(batch);
//        batch.end();
//        batch.begin();

        previous = batch.getProjectionMatrix();
        batch.setProjectionMatrix(camera.combined);

//        float xOff = manager.getCamera().position.x - LudumDare40.WIDTH /2f;
//        float yOff = manager.getCamera().position.y - LudumDare40.HEIGHT /2f;
        float xOff = 0f;
        float yOff = 0f;

        batch.draw(background,  xOff, yOff, LudumDare40.WIDTH, LudumDare40.HEIGHT);
        scoreLabel = "+ " + SCORE_FORMAT.format(currentScore);
        float x = (LudumDare40.WIDTH - Utils.calcDimensions(font, scoreLabel).x)/2f;

        font.draw(batch, scoreLabel, xOff + x, yOff + y);

        batch.setProjectionMatrix(previous);
//        batch.end();
//        batch.begin();
    }
}
