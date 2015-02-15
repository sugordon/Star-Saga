import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Frame {
	ArrayList<Star> list= new ArrayList<Star>();
	Star tempstar;
	ArrayList<ShootingStar> shootinglist = new ArrayList<ShootingStar>();
	ShootingStar tempshooting;
	int maxstars=400;
	int maxshootingstars=3;
	Random rn = new Random();
	int width=1280;
	int height=720;
	private float alpha = 1f;
//	int explosioncounter=0; 
	private BufferedImage starimage;
	private BufferedImage shootingstarimage;
	JFrame frame;
	private Timer ti;
	public static Dimension windowsize;
	private Updatable networking;
	private Game game;
	public static int framerate = 50;
	
	public static void main(String[] args) {
		new Frame(null);
	}
	
	public Frame(Updatable u) {
		game = new Game(this);
		networking = u;
		windowsize = new Dimension(1280,720);
		frame = new JFrame();
		frame.setVisible(true);
		frame.setSize(windowsize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel draw = new Drawing();
		frame.add(draw);
		frame.setTitle("Star Saga");
		draw.requestFocusInWindow();
		Listener li = new Listener();
		draw.addKeyListener(li);
		draw.addMouseListener(li);
		draw.addMouseMotionListener(li);
		for (int i=0; i<maxstars; i++){
			list.add( new Star(rn.nextInt(width),rn.nextInt(height),(float)0.1*(rn.nextInt(10)+1) ) );
		}
		for (int i=0; i<maxshootingstars;i++){
			shootinglist.add(new ShootingStar(width-12,rn.nextInt(height),rn.nextInt(200)+1));			
		}

		try {
			starimage = ImageIO.read(new File("background/star.png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			shootingstarimage = ImageIO.read(new File("background/shootingstar.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ti = new Timer(framerate, li);
		ti.start();
	}
	public void setName(String test) {
		frame.setTitle(test);
	}
	
	public void shots(ArrayList<Point> points) {
		for (Point p : points) {
			try {
				networking.getOutputStream().writeUnshared(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendShips(Blocks[][] ships) {
		try {
			networking.getOutputStream().writeUnshared(ships);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read() {
		Object o = null;
		try {
			o = networking.getInputStream().readUnshared();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (o instanceof Point) {
			game.incShots((Point) o);
		}
		if (o instanceof Blocks[][]) {
			game.setEnemyShips((Blocks[][]) o);
		}
	}
	
	public void stopTimer() {
		System.out.println("STOP TIMER");
		ti.stop();
	}
	
	public class Drawing extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(20,5,101));
			g2.fillRect(0,0,width,height);
			
//			g2.setColor(Color.RED);
//			g2.drawOval(100-(explosioncounter/2),100-(explosioncounter/2),explosioncounter,explosioncounter);
//			explosioncounter += 10;
//			if (explosioncounter >= 100){
//				explosioncounter=0;
//			}
			
			
			for (int i=0; i<shootinglist.size();i++){
				tempshooting=shootinglist.get(i);
				tempshooting.start += -1;
				if (tempshooting.start==0){
					//tempshooting.xlocation=width-12;
					//tempshooting.ylocation=rn.nextInt(height);
					g2.drawImage(shootingstarimage, tempshooting.xlocation,tempshooting.ylocation , 12, 6 , null);
				}
				else if(tempshooting.start < 0){
					tempshooting.xlocation+=-6;
					if (tempshooting.xlocation >= 0){
						g2.drawImage(shootingstarimage, tempshooting.xlocation,tempshooting.ylocation , 12, 6 , null);												
					}
					else {
						tempshooting.xlocation=width-12;
						tempshooting.ylocation=rn.nextInt(height);
						tempshooting.start=rn.nextInt(200)+1;
					}
				}
			}
			for (int i=0; i<list.size();i++){
			//animates the twinkling stars
				tempstar=list.get(i);
				g2.setColor(Color.YELLOW);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
	                    tempstar.transparency));
				//g2.drawString("*", tempstar.xlocation,tempstar.ylocation);
				g2.drawImage(starimage, tempstar.xlocation,tempstar.ylocation, 3,3 , null);
				tempstar.transparency += tempstar.increment;
				tempstar.transparency = Math.round(tempstar.transparency*100.0f)/100.0f;
				
				if (tempstar.transparency >= 1.0){	
					tempstar.increment= -0.01f;
				}
				if (tempstar.transparency <= 0){
					tempstar.xlocation=rn.nextInt(width);
					tempstar.ylocation=rn.nextInt(height);
					tempstar.transparency=0f;
					tempstar.increment= 0.01f;
				}

				
			}
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    1f));
			game.draw(g2);
		}
	}
	
	public class Listener implements ActionListener, KeyListener,
			MouseListener, MouseMotionListener {
		Point p = new Point();
		@Override
		public void keyPressed(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_1) {
				game.keyPressed(0);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_2) {
				game.keyPressed(1);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_3) {
				game.keyPressed(2);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_4) {
				game.keyPressed(3);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_5) {
				game.keyPressed(4);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_6) {
				game.keyPressed(5);
			}
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				game.keyPressed(-1);
				game.mouseMoved(p);
			}
		}

		public void keyReleased(KeyEvent arg0) {
			
		}

		public void keyTyped(KeyEvent arg0) {
			
		}

		public void actionPerformed(ActionEvent arg0) {
			game.update();
			frame.repaint();
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			game.click(arg0.getPoint());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			game.mouseMoved(e.getPoint());
			p = e.getPoint();
		}
	}
}
