/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel_Reception;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Krishnadas Charankat baiju
 */
class Hotel_reception_datebase {

    private ArrayList<String> roomSym;
    private ArrayList<String> bookingSym;
    private ArrayList<String> userSyms;
    private ArrayList<String> hotelSymns;
    private ArrayList<String> referenquestions;
    private ArrayList<String> AmountsSym;
    private String strResults;
    private String message;
    private static int CONTEXT_ABOUT_AMOUNT = 0;
    private static int CONTEXT_ABOUT_ROOMS = 0;
    private static int CONTEXT_ABOUT_HOTEL = 0;
    private static int CONTEXT_ABOUT_SUPPORTER = 0;
    private static int CONTEXT_ABOUT_BOOKING = 0;
    private static int CONTEXT_REFERENCE = 0;
    private static int ValidQuestionCount = 0;

    //query variable
     public final static int AVALIABLE_ROOMS = 1;
     public  final static int ALLROOMS_LIST = 2;
     public  final static int HOTEL_INFO = 3;
     public  final static int BOOKING_LIST = 4;
     public  final static int ROBOT_INFO = 5;

    //query fields
    private static int question_type = 0;
    private ProcessorXML query;
    private static boolean IS_SOMETHING_OUT = false;

    Hotel_reception_datebase(Object parent) {
        strResults = "";
        roomSym = new ArrayList<String>();
        bookingSym = new ArrayList<String>();
        userSyms = new ArrayList<String>();
        hotelSymns = new ArrayList<String>();
        AmountsSym = new ArrayList<String>();
        referenquestions = new ArrayList<String>();
        //this will get the keyword word to rearrange the semantic meaning
        QueryTypeList = new ArrayList<String>();
        query = new ProcessorXML(question_type, this);
        this.init();
    }

    private void init() {
        //The symnome should appear here
        AmountsSym.add("count");
        AmountsSym.add("amount");
        AmountsSym.add("list");
        AmountsSym.add("total");
        AmountsSym.add("vacancy");
        AmountsSym.add("available");
        AmountsSym.add("avaliable");
        AmountsSym.add("unavailable");
        AmountsSym.add("many");
        AmountsSym.add("cost");
        AmountsSym.add("price");
        AmountsSym.add("costs");
        AmountsSym.add("prices");
        AmountsSym.add("information");
        AmountsSym.add("informations");
        AmountsSym.add("show");
        // keywords towards rooms concepts

        roomSym.add("rooms");
        roomSym.add("room");
        roomSym.add("apartments");
        roomSym.add("aprtment");
        roomSym.add("apartment");
        roomSym.add("accomdations");
        roomSym.add("accomdation");
        roomSym.add("lunch");
        roomSym.add("rom");
        roomSym.add("roms");

        //keyowrds towards booking
        bookingSym.add("book");
        bookingSym.add("bookings");
        bookingSym.add("booking");
        bookingSym.add("reservations");
        bookingSym.add("reserve");
        bookingSym.add("reserves");
        //taking about the supporter
        userSyms.add("you");
        userSyms.add("who");
        userSyms.add("robot");
        userSyms.add("name");
        //Taking about the hotel
        hotelSymns.add("hotel");
        hotelSymns.add("locations");
        hotelSymns.add("place");
        // Reference sym
        referenquestions.add("that");

    }

    void analysis(StringTokenizer tokenizer) {
        this.message = "";
        QueryTypeList.clear();
        //initialise then again
        CONTEXT_ABOUT_AMOUNT = 0;
        CONTEXT_ABOUT_ROOMS = 0;
        CONTEXT_ABOUT_HOTEL = 0;
        CONTEXT_ABOUT_SUPPORTER = 0;
        CONTEXT_ABOUT_BOOKING = 0;
        CONTEXT_REFERENCE = 0;

        while (tokenizer.hasMoreTokens()) {
            String word = tokenizer.nextToken().toLowerCase();

            if (isTakingAboutAmount(word)) {
                CONTEXT_ABOUT_AMOUNT = 1;
                QueryTypeList.add(word);
                this.message += " <b>" + word + "</b> ";
            } else if (isTakingAboutRoom(word)) {

                CONTEXT_ABOUT_ROOMS = 1;
                QueryTypeList.add(word);
                this.message += " <b>" + word + "</b> ";
            } else if (isTakingAboutBooking(word)) {

                CONTEXT_ABOUT_BOOKING = 1;
                QueryTypeList.add(word);
                this.message += " <b>" + word + "</b> ";
            } else if (isReferenceToPreviousQuerySubject(word)) {

                CONTEXT_REFERENCE = 1;
                QueryTypeList.add(word);
                this.message += " <b>" + word + "</b> ";
            } else if (isTakingAboutSupporter(word)) {
                CONTEXT_ABOUT_SUPPORTER = 1;
                QueryTypeList.add(word);
                this.message += " <b>" + word + "</b> ";
            } else if (isAskingAboutHotelDetails(word)) {
                CONTEXT_ABOUT_HOTEL = 1;
                QueryTypeList.add(word);
                this.message += "<b>" + word + "</b> ";

            } else {
                this.message += word + " ";
            }

        }

        // Find the semantic meaning of the user setences
        this.analysis();

    }

    private void analysis() {

        int question_focus = 0;
        if (CONTEXT_ABOUT_AMOUNT == 1) {
            // the user is taking amount number of something
            question_focus++;
        }
        if (Hotel_reception_datebase.CONTEXT_ABOUT_BOOKING == 1) {
            question_focus++;
        }
        if (Hotel_reception_datebase.CONTEXT_ABOUT_HOTEL == 1) {
            question_focus++;
        }
        if (Hotel_reception_datebase.CONTEXT_ABOUT_ROOMS == 1) {
            question_focus++;
        }
        if (Hotel_reception_datebase.CONTEXT_ABOUT_SUPPORTER == 1) {
            question_focus++;
        }
        if (Hotel_reception_datebase.CONTEXT_REFERENCE == 1) {
            question_focus++;
        }

        boolean isAmbiguous = this.isAmbiguousQuestion(question_focus);
        if (isAmbiguous) {
            //handler and suggest question for customer or guest
            this.detectedQuestionFocus();

        } else if (isValidFocusQuestion(question_focus)) {
            this.detectedQuestionFocus();
        } else {

            this.detectedQuestionFocus();
            Hotel_reception_datebase.ValidQuestionCount += 1;
        }

    }

    String getAnalysisAnswer() {
        this.strResults = this.query.setQuestionType(question_type);
        return (this.strResults + "[" + question_type + "]").trim();
    }

    private static final String Context_subject = null;
    private static ArrayList<String> QueryTypeList;

    //This method will
    private boolean isTakingAboutAmount(String word) {
        boolean isOkay = false;

        Iterator<String> iter = AmountsSym.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }
    
    

    private boolean isTakingAboutRoom(String word) {
        boolean isOkay = false;

        Iterator<String> iter = roomSym.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }

    private boolean isTakingAboutBooking(String word) {
        boolean isOkay = false;

        Iterator<String> iter = this.bookingSym.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }

    private boolean isReferenceToPreviousQuerySubject(String word) {
        boolean isOkay = false;

        Iterator<String> iter = this.referenquestions.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }

    private boolean isTakingAboutSupporter(String word) {
        boolean isOkay = false;

        Iterator<String> iter = this.userSyms.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }

    private boolean isAskingAboutHotelDetails(String word) {
        boolean isOkay = false;

        Iterator<String> iter = this.hotelSymns.iterator();

        while (iter.hasNext()) {
            if (iter.next().equalsIgnoreCase(word)) {
                isOkay = true;
                break;
            }
        }

        return isOkay;
    }

    private boolean isAmbiguousQuestion(int question_focus) {
        boolean isOkay = false;

        if (question_focus > 3) {
            isOkay = true;

        }

        return isOkay;
    }

    private boolean isValidFocusQuestion(int question_focus) {
        boolean isOkay = false;

        if (question_focus >= 1 && question_focus <= 3) {
            isOkay = true;
        }

        return isOkay;
    }

    String recongnise() {

        return this.message.trim();
    }

    private void decision() {
        // the subject is about number of items
        if (Hotel_reception_datebase.CONTEXT_ABOUT_BOOKING == 1
                || (Hotel_reception_datebase.CONTEXT_ABOUT_BOOKING == 1 && Hotel_reception_datebase.CONTEXT_ABOUT_ROOMS == 1)) {
            // the subject is amount the number of bookings are avaliable
            question_type = Hotel_reception_datebase.BOOKING_LIST;
        } else if (Hotel_reception_datebase.CONTEXT_ABOUT_HOTEL == 1) {
            question_type = Hotel_reception_datebase.HOTEL_INFO;
        } else if (Hotel_reception_datebase.CONTEXT_ABOUT_ROOMS == 1) {
            question_type = Hotel_reception_datebase.AVALIABLE_ROOMS;
        } else if (Hotel_reception_datebase.CONTEXT_ABOUT_SUPPORTER == 1) {
            question_type = Hotel_reception_datebase.ROBOT_INFO;
        } else if (Hotel_reception_datebase.CONTEXT_REFERENCE == 1) {
            question_type = 0;
        } else {
            question_type = -1;
        }
    }

    private void detectedQuestionFocus() {

        if (Hotel_reception_datebase.CONTEXT_ABOUT_AMOUNT == 1) {
            IS_SOMETHING_OUT = true;
            decision();

        } else {
            IS_SOMETHING_OUT = false;
            decision();
            // the question did not mention mention list or count
        }
    }

  
   
}
