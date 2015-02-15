import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Thor extends Battleship {
	String name;
	public Thor() {
		super(9);
		ArrayList<Blocks> ships = new ArrayList<Blocks>();
		reference.add(new Point(0,1));
		reference.add(new Point(0,2));
		reference.add(new Point(1,1));
		reference.add(new Point(-1,1));
		reference.add(new Point(0,0));
		reference.add(new Point(-1,0));
		reference.add(new Point(1,0));
		reference.add(new Point(-2,0)); 
		reference.add(new Point(2,0));
		reference.add(new Point(-1,-1));
		reference.add(new Point(1,-1));
		this.setBlocks(ships); 
		ArrayList<Point> shotPattern = new ArrayList<Point>();
		shotPattern.add(new Point(0,1));
		shotPattern.add(new Point(-1,1));
		shotPattern.add(new Point(-2,0));
		shotPattern.add(new Point(-2,-1));
		shotPattern.add(new Point(-1,-2));
		shotPattern.add(new Point(0,-2));
		shotPattern.add(new Point(1,-1));
		shotPattern.add(new Point(1,0));
		this.setShotPattern(shotPattern);
		name = "Thor";
		try {
				img = ImageIO.read(new File("./sprites/fullships/thor.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	}