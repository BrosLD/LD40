package ld.bros.game.main;

import java.util.HashMap;
import java.util.Map;

public class Bundle {
    private static Bundle instance;

    public static Bundle get() {
        if(instance == null)
            instance = new Bundle();

        return instance;
    }

    private Map<String, Object> bag;

    private Bundle() {
        bag = new HashMap<String, Object>();
    }

    public void put(String key, Object value) {
        bag.put(key, value);
    }

    public Object get(String key) {
        return bag.get(key);
    }
}
