package Othello;

import Exceptions.InvalidCellException;
import Exceptions.InvalidMoveException;
import GameEnv.CoordMove;
import GameEnv.Driver;
import GameEnv.GameState;

import javax.swing.*;
import java.awt.*;

public class OthelloDriver extends Driver {

    public OthelloDriver() {

    }

    public void drawBoard(JPanel canvas, GameState ogs){

        Graphics g = canvas.getGraphics();

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.decode("#ACC3A6"));
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.setColor(Color.decode("#2C2C54"));
        for(int i = 0; i < 720; i+=90){
            g.drawLine(i, 0, i, canvas.getHeight());
        }

        for(int j = 0; j < 720; j+=90){
            g.drawLine(0, j, canvas.getWidth(), j);
        }

        OthelloBoard board = ((OthelloBoard) ogs.getBoard());


        for(int i = 0; i < board.getWidth(); i++){
            for(int j = 0; j < board.getHeight(); j++){
                try{
                    if(board.getCell(i, j).getData() == null){
                        if(ogs.isValidMove(new CoordMove(i, j))){
                            g.setColor(Color.decode("#A40E4C"));
                            g.fillOval(i*90 + 35, j *90 + 35, 20, 20);
                        }
                    } else {
                        if(board.getCell(i, j).getData().getOwner() == ogs.getPlayer(1)){
                            g.setColor(Color.WHITE);
                        } else {
                            g.setColor(Color.BLACK);
                        }
                        g.drawOval(i*90+10, j*90+10, 70, 70);
                        g.fillOval(i*90+10, j*90+10, 70, 70);
                    }

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleClick(JPanel canvas, GameState ogs, double x, double y){
        int x_coord = ((int)x) / 90;
        int y_coord = ((int)y) / 90;


        try{
            ogs.makeMove(new CoordMove(x_coord, y_coord));
            this.drawBoard(canvas, ogs);
        }  catch (InvalidMoveException ie){
            JOptionPane.showMessageDialog(new JFrame(), "INVALID MOVE", "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidCellException ice){
            ice.printStackTrace();
        }
    }

}
