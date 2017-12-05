package ld.bros.game.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level {
    public String name;

    private TiledMap map;

    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public void render(SpriteBatch batch) {
        renderer.setView(camera);
        renderer.render();

        batch.end();
        batch.begin();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void loadMap(String path) {
        map = new TmxMapLoader().load(path);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public TiledMap getMap() {
        return map;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}
