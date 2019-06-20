package TicTacToe;

import Exceptions.InvalidCellException;
import Exceptions.InvalidMoveException;
import GameEnv.CoordMove;
import GameEnv.Driver;
import GameEnv.GameState;

import java.awt.*;
import javax.swing.*;


public class TicTacToeDriver extends Driver {

	public TicTacToeDriver() {}

	@Override
	public void drawBoard(JPanel canvas, GameState gs) {
		Graphics g = canvas.getGraphics();

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		g.setColor(Color.decode("#ADD8E6"));
		g.fillRect(0, 0, width, height);

		g.setColor(Color.decode("#2C2C54"));
		for(int i = width/3; i < width; i+=(width/3)){
			g.drawLine(i, 0, i, height);
			g.drawLine(0, i, width, i);
		}

		TicTacToeBoard board = (TicTacToeBoard) gs.getBoard();

		for(int i = 0; i < board.getHeight(); ++i) {
			for(int j = 0; j < board.getWidth(); ++j) {
				try {
					String data = board.getCell(i, j).getData().getType();

					if(data.equals("X")) {
						g.drawLine(i*240 + 20, j * 240 + 20, (i+1)*240-20, (j+1)*240-20);
						g.drawLine(i*240+20, (j+1)*240-20, (i+1)*240-20, j*240+20);
					}else if(data.equals("O")) {
						g.drawOval(i*240+20, j*240+20, 200, 200);
					}
				}catch(InvalidCellException ice) {
					ice.printStackTrace();
				}
			}
		}
	}

	@Override
	public void handleClick(JPanel canvas, GameState gs, double x, double y) {

		int x_coord = ((int)x) / (canvas.getWidth()/3);
		int y_coord = ((int)y) / (canvas.getHeight()/3);


		try{
			gs.makeMove(new CoordMove(x_coord, y_coord));

		}catch (InvalidMoveException ime) {
			JOptionPane.showMessageDialog(new JFrame(), "INVALID MOVE", "ERROR", JOptionPane.ERROR_MESSAGE);

		}catch (Exception ie){
			ie.printStackTrace();
		} finally {
			this.drawBoard(canvas, gs);
		}

	}

}
