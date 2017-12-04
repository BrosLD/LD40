package ld.bros.game.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import ld.bros.game.LudumDare40;

import java.text.DecimalFormat;

public class Hud {
    // remaining time for stage
    private Vector2 timerPos;
    private int remainingTime;
    private float stageTimer;
    private String remainingTimeLabel;

    // sheep counter
    private Vector2 sheepPos;
    private final float padding = 4f;
    private int numSheepDead;
    private int numSheepClear;
    private int numSheepRemaining;
    private TextureRegion sheepClear;
    private TextureRegion sheepDead;
    private TextureRegion sheep;

    // score
    public static final DecimalFormat SCORE_FORMAT = new DecimalFormat("###,###,###,###");
    private Vector2 scorePos;
    private long score;
    private String scoreLabel;

    // level
    private Vector2 currLevelPos;
    private String currLevel;

    // camera for central hud
    private OrthographicCamera camera;
    private Matrix4 previous;

    private BitmapFont font;

    public Hud() {
        camera = new OrthographicCamera(LudumDare40.WIDTH, LudumDare40.HEIGHT);
        camera.setToOrtho(false);

        timerPos = new Vector2();
        sheepPos = new Vector2();
        scorePos = new Vector2();
        currLevelPos = new Vector2();

        font = Res.get().font("hud");

        sheepPos.y = LudumDare40.HEIGHT - 64f;
        sheepPos.x = 16f;

        sheep = Res.get().getHudAtlas().findRegion("sheep");
        sheepClear = Res.get().getHudAtlas().findRegion("sheep_ok");
        sheepDead = Res.get().getHudAtlas().findRegion("sheep_dead");
    }

    public void update(float delta) {
        // count seconds down
        stageTimer += delta;
        if(stageTimer >= 1f) {
            stageTimer = 0f;

            if(remainingTime > 0)
                setRemainingTime(remainingTime-1);
        }
    }

    public void render(SpriteBatch batch) {
        previous = batch.getProjectionMatrix();

        batch.setProjectionMatrix(camera.combined);

        font.draw(batch, currLevel, currLevelPos.x, currLevelPos.y);
        font.draw(batch, scoreLabel, scorePos.x, scorePos.y);
        font.draw(batch, remainingTimeLabel, timerPos.x, timerPos.y);

        // draw sheep icons
        int index = 0;
        for(int i = index; i < numSheepClear + index; i++) {
            batch.draw(sheepClear,
                    sheepPos.x + (sheep.getRegionWidth() + padding) * i,
                    sheepPos.y);
        }
        index = numSheepClear;
        for(int i = index; i < numSheepDead + index; i++) {
            batch.draw(sheepDead,
                    sheepPos.x + (sheep.getRegionWidth() + padding) * i,
                    sheepPos.y);
        }
        index = numSheepClear + numSheepDead;
        for(int i = index; i < numSheepRemaining + index; i++) {
            batch.draw(sheep,
                    sheepPos.x + (sheep.getRegionWidth() + padding) * i,
                    sheepPos.y);
        }

        batch.setProjectionMatrix(previous);
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;

        remainingTimeLabel = String.format("Time:%03d", this.remainingTime);

        timerPos.y = LudumDare40.HEIGHT - 36f;
        timerPos.x = LudumDare40.WIDTH - 16f;
        timerPos.x -= Utils.calcDimensions(font, remainingTimeLabel).x;
    }

    public float getStageTimer() {
        return stageTimer;
    }

    public void setStageTimer(float stageTimer) {
        this.stageTimer = stageTimer;
    }

    public int getNumSheepDead() {
        return numSheepDead;
    }

    public void setNumSheepDead(int numSheepDead) {
        this.numSheepDead = numSheepDead;
    }

    public int getNumSheepClear() {
        return numSheepClear;
    }

    public void setNumSheepClear(int numSheepClear) {
        this.numSheepClear = numSheepClear;
    }

    public int getNumSheepRemaining() {
        return numSheepRemaining;
    }

    public void setNumSheepRemaining(int numSheepRemaining) {
        this.numSheepRemaining = numSheepRemaining;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;

        scoreLabel = SCORE_FORMAT.format(this.score);

        scorePos.y = LudumDare40.HEIGHT - 16f;
        scorePos.x = LudumDare40.WIDTH - 16f;
        scorePos.x -= Utils.calcDimensions(font, scoreLabel).x;
    }

    public String getCurrLevel() {
        return currLevel;
    }

    public void setCurrLevel(String currLevel) {
        this.currLevel = currLevel;

        currLevelPos.y = LudumDare40.HEIGHT - 16f;
        currLevelPos.x = 16f;
    }
}
