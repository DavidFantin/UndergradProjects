//NOT COMPLETE WILL UPDATE ASAP

import edu.utulsa.unet.UDPSocket;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import edu.utulsa.unet.RReceiveUDPI;
import java.nio.ByteBuffer;
import java.util.*;

public class RReceiveUDP implements RReceiveUDPI {

	public RReceiveUDP() {
		Mode = 0;
		WindowSizeBytes = 256;
		localPort = 12987;
	}

	public boolean setMode(int mode) {
		Mode = mode;
		return true;
	}

	public int getMode() {
		return Mode;
	}

	public boolean setModeParameter(long windowSize) {
		WindowSizeBytes = windowSize;
		return true;
	}

	public long getModeParameter() {
		return WindowSizeBytes;
	}

	public void setFilename(String fname) {
		FName = fname;
	}

	public String getFilename() {
		return FName;
	}

	public boolean setLocalPort(int portnumber) {
		localPort = portnumber;
		return true;
	}

	public int getLocalPort() {
		return localPort;
	}

	public boolean receiveFile() {
		if (Mode == 0) {
			return StopAndWait();
		} else if (Mode == 1) {
			return SlidingWindow();
		} else {
			System.out.println("No Associated Mode [0 for stop and wait, 1 for sliding window]");
			return false;
		}
	}

	@SuppressWarnings("resource")
	public boolean StopAndWait() {
		System.out.println();
		byte[] PACKET = null;
		long FinalSequenceNumber = 0;
		try {
			System.out.println("Local Host: " + InetAddress.getLocalHost() + "\nUDP Port: " + getLocalPort()
					+ "\nARQ Algorithm: Stop And Wait");

			UDPSocket socket = new UDPSocket(localPort);
			// Recieve msg
			// Start AltByteCheck @ 0
			// Get the AltByte from the msg
			// Check if it matches AltByteCheck
				// If Yes -> Copy data into the final Array; Change AltByteCheck
				// If No -> Do not copy data into the final Array
			// Send an Ack of the Alt byte received
			// Bytes indexes 0-3 = Number of packets to expect -> (or should it be final
			// array size)
			// Byte index 4 = AltByte

			int AltByteCheck = 0;
			int AltByte = 0;
			int MTU = socket.getSendBufferSize();
			int endCheck = 0;
			long SequenceNumber = -1;
			while (AltByte != 2) {
				if (TC == 0) {
					tmpExTime = System.currentTimeMillis();
					TC++;
				}
				byte[] buffer = new byte[MTU];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				byte[] TMPPACKET = packet.getData();
				Sender = (InetSocketAddress) packet.getSocketAddress();
				System.out.println(
						"Sender IP Address: " + Sender.getHostName() + "\nSender Port: " + Sender.getPort() + "\n");
				PACKET = removeNullSpace(TMPPACKET);

				if (PACKET.length - 7 == 0) {
					break;
				}

				ByteBuffer.wrap(Arrays.copyOfRange(TMPPACKET, 0, 4)).getInt();
				AltByte = ByteBuffer.wrap(Arrays.copyOfRange(TMPPACKET, 4, 8)).getInt();
				byte[] tmpAltByteArray = new byte[4];
				tmpAltByteArray[0] = TMPPACKET[4];
				tmpAltByteArray[1] = TMPPACKET[5];
				tmpAltByteArray[2] = TMPPACKET[6];
				tmpAltByteArray[3] = TMPPACKET[7];

				byte[] data;
				if (AltByte == 0) {
					data = Arrays.copyOfRange(PACKET, 7, PACKET.length);
				} else {
					data = Arrays.copyOfRange(PACKET, 8, PACKET.length);
				}

				if (endCheck == 0 && AltByte == 2) {
					endCheck++;
				}

				if (endCheck == 1 || (AltByte != 2 && AltByteCheck == AltByte)) {
					SequenceNumber++;
					FinalSequenceNumber++;
					FinalListUnsorted.put(SequenceNumber, data);
				}

				System.out.println("[[New Message Received]]");
				System.out.println("Sequence Number: " + SequenceNumber);
				System.out.println("Byte Count: " + data.length);

				byte[] AckMsgSend = tmpAltByteArray;

				socket.send(new DatagramPacket(AckMsgSend, AckMsgSend.length, Sender));
				System.out.println("Sequence Number Acknowledged: " + SequenceNumber);

				if (AltByte == 0 && AltByteCheck == 0) {
					AltByteCheck = 1;
				}
				if (AltByte == 1 && AltByteCheck == 1) {
					AltByteCheck = 0;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecTime = System.currentTimeMillis() - tmpExTime;
		FinalList = new TreeMap<Long, byte[]>(FinalListUnsorted);
		Collection<byte[]> values = FinalList.values();
		byte[][] tmpArray = values.toArray(new byte[0][]);

		for (byte[] arr : tmpArray) {
			tmpArraySize = tmpArraySize + (arr.length);
		}

		// Merge all arrays into 1 Final Array
		byte[] FinalArray = new byte[(int) tmpArraySize];
		int count = 0;
		for (int i = 0; i < tmpArray.length; i++) {////// keeps chopping off the last Byte of each packet!!!!
			for (int j = 0; j < tmpArray[i].length; j++) {
				FinalArray[count] = tmpArray[i][j];
				count++;
			}
		}

		// Convert the array into a string
		String fstr = new String(FinalArray);

		try { // Write to file
			BufferedWriter bw = new BufferedWriter(new FileWriter(FName));
			bw.write(fstr);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		long Stime = System.currentTimeMillis();
		while (System.currentTimeMillis() < Stime + 10)
			;
		System.out.println("\n[[File Received]]\nNumber of Messages Received: " + FinalSequenceNumber
				+ "\nNumber of Bytes Received: " + FinalArray.length + "\nExecution Time: " + ExecTime + "ms");

		return true;

	}

	@SuppressWarnings("resource")
	public boolean SlidingWindow() {
		byte[] PACKET = null;
		try {
			UDPSocket socket = new UDPSocket(localPort);
			System.out.println("Local Host: " + InetAddress.getLocalHost() + "\nUDP Port: " + getLocalPort()
					+ "\nARQ Algorithm: Sliding Window" + "\nMode Parameter: " + getModeParameter());
			int MTU = socket.getSendBufferSize();
			int NRAS = (MTU) / 2 - 10;

			// Initial code to get the array size
			byte[] buf = new byte[MTU];
			DatagramPacket pack1 = new DatagramPacket(buf, buf.length);
			socket.receive(pack1);
			byte[] PACK1 = pack1.getData();
			ALFinalSize = ByteBuffer.wrap(Arrays.copyOfRange(PACK1, 4, 8)).getInt(); // or 32, 35 4,7
			Sender = (InetSocketAddress) pack1.getSocketAddress();
			System.out.println(
					"Sender IP Address: " + Sender.getHostName() + "\nSender Port: " + Sender.getPort() + "\n");
			if (NRAS > WindowSizeBytes) {
				NRAS = (int) WindowSizeBytes;
			}
			long SequenceNumber = -1;
			while (SequenceNumber != -500) { // ALFinalSize >= LongestSNR + 1 || ALFinalSize == -1
				if (TC == 0) {
					tmpExTime = System.currentTimeMillis();
					TC++;
				}
				byte[] buffer = new byte[MTU];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				byte[] TMPPACKET = packet.getData();

				PACKET = removeNullSpace(TMPPACKET);

				if (PACKET.length - 7 == 0) {
					break;
				}

				SequenceNumber = ByteBuffer.wrap(Arrays.copyOfRange(PACKET, 0, 4)).getInt(); // or 28, 31
				byte[] data = Arrays.copyOfRange(PACKET, 8, PACKET.length); // or
				if (!FinalListUnsorted.containsKey(SequenceNumber) && SequenceNumber != -500) {
					FinalListUnsorted.put(SequenceNumber, data);
				}

				byte[] AckMsgSend = longToBytes(LongestSNR);
				socket.send(new DatagramPacket(AckMsgSend, AckMsgSend.length, Sender));
				System.out.println("Sequence Number Acknowledged: " + SequenceNumber);

				long TMPAckMsg = 0;

				TMPAckMsg = SequenceNumber;

				if (TMPAckMsg > LongestSNR) {
					LongestSNR = TMPAckMsg;
				}

				System.out.println("[[New Message Received]]");
				System.out.println("Sequence Number: " + SequenceNumber);
				System.out.println("Byte Count: " + data.length);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ExecTime = System.currentTimeMillis() - tmpExTime;
		FinalList = new TreeMap<Long, byte[]>(FinalListUnsorted);
		Collection<byte[]> values = FinalList.values();
		byte[][] tmpArray = values.toArray(new byte[0][]);

		for (byte[] arr : tmpArray) {
			tmpArraySize = tmpArraySize + (arr.length);
		}

////////////////////////////////////////////////////////////////////////////////////////		

		// Merge all arrays into 1 Final Array
		byte[] FinalArray = new byte[(int) tmpArraySize];
		int count = 0;
		for (int i = 0; i < tmpArray.length; i++) {////// keeps chopping off the last Byte of each packet!!!!
			for (int j = 0; j < tmpArray[i].length; j++) {
				FinalArray[count] = tmpArray[i][j];
				count++;
			}
		}

//		System.out.println(Arrays.toString(FinalArray));

		// Convert the array into a string
		String fstr = new String(FinalArray);

		try { // Write to file
			BufferedWriter bw = new BufferedWriter(new FileWriter(FName));
			bw.write(fstr);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("\n[[File Received]]\nNumber of Messages Received: " + LongestSNR
				+ "\nNumber of Bytes Received: " + FinalArray.length + "\nExecution Time: " + ExecTime + "ms");

		return true;
	}

	public byte[] longToBytes(long x) {
		ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
		b.putLong(x);
		return b.array();
	}

	public byte[] removeNullSpace(byte[] packet) {
		List<Byte> tempList = new ArrayList<Byte>();
		for (int j = 0; j < 7; j = j + 1) {
			tempList.add(packet[j]);
		}
		for (int i = 7; i < packet.length; i = i + 1) {
			if (packet[i] != 0) {
				tempList.add(packet[i]);
			}
		}
		byte[] result = new byte[tempList.size()];
		for (int i = 0; i < tempList.size(); i++) {
			result[i] = tempList.get(i).byteValue();
		}
		return result;
	}

	int Mode;
	int localPort;
	long WindowSizeBytes;
	long tmpArraySize = 0;
	long ExecTime = 0;
	long tmpExTime = 0;
	int TC = 0;
	int ALFinalSize = -1;
	int BufferSize;
	long AckMsg = -1;
	long LongestSNR = 0;
	long SmallestSNR = 500;
	boolean hasMissingFames;
	String FName;
	HashMap<Long, byte[]> FinalListUnsorted = new HashMap<Long, byte[]>();
	Map<Long, byte[]> FinalList;
	InetSocketAddress Sender;
}

////*****RANDOM_SLIDING-WINDOW_NOTES*****\\\\
// use an arraylist to receive
// while (SequenceNumber of packet received > (LFR + 1)){
// if (SequenceNumber of packet received > last SequenceNumber received)

// Receive all frames and put them into an ArrayList

// bits at index [3 - 7] gives the Size the ArrayList needs to be
// When the FIRST packet is received take the bytes at indexes [3 - 7] and
// convert to long = FinalSize
// byte[] FSize = ();
// constantly check if

// if timeout then send an ack = -500
// if duplicate frame received, ignore
