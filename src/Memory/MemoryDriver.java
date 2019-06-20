package Memory;

import GameEnv.*;

import java.awt.*;

import java.io.File;


import javax.imageio.ImageIO;
import javax.swing.*;

public class MemoryDriver extends Driver {

	MemoryBoard mb;

	@Override
	public void drawBoard(JPanel canvas, GameState gs) {

		Graphics g = canvas.getGraphics();

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		g.setColor(Color.decode("#337DFF"));
		g.fillRect(0, 0, width, height);

		g.setColor(Color.decode("#2C2C54"));

		int x = 0;

		for(int i = height/4; i < height ; i+=(height/4)){
			g.drawLine(0, i, width, i);
			x = i;
		}

		for (int i = width/5; i < width; i+= (width/5)) {
			g.drawLine(i, 0, i, height);
		}

		MemoryBoard board = (MemoryBoard) gs.getBoard();

		mb = board;

		for (int i = 0; i < board.getHeight(); i+= 1) {
			for (int j = 0; j < board.getWidth(); j += 1) {
				try {
					MemoryCell data = (MemoryCell) board.getCell(j,i);

					if (!data.getHidden()) {
						switch (data.getShapeType()) {
							case BLUE_JOSH:
								g.drawImage(ImageIO.read( new File("./images/BlueJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);
								break;

							case DARK_GREEN_JOSH:
								g.drawImage(ImageIO.read( new File("./images/DarkGreenJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);
								break;
							case RED_JOSH:
								g.drawImage(ImageIO.read( new File("./images/RedJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);
								break;
							case LIGHT_BLUE_JOSH:
								g.drawImage(ImageIO.read( new File("./images/LightBlueJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);
								break;
							case GREEN_JOSH:
								g.drawImage(ImageIO.read( new File("./images/GreenJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);

								break;
							case GREY_JOSH:
								g.drawImage(ImageIO.read( new File("./images/GreyJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);

								break;
							case PINK_JOSH:
								g.drawImage(ImageIO.read( new File("./images/PinkJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);

								break;
							case PURPLE_JOSH:
								g.drawImage(ImageIO.read( new File("./images/PurpleJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);

								break;
							case ORANGE_JOSH:
								g.drawImage(ImageIO.read( new File("./images/OrangeJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);

								break;
							case YELLOW_JOSH:
								g.drawImage(ImageIO.read( new File("./images/YellowJosh.png" )), j*(width/5), (i+1)*(height/4)-144,144, 144, null);
								break;


						}
					}

				} catch (Exception e) {

				}
			}
		}
		
	}

	@Override
	public void handleClick(JPanel canvas, GameState gs, double x, double y) {
		// TODO Auto-generated method stub

		int x_coord = ((int) x) / (canvas.getWidth() / 5);
		int y_coord = ((int) y) / (canvas.getHeight() / 4);


		try {

			MemoryCell mc = (MemoryCell) mb.getCell(x_coord, y_coord);

			mc.revealCell();
			this.drawBoard(canvas, gs);
			Thread.sleep(1000);
			mc.hideCell();

			((MemoryGameState) gs).makeMove(new CoordMove(x_coord, y_coord));
			if (gs.isGameOver()) {
				System.out.println("Game is done");
			}

			this.drawBoard(canvas, gs);

		} catch (Exception ie) {
			ie.printStackTrace();
		}
	}

}

