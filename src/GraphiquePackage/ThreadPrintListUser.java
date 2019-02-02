package GraphiquePackage;

import SourcePackage.Main;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPrintListUser extends Thread {

    public ThreadPrintListUser(String name){
        super(name);

    }
    
    @Override
    public void run(){
        
        while(true) {
            try {
                sleep(1000);
                FichMenu.setListUser(Main.user.getAllUserString());
    
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadPrintListUser.class.getName()).log(Level.SEVERE, null, ex);
            }

      }
    }
    
}
