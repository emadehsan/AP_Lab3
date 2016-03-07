import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static Note makeNote() {
		Scanner scan = new Scanner(System.in);
		Note note = new Note();
		
		System.out.println("Enter username: ");
		note.username = scan.next();
		System.out.println("Enter note: ");
		note.note = scan.next();

		return note;
	}
	
	public static void searchNotes() throws UnknownHostException, IOException {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter username of notes' writer.");
		String username = scan.next();
		
		// connect to server seperately
		Socket sock = new Socket("127.0.0.1", 8008);
		
		// tell server that I am sending search
		PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
		out.println("search");
		
		// now send username
		out.println(username);
		
		// receive results and close socket
		
		sock.close();
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

		Scanner scan = new Scanner(System.in);
		
		System.out.println("Client note taking app.");
		while (true) {
			System.out.println("To make a new note type 'n'."
					+ "\nType 'q' to exit."
					+ "\nType 's' to search."
					+ "\nPress Enter.\n");
			
			String decision = scan.next();
			
			Note note = null;
			
			if (decision.equals("q")) {
				System.out.println("Exiting");
				return;
			}
			else if (decision.equals("n")) {
				note = makeNote();
			}
			else if (decision.equals("s")) {
				searchNotes();
			}
			else {
				continue;
			}
			
			// connecting.
			Socket clientSocket = new Socket("127.0.0.1", 8008);

//			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//			String serverMsg = input.readLine();
////			System.out.println("Server: " + serverMsg);
//			if (!serverMsg.equals("200")) {
//				System.out.println("Error connecting to server.");
//			}
			
			// tell server that I am sending Object
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			out.println("obj");
			
			// sending object
			ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
			objOut.writeObject(note);
			objOut.flush();
			
			clientSocket.close();
//			scan.close();
		}		
	}
}
