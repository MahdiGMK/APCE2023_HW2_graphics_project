package com.mahdigmk.apaa;

import com.mahdigmk.apaa.Model.DifficultyLevel;
import com.mahdigmk.apaa.Model.Game.GameData;
import com.mahdigmk.apaa.Model.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameTest {
    @Test
    void testGameSave() {
        GameData gameData = new GameData(DifficultyLevel.EASY, Map.GRASS_LANDS, 10, 300, 10);
        gameData.save();

        GameData loaded = GameData.load();
        assertEquals(gameData.getMap(), loaded.getMap());
        assertEquals(gameData.getRotation(), loaded.getRotation());
        assertEquals(gameData.getBalls(), loaded.getBalls());
    }
}
