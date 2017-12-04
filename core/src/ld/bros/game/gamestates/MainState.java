package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import ld.bros.game.LudumDare40;
import ld.bros.game.entity.Endzone;
import ld.bros.game.entity.EntityManager;
import ld.bros.game.entity.player.Player;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.GameStateManager;
import ld.bros.game.main.Hud;
import ld.bros.game.main.State;

public class MainState extends State<GameStateManager> {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private EntityManager entities;
    private Player player;

    private Hud hud;

    public MainState(GameStateManager manager) {
        super(manager);

        // load map
//        String m = "dummy/dummy_map.tmx";
        String m = "level/playground.tmx";
        map =  new TmxMapLoader().load(m);
        renderer = new OrthogonalTiledMapRenderer(map);

        // get object layer for spawn points
        MapLayer spawnPoints = map.getLayers().get("entity");

        // set up EntityManager
        entities = new EntityManager(this);
        entities.setMap(map);

        // create player
        player = new Player(entities);
        RectangleMapObject spawn = (RectangleMapObject) spawnPoints.getObjects().get("spawn");
        player.pos.set(spawn.getRectangle().x, spawn.getRectangle().y);

        // create sheeps
        MapObjects objects = spawnPoints.getObjects();
        for(MapObject curr : objects) {
            if("sheep".equals(curr.getName())) {
                // current object is a sheep-spawn-point
                RectangleMapObject currRect = (RectangleMapObject) curr;

                Sheep sheep = new Sheep(entities);
                sheep.pos.set(currRect.getRectangle().x, currRect.getRectangle().y);
            }

            if("endzone".equals(curr.getName())) {
                // current object defines endzone
                RectangleMapObject currRect = (RectangleMapObject) curr;

                Endzone endzone = new Endzone(entities);
                endzone.pos.set(currRect.getRectangle().x, currRect.getRectangle().y);
                endzone.width = currRect.getRectangle().width;
                endzone.height = currRect.getRectangle().height;
            }
        }

        hud = new Hud();
        hud.setCurrLevel("Playground");
        hud.setScore(999999999L);
        hud.setRemainingTime(500);
        hud.setNumSheepClear(1);
        hud.setNumSheepDead(1);
        hud.setNumSheepRemaining(2);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // update camera to follow player
        updateCamera();

        entities.update(delta);

        hud.update(delta);
    }

    private void updateCamera() {

        manager.getCamera().position.x = player.pos.x;
        manager.getCamera().position.y = player.pos.y;

        int mapPixelWidth = entities.getMapWidth();
        int mapPixelHeight = entities.getMapHeight();

        // bound camera to map bounds
        // horizontal
        if(manager.getCamera().position.x - LudumDare40.WIDTH/2f < 0) {
            manager.getCamera().position.x = LudumDare40.WIDTH/2f;
        } else if(manager.getCamera().position.x + LudumDare40.WIDTH/2f > mapPixelWidth) {
            manager.getCamera().position.x = mapPixelWidth - LudumDare40.WIDTH/2f;
        }
        // vertical
        if(manager.getCamera().position.y - LudumDare40.HEIGHT/2f < 0) {
            manager.getCamera().position.y = LudumDare40.HEIGHT/2f;
        } else if(manager.getCamera().position.y + LudumDare40.HEIGHT/2f > mapPixelHeight) {
            manager.getCamera().position.y = mapPixelHeight - LudumDare40.HEIGHT/2f;
        }

        manager.getCamera().update();
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);

        // render map
        renderer.setView(manager.getCamera());
        renderer.render();
        batch.end();


        batch.begin();
        entities.render(batch);
        hud.render(batch);
    }

    public void levelEnd() {

    }
}
