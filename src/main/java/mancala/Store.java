package mancala;

import java.io.Serializable;

public class Store implements Serializable, Countable{
    private Player owner;
    private int stones;

    public Store(Player ownerToSet){
        owner = ownerToSet;
        stones = 0;
    }

    public Store(){
        
    }

    void setOwner(Player player){
        owner = player;
    }

    Player getOwner(){
        return owner;
    }

    public void setStones(int toSet){
        stones = toSet;
    }

    public void addStones(int amount){
        stones += amount;
    }

    public void addStone(){
        stones++;
    }

    int getTotalStones(){
        return stones;
    }

    public int getStoneCount(){
        return stones;
    }

    int emptyStore(){
        int numStones = stones;
        stones = 0;
        return numStones;
    }

    public int removeStones(){
        int numStones = stones;
        stones = 0;
        return numStones;
    }

    @Override
    public String toString(){
        return " Stones: " + stones;
    }
}
