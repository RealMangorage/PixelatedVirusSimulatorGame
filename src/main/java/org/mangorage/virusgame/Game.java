package org.mangorage.virusgame;

import org.mangorage.virusgame.level.Level;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Game INSTANCE;

    static void initGame(Level level) {
        INSTANCE = new Game(level);
    }

    public static Game getInstance() {
        return INSTANCE;
    }

    private final Level level;
    private final Random random = new Random();

    private Game(Level level) {
        this.level = level;

        new Timer("Game Ticker", false)
                .scheduleAtFixedRate(
                        new TimerTask() {
                            @Override
                            public void run() {
                                level.tick();
                            }
                        },
                        0,
                        (int) (1000 * ((double) 1 / 20))
                );
    }

    public Random getRandom() {
        return random;
    }

    public Level getLevel() {
        return level;
    }
}
