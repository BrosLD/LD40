package ld.bros.game.main;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ld.bros.game.LudumDare40;

import java.util.ArrayList;
import java.util.List;

// class for printing messages onto the screen
public class TextDisplayer {

    private static TextDisplayer instance;

    public static TextDisplayer get() {
        if(instance == null) {
            instance = new TextDisplayer();
        }

        return instance;
    }

    private List<String> messages;
    private final float x = 15f;
    private final float y = LudumDare40.HEIGHT - 15f;

    private final float padding = 25f;
    private final float lineHeight;

    private BitmapFont font;

    private TextDisplayer() {
        messages = new ArrayList<String>(10);

        font = Res.get().font("default");
        lineHeight = font.getCapHeight();
    }

    public void render(SpriteBatch batch) {
        for(int i = 0; i < messages.size(); i++) {
            font.draw(batch, messages.get(i), x, y - (padding+lineHeight)*i);
        }

        messages.clear();
    }

    public void print(String msg) {
        messages.add(msg);
    }

}
