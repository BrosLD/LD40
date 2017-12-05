package ld.bros.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ld.bros.game.gamestates.MainState;
import ld.bros.game.gamestates.StartScreen;
import ld.bros.game.gamestates.Transition;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.Res;
import ld.bros.game.main.TextDisplayer;

public class LudumDare40 extends ApplicationAdapter {

	// config
	public static final String TITLE = "Ludum Dare 40";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int FPS = 60;

	private SpriteBatch batch;
	private GameStateManager gsm;

	private OrthographicCamera camera;
	private FitViewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera(WIDTH, HEIGHT);
		camera.setToOrtho(false);
		viewport = new FitViewport(WIDTH, HEIGHT, camera);

		gsm = new GameStateManager();
		gsm.setCamera(camera);

		// set MainState as starting state
//		gsm.set(new Transition(gsm, null, new MainState(gsm)));
		gsm.set(new StartScreen(gsm));
	}

	public void update(float delta) {
		TextDisplayer.get().print("FPS: " + Gdx.graphics.getFramesPerSecond());
		gsm.update(delta);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(Gdx.graphics.getDeltaTime());

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		gsm.render(batch);

		TextDisplayer.get().render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		Res.get().dispose();
	}
}
