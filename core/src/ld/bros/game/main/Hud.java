package ld.bros.game.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import ld.bros.game.LudumDare40;

public class Hud {
    // remaining time for stage
    private float remainingTime;
    private float stageTimer;

    // sheep counter
    private int numSheepDead;
    private int numSheepClear;
    private int numSheepRemaining;

    // score
    private long score;

    // level
    private String currLevel;

    // camera for central hud
    private OrthographicCamera camera;
    private Matrix4 previous;

    public Hud() {
        camera = new OrthographicCamera(LudumDare40.WIDTH, LudumDare40.HEIGHT);
        camera.setToOrtho(false);
    }

    public void update(float delta) {

    }

    public void render(SpriteBatch batch) {
        previous = batch.getProjectionMatrix();

        batch.setProjectionMatrix(camera.combined);
        // draw here

        batch.setProjectionMatrix(previous);
    }

    public float getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(float remainingTime) {
        this.remainingTime = remainingTime;
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
    }

    public String getCurrLevel() {
        return currLevel;
    }

    public void setCurrLevel(String currLevel) {
        this.currLevel = currLevel;
    }
}
