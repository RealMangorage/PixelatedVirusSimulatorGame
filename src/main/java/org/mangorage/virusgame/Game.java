package org.mangorage.virusgame;

import org.mangorage.virusgame.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Game INSTANCE = new Game();

    public static Game getInstance() {
        return INSTANCE;
    }

    private final Random random = new Random();
    private final List<Runnable> tickables = new ArrayList<>();
    private Level level;

    private Game() {
        new Timer("Game Ticker", false)
                .scheduleAtFixedRate(
                        new TimerTask() {
                            @Override
                            public void run() {
                                tick();
                            }
                        },
                        0,
1
                );
    }

    public Random getRandom() {
        return random;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void addTickable(Runnable tickable) {
        this.tickables.add(tickable);
    }

    public void tick() {
        if (level == null) return;
        tickables.forEach(Runnable::run);
        level.tick();
    }
}
