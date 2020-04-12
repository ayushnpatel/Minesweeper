import java.awt.*;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.JToggleButton;

import java.io.*;
public class Tile{
    private JToggleButton button;
    private String state;
    private int x;
    private int y;
    private boolean recursivized;
    private boolean clickable = true;
    private boolean flagged;
    public Tile(JToggleButton button, String state, Point p){
        this.button = button;
        this.state = state;
        this.x = (int)p.getX();
        this.y = (int)p.getY();
        flagged = false;
    }
    public String getState(){
        return state;
    }
    public JToggleButton getButton(){
        return button;
    }
    public void setFlagged(boolean flag){
        this.flagged = flag;
    }
    public boolean isFlagged(){
        return flagged;
    }
    public void setState(String state){
        this.state = state;
    }
    public boolean getRecursivized(){
        return recursivized;
    }
    public void setRecursivized(boolean bool){
        recursivized = bool;
    }
    public boolean getClickable(){
        return clickable;
    }
    public void setClickable(boolean bool){
        this.clickable = bool;
    }
    public void openUp(){
        setClickable(false);
        switch(getState()){
            case "0":
                getButton().setIcon(Minesweeper.empty);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                if(!recursivized){
                    this.recursivized = true;
                    Minesweeper.expansionAlgorithm(x, y);
                }
                
                break;
            case "1":
                getButton().setIcon(Minesweeper.one);
                //System.out.println(Minesweeper.one.getImage().getSource().toString());
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "2":
                getButton().setIcon(Minesweeper.two);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "3":
                getButton().setIcon(Minesweeper.three);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "4":
                getButton().setIcon(Minesweeper.four);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "5":
                getButton().setIcon(Minesweeper.five);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "6":
                getButton().setIcon(Minesweeper.six);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "7":
                getButton().setIcon(Minesweeper.seven);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "8":
                getButton().setIcon(Minesweeper.eight);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                break;
            case "BO":
                getButton().setIcon(Minesweeper.mine);
                getButton().setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY, Color.BLACK) );
                Minesweeper.endGame();
                break;
        }
        //getButton().setEnabled(false);
        //button = null;
        //getButton().removeAll();
    }
}