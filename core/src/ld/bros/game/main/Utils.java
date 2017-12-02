package ld.bros.game.main;

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
}
