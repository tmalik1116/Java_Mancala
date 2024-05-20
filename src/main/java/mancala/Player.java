package mancala;

import java.io.Serializable;

public class Player implements Serializable{
    private String name;
    private Countable store;
    private UserProfile profile;

    public Player(String nameToSet, Countable storeToSet){
        name = nameToSet;
        store = storeToSet;
    }

    public Player(){
        
    }

    public String getName(){
        return name;
    }

    void setName(String nameToSet){
        name = nameToSet;
    }

    Countable getStore(){
        return store;
    }

    int getStoreCount(){
        return store.getStoneCount();
    }

    void setStore(Countable storeToSet){
        store = storeToSet;
    }

    @Override
    public String toString(){
        return "Player: " + name;
    }
}
