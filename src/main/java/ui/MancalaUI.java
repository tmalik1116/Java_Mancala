package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mancala.MancalaGame;
import mancala.Player;
import mancala.InvalidMoveException;
import mancala.GameNotOverException;
import mancala.NoSuchPlayerException;
import java.util.Scanner;
import java.io.Serializable;
import java.io.File;

public class MancalaUI extends JFrame implements Serializable{
    private static MancalaGame game;
    private int moveNum;
    private int playerTracker;
    private int[] pits = {4,4,4,4,4,4,4,4,4,4,4,4};
    private int[] stores = {0,0};
    private int[] pitNumbers = {12,11,10,9,8,7,1,2,3,4,5,6};
    private int pitNum = 12;

    public MancalaUI() {
        // Initialize your game logic

        String ruleset = JOptionPane.showInputDialog(null, "Type in 'kalah' to play Kalah or 'ayo' to play Ayo.", "Ruleset picker", JOptionPane.QUESTION_MESSAGE);
        game = new MancalaGame(ruleset);
        game.setCurrentPlayer(game.getPlayerOne());
        playerTracker = 1;

        // Create a button grid
        JPanel panel = makeButtonGrid(2, 6);
        add(panel, BorderLayout.CENTER);


        // Set up the frame
        setTitle("Mancala");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JPanel makeButtonGrid(int tall, int wide) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(tall, wide));
        PositionAwareButton[][] buttons = new PositionAwareButton[tall][wide];

        // Create a label for the title
        JLabel headingLabel = new JLabel(game.getCurrentPlayer().toString());
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headingLabel, BorderLayout.NORTH);

        // Create labels for store counts
        JLabel storeLabels = new JLabel("Store 1: " + stores[0] + "              Store 2: " + stores[1]);
        storeLabels.setFont(new Font("Arial", Font.BOLD, 24));
        add(storeLabels, BorderLayout.SOUTH);

        pitNum = 12;
        int counter = 0;
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {  // Fix: swapped tall and wide
                int tempX = x;
                int tempY = y;
                JPanel buttonWrapper = new JPanel();
                buttons[y][x] = new PositionAwareButton("" + pits[pitNum - 1]);
                buttons[y][x].setPitNum(pitNumbers[counter]);
                buttons[y][x].setAcross(x);
                buttons[y][x].setDown(y);
                buttons[y][x].setPreferredSize(new Dimension(100,50));
                buttons[y][x].addActionListener(e -> {
                    //action listener for button clicked - most of game logic
                    
                    setMoveNum(buttons[tempY][tempX].getPitNum());
                    
                    if (!game.isInputValid(moveNum)){
                        JOptionPane.showMessageDialog(null, "That is not a valid input. Please choose a valid input.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    }else{
                        try {
                            //make move based on button clicked
                            game.move(moveNum);
                        } catch (InvalidMoveException exception) {
                            JOptionPane.showMessageDialog(null, "That move is not allowed. Choose a valid pit.", "Invalid Move Exception!", JOptionPane.ERROR_MESSAGE);
                        }

                        //update number of stones in each pit
                        for (int i = 0; i < 12; i++){
                            pits[i] = game.getStonesInPit(i + 1);
                        }
                        
                        //set stones in stores
                        try {
                            if (playerTracker == 1){
                                stores[playerTracker - 1] = game.getStoreCount(game.getPlayerOne());
                                stores[playerTracker] = game.getStoreCount(game.getPlayerTwo());
                            }else if (playerTracker == 2){
                                stores[playerTracker - 2] = game.getStoreCount(game.getPlayerOne());
                                stores[playerTracker - 1] = game.getStoreCount(game.getPlayerTwo());
                            }
                        } catch (NoSuchPlayerException exception) {
                            JOptionPane.showMessageDialog(null, "The player does not exist.", "No Such Player Exception!", JOptionPane.ERROR_MESSAGE);
                        }

                        if (game.isGameOver()){ //check if game is over
                            String winner = null;
                            try {
                                //check winner
                                if (game.getWinner() != null){
                                    winner = game.getWinner().toString();
                                    JOptionPane.showMessageDialog(null, "Player " + winner + " wins the game!", "Winner!", JOptionPane.INFORMATION_MESSAGE);
                                }else{
                                    JOptionPane.showMessageDialog(null, "It's a tie!", "Draw", JOptionPane.INFORMATION_MESSAGE);
                                }
                            } catch (GameNotOverException exception) {
                                JOptionPane.showMessageDialog(null, "The game is not over", "Game not over!", JOptionPane.ERROR_MESSAGE);
                            }

                        }

                        //Logic to account for turn being repeated (Kalah)
                        if (!game.isTurnRepeated()){
                            if (playerTracker == 1){
                                playerTracker++;
                            }else if (playerTracker == 2){
                                playerTracker--;
                            }
                        }

                        //Set the current player
                        if (playerTracker == 1){
                            game.setCurrentPlayer(game.getPlayerOne());
                        }else{
                            game.setCurrentPlayer(game.getPlayerTwo());
                        }
                        getContentPane().removeAll();

                        JPanel newPanel = makeButtonGrid(2,6);
                        add (newPanel, BorderLayout.CENTER);

                        revalidate();
                        repaint();
                    }
                });
                buttonWrapper.add(buttons[y][x]);

                buttonWrapper.setPreferredSize(new Dimension(500,200));

                panel.add(buttonWrapper);


                if (pitNum > 6){
                    pitNum--;
                }else{
                    pitNum++;
                }
                counter++;
            }
            
            pitNum = 1;
        }
        return panel;
    }

    private void createMenuBar(MancalaGame game) {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> saveGame(game));
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> loadGame(game));
        fileMenu.add(loadItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void saveGame(MancalaGame game) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            game.save(filename, game); // Assuming 'game' is your current MancalaGame instance
        }
    }

    private void loadGame(MancalaGame game) {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            MancalaGame loadedGame = game.load(filename);

            // Use the loaded game in your application as needed
            if (loadedGame != null) {
                game = loadedGame;
                for (int i = 0; i < 14; i++){
                    game.getBoard().setData(i, loadedGame.getBoard().getData(i));
                }
                for (int i = 0; i < 12; i++){
                    pits[i] = loadedGame.getStonesInPit(i + 1);
                }

                try {
                    if (playerTracker == 1){
                        stores[playerTracker - 1] = game.getStoreCount(game.getPlayerOne());
                        stores[playerTracker] = game.getStoreCount(game.getPlayerTwo());
                    }else if (playerTracker == 2){
                        stores[playerTracker - 2] = game.getStoreCount(game.getPlayerOne());
                        stores[playerTracker - 1] = game.getStoreCount(game.getPlayerTwo());
                    }
                } catch (NoSuchPlayerException exception) {
                    JOptionPane.showMessageDialog(null, "The player does not exist.", "No Such Player Exception!", JOptionPane.ERROR_MESSAGE);
                }

                game.setCurrentPlayer(loadedGame.getCurrentPlayer());
                updateUI(loadedGame);
            }
        }
    }

    private void updateUI(MancalaGame updatedGame) {
        // Implement logic to update the UI components with the new game state
        // You may need to re-create the button grid or update labels
        getContentPane().removeAll();
        JPanel newPanel = makeButtonGrid(2, 6);
        add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MancalaUI mainGame = new MancalaUI();
            mainGame.createMenuBar(game);
        });
    }

    int getMoveNum(PositionAwareButton button){
        return button.getPitNum();
    }

    void setMoveNum(int num){
        moveNum = num;
    }
}
