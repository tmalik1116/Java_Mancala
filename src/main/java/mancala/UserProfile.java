package mancala;

import java.io.Serializable;

public class UserProfile implements Serializable{
    private String username;
    private int kalahGames;
    private int kalahGamesWon;
    private int ayoGames;
    private int ayoGamesWon;

    public String getUsername(){
        return username;
    }

    public int getKalahGames(){
        return kalahGames;
    }

    public int getKalahGamesWon(){
        return kalahGamesWon;
    }

    public int getAyoGames(){
        return ayoGames;
    }

    public int getAyoGamesWon(){
        return ayoGamesWon;
    }

    void setUsername(String usernameToSet){
        username = usernameToSet;
    }

    void setKalahGames(int numToSet){
        kalahGames = numToSet;
    }

    void setKalahGamesWon(int numToSet){
        kalahGamesWon = numToSet;
    }

    void setAyoGames(int numToSet){
        ayoGames = numToSet;
    }

    void setAyoGamesWon(int numToSet){
        ayoGamesWon = numToSet;
    }
}
