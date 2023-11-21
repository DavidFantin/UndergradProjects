import edu.utulsa.unet.*;

public class RTest {
	public static void main(String[] args) {
		RSendUDP pack = new RSendUDP();
		pack.setFilename("SEND.txt");
		pack.setLocalPort(32456);
		pack.setTimeout(1000);
//		pack.setMode(1);
//		pack.setModeParameter(2000);
		pack.sendFile();
	}
}
