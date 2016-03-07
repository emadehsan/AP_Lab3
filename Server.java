import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	static ArrayList<Note> notes;
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		notes = new ArrayList<Note>();
		
		System.out.println("Server build Version 4.3.2, Running J2EE\n");
		try {
			ServerSocket listener = new ServerSocket(8008);
			
			while (true) {
				Socket socket = listener.accept();
//				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//				out.println("200");
				
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String type = input.readLine();
				if (type.equals("obj")) {
//					object is comming
					ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
					
					// save to notes list in memory
					Note note = (Note) in.readObject();
					notes.add(note);
					
					System.out.println("Note obj username: " + note.username);
					System.out.println("Note obj note: " + note.note);
					
					System.out.println("notes list length: " + notes.size());
				}
				else if (type.equals("search")) {
//					System.out.println("search request recieved.");
					String username = input.readLine();
					
					for (int i = 0; i < notes.size(); i ++) {
						if (notes.get(i).username.startsWith(username)) {
							PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
							out.println(notes.get(i).note);
							break;
						}
					}
				}
				
				
				
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
