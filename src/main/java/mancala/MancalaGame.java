package mancala;
import java.io.Serializable;
import java.util.ArrayList;

public class MancalaGame extends Object implements Serializable{
    private GameRules gameBoard;
    private Player player1;
    private Player player2;
    private int currentPlayer;

    public MancalaGame(){
        //add code here for constructor
        gameBoard = new KalahRules(); // defaults to kalah if no ruleset given
        player1 = new Player("Player 1", gameBoard.getDataStructure().getData(6));

        player2 = new Player("Player 2", gameBoard.getDataStructure().getData(13));

        gameBoard.getDataStructure().setUpPits();
        currentPlayer = 0;
    }

    public MancalaGame(String ruleset){
        //add code here for constructor
        if (ruleset.equals("kalah")){
            gameBoard = new KalahRules();
        }else if (ruleset.equals("ayo")){
            gameBoard = new AyoRules();
        }

        player1 = new Player("Player 1", gameBoard.getDataStructure().getData(6));

        player2 = new Player("Player 2", gameBoard.getDataStructure().getData(13));

        gameBoard.getDataStructure().setUpPits();
        currentPlayer = 0;
    }

    public void save(String filename, MancalaGame gameToSave){
        Saver.saveGame(filename, gameToSave);
    }

    public MancalaGame load(String filename){
        MancalaGame loadedGame = (MancalaGame) Saver.loadGame(filename);
        return loadedGame;
    }

    public MancalaDataStructure getBoard(){
        return gameBoard.getDataStructure();
    }

    void setPlayers(Player onePlayer, Player twoPlayer){
        player1.setName(onePlayer.getName());
        player2.setName(twoPlayer.getName());

        player1.setStore(onePlayer.getStore());
        player2.setStore(twoPlayer.getStore());
    }

    public int getStonesInPit(int pit){
        return getBoard().getNumStones(pit);
    }

    public Player getCurrentPlayer(){
        if (currentPlayer == 1){
            return player1;
        }else if (currentPlayer == 2){
            return player2;
        }
        return null;
    }

    public Player getPlayerOne(){
        return player1;
    }

    public Player getPlayerTwo(){
        return player2;
    }

    int getCurrentPlayerNum(){
        if (getCurrentPlayer() == player1){
            return 1;
        }else if (getCurrentPlayer() == player2){
            return 2;
        }
        return -1;
    }

    public void setCurrentPlayer(Player player){
        if (player == player1){
            currentPlayer = 1;
        }else{
            currentPlayer = 2;
        }
    }

    public boolean isTurnRepeated(){
        if (gameBoard.getDataStructure().getLastStoneIndex() != -1){
            return true;
        }
        return false;
    }

    public boolean isInputValid(int input){
        boolean validity = true;
        if (input < 1 || input > 12){
            validity = false;
        }else if (input <= 6 && getCurrentPlayer() == player2){
            validity = false;
        }else if (input > 6 && getCurrentPlayer() == player1){
            validity = false;
        }
        return validity;
    }

    int getNumStones(int pitNum) throws PitNotFoundException{
        if (pitNum < 0 || pitNum > 13){
            throw new PitNotFoundException();
        }
        //might need to make another pitMap function to deal with this
        return gameBoard.getDataStructure().getData(pitNum).getStoneCount();
    }

    public int move(int startPit) throws InvalidMoveException{
        //make move for current player
        int startOfSide = 0;
        int numStonesInSide = 0;
        //System.out.println(startPit);
        
        if (startPit < 0 || startPit > 12){
            System.out.println("1");
            throw new InvalidMoveException();

        }else if (startPit <= 5 && getCurrentPlayer() == player2){ //checking for invalid move choices
            System.out.println("2");
            throw new InvalidMoveException();

        }else if (startPit > 6 && getCurrentPlayer() == player1){
            System.out.println("3");
            throw new InvalidMoveException();

        }else if (startPit <= 5){
            setCurrentPlayer(player1);
            startOfSide = 0;
        }else if (startPit >= 7){
            setCurrentPlayer(player2);
            startOfSide = 7;
        }
        gameBoard.moveStones(startPit, getCurrentPlayerNum()); //removed startPit + 1
        
        try {
            for (int i = startOfSide; i < (startOfSide + 6); i++){
                numStonesInSide += getNumStones(i);
            }
        } catch (PitNotFoundException e) {
            System.out.println("Pit Not Found Exception!");
        }
        return numStonesInSide;
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException{
            if (player == player1){
                currentPlayer = 1;
            }else if (player == player2){
                currentPlayer = 2;
            }else{
                throw new NoSuchPlayerException();
            }

            return gameBoard.getDataStructure().getStoreCount(currentPlayer);
    }

    public Player getWinner() throws GameNotOverException{
        int[] stoneCount = {0,0};
        if (isGameOver()){
            try {
                for (int i = 0; i < 6; i++){
                    stoneCount[0] += getNumStones(i);
                    stoneCount[1] += getNumStones(i + 7);
                }
            } catch (PitNotFoundException e) {
                System.out.println("Pit Not Found Exception!");
            }
            
            stoneCount[0] += gameBoard.getDataStructure().getStoreCount(1);
            stoneCount[1] += gameBoard.getDataStructure().getStoreCount(2);

            if (stoneCount[0] > stoneCount[1]){
                return player1;
            }else if (stoneCount[0] < stoneCount[1]){
                return player2;
            }else{
                return null;
            }
        }else{
            throw new GameNotOverException();
        }
    }

    public boolean isGameOver(){
        return gameBoard.isSideEmpty(currentPlayer);
    }

    void startNewGame(){
        //reset board and set game condition
        gameBoard.getDataStructure().setUpPits();
    }

    @Override
    public String toString(){
        return "Pits: " 
        + gameBoard.toString() 
        + "\nStores: " 
        + "P1: " + gameBoard.getDataStructure().getStoreCount(1) 
        + " P2: " + gameBoard.getDataStructure().getStoreCount(2);
    }
}