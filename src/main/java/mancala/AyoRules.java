package mancala;

import java.io.Serializable;

public class AyoRules extends GameRules implements Serializable{

    public AyoRules(){
        
    }

    @Override
    public int moveStones(int startPit, int playerNum) throws InvalidMoveException{
        int success = 0;
        success = distributeStones(startPit);
        
        if (success == -1){
            throw new InvalidMoveException();
        }
        return success;
    }  


    //NEXT TASK: DO AYO RULESET
    //these same methods just change logic accordingly
    //(distributeStones, moveStones, captureStones)
    @Override
int distributeStones(int startingPoint) {
    int numStones;
    int lastStoneIndex = -1;
    int currentPlayer = 0;

    getDataStructure().setLastStoneIndex(-1);

    // check current player
    if (startingPoint > 6) {
        currentPlayer = 2;
    } else if (startingPoint <= 6) {
        currentPlayer = 1;
    }

    numStones = getDataStructure().removeStones(startingPoint);
    getDataStructure().setIterator(startingPoint, currentPlayer, false);
    Countable currentSpot;

    // will iterate through the data structure, skipping the opposite player's store
    for (int i = 0; i < numStones; i++) {
        currentSpot = getDataStructure().next();
        currentSpot.addStone();
        lastStoneIndex = getDataStructure().getIteratorPos(); // Use getIteratorPos() instead of iteratorPos

        // Adjust lastStoneIndex to be in the valid range
        if (lastStoneIndex > 13) {
            lastStoneIndex = 0;
        }
    }

    if ((numStones == 1 || (numStones == 2 && lastStoneIndex != 12)) && startingPoint > 6) {
        lastStoneIndex++;
    }

    if (getDataStructure().getNumStones(lastStoneIndex) > 0) {
        distributeStones(lastStoneIndex);
    } else if (getDataStructure().getData(lastStoneIndex).getStoneCount() == 1 && (getDataStructure().getData(pitMap(lastStoneIndex)).getStoneCount() > 0)) {
        captureStones(pitMap(lastStoneIndex));
    }

    return numStones;
}



    //is called with pitMap(lastStoneIndex)
    @Override
    int captureStones(int stoppingPoint){
        int playerNum = 1;

        int currentPit = pitMap(stoppingPoint);
        if (pitMap(stoppingPoint) <= 5){
            playerNum = 1;
            currentPit++;
        }else if (pitMap(stoppingPoint) > 6){
            playerNum = 2;
            stoppingPoint++;
        }
        
        int stonesCaptured = getDataStructure().removeStones(stoppingPoint);
        getDataStructure().addToStore(playerNum, stonesCaptured);

        getDataStructure().removeStones(currentPit);
        getDataStructure().addToStore(playerNum, 1);
        
        return playerNum;
    }

    int pitMap(int pitNum){
        switch (pitNum) {
            case 0:
                return 12;
            case 1:
                return 11;
            case 2:
                return 10;
            case 3:
                return 9;
            case 4:
                return 8;
            case 5:
                return 7;
            case 7: //skip 6 because it is P1 store
                return 5;
            case 8:
                return 4;
            case 9:
                return 3;
            case 10:
                return 2;
            case 11:
                return 1;
            case 12:
                return 0;
            default: //skip 13 because it is P2 store
                return 6;
        }
    }

    @Override
    public String toString(){
        String toReturn = "\n";
        for (int i = 12; i > 6; i--){
            toReturn += getDataStructure().getData(i).toString();
        }
        toReturn += "\n";
        for (int i = 0; i < 6; i++){
            toReturn += getDataStructure().getData(i).toString();
        }
        return toReturn;
    }
}
