package SourcePackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadReceiveTCPFinal extends Thread {

	private int port;
    private InputStreamReader stream;
	
	public ThreadReceiveTCPFinal(Socket socket) {
		this.port = socket.getPort();
	}
	
	public void run() {
		try {
			stream = new InputStreamReader(Main.user.getSocket(this.port).getInputStream());
	        BufferedReader reader = new BufferedReader(stream);
	        
	        while(Main.user.getSocket(this.port) != null && Main.user.socketConnected(this.port)) {
                    
	        		gererMessagesTCP(reader);
             }
	        
		} catch (IOException e) {
		}
                
        
	}
	
	private void gererMessagesTCP(BufferedReader reader) throws IOException {
		String a = reader.readLine();
		if(a != null){
			if(a.equals("quito")) {
             Main.user.removeSocketAndCloseList(port);
         }
         else if (a.contains("quita"))
             Main.user.getFichCom(port).setPseudo(a.substring(5));
         else 
             Main.user.getFichCom(port).setListMsg(a);
		}
	}
	
}
