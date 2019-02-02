package SourcePackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadAcceptTCP extends Thread{
	
        private ThreadReceiveTCPFinal threadReceiveTCP;
        
        private ServerSocket client1Socket ;
        
        
	public ThreadAcceptTCP(String name) {
            super(name);
            try {
                client1Socket = new ServerSocket(Main.user.getPort());
            } catch (IOException ex) {
                Logger.getLogger(ThreadAcceptTCP.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public void run() {
		Socket socketNewConnection = null;
		try {
			//Tant que l'application est ouverte
             while(!client1Socket.isClosed()){
            	 
            	 	socketNewConnection = client1Socket.accept();
            	 	
            	 	//Ouverture d'une fiche de Dialogue
	            Main.openFich(socketNewConnection.getPort(),socketNewConnection.getInetAddress().toString().substring(1),"");
	            Main.user.putListSocket(socketNewConnection);
	            
	            // Le receive TCP pour cette connection
	            threadReceiveTCP = new ThreadReceiveTCPFinal(socketNewConnection);
	            threadReceiveTCP.start();
	            
	         }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}