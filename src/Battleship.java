import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Battleship 
{
	private ArrayList<Blocks> ship;
	public ArrayList<Point> reference;
	ArrayList<Point> destroyedSections;
	private ArrayList<Point> shotPattern;
	private int cooldown;
	private final int initCooldown;
	BufferedImage img = null;
	int imgx;
	int imgy;
	double theta;
	
	public Battleship(double initCooldown)
	{
		destroyedSections = new ArrayList<Point>();
		this.initCooldown = (int) (initCooldown*1000/Frame.framerate);
		reference = new ArrayList<Point>();
		shotPattern = new ArrayList<Point>();
		ship = null;

	}
	
	public void update() {
		if (cooldown > 0) {
			cooldown--;
		}
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	public double getCooldownRatio() {
		return (double)cooldown/initCooldown;
	}
	
	public void setBlocks(ArrayList<Blocks> ship) {
		this.ship = ship;
	}
	
	public boolean dead()
	{
		return getDeadCells().size() == ship.size();
	}
	
	public ArrayList<Blocks> getDeadCells() {
		ArrayList<Blocks> dead = new ArrayList<Blocks>();
		for (Blocks b : ship) {
			if (b.isHit()) {
				dead.add(b);
			}
		}
		return dead;
	}
	
	public boolean place_down()
	{
		while(!ship.get(0).isShip())
			return false;
		return true;
	}
	
	public void move(Blocks selected, Blocks[][] array, boolean place)
	{
		imgx = selected.getA().x;
		imgy = selected.getA().y;
		for (Point p3: reference) {
			int x = (selected.getA().x - 20) / 16 + p3.x;
			int y = (selected.getA().y - 50) / 16 + p3.y;
			if (!(x < 30 && y < 30 && x*y >= 0)) {
				return;
			}
		}
		for (Blocks b : ship) {
			b.setHighlighted(false);
		}
		ship.clear();
		for (Point p2 : reference) {
				ship.add(array[(selected.getA().x - 20) / 16 + p2.x][(selected.getA().y - 50) / 16 + p2.y]);
		}
		if (place) {
			for (Blocks b : ship) {
				b.setHighlighted(true);
			}
			selected.setHighlighted(true);
		}
	}
	
	public void place(Point point)
	{
		if (!this.collisions())
		{
			for (Blocks b : ship) {
				b.setShip(true);
				b.setHighlighted(false);
			}
		}
	}
	
	public boolean collisions()
	{
		for(int i=0; i<this.ship.size(); i++)
		{
			Blocks pt = this.ship.get(i);
			if(pt.isShip())
				return true;
		}
		return false;
	}
	
	public boolean collisionEdges(ArrayList<Point> points) {
		
		return true;
	}
	
	public ArrayList<Blocks> getShip() {
		return ship;
	}

	public void setShip(ArrayList<Blocks> ship) {
		this.ship = ship;
	}
	
	public void draw(Graphics2D g2) {
		for (Blocks b : ship) {
			g2.fill(b.getRect());
		}
		AffineTransform old = g2.getTransform();
		g2.rotate(Math.PI + theta,imgx+8,imgy+8);
		g2.drawImage(img, imgx - 2*16, imgy - 2*16, null);
		g2.setTransform(old);
	}
	
	public void rotate()
	{
		System.out.println("HIHI");
		theta+= Math.PI/2;
		ArrayList<Point> tempArr = new ArrayList<Point>(reference);
		reference.clear();
		for(Point b: tempArr)
		{
			double[] pt = {b.getX(),b.getY()};
			AffineTransform.getRotateInstance(Math.toRadians(90), 0, 0).transform(pt,0,pt,0,1);
			this.reference.add(new Point((int)pt[0],(int)pt[1]));
		}
	}
	
	public ArrayList<Point> shoot(Blocks origin) {
		if (getCooldown() != 0) {
			return new ArrayList<Point>();
		}
		this.cooldown = initCooldown;
		ArrayList<Point> shots = new ArrayList<Point>();
		for (Point p : shotPattern) {
			Point originPoint = new Point((origin.getA().x - 725) / 16, (origin.getA().y - 50) / 16);
			shots.add(new Point(originPoint.x + p.x, originPoint.y+p.y));
		}
		return shots;
	}

	public void setShotPattern(ArrayList<Point> shotPattern) {
		this.shotPattern = shotPattern;
	}
	
	public ArrayList<Point> getShotPattern() {
		return shotPattern;
	}
	
	public Image getImage() {
		return img;
	}

}
