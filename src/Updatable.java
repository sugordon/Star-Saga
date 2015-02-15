import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public interface Updatable {
	public ObjectOutputStream getOutputStream();
	public ObjectInputStream getInputStream();
}
