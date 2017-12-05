package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ld.bros.game.LudumDare40;
import ld.bros.game.main.Controls;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.Res;
import ld.bros.game.main.State;

public class StartScreen extends State<GameStateManager> {

    private TextureRegion background;

    public StartScreen(GameStateManager manager) {
        super(manager);

        background = Res.get().getStartScreen();
    }

    @Override
    public void update(float delta) {
        if(Controls.jumpTapped()) {
            manager.set(
                    new Transition(manager, this, new MainState(manager))
            );
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, LudumDare40.WIDTH, LudumDare40.HEIGHT);
    }
}
