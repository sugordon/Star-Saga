import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Game 
{
	Battleship[] playerShips;
	Battleship[] enemyShips;
	Battleship[] things;
	Blocks[][] player;
	Blocks[][] enemy;
	Frame fr;
	boolean placeMode = true;
	boolean enemyReady = false;
	Battleship selectedShip;
	int shipIndex = 0;
	int delay = -1;
	boolean gameStarted = false;
	
	public Game(Frame fr)
	{
		playerShips = new Battleship[6];
		player = new Blocks[30][30];
		enemy = new Blocks[30][30];
//		Player1= null;
//		Player2 = null;
		this.fr = fr;
		things = new Battleship[6];
		things[0]= new Loki();
		things[1]= new Vanir();
		things[2]= new Valkyrie();
		things[3] = new Vishnu();
		things[4] = new Thor();
		things[5] = new Odin();
		selectedShip = things[0];
		
		for (int ii=0;ii<player.length;ii++) {
			for (int jj=0;jj<player[0].length;jj++) {
				player[ii][jj] = new Blocks(new Point(20+ii*16, 50+jj*16));
			}
		}
		for (int ii=0;ii<enemy.length;ii++) {
			for (int jj=0;jj<enemy[0].length;jj++) {
				enemy[ii][jj] = new Blocks(new Point(725+ii*16, 50+jj*16));
			}
		}
	}
	
	public void mouseMoved(Point p) {
		for (int ii=0;ii<player.length;ii++) {
			for (int jj=0;jj<player[0].length;jj++) {
				player[ii][jj].setHighlighted(false);
			}
		}
		for (int ii=0;ii<enemy.length;ii++) {
			for (int jj=0;jj<enemy[0].length;jj++) {
				enemy[ii][jj].setHighlighted(false);
			}
		}
		Blocks b = this.getIntersect(p, player);
		if (b != null && placeMode) {
			b.setHighlighted(true);
			selectedShip.move(b, player, placeMode);
		}
		if (b == null) {
			b = this.getIntersect(p, enemy);
		}
		if (b != null) {
			for (Point shot : selectedShip.getShotPattern()) {
				int x = shot.x + (b.getA().x - 725) / 16;
				int y = shot.y + (b.getA().y - 50) / 16;
				if (x < 30 && y < 30 && x*y >= 0) {
					enemy[x][y].setHighlighted(true);
				}
			}
		}
	}
	
	public Blocks getIntersect(Point p, Blocks[][] arr) {
		for (int ii=0;ii<arr.length;ii++) {
			for (int jj=0;jj<arr[0].length;jj++) {
				if (arr[ii][jj].intersects(p)) {
					return arr[ii][jj];
				}
			}
		}
		return null;
	}
	
	public void update()
	{
		for (Battleship bs : things) {
			bs.update();
		}
		if (placeMode == false) {
			fr.sendShips(player);
		}
		if (gameStarted == false && placeMode == false && enemyReady && delay == -1) {
			delay = 3 * 1000 / Frame.framerate;
		}
		if (delay != -1) {
			delay--;
		}
		if (delay == 0) {
			System.out.println("GAMESTARTED");
			gameStarted = true;
		}
//		if (gameStarted){
//			boolean lose = true;
//			for (Battleship bs : things) {
//				if (bs.dead()) {
//					lose = false;
//				}
//			}
//			if (lose) {
//				fr.stopTimer();
//				JOptionPane.showMessageDialog(fr.frame, "YOU WIN!");
//			}
//			boolean win = true;
//			if (enemyShips != null) {
//				for (Battleship bs: enemyShips) {
//					if (bs.dead()) {
//						win = false;
//					}
//				}
//			}
//			if (win && gameStarted) {
//				fr.stopTimer();
//				JOptionPane.showMessageDialog(fr.frame, "YOU LOSE!");
//			}
//		}
	}
	
	public void incShots(Point p) {
		System.out.println(p);
		player[p.x][p.y].setHit(true);
		for (Battleship bs : things) {
			for (Point p2 : bs.reference) {
				if (p2.equals(p)) {
					bs.destroyedSections.add(p2);
				}
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		if (!gameStarted && delay != -1) {
			g2.drawString(Integer.toString(delay), 1280/2, 720/2);
		}
		for (int ii=0;ii<player.length;ii++) {
			for (int jj=0;jj<player[0].length;jj++) {
				player[ii][jj].draw(g2, true);
			}
		}
		for (int ii=0;ii<enemy.length;ii++) {
			for (int jj=0;jj<enemy[0].length;jj++) {
				enemy[ii][jj].draw(g2, false);
			}
		}
		for (Battleship bs : playerShips) {
			if (bs != null) {
				bs.draw(g2);
			}
		}
		if (placeMode) {
			selectedShip.draw(g2);
		}
		int counter = 0;
		for (Battleship bs : things) {
			for (int ii = 0;ii<bs.reference.size();ii++) {
//				Point p = bs.reference.get(ii);
//				if (bs.destroyedSections.contains(p)) {
//					g2.fillRect(30 + 16*p.x + counter*200, 570 + 16*p.y, 16, 16);
//				} else {
//					g2.drawRect(30 + 16*p.x + counter*200, 570 + 16*p.y, 16, 16);
//				}
			}
			g2.drawImage(bs.getImage(), counter*200 + 1, 550, null);
			if (!bs.dead()) {
				g2.fillArc(counter*200 + 8, 550, 65, 65, 0, (int)(360 * bs.getCooldownRatio()));
			} else {
				g2.fillOval(counter*200 + 8 , 550, 65, 65);
			}
			for (Point p : bs.getShotPattern()) {
				g2.fillRect(counter*200 + 120 + p.x*16, 575 + p.y*16, 16, 16);
			}
			g2.drawString(Integer.toString(counter+1), counter*190 + 35, 635);
			counter++;
		}
	}
	
	public void keyPressed(int key) {
		if (key == -1) {
			if (placeMode) {
				selectedShip.rotate();
			}
		} else {
			selectedShip = playerShips[key];
		}
	}

	public void click(Point p) {
		Blocks b = this.getIntersect(p, player);
		if (b != null && placeMode && !selectedShip.collisions()) {
			things[shipIndex].place(p);
			playerShips[shipIndex] = things[shipIndex];
			shipIndex++;
			if (shipIndex > 5) {
				placeMode = false;
				selectedShip = things[0];
			} else {
				selectedShip = things[shipIndex];
			}
		}
		b = this.getIntersect(p, enemy);
		if (b != null && gameStarted && selectedShip.dead() == false) {
			ArrayList<Point> shootPoints = selectedShip.shoot(b);
			System.out.println("SHOOT");
			fr.shots(shootPoints);
			for (Point shot : shootPoints) {
				int x = shot.x;
				int y = shot.y;
				if (x < 30 && y < 30 && x*y >= 0) {
					enemy[x][y].setHit(true);
				}
			}
		}
	}
	
	public void setEnemyShips(Blocks[][] o) {
		enemyReady = true;
		for (int ii=0;ii<o.length;ii++) {
			for (int jj=0;jj<o[0].length;jj++) {
				enemy[ii][jj].setShip(o[ii][jj].isShip());
			}
		}
	}
}
