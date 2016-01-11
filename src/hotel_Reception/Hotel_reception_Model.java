/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_Reception;

import java.util.StringTokenizer;
import helps.IObserver;
import helps.ISubject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Krishnadas Charankat Baiju
 */
class Hotel_reception_Model extends Object implements ISubject {

    private Hotel_reception_Controller controller;
    private Vector<String> transfer;
    private Hotel_reception_datebase database;
    final String Receptionist_Assistance_starts = "<font color='Green'><b> Hotel Receptionist: </b></font>:",
            UserStart = "<font color='blue'><b>You: </b></font>";

    Hotel_reception_Model() {
        transfer = new Vector<String>();
         database= new Hotel_reception_datebase(this);
        //transfer.add("<i>Connecting to server...</i><br>");
        transfer.add("Connection established successfully.<br>");
        transfer.add("Transffering to our online supported team of sea side hotel .<br><br><br>");
     
        this.helpType();
        
    }

    @Override
    public void attach(IObserver observer) {
        controller = (Hotel_reception_Controller) observer;
    }

    void currentMessage(String text) {
        
        String[] message = text.split("or");
        //re split with and 
        String[] bitMessages = this.getSplitterMessage(message);
        
        
        StringTokenizer tokenizer = new StringTokenizer(text);
        this.database.analysis(tokenizer);       
        this.transfer.add("<br>" + UserStart + this.database.recongnise() );
        
        for(int i=0; i <  bitMessages.length;i++){
            StringTokenizer tokenizer1 = new StringTokenizer(bitMessages[i]);

        // finished processs the message call the controller results to display to the user
        
        this.database.analysis(tokenizer1);       
        String result = this.database.getAnalysisAnswer();        
        this.transfer.add("<br>" + Receptionist_Assistance_starts +  result );       
        this.controller.xhsUpdateMessageBoard();       
        
        }
        
      
    }
    
    // Vector<String> getConversations()  This method get all the conversions 
    //of that as already be made between the user and the Hotel reception(NLP). 

    public Vector<String> returnConversation() {
        if (this.transfer == null) {
            return null;
        }
        return transfer;
    }

    private void helpType() {
       transfer.add(Receptionist_Assistance_starts + " How may i help you. I can help you with the following three. Please choose one or For exit go to exit and press quit <br><br>");
     
       transfer.add("\n1- The hotel Details.<br>");
       transfer.add("\n2 - The Available Rooms for booking.<br>");
       transfer.add("\n2 - Current booking list .<br>");    
      
    }

    private String[] getSplitterMessage(String[] message) {
     
        //now take each substring and split
        
        List<String> tokensMessage=new ArrayList<>();
        
        for(String str:message)
        {
          String[] arrStr =   str.split("and");
          for( String str2:arrStr)
          {
              tokensMessage.add(str2);
          }
        }
        
       String[] result = new String[tokensMessage.size()];
       Iterator<String> iter = tokensMessage.iterator();
       int counter=0;
       while(iter.hasNext())
       {
           result[counter]=iter.next();
           counter++;
       }
       
       return result;
       
    }

}
