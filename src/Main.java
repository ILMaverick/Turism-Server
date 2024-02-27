import java.net.Socket;

public class Main {

	static Socket client;
	
	public static void main(String[] args) {
		ServerTCP server = new ServerTCP();
		client = server.attendi();
		server.comunica();
	}
}
