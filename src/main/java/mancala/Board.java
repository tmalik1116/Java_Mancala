package mancala;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable{
    private ArrayList<Pit> pits;
    private ArrayList<Store> stores;
    private boolean repeatTurn; 

    public Board(){
        pits = new ArrayList<Pit>(12);
        stores = new ArrayList<Store>(2);
        repeatTurn = false;

        for (int i = 0; i < 12; i++) {
            pits.add(new Pit(0));
        }
    
        // Create and add 2 stores to the stores list
        for (int i = 0; i < 2; i++) {
            stores.add(new Store(new Player(null, null)));
        }
    }

    void setUpPits(){
        for (Pit pit : pits) {
            pit.removeStones();
            //pits.add(pit);
            for (int i = 0; i < 4; i++){
                pit.addStone(); 
            }
            
        }
    }

    ArrayList<Pit> getPits(){
        return pits;
    }

    ArrayList<Store> getStores(){
        return stores;
    }

    boolean getRepeatTurn(){
        return repeatTurn;
    }

    void setUpStores(){
        for (Store store : stores) {
            store.addStones(1);
            store.emptyStore();
        }
    }

    void initializeBoard(){
        setUpPits();
        setUpStores();
    }

    void resetBoard(){
        initializeBoard();
    }

    void registerPlayers(Player one, Player two){
        one.setStore(stores.get(0));
        stores.get(0).setOwner(one);

        two.setStore(stores.get(1));
        stores.get(1).setOwner(two);
    }

    int moveStones(int startPit, Player player) throws InvalidMoveException{
        int success = 0;
        try{
            
            try{
                success = distributeStones(startPit);
            
            }catch (PitNotFoundException e){
                System.out.println("Pit Not Found Exception!");
                return -1;
            }    
            
            if (success == -1){
                throw new InvalidMoveException();
            }
            return success;

        }catch (InvalidMoveException e) {
            System.out.println("Invalid Move Exception!");
            return 0;
        }
    }  


    int distributeStones(int startingPoint) throws PitNotFoundException {
        int numStones = pits.get(startingPoint).getStoneCount();
        int lastStoneIndex = -1;
        repeatTurn = false;
        boolean storeUsed = false;
        boolean beforeStoreStartLogic = false;
        int remainingStones = numStones;
    
        try {
            if (!(startingPoint <= 11 && startingPoint >= 0)) {
                throw new PitNotFoundException();
            }
    
            // Remove all stones from the startingPoint pit
            pits.get(startingPoint).removeStones();
    
            for (int i = startingPoint + 1; remainingStones > 0; i++) {
                // Wrap around to the first pit if necessary
                if (i > 11) {
                    i = 0;
                }
    
                // Skip the opponent's store during distribution
                // if ((i == 11 && startingPoint <= 5) || (i == 6 && startingPoint >= 6)) {
                //     if (i == 11 && startingPoint <= 5){
                //         pits.get(i).addStone();
                //         remainingStones--;
                //     }
                //     continue;
                // }

                if (i == 11 && startingPoint <= 5){
                    pits.get(i).addStone();
                    remainingStones--;
                    continue;
                }
                if (i == 6 && startingPoint >= 6){
                    continue;
                }
    
                // Distribute stones to pits
                pits.get(i).addStone();
                lastStoneIndex = i;
                if (startingPoint == 5 && i == 6 && remainingStones > 0 && !beforeStoreStartLogic){
                    i = 5;
                    pits.get(6).removeSingleStone();
                    beforeStoreStartLogic = true;
                }else if (startingPoint == 11 && i == 0 && remainingStones > 0 && !beforeStoreStartLogic){
                    i = 11;
                    pits.get(0).removeSingleStone();
                    beforeStoreStartLogic = true;
                }else{
                    remainingStones--;
                }
    
                // Check if a stone is added to the store
                if (remainingStones == 0) {
                    break;
                } else if (i == 5 && startingPoint <= 5) {
                    // Player 1's store
                    stores.get(0).addStones(1);
                    storeUsed = true;
                    remainingStones--;
                } else if (i == 11 && startingPoint >= 6) {
                    // Player 2's store
                    stores.get(1).addStones(1);
                    storeUsed = true;
                    remainingStones--;
                }
                if (remainingStones == 0){
                    break;
                }
                
            }
            if (lastStoneIndex > 11){
                lastStoneIndex = 11;
            }else if (lastStoneIndex < 0){
                lastStoneIndex = 0;
            }
    
            // Check if the last stone landed in the player's store
            if ((startingPoint <= 5 && lastStoneIndex == 5 && storeUsed)
            || (startingPoint >= 6 && lastStoneIndex == 11 && storeUsed)) {
                repeatTurn = true;
            } else if (remainingStones == 0 && pits.get(lastStoneIndex).getStoneCount() == 1) {
                // Check for capturing stones
                if ((lastStoneIndex <= 5 && startingPoint <= 5) || (lastStoneIndex >= 6 && startingPoint >= 6)){
                    captureStones(pitMap(lastStoneIndex));
                }
                
            }
    
            return numStones;
        } catch (PitNotFoundException e) {
            System.out.println("Pit Not Found Exception!");
            return -1;
        }
    }    

    //is called with pitMap(lastStoneIndex)
    int captureStones(int stoppingPoint) throws PitNotFoundException{
        int playerNum = 0;
        try {
            if (pitMap(stoppingPoint) <= 5){
                playerNum = 0;
            }else if (pitMap(stoppingPoint) >= 6){
                playerNum = 1;
            }
            if (stoppingPoint < 0 || stoppingPoint > 11){
                throw new PitNotFoundException();
            }
            if (pits.get(stoppingPoint).getStoneCount() > 0){
                stores.get(playerNum).addStones(pits.get(stoppingPoint).getStoneCount());
                pits.get(stoppingPoint).removeStones();

                stores.get(playerNum).addStones(1);
                pits.get(pitMap(stoppingPoint)).removeStones();
            }
            
            return playerNum;
        } catch (PitNotFoundException e) {
            System.out.println("Pit Not Found Exception!");
            return -1;
        }
            
    }

    int getNumStones(int pitNum) throws PitNotFoundException{
        
        try {
            if (pitNum < 0 || pitNum > 11){
                throw new PitNotFoundException();
            }

            return pits.get(pitNum).getStoneCount();
        } catch (PitNotFoundException e) {
            System.out.println("Pit Not Found Exception!");
            return -1;
        }
        
        
    }

    boolean isSideEmpty(int pitNum) throws PitNotFoundException{
        
        try {
            if (pitNum < 0 || pitNum > 11){
                throw new PitNotFoundException();
            }
            boolean conditionCheck = true;
            if (pitNum == 0){
                for (int i = 0; i < 6; i++){
                    if (pits.get(i).getStoneCount() > 0){
                        conditionCheck = false;
                        //if any stones found empty status is false
                    }
                }
            }
            if (pitNum == 1){
                for (int i = 6; i < 12; i++){
                    if (pits.get(i).getStoneCount() > 0){
                        conditionCheck = false;
                    }
                }
            }
            return conditionCheck;
        } catch (PitNotFoundException e) {
            System.out.println("Pit Not Found Exception!");
            return false;
        }
        
            
    }

    int pitMap(int pitNum){
        switch (pitNum) {
            case 0:
                return 11;
            case 1:
                return 10;
            case 2:
                return 9;
            case 3:
                return 8;
            case 4:
                return 7;
            case 5:
                return 6;
            case 6:
                return 5;
            case 7: 
                return 4;
            case 8:
                return 3;
            case 9:
                return 2;
            case 10:
                return 1;
            case 11:
                return 0;
            default:
                return -1;
        }
    }

    public String toString(){
        return 
        pits.get(11).toString() + " " 
        + pits.get(10).toString() + " " 
        + pits.get(9).toString() + " " 
        + pits.get(8).toString() + " " 
        + pits.get(7).toString() + " " 
        + pits.get(6).toString() + "\n      " 
        + pits.get(0).toString() + " " 
        + pits.get(1).toString() + " " 
        + pits.get(2).toString() + " " 
        + pits.get(3).toString() + " " 
        + pits.get(4).toString() + " " 
        + pits.get(5).toString();
    }
}
