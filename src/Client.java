import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Client implements Runnable, Updatable {
	private Socket socket;
	private int IP;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	private Thread thread;
	private Frame frame;
	
	public static void main(String[] args) {
		new Client().startThread();
	}
	
	private Client() {
		thread = new Thread(this);
		String rawString = JOptionPane.showInputDialog("IP?");
		InetAddress hostIP;
		try {
			hostIP = InetAddress.getByName(rawString);
			socket = new Socket(hostIP, 33333);
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			objectOut.flush();
			objectIn = new ObjectInputStream(socket.getInputStream());
			System.out.println("Connect");
			frame = new Frame(this);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		return objectOut;
	}

	@Override
	public ObjectInputStream getInputStream() {
		return objectIn;
	}
}
