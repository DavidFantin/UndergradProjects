//NOT COMPLETE WILL UPDATE ASAP

import edu.utulsa.unet.UDPSocket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.DatagramPacket;
import java.net.SocketTimeoutException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.lang.Math;
import edu.utulsa.unet.RSendUDPI;

public class RSendUDP implements RSendUDPI {

	public RSendUDP() {
		Mode = 0;
		WindowSizeBytes = 256;
		Timeout = 1000;
		localPort = 12987;
		Receiver = new InetSocketAddress("127.0.0.1", 12987);
	}

	public boolean setMode(int mode) {
		Mode = mode;
		return true;
	}

	public int getMode() {
		return Mode;
	}

	public boolean setModeParameter(long parameter) {
		WindowSizeBytes = parameter;
		return true;
	}

	public long getModeParameter() {
		return WindowSizeBytes;
	}

	public boolean setTimeout(long time) {
		Timeout = time;
		return true;
	}

	public long getTimeout() {
		return Timeout;
	}

	public void setFilename(String name) {
		FName = name;
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

	public boolean setReceiver(InetSocketAddress addr) {
		Receiver = addr;
		return true;
	}

	public InetSocketAddress getReceiver() {
		return Receiver;
	}

	public boolean sendFile() {
		if (Mode == 0) {
			boolean s = StopAndWait();
			return s;
		} else if (Mode == 1) {
			return SlidingWindow();
		} else {
			System.out.println("No Associated Mode [0 for stop and wait, 1 for sliding window]");
			return false;
		}
	}

	@SuppressWarnings("resource")
	public boolean StopAndWait() {

		customHeaderSize = 5;
		fullHeaderSize = customHeaderSize + 28;
		try {
			UDPSocket socket = new UDPSocket(localPort);
			System.out.println("Local Host: " + InetAddress.getLocalHost() + "\nUDP Port: " + getLocalPort()
					+ "\nARQ Algorithm: Stop And Wait");
			socket.setSoTimeout((int) Timeout);
			MTU = socket.getSendBufferSize();
			if (MTU <= fullHeaderSize) {
				System.out.println("MTU is not large enough to send any data.");
				return false;
			}
			Receiver.getAddress();
			System.out.println(
					"Sender IP Address: " + Receiver.getHostName() + "\nSender Port: " + Receiver.getPort() + "\n");

			Path path = Paths.get(FName);
			data = Files.readAllBytes(path);

			dataSize = (MTU - fullHeaderSize) / 2;
			System.out.println(MTU);
			int remainderSize = data.length % dataSize;
			int RAL = Math.abs(data.length - remainderSize) / dataSize + 1; // Final Array Length

			byte[][] FinalArray = new byte[RAL][];
			byte[] endPacket = new byte[0];
			byte[] dataSlice = new byte[0];
			byte[] packet = new byte[0];

			if (data.length < dataSize) {
				packet = packetBuilder0(data, data.length, 0);
				FinalArray[0] = packet;
				endPacket = packetBuilder0(dataSlice, RAL, 2);
			}

			else if (dataSize <= (data.length - remainderSize)) {
				// builds all the packets
				int tmpcount = 0;
				for (int i = 0; i <= (data.length - remainderSize); i += dataSize) {
					dataSlice = Arrays.copyOfRange(data, startIndex + i, dataSize + i);
					packet = packetBuilder0(dataSlice, RAL, AltB);
					FinalArray[tmpcount] = packet;
					tmpcount++;
					if (AltB == 0) {
						AltB = 1;
					} else {
						AltB = 0;
					}
				}
				endPacket = packetBuilder0(dataSlice, RAL, 2);
				if (remainderSize != 0) {
					dataSlice = Arrays.copyOfRange(data, (data.length - remainderSize), (data.length));
					packet = packetBuilder0(dataSlice, RAL, AltB);
					FinalArray[RAL - 1] = packet; // puts packet into final array
				}
			} else {
				dataSlice = Arrays.copyOfRange(data, 0, (data.length - 1));
				packet = packetBuilder0(dataSlice, RAL, AltB);
				FinalArray[0] = packet;
			}
			// All packets should be made and put into the final array by now
			// Now I need to send them and implement stop and wait:

			// Send a packet,
			// Wait for a response,
			// Check that matches AltB,
			// If yes: send the next packet
			// If no: Re-send the packet
			AltB = 0;
			int FAL = FinalArray.length;
			int pointer = 0;
			int count = 0;
			while (pointer < FAL) {
				System.out.println(count);
				System.out.println(FinalArray.length);
				if (count == FAL - 1) {
					for (int i = 0; i < 4; i++) {
						socket.send(new DatagramPacket(endPacket, endPacket.length, Receiver));
						System.out.println("\n[[Packet Sent]]");
						System.out.println(
								"Sequence Number: " + FAL + "\nNumber of Bytes In the Packet: " + endPacket.length);

					}
					break;
				}

				socket.send(new DatagramPacket(FinalArray[pointer], FinalArray[pointer].length, Receiver));
				System.out.println("\n[[Packet Sent]]");
				System.out.println("Sequence Number: " + pointer + "\nNumber of Bytes In the Packet: "
						+ FinalArray[pointer].length);

				int AltBReceived = 3;
				while (true) {
					try {
						//////////// Wait for Ack stage
						TOChecker++;
						byte[] intake = new byte[4];
						DatagramPacket pkt = new DatagramPacket(intake, intake.length);
						socket.receive(pkt);
						System.out.println("\nACK Received for SN: " + pointer + "\n");
						TOChecker = 0;
						AltBReceived = ByteBuffer.wrap(pkt.getData()).getInt();
						if (AltBReceived == AltB) {
							count++;
							break;
						} else {
							continue;
						}
					} catch (SocketTimeoutException e) {
						if (TOChecker == 0) {
							break;
						} else {
							System.out.println("Message Timed Out: Resending");
							socket.send(new DatagramPacket(FinalArray[pointer], FinalArray[pointer].length, Receiver));
							System.out.println("\n[[Packet Sent]]");
							System.out.println("Sequence Number: " + pointer + "\nNumber of Bytes In the Packet: "
									+ FinalArray[pointer].length);
						}
					}

				}
				if (AltB == 0) {
					AltB = 1;
				} else {
					AltB = 0;
				}
				pointer++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecTime = System.currentTimeMillis() - tmpExTime;
		System.out.println("\n[[File Sent]]" + "\nNumber of Bytes Sent: " + data.length + "\nExecution Time: " + ExecTime + "ms");
		return true;
	}

	public byte[] packetBuilder0(byte[] DataSlice, int FinalArrayLength, int AltByte) {
		byte[] AByte = toByteArray(AltByte);
		byte[] FALength = toByteArray(FinalArrayLength);

		int FALL = FALength.length;
		int AB = AByte.length;
		int DSL = DataSlice.length;

		byte[] packet = new byte[FALL + AB + DSL];
		System.arraycopy(FALength, 0, packet, 0, FALL);
		System.arraycopy(AByte, 0, packet, FALL, AB);
		System.arraycopy(DataSlice, 0, packet, FALL + AB, DSL);

		return packet; // packet == buffer
	}

	// Read data from the file into 1 byte[] array (data)

	// Find the size of the last packet by finding the remainder (data.length %
	// (MTU))

	// Splice the array into dataSlices

	// Build custom packets: [ Number of packets to be sent (4 bytes) | Alternating
	// Byte (1 Byte) | Data (MTU - (28 + customHeaderSize)) ]

	// initialize sending

	@SuppressWarnings("resource")
	public boolean SlidingWindow() {
		tmpExTime = System.currentTimeMillis();
		int LAR = -1; // left side of window
		int PAR = -1;
		int LFS = -1; // right side of window
		customHeaderSize = 8; // First 4 -> sequence number // Next 4 -> size of the FinalArray
		fullHeaderSize = customHeaderSize + 28;
		try {
			UDPSocket socket = new UDPSocket(localPort);
			System.out.println("Local Host: " + InetAddress.getLocalHost() + "\nUDP Port: " + getLocalPort()
					+ "\nARQ Algorithm: Sliding Window" + "\nMode Parameter: " + getModeParameter());
			socket.setSoTimeout((int) Timeout);
			MTU = socket.getSendBufferSize();
			if (MTU > WindowSizeBytes) { // Fixes error where MTU > WindowSizeBytes
				MTU = (int) WindowSizeBytes;
			}
			MaxOutstanding = (int) (WindowSizeBytes / MTU);
			if (WindowSizeBytes <= fullHeaderSize) {
				System.out.println("Window Size is not large enough to send any data.");
				return false;
			}
			if (MTU <= fullHeaderSize) {
				System.out.println("MTU is not large enough to send any data.");
				return false;
			}

			Receiver.getAddress();

			System.out.println(
					"Sender IP Address: " + Receiver.getHostName() + "\nSender Port: " + Receiver.getPort() + "\n");

			Path path = Paths.get(FName);
			data = Files.readAllBytes(path);

			startIndex = 0;

			dataSize = (MTU - fullHeaderSize) / 2;//// *2?
			int remainderSize = data.length % dataSize;
			int FAL = (data.length - remainderSize) / dataSize + 1; // Final Array Length

			byte[][] FinalArray = new byte[FAL][];
			byte[] endPacket = new byte[0];
			byte[] dataSlice = new byte[0];
			byte[] packet = new byte[0];

			if (data.length < dataSize && data.length < WindowSizeBytes) {
				packet = packetBuilder1(sequenceNumber, data, data.length);
				FinalArray[0] = packet;
			}

			else if (dataSize <= (data.length - remainderSize)) {
				for (int i = 0; i <= (data.length - remainderSize); i += dataSize) {
					dataSlice = Arrays.copyOfRange(data, startIndex + i, dataSize + i);
					packet = packetBuilder1(sequenceNumber, dataSlice, FAL);
					FinalArray[sequenceNumber] = packet;
					sequenceNumber++;
				}
				endPacket = packetBuilder1(-500, dataSlice, FAL);
				if (remainderSize != 0) {
					dataSlice = Arrays.copyOfRange(data, (data.length - remainderSize), (data.length));
					packet = packetBuilder1(FAL - 1, dataSlice, FAL);
					FinalArray[FAL - 1] = packet; // puts packet into final array
				}
			} else {
				dataSlice = Arrays.copyOfRange(data, 0, (data.length - 1));
				packet = packetBuilder1(sequenceNumber, dataSlice, FAL);
				FinalArray[0] = packet;
			}

			if (FAL < MaxOutstanding) {
				for (LFS = 0; LFS < FAL; LFS++) { // This should be the first step and should send all packets in the
													// first window and stop to wait for the ACK
					socket.send(new DatagramPacket(FinalArray[LFS], FinalArray[LFS].length, Receiver));
					System.out.println("\n[[Packet Sent]]");
					System.out.println("Sequence Number: " + sequenceNumber + "\nNumber of Bytes In the Packet: "
							+ FinalArray[LFS].length);
				}

			} else {
				for (LFS = 0; LFS < MaxOutstanding; LFS++) { // This should be the first step and should send
					socket.send(new DatagramPacket(FinalArray[LFS], // all-packets-in-the-first-window-and-stop-to-wait-for-the-ACK
							FinalArray[LFS].length, Receiver));
					System.out.println("\n[[Packet Sent]]");
					System.out.println("Sequence Number: " + sequenceNumber + "\nNumber of Bytes In the Packet: "
							+ FinalArray[LFS].length);

				}
			}
			//////////// receiving the Ack
			while (true) {
				try {
					//////////// Wait for Ack stage
					PAR = LAR;
					byte[] intake = new byte[8];
					DatagramPacket pkt = new DatagramPacket(intake, intake.length);
					socket.receive(pkt);
					AckReceived = ByteBuffer.wrap(pkt.getData()).getLong();
					System.out.println("\nACK Received for SN: " + AckReceived + "\n");


					/////////// Ack Received stage
					if (AckReceived <= PAR && PAR != 0) {
					} else {
						LAR = (int) AckReceived; // setting last Ack received

						/////////// Exit Condition
						if (AckReceived == FAL - 1) {
							TOChecker++;

							long Stime = System.currentTimeMillis();
							
							socket.send(new DatagramPacket(endPacket, // all-packets-in-the-first-window-and-stop-to-wait-for-the-ACK
									endPacket.length, Receiver));
							System.out.println("\n[[Packet Sent]]");
							System.out.println("Sequence Number: " + sequenceNumber
									+ "\nNumber of Bytes In the Packet: " + endPacket.length);

							Stime = System.currentTimeMillis();
							while (System.currentTimeMillis() < Stime + 500)
								;
							break;
						}

						/////////// Send next frame
						else {
							if ((LFS + (AckReceived - PAR)) >= FAL) {
								LFS = FAL;
							} else {
								LFS = (int) (LFS + (AckReceived - PAR)); // (AckReceived - PAR) should be the
							}
						}
					}
					TOChecker = 0;
				} catch (SocketTimeoutException e) {
					if (TOChecker > 0) {
						break;
					} else {
						System.out.println("Message Timed Out: Resending");

						for (int tmp = LAR + 1; tmp < LFS; tmp++) {
							socket.send(new DatagramPacket(FinalArray[tmp], FinalArray[tmp].length, Receiver));
							System.out.println("\n[[Packet Sent]]");
							System.out.println("Sequence Number: " + sequenceNumber
									+ "\nNumber of Bytes In the Packet: " + FinalArray[tmp].length);
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		ExecTime = System.currentTimeMillis() - tmpExTime;
		System.out.println("\n[[File Sent]]" + "\nNumber of Bytes Sent: " + data.length + "\nExecution Time: " + ExecTime + "ms");
		return true;
	}

	public byte[] packetBuilder1(int SequenceNumber, byte[] DataSlice, int FinalArrayLength) {
		byte[] SeqNum = toByteArray(SequenceNumber);
		byte[] FALength = toByteArray(FinalArrayLength);

		int SNL = SeqNum.length;
		int FALL = FALength.length;
		int DSL = DataSlice.length;

		byte[] packet = new byte[SNL + FALL + DSL];
		System.arraycopy(SeqNum, 0, packet, 0, SNL);
		System.arraycopy(FALength, 0, packet, SNL, FALL);
		System.arraycopy(DataSlice, 0, packet, SNL + FALL, DSL);

		return packet; // packet == buffer
	}

	public byte[] toByteArray(int i) {

		byte[] result = new byte[4];

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i >> 0);

		return result;
	}

	int AltB = 0;
	int TOChecker = 0;
	int TrueLAR;
	int c = 0;
	int Mode;
	int MTU;
	int customHeaderSize;
	int fullHeaderSize;
	int MaxOutstanding;
	int startIndex;
	int dataSize;
	int sequenceNumber = 0;
	int localPort;
	int TC = 0;
	long ExecTime = 0;
	long tmpExTime = 0;
	long AckReceived = 0;
	long WindowSizeBytes;
	long Timeout;
	char AckBit = '0';
	byte[] data;
	String FName;
	InetSocketAddress Receiver;

}
