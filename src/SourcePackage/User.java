package SourcePackage;

import GraphiquePackage.FichCom;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;

public class User {
    private static String pseudo;
    private static DatagramSocket socketEnvoi;
    private static DatagramSocket socketEcoute;
    private HashMap<Integer,Socket> listSocket;
    private HashMap<Integer,FichCom> listFichCom;
    private PrintWriter writer = null;
    private HashMap<String, String> listUser ; 

    public User(String pseudo1){
        listFichCom = new HashMap<>();
        listUser = new HashMap<>();
        listSocket = new HashMap<>();

        pseudo = pseudo1;
    }
    
    public static void startThreadUDP() throws SocketException{
				socketEnvoi = new DatagramSocket();
				socketEcoute = new DatagramSocket(45047);


        Thread threadReceive = new ThreadReceive("receiveUDP");
        threadReceive.start();
    }
    
    public void startThreadTCP() throws IOException{
        sendMessageUDP(null,0);
        
        ThreadAcceptTCP threadReceiveTCP = new ThreadAcceptTCP("receiveTCP");
        threadReceiveTCP.start();
        
    }
    
    public static void sendMessageUDPInit(String message, int port) throws IOException {
    			send(message);

    }
    
    public void sendMessageUDP(String message,int port) throws IOException{
        String data = "" ;
        
        if(message != null){

            data = message+"-"+pseudo;

        }
        
        else {
            data = Main.user.getPseudo();
        }

        send(data);
    }
    
    private static void send(String data) throws IOException {
        User.getSocketEnvoi().setBroadcast(true);
        InetAddress address = InetAddress.getByName("255.255.255.255"); //mettre l'adresse de broadcast directement
        DatagramPacket packet = new DatagramPacket(data.getBytes(),
        data.getBytes().length, address, 45047);
        User.getSocketEnvoi().send(packet);
    }
    
    
    public void sendMessageTCP(String msg,String ip, int port ,boolean userStartConnexion ) throws IOException{

        Socket socket = listSocket.get(port);
        if(userStartConnexion){
                  socket = new Socket(ip, port);
                  listSocket.put(port, socket);
            		 socketReceiveIniationThread(socket);
          }

            	 	writer = new PrintWriter(socket.getOutputStream());          
                writer.print(msg  + "\n");
                writer.flush();
                if(msg.contains("quito"))
                 		removeSocketList(port);

    }
    
    
     private void socketReceiveIniationThread(Socket socket) throws UnknownHostException, IOException {
		ThreadReceiveTCPFinal threadReceive = new ThreadReceiveTCPFinal(socket);
		threadReceive.start();
    }
    

    public User getUser(){
        return this;
    } 
    
    public String getPseudo(){

        return pseudo;
    }

    public static void setPseudo(String pseudo1) {
		pseudo = pseudo1;
    }
    @Override



    public String toString() {
        return pseudo;
    }
    
    public static DatagramSocket getSocketEnvoi(){
        return socketEnvoi;
    }
    
    public static DatagramSocket getSocketEcoute(){
        return socketEcoute;
    }
    
    public boolean contient(String pseudo) {
    		return listUser.containsKey(pseudo);
    }
    
    public void setListUser(String pseudo, String ipPort){
        this.listUser.put(pseudo, ipPort);
    }
    
    public void removeUserList(String pseudo1){
        this.listUser.remove(pseudo1);
    }
    
    public void removeSocketAndCloseList(int port) throws IOException{
        listSocket.get(port).close();
		removeFichCom(port);
        this.listSocket.remove(port);
    }
    
    public void removeSocketList(int port) {
    	
	        this.listSocket.remove(port);

    			removeFichCom(port);
    }
    
    public boolean belongList(String pseudo){
        if (listUser.containsKey(pseudo)){
            return true;
        }
        return false;
    }
    
    public int getPort(){
        return socketEnvoi.getLocalPort();
    }
    
    public void addFichCom(int port,FichCom fichCom,String addr){
        this.listFichCom.put(port,fichCom);
    }
    
    public FichCom getFichCom(int i){
        return listFichCom.get(i);
    }
    
    public void removeFichCom(int port) {
    		this.listFichCom.get(port).dispose();

    }
    
    public void putListSocket(Socket socket){
        listSocket.put(socket.getPort(), socket);
    }

    public String getUserPortIP(String pseudo) {
    		return listUser.get(pseudo);
    }
    
    public String getAllUserString() {
    		return listUser.toString();
    }
    
    public Socket getSocket(int port) {
    		return listSocket.get(port);
    }
    
    public boolean socketConnected(int port1) {
    		return getSocket(port1).isConnected();
    }
    
    
}