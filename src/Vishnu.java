import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Vishnu extends Battleship {
	String name;
	public Vishnu() {
		super(1);
		ArrayList<Blocks> ships = new ArrayList<Blocks>();
		reference.add(new Point(0,0));
		reference.add(new Point(0,1));
		this.setBlocks(ships);
		ArrayList<Point> shotPattern = new ArrayList<Point>();
		shotPattern.add(new Point(0,0));
		this.setShotPattern(shotPattern);
		name = "Vishnu";
	    try {
			img = ImageIO.read(new File("./sprites/fullships/vishnu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

}