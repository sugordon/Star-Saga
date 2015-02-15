import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Server implements Runnable, Updatable {
	private String Hostname;
	private ServerSocket server;
	private Socket socket;
	private ObjectInputStream objectIn;
	private ObjectOutputStream objectOut;
	private Thread thread;
	private Frame frame;
	
	public static void main(String[] args) {
		new Server().startThread();
	}
	
	public Server () {
		JFrame temp = new JFrame();
		temp.setVisible(true);
		temp.setSize(500, 200);
		temp.add(new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.drawString("Waiting for Connection", 150, 100);
			}
		});
		temp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		temp.repaint();
		thread = new Thread(this);
		try {
			server = new ServerSocket(33333);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket = server.accept();
			System.out.println("ACCEPT");
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("CREATED");
			temp.setVisible(false);
			frame = new Frame(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startThread() {
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			frame.read();
		}
	}

	@Override
	public ObjectOutputStream getOutputStream() {
		// TODO Auto-generated method stub
		return objectOut;
	}

	@Override
	public ObjectInputStream getInputStream() {
		// TODO Auto-generated method stub
		return objectIn;
	}


}
