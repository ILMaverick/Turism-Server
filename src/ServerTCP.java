import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
	ServerSocket welcomeSocket = null;
	Socket client = null;

	String stringaRicevuta = null;
	String stringaModificata = null;

	BufferedReader inFromClient;
	DataOutputStream outToClient;

	public Socket attendi() {
		try {
			System.out.println("SERVER partito in esecuzione....");

			// creo una socket di benvenuto sulla porta 6789
			welcomeSocket = new ServerSocket(6789);

			// rimane in attesa sulla socket di benvenuto il contatto //con un client
			client = welcomeSocket.accept();

			// chiudo il server per inibire altri client
			welcomeSocket.close();

			// associo due oggetti al socket del client per //effettuare la scrittura e la
			// lettura
			inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outToClient = new DataOutputStream(client.getOutputStream());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Errore durante l'istanza del server!");
			System.exit(1);
		}

		return client; // ritorna la Socket connessa al client
	}

	public void comunica() {
		try {
			// rimango in attesa della riga trasmessa dal client
			System.out.println("benvenuto client, scrivi una frase e la trasformo in maiuscolo. Attendo...");
			// leggo una stringa dalla socket
			stringaRicevuta = inFromClient.readLine();
			System.out.println("ricevuta la stringa dal client: " + stringaRicevuta);

			// la modifico e la rispedisco al client
			stringaModificata = stringaRicevuta.toUpperCase();
			System.out.println("invio la stringa modificata al client...");
			// scrivo una stringa sul socket
			outToClient.writeBytes(stringaModificata + '\n');

			// termina elaborazione sul server: chiudo la connessione del client
			System.out.println("SERVER: fine elaborazione.");
			client.close(); // chiusura socket

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

}
