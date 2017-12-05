package ld.bros.game.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import ld.bros.game.entity.player.state.Idle;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.main.*;

import java.util.ArrayList;
import java.util.List;

public class MainState extends State<GameStateManager> {

    private EntityManager entities;
    private Player player;

    private Hud hud;

    private List<Level> levelList;
    private int currLevelIndex;
    private Level currLevel;

    private int numCurrentSheep;

    private TextureRegion background;

    private final float SHEEP_OFFSET = 10f;

    public MainState(GameStateManager manager) {
        super(manager);

        background = Res.get().quick("images/background_plain.png");

        // set up EntityManager
        entities = new EntityManager(this);

        // create hud
        hud = (Hud) Bundle.get().get("hud");
        if(hud == null)
            hud = new Hud();

//        hud.setScore(999999999L);
//        hud.setRemainingTime(500);
//        hud.setNumSheepClear(1);
//        hud.setNumSheepDead(1);
//        hud.setNumSheepRemaining(2);

        levelList = (ArrayList<Level>) Bundle.get().get("levelList");
        if(levelList == null) setUpLevelList();

        Integer cLi = (Integer) Bundle.get().get("currLevelIndex");
        if(cLi == null) currLevelIndex = 0;
        else currLevelIndex = cLi;

        Integer nCS = (Integer) Bundle.get().get("numCurrentSheep");
        if(nCS == null) numCurrentSheep = 0;
        else numCurrentSheep = nCS;

        loadLevel(currLevelIndex);
    }

    @Override
    public void enter() {
        loadLevel(currLevelIndex);
    }

    private void setUpLevelList() {
        levelList = new ArrayList<Level>();

        Level curr;

        curr = new Level();
        curr.name = "Hello Sheep-World";
        curr.setCamera(manager.getCamera());
        curr.loadMap("level/tutorial.tmx");
        levelList.add(curr);

        curr = new Level();
        curr.name = "Playground";
        curr.setCamera(manager.getCamera());
        curr.loadMap("level/playground.tmx");
        levelList.add(curr);

        curr = new Level();
        curr.name = "Down and Up";
        curr.setCamera(manager.getCamera());
        curr.loadMap("level/stage_2.tmx");
        levelList.add(curr);

        curr = new Level();
        curr.name = "Where are the sheep?";
        curr.setCamera(manager.getCamera());
        curr.loadMap("level/stage_3.tmx");
        levelList.add(curr);

        curr = new Level();
        curr.name = "Bye Bye!";
        curr.setCamera(manager.getCamera());
        curr.loadMap("level/micro.tmx");
        levelList.add(curr);
    }

    public void loadLevel(int num) {
        currLevel = levelList.get(num);

        entities.clear();
        entities.setMap(currLevel.getMap());
        hud.setCurrLevel(currLevel.name);
        hud.setRemainingTime(500);

        // get object layer for spawn points
        MapLayer spawnPoints = currLevel.getMap().getLayers().get("entity");

        // create entities
        MapObjects objects = spawnPoints.getObjects();
        for(MapObject o : objects) {
            RectangleMapObject curr = (RectangleMapObject) o;

            if("player".equals(curr.getName())) {
                player = new Player(entities);
                player.pos.set(curr.getRectangle().x, curr.getRectangle().y);
            }

            if("sheep".equals(curr.getName())) {
                // check number of sheep to be generated
                Integer numberOfSheep = curr.getProperties().get("number", Integer.class);
                if(numberOfSheep != null) {
                    for(int i = 0; i < numberOfSheep + numCurrentSheep; i++) {
                        Sheep sheep = new Sheep(entities);
                        sheep.pos.set(curr.getRectangle().x + SHEEP_OFFSET*i,
                                curr.getRectangle().y);
                    }
                } else {
                    for(int i = 0; i < numCurrentSheep; i++) {
                        Sheep sheep = new Sheep(entities);
                        sheep.pos.set(curr.getRectangle().x + SHEEP_OFFSET*i,
                                curr.getRectangle().y);
                    }
                }
            }

            if("endzone".equals(curr.getName())) {
                // current object defines endzone
                Endzone endzone = new Endzone(entities);
                endzone.pos.set(curr.getRectangle().x, curr.getRectangle().y);
                endzone.width = curr.getRectangle().width;
                endzone.height = curr.getRectangle().height;
            }
        }

        updateHud();
    }

    public void levelEnd() {
        numCurrentSheep = entities.getNumSheepClear();

        currLevelIndex++;

        saveState();

        if(currLevelIndex >= levelList.size()) {

            saveState();
            // GAME OVER
            manager.set(new GameOverState(manager));

            // calculate score
            manager.push(new ScoreState(manager, this));

        } else {
            // calculate score
            manager.push(new ScoreState(manager, this));

            // load next level
//            loadLevel(currLevelIndex);
        }
    }

    private void saveState() {
        Bundle.get().put("currLevelIndex", currLevelIndex);
        Bundle.get().put("levelList", levelList);
        Bundle.get().put("hud", hud);
        Bundle.get().put("numCurrentSheep", numCurrentSheep);
    }

    public void resetPlayer() {
        // set player to spawnpoint
        RectangleMapObject spawn = (RectangleMapObject) currLevel.getMap().getLayers().get("entity").getObjects().get("player");
        player.pos.set(spawn.getRectangle().x, spawn.getRectangle().y);
        player.dead = false;
    }

    public void resetLevel() {
        // reload complete level
        loadLevel(currLevelIndex);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // update camera to follow player
        updateCamera();

        entities.update(delta);

        hud.update(delta);

        if(Controls.reset()) {
            resetLevel();
        }
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

        // render background
        batch.end();
        batch.begin();
        batch.draw(background,
                manager.getCamera().position.x - LudumDare40.WIDTH/2f,
                manager.getCamera().position.y - LudumDare40.HEIGHT/2f,
                LudumDare40.WIDTH, LudumDare40.HEIGHT);
        batch.end();
        batch.begin();

        // render level
        currLevel.render(batch);

        entities.render(batch);
        hud.render(batch);
    }

    public void updateHud() {
        hud.setNumSheepClear(entities.getNumSheepClear());
        hud.setNumSheepDead(entities.getNumSheepDead());
        hud.setNumSheepRemaining(entities.getNumberSheep());
    }
}
