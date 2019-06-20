package EelsAndEscalator;

import Exceptions.InvalidCellException;
import Exceptions.InvalidMoveException;
import GameEnv.CounterMove;
import GameEnv.Driver;
import GameEnv.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EelsAndEscalatorDriver extends Driver {
	private BufferedImage image;

	private BufferedImage player1Piece;
	private BufferedImage player2Piece;

	@Override
	public void drawBoard(JPanel canvas, GameState gs) {
		try {

			image = ImageIO.read(new File("images/eelsandescalators.jpg"));

			player1Piece = ImageIO.read(new File("images/spongebobpiece.png"));

			player2Piece = ImageIO.read(new File("images/patrickpiece.png"));

			Graphics g = canvas.getGraphics();

			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g.drawImage(image, 0, 0, canvas.getWidth(),  canvas.getHeight(), null);

			EelsAndEscalatorBoard board = (EelsAndEscalatorBoard) gs.getBoard();

			for(int i = 0; i < board.getHeight(); ++i) {
				for(int j = 0; j < board.getWidth(); ++j) {
					try {
						String data = board.getCell(i, j).getData().getType();

						if(data.equals("1")) {
							g.drawImage(player1Piece, j*(canvas.getWidth()/10), (i+1)*(canvas.getHeight()/10)- 72, 72, 72, null);
						}else if (data.equals("2")){
							g.drawImage(player2Piece, j*(canvas.getWidth()/10), (i+1)*(canvas.getHeight()/10)- 72, 72, 72, null);
						}
					}catch(InvalidCellException ice) {
						ice.printStackTrace();
					}
				}
			}

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void handleClick(JPanel canvas, GameState gs, double x, double y) {
        int move = ((EelsAndEscalatorGameState) gs).rollDice();

        try {
			gs.makeMove(new CounterMove(move));
        } catch (InvalidMoveException ime) {
            ime.printStackTrace();
        } catch (InvalidCellException ice) {
            ice.printStackTrace();
        } finally {
            drawBoard(canvas, gs);
        }
    }
}