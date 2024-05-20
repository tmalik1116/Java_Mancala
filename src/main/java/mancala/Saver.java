package mancala;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.nio.file.*;
import java.io.Serializable;

public class Saver {
    // Method to save the game state
    public static void saveGame(String fileName, Object gameState) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(gameState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load the game state
    public static Object loadGame(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            Object gameState = inputStream.readObject();
            return gameState;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
