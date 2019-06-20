package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import DB.UserDB;
import Enums.GameType;
import Exceptions.InvalidCellException;
import GameEnv.Driver;
import GameEnv.DriverFactory;
import GameEnv.GameState;
import GameEnv.GameStateFactory;
import EelsAndEscalator.EelsAndEscalatorGameState;

public class Fortnite  extends JFrame {

    //    private JPanel MainWindow;
    private JPanel controlPanel;
    private JPanel boardPanel;
    private JButton startGameButton;
    private JButton playerStatus;
    private JButton addPLayer;
    private JTextField newPlayer;

    private GameType gtype;

    private JComboBox<GameType> gameSelector;

    private JComboBox<String> p1Selector;
    private JComboBox<String> p2Selector;

    private JButton dice;
    private JLabel diceValue;

    private JLabel memP1Score;
    private JLabel memP2Score;

    private JLabel systemStatus;

    private JLabel turnStatus;

    UserDB udb;
    private GameState gs;
    private Driver dr;

    private GameStateFactory gFactory = new GameStateFactory();
    private DriverFactory dFactory = new DriverFactory();

    public void initAllComponents() {
        controlPanel = new JPanel();
        boardPanel = new JPanel();
        startGameButton = new JButton("START GAME");
        playerStatus = new JButton("View Leaderboard");
        addPLayer = new JButton("Add Player");
        newPlayer = new JTextField("Enter Player Name");

        gameSelector = new JComboBox<GameType>();

        p1Selector = new JComboBox<String>();
        p2Selector = new JComboBox<String>();

        systemStatus = new JLabel();

        turnStatus = new JLabel();
        dice = new JButton("Roll Dice");
        dice.setVisible(false);
        dice.setPreferredSize(new Dimension(145, 35));
        diceValue = new JLabel();
        diceValue.setVisible(false);

        memP1Score = new JLabel();
        memP2Score = new JLabel();

    }

    Fortnite() {

        udb = new UserDB();
        udb.loadDB();

        initAllComponents();

        controlPanel.setBackground(Color.decode("#1A7AA0"));
        boardPanel.setBackground(Color.decode("#FCE25D"));


        controlPanel.setBounds(0, 0, 200, 720);
        boardPanel.setBounds(200, 0, 720, 720);

        this.add(controlPanel);
        this.add(boardPanel);

        controlPanel.add(new JLabel("<html><h1><font color='white'>Select Game: </font></h1></html>"));
        controlPanel.add(gameSelector);

        controlPanel.add(new JLabel("<html><p><font color='white'>======================</font></p></html>"));


        // Adding text field and button to add new player to game.
        controlPanel.add(newPlayer);
        controlPanel.add(addPLayer);

        controlPanel.add(new JLabel("<html><h2><font color='white'>Select Player 1</font></h2></html>"));
        controlPanel.add(p1Selector);

        controlPanel.add(new JLabel("<html><h2><font color='white'>Select Player 2</font></h2></html>"));
        controlPanel.add(p2Selector);

        controlPanel.add(new JLabel("<html><p><font color='white'>++++++++  ++++++++</font></p></html>"));

        controlPanel.add(playerStatus);

        controlPanel.add(new JLabel("<html><p><font color='white'>======================</font></p></html>"));

        controlPanel.add(startGameButton);

        controlPanel.add(new JSeparator());
        controlPanel.add(new JLabel("<html><p><font color='white'>======================</font></p></html>"));

        controlPanel.add(new JLabel("<html><p><font color='white'>System Status: </font></p></html>"));
        controlPanel.add(this.systemStatus);
        this.setSystemStatus("Waiting for a new game");

        controlPanel.add(new JLabel("<html><p><font color='white'>======================</font></p></html>"));

        controlPanel.add(this.turnStatus);

        controlPanel.add(dice);
        controlPanel.add(diceValue);

        controlPanel.add(memP1Score);
        controlPanel.add(memP2Score);


        for(String s : udb.getUserNames()) {
            p1Selector.addItem(s);
            p2Selector.addItem(s);
        }

        for(GameType g : GameType.values()){
            gameSelector.addItem(g);
        }

        addListeners();

        startMain();
    }

    public void setSystemStatus(String status) {
        this.systemStatus.setText("<html><p><u><font color='white'>" + status + "</font></u></p></html>");
    }

    public void setSystemError(String status) {
        this.systemStatus.setText("<html><p><strong><u><font color='#f35f57'>" + status + "</font></u></strong></p></html>");
    }

    public void setGameStatus(String title, String content) {
        String t = "<html><p><font color='white' style='font-size:15px'><strong>"+title+": </strong>" + content + " </font></p></html>";
        turnStatus.setText(t);
    }

    public void setDiceRoll(int roll) {
        String t = "<html><h1><font color='white' style='font-size:15px'><strong>Rolled a "+ roll +" </strong> </font></h1></html>\n";
        diceValue.setText(t);
    }

    public void setMemScores(int p1Score, int p2Score, String p1, String p2) {
        String t = "<html><p><font color='white' style='font-size:10px'><strong>" + p1 + " Score: " + p1Score + "</strong></font></p></html>";
        String s = "<html><p><font color='white' style='font-size:10px'><strong>" + p2 + " Score: " + p2Score + "</strong></font></p></html>";
        memP1Score.setText(t);
        memP2Score.setText(s);
    }

    public void addListeners() {

        newPlayer.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e){
                newPlayer.setText("");
            }
        });

        addPLayer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                try {
                    String playerName = newPlayer.getText();
                    udb.addUser(playerName);
                    p1Selector.addItem(playerName);
                    p2Selector.addItem(playerName);
                    newPlayer.setText("Enter Player Name");
                    JOptionPane.showMessageDialog(new JFrame(), "PLAYER CREATED: " + playerName, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception error){
                    JOptionPane.showMessageDialog(new JFrame(), "PLAYER ALREADY EXISTS", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        playerStatus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                String n = udb.printDB(gameSelector.getSelectedItem().toString());

                boardPanel.getGraphics().clearRect(0, 0, boardPanel.getWidth(), boardPanel.getHeight());

                int x = 50;
                int y = 50;
                Graphics g = boardPanel.getGraphics();
                for (String line : n.split("\n"))
                    g.drawString(line, x, y += g.getFontMetrics().getHeight());
            }
        });


        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                if(p1Selector.getSelectedItem().toString().equals(p2Selector.getSelectedItem().toString())) {
                    setSystemError("Play against yourself?");
                } else {
                    setSystemStatus("Game started :)");
                    gtype = ((GameType) gameSelector.getSelectedItem());
                    try {
                        gs = gFactory.createGameState(gtype, p1Selector.getSelectedItem().toString(), p2Selector.getSelectedItem().toString());
                        if(gtype == GameType.EELS_AND_ESCALATORS) {
                            dice.setVisible(true);
                            diceValue.setVisible(true);
                        } else if(gtype == GameType.MEMORY) {
                            memP1Score.setVisible(true);
                            memP2Score.setVisible(true);
                        } else{
                            dice.setVisible(false);
                            diceValue.setVisible(false);
                            memP1Score.setVisible(false);
                            memP2Score.setVisible(false);
                        }
                        dr = dFactory.createDriver(gs, gtype);
                    }catch(InvalidCellException ice) {
                        ice.printStackTrace();
                    }
                    setGameStatus("Turn", gs.getTurn().getPlayer());
                    dr.drawBoard(boardPanel, gs);
                }
            }
        });

        dice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                dr.handleClick(boardPanel, gs, e.getX(), e.getY());
                setGameStatus("Turn", gs.getTurn().getPlayer());
                setDiceRoll(((EelsAndEscalatorGameState) gs).getRoll());
                if(gs.isGameOver()) {
                    String winner = gs.getWinner().getPlayer();

                    setSystemStatus("Game Ended :D");
                    setGameStatus("Winner", winner);

                    if(!winner.equals("NONE")) {
                        udb.findUser(gs.getWinner().getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());
                        if (gs.getPlayer(1) == gs.getWinner()) {
                            udb.findUser(gs.getPlayer(2).getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());
                        } else {
                            udb.findUser(gs.getPlayer(1).getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());
                        }
                    }
                    dice.setVisible(false);
                    diceValue.setVisible(false);
                }
            }
        });

        boardPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(gtype != GameType.EELS_AND_ESCALATORS && gtype != null) {
                    super.mouseReleased(e);
                    dr.handleClick(boardPanel, gs, e.getX(), e.getY());
                    setGameStatus("Turn", gs.getTurn().getPlayer());
                    setMemScores(gs.getPlayer(1).getScore(), gs.getPlayer(2).getScore(), gs.getPlayer(1).getPlayer(), gs.getPlayer(2).getPlayer());
                    if (gs.isGameOver()) {
                        String winner = gs.getWinner().getPlayer();

                        setSystemStatus("Game Ended :D");
                        setGameStatus("Winner", winner);

                        if (!winner.equals("NONE")) {
                            udb.findUser(gs.getWinner().getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());

                            if (gs.getPlayer(1) == gs.getWinner()) {
                                udb.findUser(gs.getPlayer(2).getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());
                            } else {
                                udb.findUser(gs.getPlayer(1).getPlayer()).incGameWon(gameSelector.getSelectedItem().toString());
                            }
                        }
                    }
                }
                udb.saveDB();
                udb.loadDB();
            }
        });
    }

    public void centerWindow() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - this.getWidth()) / 2);
        int y = (int) ((d.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    public void startMain() {
        this.setSize(920, 750);
        this.setLayout(null);
        this.centerWindow();
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Fortnite();
    }

}