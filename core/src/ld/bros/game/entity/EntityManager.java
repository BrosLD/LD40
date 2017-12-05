package ld.bros.game.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import ld.bros.game.entity.player.Player;
import ld.bros.game.entity.sheep.Sheep;
import ld.bros.game.gamestates.MainState;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private MainState state;

    private List<Entity> entityList;

    private List<Sheep> sheepList;
    private int numSheepClear;
    private int numSheepDead;

    private TiledMap map;

    private int mapWidth;
    private int mapHeight;

    public EntityManager(MainState state) {
        this.state = state;

        entityList = new ArrayList<Entity>();
        sheepList = new ArrayList<Sheep>();
    }

    public void update(float delta) {
        // backwards iteration to allow removing elements during loop
        for(int i = entityList.size()-1; i >= 0; i--) {
            entityList.get(i).update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for(int i = 0; i < entityList.size(); i++) {
            entityList.get(i).render(batch);
        }
    }

    /**
     * Checks if there will be a collision if given entity will be placed to the given coordinates.
     * @param e the entity to check for
     * @param x_pos the x coordinate
     * @param y_pos the y coordinate
     * @return true if there will be a collision. False otherwise.
     */
    public boolean checkCollision(Entity e, int x_pos, int y_pos) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);

        // set up entity collision box
        Polygon entity = new Polygon(new float[]{
                x_pos, y_pos,
                x_pos + e.width, y_pos,
                x_pos + e.width, y_pos + e.height,
                x_pos, y_pos + e.height
        });

        // calculate bounds for tiles to check for collision
        int x_start = x_pos / (int)layer.getTileWidth();
        int x_end = (x_pos + (int)e.width) / (int)layer.getTileWidth();

        int y_start = y_pos / (int)layer.getTileHeight();
        int y_end = (y_pos + (int)e.height) / (int)layer.getTileHeight();

        // check tiles for collision
        for(int y = y_start; y <= y_end; y++) {
            for(int x = x_start; x <= x_end; x++) {
                TiledMapTileLayer.Cell currCell = layer.getCell(x, y);

                if(checkTileCollision(currCell)) {
                    // set up rectangle
                    float w = layer.getTileWidth();
                    float h = layer.getTileHeight();

                    String property = currCell.getTile().getProperties().get("collision", String.class);
                    String coordinates[] = property.split(",");

                    float v[] = new float[coordinates.length];
                    for(int i = 0; i < v.length; i+=2) {
                        v[i] = x*w + Float.parseFloat(coordinates[i])*w;
                        v[i+1] = y*h + Float.parseFloat(coordinates[i+1])*h;
                    }

//                    Polygon cell = new Polygon(new float[]{
//                            x*w, y*h,
//                            x*w + w, y*h,
//                            x*w + w, y*h + h,
//                            x*w, y*h + h
//                    });
                    Polygon cell = new Polygon(v);

                    // check collision
                    if(Intersector.overlapConvexPolygons(entity, cell)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks whether the given cell has the "collision: rectangle" property or not.
     * @param cell the cell to check.
     * @return true if the cell has the property. False otherwise.
     */
    private boolean checkTileCollision(TiledMapTileLayer.Cell cell) {
        if(cell != null) {
            if (cell.getTile().getProperties().containsKey("collision")) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether the given entity collides with another one inside the entity list and returns it.
     * Returns null, when there was no collision.
     * @param which the entity to check for.
     * @return the collided entity. null if there was no collision.
     */
    public Entity collisionWithEntity(Entity which) {

        Rectangle a = new Rectangle();
        a.x = which.pos.x;
        a.y = which.pos.y;
        a.width = which.width;
        a.height = which.height;

        Rectangle other = new Rectangle();

        for(int i = 0; i < entityList.size(); i++) {
            // skip if current element is which
            // Otherwise we will always collide with the given entity itself
            if(entityList.get(i) == which)
                continue;

            other.x = entityList.get(i).pos.x;
            other.y = entityList.get(i).pos.y;
            other.width = entityList.get(i).width;
            other.height = entityList.get(i).height;

            if(a.overlaps(other)) {
                return entityList.get(i);
            }
        }

        return null;
    }

    public void setMap(TiledMap map) {
        this.map = map;

        MapProperties prop = map.getProperties();
        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        this.mapWidth = mapPixelWidth;
        this.mapHeight = mapPixelHeight;
    }

    public TiledMap getMap() {
        return map;
    }

    public void add(Entity entity) {
        entityList.add(entity);

        if(entity instanceof Sheep) {
            sheepList.add((Sheep)entity);
        }
    }

    public void remove(Entity entity) {
        entityList.remove(entity);

        if(entity instanceof Sheep) {
            sheepList.remove(entity);
        }
    }

    public void removeSheep(Sheep s) {
        entityList.remove(s);
        sheepList.remove(s);
        numSheepDead++;
        state.updateHud();
    }

    public List<Entity> getEntityList() {
        return entityList;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public void sheepInEndzone(Sheep sheep) {
        // remove from list
        remove(sheep);
        numSheepClear++;

        // TODO create new sheep-in-endzone Marker

        // update hud (inform state)
        state.updateHud();
    }

    public void playerInEndzone(Player player) {
        state.levelEnd();
    }

    public int getNumberSheep() {
        return sheepList.size();
    }

    public int getNumSheepClear() {
        return numSheepClear;
    }

    public int getNumSheepDead() {
        return numSheepDead;
    }

    public void resetPlayer() {
        state.resetPlayer();
    }

    public void clear() {
        entityList.clear();
        sheepList.clear();
        numSheepClear = 0;
        numSheepDead = 0;
    }
}
