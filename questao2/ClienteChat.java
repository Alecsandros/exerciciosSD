
import java.util.Scanner;

import exercicio.ServidorChat.Get;
import exercicio.ServidorChat.Post;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteChat {

	static class Get extends Thread {
		private BufferedReader in;
		private int port;

		public Get(BufferedReader in, int port) {
			this.in = in;
			this.port = port;
		}

		public void run() {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String input = "";

			while (input != null) {
				try {
					input = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println(input);

			}

		}
	}

	static class Post extends Thread {
		private PrintWriter out;
		private BufferedReader in;

		public Post(PrintWriter out, BufferedReader in) {
			this.out = out;
			this.in = in;
		}

		public void run() {

			BufferedReader stdIn = new BufferedReader(new InputStreamReader(

					System.in));

			String userInput;
			
			try {
				userInput = stdIn.readLine();
				while( userInput != null) {
					out.println(userInput);
					userInput = stdIn.readLine();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int port = 9090;
		try {
			echoSocket = new Socket("localhost", port);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		Thread Post = new Post(out, in);
		Thread Get = new Get(in, port);
		
		Post.start();
		Get.start();	
		
	}

}