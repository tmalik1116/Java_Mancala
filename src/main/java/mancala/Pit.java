package mancala;

import java.io.Serializable;

public class Pit implements Serializable, Countable{
    private int stones;

    public Pit(int numStones){
        stones = numStones;
    }
    public Pit(){
        stones = 0;
    }

    public void addStone(){
        stones++;
    }

    public void addStones(int stonesToAdd){
        stones += stonesToAdd;
    }

    public void setStones(int toSet){
        stones = toSet;
    }

    public int removeStones(){
        int numStones = stones;
        stones = 0;
        return numStones;
    }

    int removeSingleStone(){
        int numStones = stones;
        stones -= 1;
        return numStones;
    }

    public int getStoneCount(){
        return stones;
    }
    
    @Override
    public String toString(){
        return "(" + stones + ")";
    }
}
