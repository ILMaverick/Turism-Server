import java.io.*;
import java.net.*;

public class ClientTCP{
    
    String nomeServer = "localhost";
    int portaServer = 6789;

    Socket mioSocket;

    String stringaUtente; //stringa inserita da utente 
    String stringaRicevutaDalServer;  //stringa ricevuta dal server

    DataOutputStream outToServer; //stream di output
    BufferedReader inFromServer;  //stream di input
    
    public static void main(String[] args) {
        ClientTCP cliente = new ClientTCP();
        cliente.connetti();
        cliente.comunica();
    }
    
    public Socket connetti(){
        System.out.println("CLIENT partito in esecuzione....");
        
        try{
            //creo un socket
            mioSocket = new Socket(nomeServer,portaServer);
            
            //associo due oggetti al socket per effettuare la //scrittura e la lettura
            outToServer = new DataOutputStream(mioSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            
        }catch(UnknownHostException e){
            
            System.err.println("Host sconosciuto");
            
        }catch(Exception e){
            
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
            
        }
        
        return mioSocket;

    }
    
    public void comunica(){
        try{
            //input da tastiera            
            BufferedReader tastiera = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Inserisci la stringa da tasmettere al server:"+'\n');
            stringaUtente = tastiera.readLine();
            
            //la spedisco al server
            System.out.println("Invio la stringa al server e attendo...");
            outToServer.writeBytes(stringaUtente+'\n');
            
            //leggo la risposta del server
            stringaRicevutaDalServer = inFromServer.readLine();
            System.out.println("...risposta dal server "+'\n'+stringaRicevutaDalServer);
            
            //chiudo la connessione
            System.out.println("CLIENT: termina elaborazione e chiude connessione");

            mioSocket.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }
    
}