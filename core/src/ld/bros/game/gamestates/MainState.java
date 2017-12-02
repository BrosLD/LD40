package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.Player;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.State;

public class MainState extends State<GameStateManager> {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private EntityManager entities;
    private Player player;

    public MainState(GameStateManager manager) {
        super(manager);

        // load map
        String m = "dummy/dummy_map.tmx";
        map =  new TmxMapLoader().load(m);
        renderer = new OrthogonalTiledMapRenderer(map);

        // set up EntityManager
        entities = new EntityManager();
        entities.setMap(map);

        // create player
        player = new Player(entities);
        player.pos.set(100f, 100f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        entities.update(delta);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        renderer.setView(manager.getCamera());
        renderer.render();
        batch.end();

        batch.begin();

        entities.render(batch);
    }
}
