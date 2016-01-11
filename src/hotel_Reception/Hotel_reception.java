/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_Reception;

/**
 *
 * @author Krishnadas Charankat baiju
 */
public class Hotel_reception {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Hotel_reception_Controller controller = new Hotel_reception_Controller(new Hotel_reception_Model(), new Hotel_reception_View("Hotel Reception Help Desk"));
        controller.launch();

    }

}
