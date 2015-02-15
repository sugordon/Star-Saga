import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class Loki extends Battleship
{
	String name;
	public Loki() 
	{
		super(2);
		ArrayList<Blocks> ships = new ArrayList<Blocks>();
		reference.add(new Point(0,0));
		reference.add(new Point(1,0));
		reference.add(new Point(0,1));
		reference.add(new Point(-1,0));
		this.setBlocks(ships);
		ArrayList<Point> shotPattern = new ArrayList<Point>();
		shotPattern.add(new Point(0,0));
		shotPattern.add(new Point(0,1));
		this.setShotPattern(shotPattern);
		name = "Loki";
	    try {
			img = ImageIO.read(new File("sprites/fullships/loki.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
