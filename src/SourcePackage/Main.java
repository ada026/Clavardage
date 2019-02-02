package SourcePackage;

import GraphiquePackage.FichAccueil;
import GraphiquePackage.FichMenu;

import java.io.IOException;
import java.net.SocketException;

public class Main {
    static public User user;
    private static FichAccueil fichAccueil;
    private static FichMenu fichMenu;
    
    public static void main(String[] args){
    		try {
		    fichAccueil = new FichAccueil();
		    fichAccueil.setVisible(true);
    		}
    		catch(SocketException e){
    			System.out.println("Tu peux pas lancer 2 applications");
    		}

    }
    
    public static void launchUser(String pseudo){
    	
    		User.setPseudo(pseudo);
    		try {
    			user.startThreadTCP();
    		}
    		catch(IOException e) {
    			
    		}
        
    }
    
    
    
    public static void setFichMenu(FichMenu fichMenu1){
        fichMenu = fichMenu1;
    }
    
    public static void openFich(int port,String addr,String pseudo){
        fichMenu.openWindow(port,addr,pseudo);
    }  
    
    public static User getUser(){
        return user;
    }
   

}