package GraphiquePackage;

import SourcePackage.Main;
import SourcePackage.User;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;


public class FichAccueil extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**     * Creates new form FichMenu
     */
    private FichMenu menu;
    private javax.swing.JTextField pseudo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    
    public static ArrayList<String> listUserInit = new ArrayList<>();
    
    public FichAccueil() throws SocketException{
        
        Main.user = new User("quiti");
        User.startThreadUDP();
        initComponents();

    }

   

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        pseudo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("BIENVENUE");

        pseudo.setText("Entrez pseudo");
        pseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		pseudo.setText("");
                PseudoActionPerformed(evt);
            }
        });

        jButton1.setText("Se connecter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					jButton1ActionPerformed(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(248, 248, 248)
                        .addComponent(jLabel1)))
                .addContainerGap(228, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pseudo, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(314, Short.MAX_VALUE))
        );

        pack();
    }

    private void PseudoActionPerformed(java.awt.event.ActionEvent evt) {
     
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
    		boolean pasEnvoyer = true;
    		while(pasEnvoyer) {
	        try{
	        		User.sendMessageUDPInit("quito", 0);
	        		pasEnvoyer = false;
	    			Thread.sleep(3000);
	        }
	        catch(IOException | InterruptedException e) {
	        		
	        }
    		}
	        
        String a = pseudo.getText();
        if(!listUserInit.contains(a+"quiti") && !a.equals("")) {
        	
	          	Main.launchUser(a);
	            menu = new FichMenu(this, false);
	            this.setVisible(false);
	            menu.setVisible(true);
	            
	            Main.setFichMenu(menu);
        }
            
    }

    public String getPseudo(){
        return pseudo.getText();
    }
    

}
