import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.Border;
import javax.swing.Timer;

public class Minesweeper extends JPanel implements ActionListener{
    JFrame frame;
    JMenu menuGame, menuIcons, menuControls; 
    JPanel menuPanel, gridPanel, scorePanel, headPanel;
    JMenuBar menuBar;
    ButtonGroup difficultyGroup, iconGroup;
    static JTextField timerLabel, mineLabel;
    static Timer timer;
    static int x,y;
    static int mineCount = 10;
    static int finalMineCount = 10;
    static int timeCount = 0;
    GraphicsEnvironment ge;
    JRadioButtonMenuItem beginnerButton, intermediateButton, advancedButton, regularButton, retroButton, coolButton;
    static JButton smileyButton;
    int difficultyHeight = 9;
    int difficultyWidth = 9;
    static Tile[][] gridArray;
    public boolean firstClick = false;
    static boolean gameEnded = false;
    public boolean gameFirstStarted;
    public static ImageIcon flag, block, smileyStart, one, two, three, four, five, six, seven, eight, empty, mine, smileyHappy, smileyLose, smileyPressed;
    

    public Minesweeper(){
        smileyStart = new ImageIcon("smiley.png");
        smileyStart = new ImageIcon(smileyStart.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
        smileyHappy = new ImageIcon("smileyHappy.png");
        smileyHappy = new ImageIcon(smileyHappy.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
        smileyLose = new ImageIcon("smileyLose.png");
        smileyLose = new ImageIcon(smileyLose.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
        smileyPressed = new ImageIcon("smileyPressed.png");
        smileyPressed = new ImageIcon(smileyPressed.getImage().getScaledInstance(37, 37, Image.SCALE_SMOOTH));
        if(!gameFirstStarted){
            flag = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/flagged.png");
            flag = new ImageIcon(flag.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            block = new ImageIcon("block.png");
            block = new ImageIcon(block.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            one = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/one.png");
            one = new ImageIcon(one.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            two = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/two.png");
            two = new ImageIcon(two.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            three = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/three.png");
            three = new ImageIcon(three.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            four = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/four.png");
            four = new ImageIcon(four.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            five = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/five.png");
            five = new ImageIcon(five.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            six = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/six.png");
            six = new ImageIcon(six.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            seven = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/seven.png");
            seven = new ImageIcon(seven.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            eight = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/eight.png");
            eight = new ImageIcon(eight.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            empty = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/empty.png");
            empty = new ImageIcon(empty.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            mine = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/mine.png");
            mine = new ImageIcon(mine.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            gameFirstStarted = true;
        }
        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.BOLD, new File("digital-7 (italic).ttf")));
            System.out.println(ge.getAllFonts()[ge.getAllFonts().length-1]);
       } catch (IOException|FontFormatException e) {
            //Handle exception  
        }  
        makingMenu();
        frame = new JFrame("Minesweeper");
        frame.add(this);
        frame.add(headPanel, BorderLayout.NORTH);
        //frame.add(scorePanel, BorderLayout.NORTH);
        makeMap();
        frame.setSize(difficultyHeight*32, difficultyWidth*35+headPanel.getHeight());
        frame.pack();
        frame.setLocationRelativeTo(null);    
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UIManager.put("ToggleButton.select", Color.LIGHT_GRAY);
        repaint();
        timerLabel.setText(timeCount+"");
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                timeCount++;
                if(timeCount < 10){
                    timerLabel.setText("00"+timeCount);
                }else if(timeCount < 100){
                    timerLabel.setText("0"+timeCount);
                }else
                    timerLabel.setText(""+timeCount);
                //System.out.println(timeCount);
  
            }
        };
        timer = new Timer(1000,taskPerformer);
        //Thread.sleep(5000);
    } 
     
    

        
        //g.dr awString("ASFASF", 0, 0);
    
    public  static void main(String [] args){
        new Minesweeper();
    } 
    public void makingMenu(){
        timerLabel = new JTextField("");
        mineLabel = new JTextField(mineCount+"");
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("digital-7 (italic).ttf")).deriveFont(20f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            timerLabel.setFont(customFont);
            timerLabel.setOpaque(true);
            timerLabel.setBackground(Color.BLACK);
            timerLabel.setForeground(Color.RED);
            mineLabel.setFont(customFont);
            mineLabel.setOpaque(true);
            mineLabel.setBackground(Color.BLACK);
            mineLabel.setForeground(Color.RED);
            timerLabel.setEditable(false);
            mineLabel.setEditable(false);
            timerLabel.setEnabled(false);
            mineLabel.setEnabled(false);
            timerLabel.setDisabledTextColor(Color.RED);
            mineLabel.setDisabledTextColor(Color.RED);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }
        menuGame = new JMenu("Game");
        menuBar = new JMenuBar();
        menuIcons = new JMenu("Icons");
        menuControls = new JMenu("Controls");
        menuControls.add(new JMenuItem("Time to play Minesweeper! Change the icons, the board size, and try to not hit the mines! When you cover up all the mines with flags, you win!"));
        menuPanel = new JPanel();
        difficultyGroup = new ButtonGroup();
        iconGroup = new ButtonGroup();
        beginnerButton = new JRadioButtonMenuItem("Beginner");
        beginnerButton.addActionListener(this);
        difficultyGroup.add(beginnerButton);
        menuGame.add(beginnerButton);
        intermediateButton = new JRadioButtonMenuItem("Intermediate");
        intermediateButton.addActionListener(this);
        difficultyGroup.add(intermediateButton);
        menuGame.add(intermediateButton);
        advancedButton = new JRadioButtonMenuItem("Advanced");
        advancedButton.addActionListener(this);
        difficultyGroup.add(advancedButton);
        menuGame.add(advancedButton);
        beginnerButton.setSelected(true);

        regularButton = new JRadioButtonMenuItem("Regular");
        retroButton = new JRadioButtonMenuItem("Retro");
        coolButton = new JRadioButtonMenuItem("Cool");
        regularButton.addActionListener(this);
        retroButton.addActionListener(this);
        coolButton.addActionListener(this);
        iconGroup.add(regularButton);
        iconGroup.add(retroButton);
        iconGroup.add(coolButton);
        menuIcons.add(regularButton);
        menuIcons.add(retroButton);
        menuIcons.add(coolButton);
        regularButton.setSelected(true);

        scorePanel = new JPanel();
        smileyButton = new JButton(smileyStart);
        smileyButton.addActionListener(this);
        scorePanel.setLayout(new GridBagLayout());
        //GridBagLayout gBagLayout = new GridBagLayout();
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.X_AXIS));
        // c.fill = GridBagConstraints.HORIZONTAL;
        // c.gridx = 0;
        // c.gridy = 0;
        // //c.anchor = c.CENTER;
        //gBagLayout.setConstraints(smileyButton);
        scorePanel.add(mineLabel);
        scorePanel.add(Box.createHorizontalGlue());
        //scorePanel.add(Box.createRigidArea(new Dimension(100,60)));
        scorePanel.add(smileyButton);
        scorePanel.add(Box.createHorizontalGlue());
        //scorePanel.add(Box.createRigidArea(new Dimension(100,60)));
        //timerLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        //mineLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        scorePanel.add(timerLabel);
        headPanel = new JPanel();
        headPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 5;
        headPanel.add(menuPanel,c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 5;
        c.gridx = 0;
        c.gridy = 1;
        headPanel.add(scorePanel,c);  

        Border blackline = BorderFactory.createLineBorder(Color.black);   
        //scorePanel.setBorder(blackline);

        JMenu spacer = new JMenu();
        spacer.setEnabled(false);
        spacer.setMinimumSize(new Dimension(30, 1));
        spacer.setPreferredSize(new Dimension(30, 1));
        spacer.setMaximumSize(new Dimension(30, 1));
        
        menuBar.add(menuControls);
        menuBar.add(spacer);
        menuBar.add(menuGame);
        menuBar.add(spacer);
        menuBar.add(menuIcons);
        menuGame = new JMenu("Controls");
        menuPanel.add(menuBar);
    }
    public void makeMap(){
        gameEnded = false;
        firstClick = false;
        frame.setSize(difficultyWidth*32, difficultyHeight*35+headPanel.getHeight());
        if(gridPanel != null)
            frame.remove(gridPanel);
        gridPanel = new JPanel();
        gridArray = new Tile[difficultyHeight][difficultyWidth];
        gridPanel.setLayout(new GridLayout(difficultyHeight, difficultyWidth));
        System.out.println(gridArray.length+"    "+gridArray[0].length);
        for(x = 0; x < gridArray.length; x++){
            for( y = 0; y < gridArray[x].length; y++){
                gridArray[x][y] = new Tile(new JToggleButton(block), "BL", new Point(x,y));
                gridArray[x][y].getButton().setBorder(BorderFactory.createEmptyBorder());
                gridArray[x][y].getButton().addMouseListener(new MouseInputAdapter() {
                    int thisX = x;
                    int thisY = y;
                    boolean flagged;
                    boolean unclickable;
                    public void mousePressed(MouseEvent e) {
                        if(e.getButton() == java.awt.event.MouseEvent.BUTTON1){
                            smileyButton.setIcon(smileyPressed);
                        }
                    }public void mouseReleased(MouseEvent e) {
                        if(!gameEnded)
                            smileyButton.setIcon(smileyStart);
                        else{
                            smileyButton.setIcon(smileyLose);
                        }
                        try {
                            if( (e.getButton() == java.awt.event.MouseEvent.BUTTON3 || (e.getModifiersEx() == 128 && e.getButton() == 1))  && (gridArray[thisX][thisY].getButton().getIcon().equals(block) || gridArray[thisX][thisY].getButton().getIcon().equals(flag))){
                                if(!flagged && gridArray[thisX][thisY].getClickable()){
                                    gridArray[thisX][thisY].getButton().setIcon(flag);
                                    flagged = true;
                                    gridArray[thisX][thisY].setClickable(false);
                                    mineCount--;
                                    mineLabel.setText(mineCount+"");
                                    System.out.println("first "+flagged);
                                    gridArray[thisX][thisY].setFlagged(true);
                                }else if(flagged && !gridArray[thisX][thisY].getClickable()){
                                    gridArray[thisX][thisY].getButton().setIcon(block);
                                    flagged = false;
                                    mineCount++;
                                    mineLabel.setText(mineCount+"");
                                    gridArray[thisX][thisY].setClickable(true);
                                    System.out.println("second "+flagged);
                                    gridArray[thisX][thisY].setFlagged(false);
                                }           
                                
                            }else if( e.getButton() == java.awt.event.MouseEvent.BUTTON1 && !firstClick && gridArray[thisX][thisY].getClickable()){
                                setBoard(thisX, thisY);
                                firstClick = true;
                                timer.start();
                                gridArray[thisX][thisY].openUp();
                                gridArray[thisX][thisY].setClickable(false);
                                //gridArray[thisX][thisY].getButton().removeMouseListener(this);
                                //gridArray[thisX][thisY] = null;
                            }
                            else if(e.getButton() == java.awt.event.MouseEvent.BUTTON1 && gridArray[thisX][thisY].getClickable()){
                                
                                gridArray[thisX][thisY].openUp();
                                gridArray[thisX][thisY].setClickable(false);
                                //gridArray[thisX][thisY].getButton().removeMouseListener(this);
                                //gridArray[thisX][thisY] = null;
                            }
                            checkForWin();  
                        } catch (Exception exception) {
                        }   
                    
                    }
                
                    public void mouseEntered(MouseEvent e) {
                    }
                
                    public void mouseExited(MouseEvent e) {
                    }
                
                    public void mouseClicked(MouseEvent e) {
                    }
                });
                gridPanel.add(gridArray[x][y].getButton());
                //System.out.print(gridArray[x][y].getState()+" ");
            }
            //System.out.println();
        }
        Border blackline = BorderFactory.createLineBorder(Color.black);
        gridPanel.setBorder(blackline);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    public void doSomething(){
        timeCount++;
        System.out.println(timeCount);
    }public void actionPerformed(ActionEvent e) {
        if (beginnerButton.isSelected()) {
            timeCount = 0;
            mineCount = 10;
            finalMineCount = 10;
            mineLabel.setText(mineCount+"");
            timerLabel.setText(timeCount+"");
            timer.stop();
            difficultyHeight = 9;
            difficultyWidth = 9;
            makeMap();
        } 
        if (intermediateButton.isSelected()) {
            timeCount = 0;
            mineCount = 40;
            finalMineCount = 40;
            mineLabel.setText(mineCount+"");
            timerLabel.setText(timeCount+"");
            timer.stop();
            difficultyHeight = 16;
            difficultyWidth = 16;
            makeMap();
        } 
        if (advancedButton.isSelected()) {
            timeCount = 0;
            mineCount = 99;
            finalMineCount = 99;
            mineLabel.setText(mineCount+"");
            timerLabel.setText(timeCount+"");
            timer.stop();
            difficultyHeight = 16;
            difficultyWidth = 30;
            makeMap();
        }
        if(retroButton.isSelected()){
            timeCount = 0;
            timerLabel.setText(timeCount+"");
            timer.stop();
            flag = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/flagged.png");
            flag = new ImageIcon(flag.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            block = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/block.png");
            block = new ImageIcon(block.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            one = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/one.png");
            one = new ImageIcon(one.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            two = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/two.png");
            two = new ImageIcon(two.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            three = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/three.png");
            three = new ImageIcon(three.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            four = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/four.png");
            four = new ImageIcon(four.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            five = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/five.png");
            five = new ImageIcon(five.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            six = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/six.png");
            six = new ImageIcon(six.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            seven = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/seven.png");
            seven = new ImageIcon(seven.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            eight = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/eight.png");
            eight = new ImageIcon(eight.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            empty = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/empty.png");
            empty = new ImageIcon(empty.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            mine = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites2/mine.png");
            mine = new ImageIcon(mine.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            makeMap();
        }
        if(regularButton.isSelected()){
            timeCount = 0;
            timerLabel.setText(timeCount+"");
            timer.stop();
            flag = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/flagged.png");
            flag = new ImageIcon(flag.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            block = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/block.png");
            block = new ImageIcon(block.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            one = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/one.png");
            one = new ImageIcon(one.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            two = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/two.png");
            two = new ImageIcon(two.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            three = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/three.png");
            three = new ImageIcon(three.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            four = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/four.png");
            four = new ImageIcon(four.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            five = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/five.png");
            five = new ImageIcon(five.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            six = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/six.png");
            six = new ImageIcon(six.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            seven = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/seven.png");
            seven = new ImageIcon(seven.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            eight = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/eight.png");
            eight = new ImageIcon(eight.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            empty = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/empty.png");
            empty = new ImageIcon(empty.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            mine = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites/mine.png");
            mine = new ImageIcon(mine.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            makeMap();
        }
        if(coolButton.isSelected()){
            timeCount = 0;
            timerLabel.setText(timeCount+"");
            timer.stop();
            flag = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/flagged.png");
            flag = new ImageIcon(flag.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            block = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/block.png");
            block = new ImageIcon(block.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            one = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/one.png");
            one = new ImageIcon(one.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            two = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/two.png");
            two = new ImageIcon(two.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            three = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/three.png");
            three = new ImageIcon(three.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            four = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/four.png");
            four = new ImageIcon(four.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            five = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/five.png");
            five = new ImageIcon(five.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            six = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/six.png");
            six = new ImageIcon(six.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            seven = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/seven.png");
            seven = new ImageIcon(seven.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            eight = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/eight.png");
            eight = new ImageIcon(eight.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            empty = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/empty.png");
            empty = new ImageIcon(empty.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            mine = new ImageIcon("/Users/ayush/Desktop/Data Structures/Minesweeper/sprites3/mine.png");
            mine = new ImageIcon(mine.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
            makeMap();
        }
        if(e.getSource().equals(smileyButton)){
            smileyButton.setIcon(smileyStart);
            makeMap();
            timer.stop();
            timeCount = 0;
            timerLabel.setText(timeCount+"");
        } 
        //repaint();
    }
    public void setBoard(int clickedX, int clickedY){
        smileyButton.setIcon(smileyStart);
        //System.out.println("ASFASFASFASFASF");
        int mineCountTemp = 0;
        switch(difficultyWidth){
            case 9:
                mineCountTemp = 10;
                break;
            case 16:
                mineCountTemp = 40;
                break;
            case 30:
                mineCountTemp = 99;
                break;
        }
        int randomX;
        int randomY;
        ArrayList<Point> pList = new ArrayList<Point>(Arrays.asList(new Point(clickedX, clickedY), new Point(clickedX-1, clickedY), new Point(clickedX+1, clickedY), new Point(clickedX, clickedY-1), new Point(clickedX-1, clickedY-1), new Point(clickedX+1, clickedY-1), new Point(clickedX, clickedY+1), new Point(clickedX-1, clickedY+1), new Point(clickedX+1, clickedY+1)));
        while(mineCountTemp > 0){
            do{
                randomX = (int)(Math.random()*gridArray.length);
                randomY = (int)(Math.random()*gridArray[0].length);
            }while(pList.contains(new Point(randomX, randomY)));
            pList.add(new Point(randomX, randomY));
            gridArray[randomX][randomY].setState("BO");
            mineCountTemp--;
        }
        for(int x = 0; x < gridArray.length; x++){
            for(int y = 0; y < gridArray[0].length; y++){
                if(gridArray[x][y].getState().equals("BL")){
                    checkAdjacentMines(gridArray[x][y], x, y);
                }
            }
        }
        for(int x = 0; x < gridArray.length; x++){
            for(int y = 0; y < gridArray[0].length; y++){
                System.out.print(gridArray[x][y].getState()+"\t");
            }
            System.out.println();
        }
    }
    public static void expansionAlgorithm(int emptyX, int emptyY){
        //System.out.println(emptyX+" "+emptyY);
        if(isValid(emptyX,emptyY-1) && !gridArray[emptyX][emptyY-1].getState().equals("BO")){
            gridArray[emptyX][emptyY-1].openUp();
        }if(isValid(emptyX-1,emptyY-1) && !gridArray[emptyX-1][emptyY-1].getState().equals("BO")){
            gridArray[emptyX-1][emptyY-1].openUp();
        }if(isValid(emptyX+1,emptyY-1) && !gridArray[emptyX+1][emptyY-1].getState().equals("BO")){
            gridArray[emptyX+1][emptyY-1].openUp();
        }if(isValid(emptyX,emptyY) && !gridArray[emptyX][emptyY].getState().equals("BO")){
            gridArray[emptyX][emptyY].openUp();
        }if(isValid(emptyX-1,emptyY) && !gridArray[emptyX-1][emptyY].getState().equals("BO")){
            gridArray[emptyX-1][emptyY].openUp();
        }if(isValid(emptyX+1,emptyY) && !gridArray[emptyX+1][emptyY].getState().equals("BO")){
            gridArray[emptyX+1][emptyY].openUp();
        }if(isValid(emptyX,emptyY+1) && !gridArray[emptyX][emptyY+1].getState().equals("BO")){
            gridArray[emptyX][emptyY+1].openUp();
        }if(isValid(emptyX-1,emptyY+1) && !gridArray[emptyX-1][emptyY+1].getState().equals("BO")){
            gridArray[emptyX-1][emptyY+1].openUp();
        }if(isValid(emptyX+1,emptyY+1) && !gridArray[emptyX+1][emptyY+1].getState().equals("BO")){
            gridArray[emptyX+1][emptyY+1].openUp();
        }
    }
    public static void endGame(){
        timer.stop();
        if(!gameEnded){
            for(int x = 0; x < gridArray.length; x++){
                for(int y = 0; y < gridArray[x].length; y++){
                    if(gridArray[x][y] != null && gridArray[x][y].getState().equals(("BO")))
                        gridArray[x][y].getButton().setIcon(mine);
                    gridArray[x][y] = null;
                }
            }
        }
        gameEnded = true;
        smileyButton.setIcon(smileyLose);
    }
    public static void checkForWin(){
        int tileCount = 0;
        for(int x = 0; x < gridArray.length; x++){
            for(int y = 0; y < gridArray[x].length; y++){
                if(gridArray[x][y].isFlagged() || gridArray[x][y].getButton().getIcon() == block){
                    tileCount++;
                }
            }
        }
        if(tileCount == finalMineCount){
            timer.stop();
            mineLabel.setText("0");
            gameEnded = true;
            smileyButton.setIcon(smileyHappy);
            for(int x = 0; x < gridArray.length; x++){
                for(int y = 0; y < gridArray[x].length; y++){
                    if(gridArray[x][y].getButton().getIcon() == block){
                        gridArray[x][y].getButton().setIcon(flag);
                    }
                        gridArray[x][y] = null;
                }
            }

        }
        //if(tileCount == mine)
    }
    public void checkAdjacentMines(Tile tile, int x, int y){
        int mineCounter = 0;
        if(isValid(x,y-1) && gridArray[x][y-1].getState().equals("BO"))
            mineCounter++;
        if(isValid(x-1,y-1) && gridArray[x-1][y-1].getState().equals("BO"))
            mineCounter++;
        if(isValid(x+1,y-1) && gridArray[x+1][y-1].getState().equals("BO"))
            mineCounter++;
        if(isValid(x,y) && gridArray[x][y].getState().equals("BO"))
            mineCounter++;
        if(isValid(x-1,y) && gridArray[x-1][y].getState().equals("BO"))
            mineCounter++;
        if(isValid(x+1,y) && gridArray[x+1][y].getState().equals("BO"))
            mineCounter++;
        if(isValid(x,y+1) && gridArray[x][y+1].getState().equals("BO"))
            mineCounter++;
        if(isValid(x-1,y+1) && gridArray[x-1][y+1].getState().equals("BO"))
            mineCounter++;
        if(isValid(x+1,y+1) && gridArray[x+1][y+1].getState().equals("BO"))
            mineCounter++;
        gridArray[x][y].setState(mineCounter+"");
    }
    public static boolean isValid(int x, int y){
        if(x < gridArray.length && x >= 0 && y < gridArray[0].length && y >= 0)
            return true;
        return false;
    }
}