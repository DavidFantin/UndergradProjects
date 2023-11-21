//Incomplete

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HttpServer {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("There is no inputed port.");
			System.exit(1);
		} else {
			try {
				int port = Integer.parseInt(args[0]);
//			int port = 0;
				////////
				port = 1648;
				////////
				if (port > 0 && port <= 65535) {
					try (ServerSocket serverSocket = new ServerSocket(port)) {
						while (true) {
							try (Socket client = serverSocket.accept()) {
								hClient(client);
							}
						}
					}
				} else {
					System.out.println("The inputed port is not valid.");
					System.exit(1);
				}
			} catch (NumberFormatException e) {
				System.out.println("The inputed port is not valid.");
				System.exit(1);
			}
		}
	}

	private static void hClient(Socket client) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

		StringBuilder requestBuilder = new StringBuilder();
		String line;
		while (!(line = br.readLine()).isBlank()) {
			requestBuilder.append(line + "\r\n");
		}

		String request = requestBuilder.toString();
		String[] requestsLines = request.split("\r\n");

		List<String> headers = new ArrayList<>();
		for (int h = 2; h < requestsLines.length; h++) {
			String header = requestsLines[h];
			headers.add(header);
		}

		Path fPath = Paths.get("/tmp/www", "/public_html");
		if (Files.exists(fPath)) {
			sendResponse(client, "200 OK", contentType, Files.readAllBytes(fPath));
		} else {
			byte[] notFoundContent = "404 File Not Found".getBytes();
			sendResponse(client, "404 Not Found", "text/html", notFoundContent);
		}
	}

	private static void sendResponse(Socket client, String string, String contentType, byte[] readAllBytes) {
		// TODO Auto-generated method stub
		
	}


}
