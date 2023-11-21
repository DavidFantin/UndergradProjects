// Name: David Fantin
// ID: 1525813
// Class: CS-4333 [Computer Networks]
// Professor: Dr. M. Papa
// Completed: 9/29/2020 @ 02:29

import java.io.*;
import java.net.*;

public class Talk {
	public static void main(String[] args) {

		ARGS = args;

		pCheck = (ARGS.length == 3 && ARGS[1].contentEquals("-p"));
		hCheck = (ARGS.length == 2);

		// Client Mode (-h) || Auto Mode (-a)
		if (ARGS[0].contentEquals(h) || ARGS[0].contentEquals(a)) {
			if (ARGS.length == 1 || hCheck || pCheck || (ARGS.length <= 4 && ARGS[2].contentEquals("-p"))) {
				if (hCheck || pCheck) {
					p = 2;
					TClntSrvr.TCS(ARGS);
				} else {
					p = 3;
					TClntSrvr.TCS(ARGS);
				}
			} else {
				System.out.print("Error: invalid arguments");
				System.exit(-1);
			}

			// Server Mode (-s)
		} else if (ARGS[0].contentEquals(s)) {
			if (ARGS.length == 1 || (ARGS.length <= 3 && ARGS[1].contentEquals("-p"))) {
				p = 2;
				TClntSrvr.TCS(ARGS);
			} else {
				System.out.print("Error: invalid arguments");
				System.exit(-1);
			}

			// Help
		} else if (ARGS[0].contentEquals(help)) {
			if (ARGS.length == 1) {
				System.out.println("-----------------\nName: David Fantin\nID: 1525813\n" + "-----------------\n" + "OPERATING MODES\n"
						+ "Client Mode: -h [hostname | IPaddress] [–p portnumber]\n"
						+ "     The program behaves as a client connecting to [hostname | IPaddress] on port portnumber\n"
						+ "Server Mode: -s [–p portnumber]\n"
						+ "     The program behaves as a server listening for connections on port portnumber\n"
						+ "Auto Mode: -a [hostname | IPaddress] [–p portnumber]\n"
						+ "     The program begins by behaving as a client, but if it cannot find a server, it switches to behaving as a server\n-----------------\n"
						+ "DEFAULTS\n" + "Hostname: 127.0.0.1\n" + "Portnumber: 12987\n-----------------\n" + "EXIT\n" + "[CTL + c]\n-----------------");
			} else {
				System.out.print("Error: invalid arguments");
				System.exit(-1);
			}
		} else {
			System.out.print("Error: invalid invocation");
		}
	}

	public static class TClntSrvr {
		public static void TCS(String[] ARGS) {
			defaultQ = 0; // 0 = results->inputed; 1 = results->default
			// ONLY HOSTNAME GIVEN
			if (ARGS.length == 2) {
				serverName = ARGS[1];
				serverPortNumber = defaultPortNumber;
			}
			// ONLY PORT NUMBER GIVEN
			else if (ARGS.length == 3) {
				serverPortNumber = Integer.parseInt(ARGS[p]);
			}
			// EVERYTHING GIVEN
			else if (ARGS.length == 4) {
				serverName = ARGS[p - 2];
				serverPortNumber = Integer.parseInt(ARGS[p]);
			}
			// NOTHING GIVEN
			else {
				defaultQ++;
				// ***********************//
				// Uncomment if you want the actual IP address
//				try {
//					defaultHostname = InetAddress.getLocalHost(); // set defaultHostname to the local IP address
//				} catch (UnknownHostException e) {
//					e.printStackTrace();
//				}
				// ***********************//
				serverPortNumber = defaultPortNumber;
			}

			// Client Start (user1)
			if (ARGS[0].contentEquals(h) || ARGS[0].contentEquals(a)) {
				TClnt.TC(ARGS);
			}

			// Server Start (user2)
			if ((exitCheck) && (ARGS[0].contentEquals(s) || aCheck != 0)) {
				TSrvr.TS(ARGS);
			}
		}
	}

	public static class TClnt {
		public static void TC(String[] ARGS) {
			System.out.println("Starting TalkClient");
			try {
				if (defaultQ < 1) {
					user1 = new Socket(serverName, serverPortNumber);
				} else {
					user1 = new Socket(defaultHostname, serverPortNumber);
				}
				// Print local IP address and port number
				System.out.println("Local IP:" + user1.getLocalAddress().getHostAddress() + "  Local Port:"
						+ user1.getLocalPort());
				// Print remote IP and port number
				System.out.println(
						"Remote IP:" + user1.getInetAddress().getHostAddress() + "  Remote port:" + user1.getPort());
				out1 = new PrintWriter(user1.getOutputStream(), true);
				localin = new BufferedReader(new InputStreamReader(System.in));
				remotein = new BufferedReader(new InputStreamReader(user1.getInputStream()));

				Input TCin = new Input(out1, message1, user1, localin);
				TCin.start();

				Output TCOut = new Output(message2, remotein);
				TCOut.start();

			} catch (UnknownHostException e) {
				System.out.println("Unknown host:" + serverName);
				System.exit(1);
			} catch (IOException e) {
				System.out.println("Client unable to communicate with server");
				// System.out.println("No I/O"); // OLD MESSAGE
				// START THE SERVER IF NO SERVER IS DETECTED (only for Auto Mode)
				if (ARGS[0].contentEquals(a)) {
					aCheck++;
				}
			}
		}
	}

	public static class TSrvr {
		public static void TS(String[] ARGS) {
			System.out.println("Starting TalkServer");
			try {
				srvrInitialize = new ServerSocket(serverPortNumber);
				// Print server IP and port it is listening to
				System.out.println("ServerSocket \nLocal IP:" + srvrInitialize.getInetAddress().getHostAddress()
						+ "  Local Port:" + srvrInitialize.getLocalPort());
			} catch (IOException e) {
				// Server unable to listen on specified port
				System.out.println("Server unable to listen on port " + serverPortNumber);
				System.exit(-1);
			}
			try {
				user2 = srvrInitialize.accept();
				// Print client IP and port number
				System.out.println("Server accepted connection");
				System.out.println(
						"Local IP:" + user2.getLocalAddress().getHostAddress() + " Local port:" + user2.getLocalPort());
				System.out.println(
						"Remote IP: " + user2.getInetAddress().getHostAddress() + "  Remote port:" + user2.getPort());
			} catch (IOException e) {
				System.out.println("Accept failed on port " + serverPortNumber);
				System.exit(-1);
			}
			try {
				out2 = new PrintWriter(user2.getOutputStream(), true);
				localin = new BufferedReader(new InputStreamReader(System.in));
				remotein = new BufferedReader(new InputStreamReader(user2.getInputStream()));
			} catch (IOException e) {
				System.out.println("Couldn't get an inputStream from the client");
				System.exit(-1);
			}

			Input TSin = new Input(out2, message2, user2, localin);
			TSin.start();

			Output TSOut = new Output(message1, remotein);
			TSOut.start();
		}
	}

	public static class Input extends Thread {
		private PrintWriter outx;
		private String messagex;
		private Socket userx;
		private BufferedReader localin;

		public Input(PrintWriter outx, String messagex, Socket userx, BufferedReader localin) {
			this.outx = outx;
			this.messagex = messagex;
			this.userx = userx;
			this.localin = localin;
		}

		public void run() {
			while (true) {
				try {
					messagex = localin.readLine();
					if (messagex.contentEquals(STATUS)) {
						System.out.println("Local IP: " + userx.getLocalAddress().getHostAddress() + "\nLocal Port: "
								+ userx.getLocalPort() + "\nRemote IP: " + userx.getInetAddress().getHostAddress()
								+ "\nRemote Port: " + userx.getPort());
					} else {
						outx.println(messagex);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Output extends Thread {
		private String messagex;
		private BufferedReader remotein;

		public Output(String messagex, BufferedReader remotein) {
			this.messagex = messagex;
			this.remotein = remotein;
		}

		public void run() {
			while (true) {
				try {
					messagex = remotein.readLine();
					System.out.println("[remote]" + messagex);
				} catch (IOException e) {
					System.out.println("Read failed");
					System.exit(-1);
				}
			}
		}
	}

	static int defaultQ = 0;

	static BufferedReader remotein = null;
	static BufferedReader localin = null;

	static PrintWriter out1 = null;
	static PrintWriter out2 = null;

	// User 1 = Initial Client
	static Socket user1 = null;

	// User 2 = Initial Server
	static Socket user2 = null;
	static ServerSocket srvrInitialize = null;

	static String message1 = null;
	static String message2 = null;

	static String h = "-h";
	static String s = "-s";
	static String a = "-a";
	static String help = "-help";
	static String STATUS = "STATUS";
	static int p = 1; // Location in ARGS[] where the port was inputed
	static int aCheck = 0; // Checks if -a has already tried being a client
	static boolean exitCheck = true;
//	static InetAddress defaultHostname; //*****************Replaces "127.0.0.1" with the current computer's IP address******************//
	static String defaultHostname = "127.0.0.1"; // Comment out if you want actual IP address
	static String serverName;
	static int defaultPortNumber = 12987;
	static int serverPortNumber;
	static boolean pCheck;
	static boolean hCheck;
	static String[] ARGS;
}
