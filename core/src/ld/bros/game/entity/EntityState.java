package ld.bros.game.entity;

import ld.bros.game.main.State;
import ld.bros.game.main.StateManager;

// States for specific entity T
public abstract class EntityState<T extends StateManager> extends State<T> {

    public EntityState(T manager) {
        super(manager);
    }
}
