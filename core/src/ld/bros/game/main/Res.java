package ld.bros.game.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class Res {
    private static Res instance;

    public static Res get() {
        if(instance == null) {
            instance = new Res();
        }

        return instance;
    }

    // map for (dirty) quick loading an image
    private Map<String, TextureRegion> quickRegions;

    private Res() {
        quickRegions = new HashMap<String, TextureRegion>();
    }


    public TextureRegion quick(String internal) {
        TextureRegion region = quickRegions.get(internal);
        if(region == null) {
            // quick load desired region
            region = new TextureRegion(new Texture(internal));

            // store in map
            quickRegions.put(internal, region);
        }

        return region;
    }

    public void dispose() {

        // dispose quick loaded regions
        for(Map.Entry<String, TextureRegion> entry : quickRegions.entrySet())
            entry.getValue().getTexture().dispose();
    }
}
