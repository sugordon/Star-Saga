import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class Blocks implements Serializable
{
	private Point a;
	private boolean ship;
	private boolean hit;
	private boolean highlighted;
	private Rectangle rect;
	
	public Blocks(Point pt)
	{
		a = pt;
		ship = false;
		hit = false;
		rect = new Rectangle(a.x, a.y, 16, 16);
	}

	public Point getA() {
		return a;
	}
	
	public boolean intersects(Point p) {
		return rect.contains(p);
	}

	public void setA(Point a) {
		this.a = a;
	}

	public boolean isShip() {
		return ship;
	}

	public void setShip(boolean ship) {
		this.ship = ship;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean selected) {
		this.hit = selected;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public void draw(Graphics2D g2, boolean draw) {
		g2.setColor(new Color(120,120,120));
		g2.draw(rect);
		if (this.isShip() && draw) {
//			g2.setColor(Color.BLACK);
//			g2.fill(rect);
		} else if (this.isHighlighted()) {
			System.out.println("here");
			System.out.println(a);
			g2.setColor(Color.BLACK);
			g2.fill(rect);
		} else if (this.isHit()) {
			if (this.isShip()) {
				g2.setColor(new Color(255,0,0,100));
			} else {
				g2.setColor(new Color(0,0,255,100));
			}
			g2.fill(rect);
		}
		g2.setColor(new Color(120,120,120));
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	@Override
	public String toString() {
		return a.toString();
	}

}
