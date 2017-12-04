package ld.bros.game.main;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

// various methods
public class Utils {

    /**
     * Returns the 'direction' of the given value.
     * If it's positive -> +1
     * If it's zero     -> 0
     * If it's negative -> -1
     * @param val the value to check
     * @return the direction in one unit
     */
    public static int direction(float val) {
        if(val > 0) return 1;
        if(val < 0) return -1;
        return 0;
    }

    /**
     * Calculates the width and height of the given text in the given font.
     * @param font the font of the text.
     * @param text the actual text.
     * @return the dimensions in a vector. x = width, y = height.
     */
    public static Vector2 calcDimensions(BitmapFont font, String text) {
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, text);
        float w = glyphLayout.width;
        float h = glyphLayout.height;

        return new Vector2(w, h);
    }
}
