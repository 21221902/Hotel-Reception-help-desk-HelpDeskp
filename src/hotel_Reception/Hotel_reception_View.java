/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_Reception;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import helps.IObserver;
import helps.View;
import helps.ISubject;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
/**
 *
 * @author Krishnadas Charankat baiju
 */
class Hotel_reception_View extends View implements ISubject {

    private static String CMD_SEND_MESSAGE = "Ask";
    private static String CMD_CLEAR_SCREEN = "Clear";
    private static String CMD_DISCONNECT_APP = "Disconnect";
    private static String CMD_CLOSE_APPLICATION = "Quit Application";
    private Hotel_reception_Controller controller;
    private JEditorPane jtplMessages;
    private GridBagLayout jgb;
    private JScrollPane jsrpl;
    private JPanel pnlMessageInput;
    private JPanel jpnlContent;
    private JTextField jtxtMessage;
    private JButton jbtnSendMessage;
    private JButton btnSettings;
    private JButton jbtnDisconnect;
    private JButton jbtnCloseApp;

    private GridBagLayout g;
    private Font inputMessageFont;
    private JToolBar toolbar;
    
    
    // The menu  bar 
    
    private static final String CMD_EXIT_APPLICATION="Exit";
    private JMenuBar menubar;
    private JMenu    mFile;
    private JMenu    mFile1;
    private JMenuItem mExistApplication;
    

    //private statics
    private static int APP_WIDTH = 400;
    private static int APP_HEIGHT = 400;
    private static final int LEFT = 4;
    private static final int RIGHT = 4;
    private static final int TOP = 4;
    private static final int BOTTOM = 4;

    public Hotel_reception_View(String title) {

        this.setTitle(title);
         initMenuBar();
        this.initGui();
        this.addWindowListener(new EventHandler(this));
        this.jbtnSendMessage.addActionListener(new EventHandler(this));
        this.jtxtMessage.addActionListener(new EventHandler(this));
    }

    private void initGui() {
        inputMessageFont = new Font(Font.SERIF, Font.PLAIN, 15);
        jtplMessages = new JEditorPane();
        jtplMessages.setPreferredSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        jtplMessages.setContentType("text/html");
        jtplMessages.setEditable(false);
        this.jpnlContent = new JPanel(new GridBagLayout());
        this.jsrpl = new JScrollPane(jtplMessages);
        this.jsrpl.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.jsrpl.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.jsrpl.setBorder(BorderFactory.createTitledBorder("Chat session:"));
        this.jsrpl.getViewport().setPreferredSize(new Dimension(900, 700));

        this.toolbar = new JToolBar();
         this.toolbar.setVisible(false);
        pnlMessageInput = new JPanel(new FlowLayout());
        this.jbtnSendMessage = new JButton(Hotel_reception_View.CMD_SEND_MESSAGE);
        this.btnSettings = new JButton(Hotel_reception_View.CMD_CLEAR_SCREEN);
        this.jbtnCloseApp = new JButton(Hotel_reception_View.CMD_CLOSE_APPLICATION);
        this.jbtnDisconnect = new JButton(Hotel_reception_View.CMD_DISCONNECT_APP);
        this.jtxtMessage = new JTextField(40);
        this.jtxtMessage.setFont(inputMessageFont);

        //add the buttons to the button panel
        this.jgb = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        gc.insets = new Insets(LEFT, RIGHT, TOP, BOTTOM);
        this.jpnlContent.add(this.toolbar, gc);
        //add the textpane
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        gc.insets = new Insets(LEFT, RIGHT, TOP, BOTTOM);
        this.jpnlContent.add(this.jsrpl, gc);

        
        
        
         //add the text message field
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(LEFT, RIGHT, TOP, BOTTOM);
        this.pnlMessageInput.add(this.jtxtMessage, gc);

        //add the button
        //add the text message field
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 1;
        gc.gridy = 0;
        gc.insets = new Insets(LEFT, RIGHT, TOP, BOTTOM);
        this.pnlMessageInput.add(this.jbtnSendMessage, gc);
        
        //add the right handside command buttons
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.gridheight = 1;
        gc.gridwidth = 2;
        gc.insets = new Insets(LEFT, RIGHT, TOP, BOTTOM);
        this.jpnlContent.add(this.pnlMessageInput, gc);
       
      

        //add the main panle to the form
        this.getContentPane().add(jpnlContent);

    }
   private void initMenuBar()
   {
        try {
            //This will get the system look for the UI
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
              this.addWindowListener(new Hotel_reception_View.CustomizeWindowAdaptor(this));
        } catch (Exception err) {
                err.printStackTrace();
        }
       
        menubar= new JMenuBar();
       
        Font menuFont = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
       
        mFile= new JMenu("Exit");
        mFile1 =new JMenu("Save");
        mExistApplication = new JMenuItem(CMD_EXIT_APPLICATION);
        mExistApplication.setMnemonic(KeyEvent.VK_F);
        mExistApplication.setFont(menuFont);
        mExistApplication.addActionListener(new CustomizeWindowAdaptor(this));
        mFile.add(mExistApplication);
        
        
    
        menubar.add(mFile);
        this.setJMenuBar(menubar);
        
        menubar.add(mFile1);
        this.setJMenuBar(menubar);
        
    
   }
    @Override
    public void attach(IObserver observer) {
        controller = (Hotel_reception_Controller) observer;
    }

    void updateConversations(Vector<String> conversations) {
        if (conversations == null) {
            return;
        }
        Iterator<String> iter = conversations.iterator();
        String strConversations = "";
        while (iter.hasNext()) {
            strConversations += iter.next();
        }
        this.jtplMessages.setText(strConversations);
        scroll();
    }

    private void scroll() {

    }

    void clearConversation() {
        this.jtplMessages.setText("");
    }

    
    
    
    // the inner class that handle events 
    private class EventHandler implements WindowListener, ActionListener {

        Hotel_reception_View parent;

        EventHandler(Hotel_reception_View view) {
            this.parent = view;
        }

        @Override
        public void windowOpened(WindowEvent e) {
        }

        @Override
        public void windowClosing(WindowEvent e) {
            this.parent.controller.xhsCloseWindow();
        }

        @Override
        public void windowClosed(WindowEvent e) {
        }

        @Override
        public void windowIconified(WindowEvent e) {
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
        }

        @Override
        public void windowActivated(WindowEvent e) {
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Hotel_reception_View.CMD_SEND_MESSAGE.equals(e.getActionCommand())) {
                sendMessage();
            } else if (e.getSource().equals(this.parent.jtxtMessage)) {
                sendMessage();
            }

        }

        private void sendMessage() {

            this.parent.controller.xhsSendMessage(this.parent.jtxtMessage.getText());
            this.parent.jtxtMessage.setText("");
        }
    }
    
    
    
     private class CustomizeWindowAdaptor extends WindowAdapter implements ActionListener{

        Hotel_reception_View window;

        CustomizeWindowAdaptor(Hotel_reception_View window) {
            this.window = window;
        }

        public void windowClosing(WindowEvent evt) {
            this.window.controller.xhsCloseWindow();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
           if(this.window.mExistApplication.equals(e.getSource()))
           {
               windowClosing(null);
           }
            
        }
    }

}
