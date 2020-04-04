import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class UniLink {

    //Collection of Type Post to store Post objects(Event/Job/Sale)
    private Set<Post> postCollection = new HashSet<>();

    //Function to generate Auto increment id for Event/Sale/Job (User input : Type of Post)
    String generateAutoIncrementId(String type_post) {
        int current_max = 0;
        //Get current maximum id of selected type of Post
        for (Post iterator : postCollection) {

            if (iterator.getId().substring(0, 3).equals(type_post)) {
                int iterator_id = Integer.parseInt(iterator.getId().substring(3, 6));
                if (iterator_id > current_max) {
                    current_max = iterator_id;
                }
            }
        }
        //Generate new Id ,  by incrementing value by 1
        int new_id = ++current_max;
        String new_max_id = String.format("%03d", new_id);
        return type_post + new_max_id;
    }

    //Function to Handle Reply Functionality, according to Type of Post determined by entered Post Id
    void handleMethodReply(String reply_post_id, String responder_id) throws IOException {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        double value;
        Boolean post_found_reply = false;
        try {

            for (Post iterator : postCollection) {
                String iterator_id = iterator.getId();

                if (iterator_id.equals(reply_post_id)) {
                    //If Entered Post Id of type Event , Show user option to join Event
                    if (iterator_id.substring(0, 3).equals("EVE")) {
                        post_found_reply = true;
                        if (iterator.getCreator_id().equals(responder_id)) //To check if user is trying to "Reply" to his own Post
                        {
                            System.out.println("Sorry! Can not Reply to your own Post!! ");
                            break;
                        }

                        System.out.println("Name : " + iterator.getTitle());
                        System.out.println("Enter 1 to Join the event :  ");
                        value = Double.parseDouble(br.readLine());
                        Reply reply = new Reply(reply_post_id, value, responder_id);
                        if (iterator.handleReply(reply)) {
                            System.out.println("You have been added to the event");

                        } else {
                            System.out.println("Sorry! Not able to Join Event");
                        }
                        break;
                    }


                    //If Entered Post Id of type Job , Show user option to enter offer for Job
                    else if (iterator_id.substring(0, 3).equals("JOB")) {
                        post_found_reply = true;
                        if (iterator.getCreator_id().equals(responder_id)) //To check if user is trying to "Reply" to his own Post
                        {
                            System.out.println("Sorry! Can not Reply to your own Post!! ");
                            break;
                        }

                        System.out.println("Name : " + iterator.getTitle());
                        System.out.println("Enter your Offer :  ");
                        value = Double.parseDouble(br.readLine());
                        Reply reply = new Reply(reply_post_id, value, responder_id);
                        if (iterator.handleReply(reply)) {
                            System.out.println("You offer has been recorded");

                        } else {
                            System.out.println("Sorry! offer not recorded");
                        }
                        break;
                    }
                    //If Entered Post Id of type Sale , Show user option to enter offer for Sale
                    else if (iterator_id.substring(0, 3).equals("SAL")) {
                        post_found_reply = true;
                        if (iterator.getCreator_id().equals(responder_id)) //To check if user is trying to "Reply" to his own Post
                        {
                            System.out.println("Sorry! Can not Reply to your own Post!! ");
                            break;
                        }

                        System.out.println("Name : " + iterator.getTitle());
                        System.out.println("Enter your Offer :  ");
                        value = Double.parseDouble(br.readLine());
                        Reply reply = new Reply(reply_post_id, value, responder_id);
                        if (iterator.handleReply(reply)) {
                            System.out.println("You offer has been recorded");

                        } else {
                            System.out.println("Sorry! offer not recorded");
                        }
                        break;

                    }
                    //To handle is user enters incorrect or wrong "Post id"

                }
                //To handle is user enters incorrect or wrong "Post id"


            }
            if(!post_found_reply)
            {
                System.out.println("Invalid Post id or Post not found! Returning to main menu");
            }
        }
        //To handle if user enters incorrect Decimal number for Reply!
        catch (NumberFormatException e) {
            System.out.println("Not a valid  number! Returning to main menu");
        } catch (IOException e) {
            System.out.println("Failed to get input");
        }

    }


    // Common function to input and validate all inputs of type "String"  in the MENU for creation of "Event" , "Job" and "Sale"
    public String inputValidateString(String inputStringName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Boolean isInputString = true;
        String inputString;

        do { //Keeps asking user to enter "String" if user leaves string empty
            System.out.println(inputStringName + ": ");
            inputString = br.readLine();
            if (inputString.isEmpty()) //To check if entered String is Empty or not
            {
                System.out.println(inputStringName + "Can not be blank\n");
            } else { //Only if not empty , accept the input
                isInputString = false;

            }
        } while (isInputString);

        return inputString;

    }

    // Common function to input and validate all inputs of type "Double"  in the MENU for creation of "Event" , "Job" and "Sale"
    public Double inputValidateDouble(String inputDoubleName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Boolean isInputDouble = true;
        Double inputDouble = 0.0;

        do { //Keeps asking user to enter Decimal if user leaves empty or enters negative number
            try {
                System.out.println(inputDoubleName + ": ");

                inputDouble = Double.parseDouble(br.readLine());


                if (inputDouble <= 0.0) //To check if entered Asking Price is positive decimal or not
                {
                    System.out.println("Please enter a positive Decimal for " + inputDoubleName);
                } else { //If positive decimal , accept the input
                    isInputDouble = false;

                }
            } catch (NumberFormatException e) //To catch Exception if user does not enter a decimal
            {
                System.out.println("Please enter a valid Decimal number");
            }
        } while (isInputDouble);

        return inputDouble;

    }

    // Function to input and Validate "DATE" for format  "dd/mm/yyyy"
    public String inputValidateDate() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Boolean isInputDate = true;
        String inputDate = "";


        do { //Keeps asking user to enter "Date" if user leaves string empty or not of format "dd/mm/yyyy"
            System.out.println("Date : ");
            inputDate = br.readLine();
            if (inputDate.isEmpty()) //To check if entered Date is Empty or not
            {
                System.out.println("Date can not be blank\n");
            } else if (!inputDate.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) { //To check if Date is of format "dd/mm/yyyy"
                System.out.println("Please enter Date in format dd/mm/yyyy ");
            } else { //Only if not empty and of format dd/mm/yyyy , accept the input
                isInputDate = false;

            }
        } while (isInputDate);

        return inputDate;

    }

    //Method to hard-code some data for testing purposes
    public void hardCodeData()
    {
        //To hard code one Event "EVE001" with few attendees
        Event newEvent = new Event("EVE001", "Programming Study Group ", "Let's meet tonight to finish the assignment" , "RMIT Library","06/05/2020", 5, "S001");
        postCollection.add(newEvent);
        Reply replyEvent1 = new Reply("EVE001",1, "S002");
        newEvent.handleReply(replyEvent1);
        Reply replyEvent2 = new Reply("EVE001",1, "S003");
        newEvent.handleReply(replyEvent2);
        Reply replyEvent3 = new Reply("EVE001",1, "S004");
        newEvent.handleReply(replyEvent3);

        //To hard code one Sale with few offers
        Sale newSale = new Sale("SAL001","I phone 6S","Working condtion , with box and charger",400,20,"S005");
        postCollection.add(newSale);
        Reply replySale1 = new Reply("SAL001",200, "S006");
        newSale.handleReply(replySale1);
        Reply replySale2 = new Reply("SAL001",250, "S007");
        newSale.handleReply(replySale2);
        Reply replySale3 = new Reply("SAL001",300, "S008");
        newSale.handleReply(replySale3);

        //To hard code one Job with few offers
        Job newJob = new Job("JOB001","Changing house","Need someone to help me move my belongings to new place",200,"S009");
        postCollection.add(newJob);
        Reply replyJob1 = new Reply("JOB001",200, "S010");
        newJob.handleReply(replyJob1);
        Reply replyJob2 = new Reply("JOB001",150, "S011");
        newJob.handleReply(replyJob2);
        Reply replyJob3 = new Reply("JOB001",100, "S012");
        newJob.handleReply(replyJob3);





    }


    //Method called in "Startup.java" , shows MENU to Login and Perform all the operations of Unilink Application
    public void startUpApplication() throws Exception {



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean menu_open = true, internal_menu = true;

        //Outer Menu to Log in and Quit
        while (menu_open) {
            try {
                System.out.println("** UniLink System **");
                System.out.println("1: Log in");
                System.out.println("2.Quit");
                System.out.println("Enter your choice : ");
                int option_outer_menu = Integer.parseInt(br.readLine());


                switch (option_outer_menu) {
                    case 1:
                        boolean isUsername = true;
                        String creator_id;
                        do { //Keeps asking user to enter "UserName" if user leaves string empty
                            System.out.println("Enter Username: ");
                            creator_id = br.readLine();
                            if (creator_id.isEmpty()) {  //To check if entered User name is Empty or not
                                System.out.println("User name can not be blank");

                            } else { //Only if not empty , accept the input
                                isUsername = false;
                                internal_menu = true;
                            }
                        } while (isUsername);


                        //Inner menu to show all available options after user has logged in
                        while (internal_menu) {
                            try {
                                System.out.println("Welcome " + creator_id);
                                System.out.println("** Student Menu **");
                                System.out.println("1: New Event Post");
                                System.out.println("2: New Sale Post");
                                System.out.println("3. New Job Post");
                                System.out.println("4. Reply to Post");
                                System.out.println("5. Display My Posts");
                                System.out.println("6. Display All Posts");
                                System.out.println("7. Close Post");
                                System.out.println("8. Delete Post");
                                System.out.println("9. Logout");
                                System.out.println("Enter your choice : ");

                                int option_inner_menu = Integer.parseInt(br.readLine());
                                switch (option_inner_menu) {

                                    case 1:
                                        System.out.println("Enter details of the event below: ");
                                        String id_new = generateAutoIncrementId("EVE");

                                        //Calling function "inputValidateString" to input and validate string inputs
                                        String title = inputValidateString("Title");
                                        String description = inputValidateString("Description");
                                        String venue = inputValidateString("Venue");

                                        //Calling function "inputValidateDate" to input and validate date of format "dd/mm/yyyy"
                                        String date = inputValidateDate();


                                        Boolean isCapacity = true;
                                        int capacity = 0;

                                        do
                                        { //Keeps asking user to enter "Capacity" if user leaves empty or enters negative number
                                            try {
                                                System.out.println("Capacity: ");

                                                capacity = Integer.parseInt(br.readLine());


                                                if (capacity <= 0) //To check if entered Capacity is positive integer or not
                                                {
                                                    System.out.println("Please enter a positive Integer for Capacity");
                                                } else { //Only if Positive capacity, accept the input
                                                    isCapacity = false;

                                                }
                                            } catch (NumberFormatException e) //To catch Exception if user does not enter a Integer
                                            {
                                                System.out.println("Please enter a valid Integer");
                                            }
                                        } while (isCapacity);


                                        Event newEvent = new Event(id_new, title, description, venue, date, capacity, creator_id);
                                        postCollection.add(newEvent);
                                        System.out.println("Success! Your event has been created with id: " + id_new);
                                        break;
                                    case 2:
                                        System.out.println("Enter details of the Sale below: ");
                                        id_new = generateAutoIncrementId("SAL");

                                        //Calling function "inputValidateString" to input and validate string inputs
                                        title = inputValidateString("Title");
                                        description = inputValidateString("Description");

                                        //Calling function "inputValidateDouble" to input and validate "Decimal" double inputs
                                        double asking_price = inputValidateDouble("Asking Price");
                                        double minimum_raise = inputValidateDouble("Minimum Raise");


                                        Sale newSale = new Sale(id_new, title, description, asking_price, minimum_raise, creator_id);
                                        postCollection.add(newSale);
                                        System.out.println("Success! Your Sale has been created with id: " + id_new);
                                        break;

                                    case 3:
                                        System.out.println("Enter details of the Job below: ");
                                        id_new = generateAutoIncrementId("JOB");

                                        //Calling function "inputValidateString" to input and validate string inputs
                                        title = inputValidateString("Title");
                                        description = inputValidateString("Description");

                                        //Calling function "inputValidateDouble" to input and validate "Decimal" double inputs
                                        double proposed_price = inputValidateDouble("Proposed price");

                                        Job newJob = new Job(id_new, title, description, proposed_price, creator_id);
                                        postCollection.add(newJob);
                                        System.out.println("Success! Your Job has been created with id: " + id_new);
                                        break;



                                    case 4:
                                        String reply_post_id;
                                        Boolean isReplyPost = true;
                                        do { //Loop keeps asking "Post id" until user enters a String or types 'Q'
                                            System.out.println("Enter post id or 'Q' to quit :");
                                            reply_post_id = br.readLine();
                                            if (reply_post_id.isEmpty()) {
                                                System.out.println("Can not leave empty!");
                                            } else if (!reply_post_id.equals('Q')) {  //Handle Reply only if 'Q' not pressed
                                                handleMethodReply(reply_post_id, creator_id);
                                                isReplyPost = false;
                                            } else {
                                                System.out.println("Returning to main menu");
                                                isReplyPost = false;
                                            }
                                        }
                                        while (isReplyPost);

                                        break;

                                    case 5:
                                        Boolean my_posts = false;
                                        for (Post iterator : postCollection) {
                                            if (iterator.getCreator_id().equals(creator_id)) {//To show Post and Reply Details of only the current user
                                                my_posts = true;
                                                System.out.println(iterator.getPostDetails());
                                                System.out.println(iterator.getReplyDetails());
                                                System.out.println("---------------------------------------------");
                                            }


                                        }
                                        if(!my_posts)
                                        {
                                            System.out.println("No Posts to Display!");
                                        }
                                        break;
                                    case 6:
                                        for (Post iterator : postCollection) { //To Display all Post Details
                                            System.out.println(iterator.getPostDetails());
                                            System.out.println("---------------------------------------------");

                                        }
                                        if(postCollection.size() == 0 )
                                        {
                                            System.out.println("No Posts to Display!");
                                        }
                                        break;
                                    case 7:
                                            System.out.println("Enter Post Id to close the post");
                                            String post_id = inputValidateString("Post Id ");
                                            Boolean post_found = false;
                                        for (Post iterator : postCollection) {
                                            if(iterator.getId().equals(post_id)) //To get the Post with respect to the entered Post id
                                            {
                                                post_found = true;
                                                if (iterator.getCreator_id().equals(creator_id)) { //To check if Creator of post is trying to close the post
                                                    if(iterator.getStatus().equals("OPEN")) {
                                                        System.out.println("Are you sure you want to Close the post ? ");
                                                        String confirm_message = inputValidateString("Yes/No ");
                                                        if (confirm_message.equalsIgnoreCase("Yes")) {
                                                            iterator.setStatus("CLOSE");
                                                            System.out.println("You have successfully closed the Post");

                                                        } else {
                                                            System.out.println("The post is not closed");


                                                        }
                                                        break;
                                                    }
                                                    else{
                                                        System.out.println("The Post is already Closed!");
                                                    }
                                                }
                                                else{
                                                    System.out.println("You are not the creator of the Post! You can not close it");
                                                    break;
                                                }
                                            }


                                        }
                                        if(!post_found)
                                        {
                                            System.out.println("Entered Post id not found ");
                                        }
                                        break;

                                    case 8:
                                        System.out.println("Enter Post Id to delete the post");
                                        String post_id_delete = inputValidateString("Post Id ");
                                        Boolean post_found_delete = false;
                                        Iterator<Post> iterator = postCollection.iterator();  //Using an iterator to remove/delete the post
                                        while (iterator.hasNext()) {
                                            Post  currentPost = iterator.next();

                                            if(currentPost.getId().equals(post_id_delete)) //To get the Post with respect to the entered Post id
                                            {
                                                post_found_delete = true;
                                                if (currentPost.getCreator_id().equals(creator_id)) { //To check if Creator of post is trying to close the post
                                                    System.out.println("Are you sure you want to Delete the post ? ");
                                                    String confirm_message = inputValidateString("Yes/No");
                                                    if(confirm_message.equalsIgnoreCase("Yes"))
                                                    {
                                                        iterator.remove();
                                                        System.out.println("You have Deleted the Post");

                                                    }
                                                    else{
                                                        System.out.println("The post is not Deleted");


                                                    }
                                                    break;

                                                }
                                                else{
                                                    System.out.println("You are not the creator of the Post! You can not Delete it");
                                                    break;
                                                }
                                            }


                                        }
                                        if(!post_found_delete)
                                        {
                                            System.out.println("Entered Post id not found ");
                                        }
                                        break;

                                    case 9:
                                        internal_menu = false; //To Return to the log in Menu
                                        break;

                                    //Default case to handle when user enters option not in the list
                                    default:
                                        System.out.println("\nIncorrect option - Please select a valid choice\n");

                                }

                                //Catch statement to handle incorrect inputs
                            } catch (NumberFormatException e) {
                                System.out.println("Not a valid number");
                            } catch (IOException e) {
                                System.out.println("Failed to get input");
                            }
                        }
                        break;
                    case 2:
                        menu_open = false; //To Quit the Application
                        break;

                    //Default case to handle when user enters option not in the list
                    default:
                        System.out.println("\nIncorrect option - Please select a valid choice\n");

                }

            }
            //Catch statement to handle incorrect inputs
            catch (NumberFormatException e) {
                System.out.println("Not a valid number");
            } catch (IOException e) {
                System.out.println("Failed to get input");
            }


        }
    }
}