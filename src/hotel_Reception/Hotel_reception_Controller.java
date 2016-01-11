/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_Reception;

import java.util.Vector;
import helps.Controller;
import helps.ISubject;
import helps.IObserver;

/**
 *
 * @author Krishnadas Charankat baiju
 */
public class Hotel_reception_Controller extends IObserver implements Controller {

    private final Hotel_reception_Model model;
    private final Hotel_reception_View view;

    public Hotel_reception_Controller(Hotel_reception_Model amodel, Hotel_reception_View aview) {
        this.model = amodel;
        this.view = aview;
        this.view.attach(this);
        this.model.attach(this);
        this.view.updateConversations(this.model.returnConversation());

    }

    @Override
    public void launch() {
        this.view.setAlwaysOnTop(true);
        this.view.pack();
        this.view.setResizable(false);

        this.view.setVisible(true);
    }

    @Override
    public ISubject getModel() {
        return this.model;
    }

    @Override
    public ISubject getView() {
        return this.view;
    }

    void xhsCloseWindow() {
        this.view.dispose();
        System.exit(0);
    }

    void xhsSendMessage(String text) {
        if (!text.isEmpty()) {
            this.view.clearConversation();
            //process the message in its own thread 
            model.currentMessage(text);
            
        }

    }

    void xhsUpdateMessageBoard() {
         this.view.updateConversations(this.model.returnConversation());
         }

}
