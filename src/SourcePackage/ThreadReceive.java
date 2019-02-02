package SourcePackage;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import GraphiquePackage.FichAccueil;

public class ThreadReceive extends Thread {
    
        public ThreadReceive(String name){
            super(name);
        }

        public void run(){
            try {
                receiveMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    //UDP : boucle qui recoit les messages udp broadcast pour voir qui est en ligne
    public static void receiveMessage() throws IOException {
        while(true){

            byte[] recvBuf = new byte[1024];
            DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
            User.getSocketEcoute().receive(recvPacket);
            gererMessageUDP(recvPacket);
         
          
        }
    }
    
    private static void gererMessageUDP(DatagramPacket recvPacket) throws IOException {
    	
        String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
        
      //mettre a jour sa liste d'utilisateur, un user a quitté l'application
        if(recvStr.contains("quita")){
            String[] info = recvStr.split("-");
              Main.user.removeUserList(info[1]);
        }
        //On répond à la demande de pseudo pour l'initialisation (Unicité)
        else if(recvStr.contains("quito")) {
        		InetAddress addr = recvPacket.getAddress();
        		int port = recvPacket.getPort();
        		sendMessageInit(addr,port);
        }
        // On ajoute les réponses à nos demandes de pseudo pour l'initialisation (Unicité)
        else if(recvStr.contains("quiti")) { 
        		System.out.println(recvStr);
        		FichAccueil.listUserInit.add(recvStr);
        }
        // On répond à la demande de pseudo et on ajoute la source à notre liste
        else  {
            if( !Main.user.belongList(recvStr)){
            		InetAddress addr = recvPacket.getAddress();
            		int port = recvPacket.getPort();
            		sendMessage(addr, port);
            		ajoutUserListe(recvStr,addr.toString().substring(1)+"-"+port);
            }
        }
    }
    
    public static void sendMessageInit(InetAddress address, int port) {
	    	 try {
	             sleep(1000);
	         } catch (InterruptedException e) {
	             e.printStackTrace();
	         }
	
	         //String data = user.getPseudo();
	         String data = Main.user.getPseudo()+"quiti";
	         DatagramPacket packet = new DatagramPacket(data.getBytes(),
	                 data.getBytes().length, address, 45047);
	         //System.out.println("J'ai renvoyé mon paquet apres l'ecoute sur le port : " + 45047);
	         try {
				User.getSocketEnvoi().send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     
    		
    	
    }
    // Pour faire la réponse à la demande des personnes connectées
    public static void sendMessage (InetAddress address, int port) throws IOException {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String data = Main.user.getPseudo();
        DatagramPacket packet = new DatagramPacket(data.getBytes(),
        data.getBytes().length, address, 45047);
        User.getSocketEnvoi().send(packet);
    }
    
    public synchronized static void ajoutUserListe(String pseudo, String ip){
            Main.user.setListUser(pseudo,ip);
    }


}