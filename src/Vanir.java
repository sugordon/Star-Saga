import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vanir extends Battleship {
	String name;
	public Vanir() {
		super(4);
		ArrayList<Blocks> ships = new ArrayList<Blocks>();
		reference.add(new Point(0,0));
		reference.add(new Point(1,0));
		reference.add(new Point(-1,0));
		reference.add(new Point(0,-1));
		reference.add(new Point(0,1));
		reference.add(new Point(0,2));
		this.setBlocks(ships); 
		ArrayList<Point> shotPattern = new ArrayList<Point>();
		shotPattern.add(new Point(-1,1));
		shotPattern.add(new Point(1,1));
		shotPattern.add(new Point(-1,-1));
		shotPattern.add(new Point(1,-1));
		this.setShotPattern(shotPattern);
		name = "Vanir";
	    try {
			img = ImageIO.read(new File("./sprites/fullships/vanir.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }

}