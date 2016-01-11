package hotel_Reception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Krishnadas Charankat baiju
 */
 class ProcessorXML {

        private String answer;
        private JAXB_XMLParser xmlhandler; // we need an instance of our parser
        //This is a candidate for a name change
     
        private HotelInfo hotel;
       //create individual object list
        private List<Room> rooms = new ArrayList<Room>();
        private List<BookingInfo> bookings = new ArrayList<BookingInfo>();
        private List<Supporter> supporters = new ArrayList<Supporter>();
        private Hotel_reception_datebase database;

        public ProcessorXML(int task, Hotel_reception_datebase adatabase) {
            answer = "";
            database = adatabase;         
            hotel = new HotelInfo();
            try
            {
              xmlhandler = new JAXB_XMLParser("C:\\Users\\KrishnaDasCharankatB\\Desktop\\Final year project\\knowledge  based system\\KBS1\\KBS\\Hotel-Robot-HelpDeskp\\src\\hotel_Reception\\database.xml",ObjectFactory.class);
              hotel=(HotelInfo) xmlhandler.unMarshaller();
               
              setQuestionType(task);// Set the question task  and return the question focus directions
            }
            catch(Exception err)
            {
                JOptionPane.showMessageDialog(null,err.getMessage(),"Error Message",JOptionPane.ERROR_MESSAGE);
            }
            

        }

        private void analysis(int task) {
            switch (task) {
                case Hotel_reception_datebase.AVALIABLE_ROOMS:
                    getAvaliableRooms();
                    break;
                case Hotel_reception_datebase.HOTEL_INFO:
                    getHotelDetails();
                    break;
                case Hotel_reception_datebase.BOOKING_LIST:
                    getBookings();
                    break;
                case Hotel_reception_datebase.ROBOT_INFO:
                    getRobotInformation();
                    break;
                default:
                    error();
                    break;

            }
        }

        //The Xml file questies here
        private void getAvaliableRooms() {

            this.rooms = this.hotel.getRoom();
            this.answer = " Below is the search found on availble rooms \n<center><font color='Red' size='10' > Available Rooms </font></center>\n";

            Iterator<Room> iter = this.rooms.iterator();
             
            while (iter.hasNext()) {
                //get all the rooms in the databases
                Room temRoom = iter.next();
                this.answer += "<hr>Room Number : "+temRoom.getRoomNumber();
                this.answer+="<br>Room Type: "+temRoom.getType();
                this.answer+="<br>Cost :"+temRoom.getAmount();
                this.answer+="<br>Description "+temRoom.getDescriptions();
                this.answer+="Status "+temRoom.isStatus()+"<hr>";
                
            }

            //the last room
            this.answer += "</table>";

        }

        private void getHotelDetails() {

            this.answer = "Below the search found on hotel details  \n <center><font color='Red' size='10' > The Hotel Details</font></center>\n";
            this.answer+=" Hotel Name : "+hotel.getName()+"<br> ";
            this.answer+=" Description: "+hotel.getDescriptions() +"<br>";
            this.answer+=" Number of Rooms: "+hotel.getNumberOfRooms()+"<br>";
        }

        private void getBookings() {
            this.bookings = this.hotel.getBookingInfo();
            this.answer = " Below is the search found on the booking information requested\n<center><font color='Red' size='10' > Booking Informations</font></center>\n";
            
            Iterator<BookingInfo> iter;
            iter = this.bookings.iterator();
               while(iter.hasNext())
            {
              BookingInfo booking = iter.next();
              this.answer+="<hr><br>Booking Number: "+booking.getBookNo();
              this.answer+="<br>Room Number: "+booking.getRoomNumber();
              this.answer+="<br> Customer Id :"+booking.getCustomerID();
              this.answer +="<br>Description :"+booking.getDescriptions();
              this.answer+="<br>Avaliable : "+booking.isStatus()+"<br><hr>"  ;
            }
            
          
        }

        private void getRobotInformation() {

            this.supporters = hotel.get_0020Supporter();
            this.answer += " Well! I'm the receptionist of sea side hotel and I am here to help you";
        }

        private void error() {

            this.answer = "Invalid Entry!!! <br> Please type one of the follow. <br> The hotel <br> The booking <br> The available rooms";
        }

       String setQuestionType(int question_type) {
          try{
            this.analysis(question_type);
            this.xmlhandler.marshaller(this.hotel);
          }catch(Exception err){}
            return this.answer;

        }

    }