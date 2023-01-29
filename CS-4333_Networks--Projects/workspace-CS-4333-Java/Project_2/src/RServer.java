import edu.utulsa.unet.*;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class RServer {
	public static void main(String [] args) {
		RReceiveUDP start = new RReceiveUDP();
		start.setFilename("RECEIVE.txt");
//		start.setMode(1);
		start.receiveFile();
//		start.setModeParameter(200);
	}
}
