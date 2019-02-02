package GraphiquePackage;

import SourcePackage.Main;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import javax.swing.JButton;


public class FichMenu extends javax.swing.JDialog {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private javax.swing.JLabel MonPseudo;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private static javax.swing.JLabel listUser;
    private javax.swing.JTextField pseudoEntry;
	
    /**
     * Creates new form FichMenu
     */
    
    public FichMenu(FichAccueil parent, boolean modal) throws IOException,NumberFormatException{
        super(parent, modal);
        initComponents();
        MonPseudo.setText(parent.getPseudo());
        
        ThreadPrintListUser threadPrint = new ThreadPrintListUser("thread print");
        threadPrint.start();
        
        exitSystem();
    }
    
     public void exitSystem(){
         this.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                    	boolean pasQuitter = true;
                    	while(pasQuitter)
	                    try {
							Main.user.sendMessageUDP("quita",0);
							pasQuitter = false;
						} catch (IOException e1) {
							
						}
                    System.exit(0);
                    }
                });
    }
    

    public static void setListUser(String liste){
        listUser.setText(liste);
    }

    private void initComponents() throws IOException,NumberFormatException{

        jLabel1 = new javax.swing.JLabel();
        listUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        MonPseudo = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        pseudoEntry = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Liste des utilisateurs présents :");

        listUser.setText("...");

        jLabel3.setText("Mon pseudo : ");

        MonPseudo.setText("jLabel4");

        jButton2.setText("Communiquer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
					jButton2ActionPerformed(evt);
				} catch (NumberFormatException | IOException  e) {
					e.printStackTrace();
				}
            }
        });

        pseudoEntry.setText("A qui parler ? entrer pseudo");
        pseudoEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            		pseudoEntry.setText("");
                pseudoEntryActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(listUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(37, 37, 37)))
                .addGap(44, 44, 44))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pseudoEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pseudoEntry, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jButton2))
                    .addComponent(listUser, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        pack();
    }

    private void pseudoEntryActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) throws IOException,NumberFormatException{
    		String pseudo = pseudoEntry.getText();
        if(Main.user.contient(pseudo)){
        
        		String[] info = Main.user.getUserPortIP(pseudoEntry.getText()).toString().split("-");
			Main.user.sendMessageTCP("quita"+Main.user.getPseudo(),info[0],parseInt(info[1]), true);

	        // Création d'une nouvelle fenêtre de communication
	        Main.user.addFichCom(Integer.parseInt(info[1]),new FichCom(this,false,parseInt(info[1]),info[0],pseudo),info[0]);
	        Main.user.getFichCom(Integer.parseInt(info[1])).setVisible(true);
        }
        else
        		pseudoEntry.setText("Mauvais pseudo");
        		
        
    }
    
    public void majButton(String name){
        JButton button = new JButton(name);
        button.setVisible(true);
    }
    
    public void openWindow(int port,String addr,String pseudo){
        Main.user.addFichCom(port,new FichCom(this,false,port,addr,pseudo),addr);
        Main.user.getFichCom(port).setVisible(true);
    }
    
    public String getPseudo(){
        return ((FichAccueil)this.getParent()).getPseudo();
    }
    
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FichMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FichMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FichMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FichMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
            		FichMenu dialog = null;
               try {
                dialog = new FichMenu(new FichAccueil(), true);
               }
               catch(IOException  e) {
            	   	System.out.println("normalement ça marche...");
               }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
                
               
            }
        });
    }
}
