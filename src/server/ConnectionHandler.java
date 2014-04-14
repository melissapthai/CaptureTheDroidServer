package server;

import java.net.*;
import java.io.*;

import com.google.gson.*;

/*
 * Manages the server socket (waits for a connection request.  When the socket from the 
 * client opens, a new socket is created to connect to the client socket.)
 * */
public class ConnectionHandler {
	private final int portNumber = 1432;

	public static void main(String[] args) throws IOException {

		int portNumber = 1432;
		boolean listening = true;
		GameRunner game = new GameRunner();
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			System.out.println("ServerSocket established");
			while (listening) {
				new UserHandler(serverSocket.accept(), game).start();// just
																		// create
																		// new
																		// UserHandlers
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}