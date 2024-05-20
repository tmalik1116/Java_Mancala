package ui;
/*
 * Class to provide a GUI button component that knows what its position is in a grid.
 */

public class PositionAwareButton extends javax.swing.JButton{
    private int yPos;
    private int xPos;
    private int pitNum;

    public PositionAwareButton(){
        super();
    }
    public PositionAwareButton(String val){
        super(val);
    }

    public int getAcross(){
        return xPos;
    }

    public int getDown(){
        return yPos;
    }

    public int getPitNum(){
        return pitNum;
    }
    public void setAcross(int val){
        xPos = val;
    }
    public void setDown(int val){
        yPos = val;
    }
    public void setPitNum(int val){
        pitNum = val;
    }

}