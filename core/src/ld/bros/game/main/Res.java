package ld.bros.game.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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

    // fonts
    private static Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>(10);

    // atlas
    private TextureAtlas playerAtlas;
    private TextureAtlas sheepAtlas;
    private TextureAtlas endzoneAtlas;
    private TextureAtlas hudAtlas;

    private Res() {
        quickRegions = new HashMap<String, TextureRegion>();
        loadFonts();

        playerAtlas = new TextureAtlas(Gdx.files.internal("images/player.atlas"));
        sheepAtlas = new TextureAtlas(Gdx.files.internal("images/sheep.atlas"));
        endzoneAtlas = new TextureAtlas(Gdx.files.internal("images/endzone.atlas"));
        hudAtlas = new TextureAtlas(Gdx.files.internal("images/hud.atlas"));
    }

    private void loadFonts() {
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 18;
        params.color = Color.BLACK;
//        params.borderWidth = 2f;
//        params.borderColor = Color.WHITE;
        generateFont("default", "aldothe_apache.ttf", params);

        params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = 14;
        params.color = Color.WHITE;
        params.borderWidth = 2f;
        params.borderColor = Color.BLACK;
        generateFont("hud", "press_start_2p.ttf", params);
    }

    private void generateFont(String name, String path, FreeTypeFontGenerator.FreeTypeFontParameter params) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/" + path));
        fonts.put(name, generator.generateFont(params));
        generator.dispose();
    }

    public BitmapFont font(String which) {
        return fonts.get(which);
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

        // dispose all fonts in map
        for(Map.Entry<String, BitmapFont> entry : fonts.entrySet()) {
            entry.getValue().dispose();
        }

        // dispose atlas
        playerAtlas.dispose();
        sheepAtlas.dispose();
        endzoneAtlas.dispose();
        hudAtlas.dispose();
    }

    public TextureAtlas getPlayerAtlas() {
        return playerAtlas;
    }

    public TextureAtlas getSheepAtlas() {
        return sheepAtlas;
    }

    public TextureAtlas getEndzoneAtlas() {
        return endzoneAtlas;
    }

    public TextureAtlas getHudAtlas() {
        return hudAtlas;
    }
}
