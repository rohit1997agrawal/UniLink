import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public  class UniLink {

    private  Set<Post> postCollection = new HashSet<>();

    Event obj1 = new Event();

     String generateAutoIncrementId(String type_post)
    {
        int current_max = 0;
            for (Post iterator : postCollection) {

                if(iterator.getId().substring(0,3).equals(type_post))
                {
                    int iterator_id = Integer.parseInt(iterator.getId().substring(3,6));
                   if( iterator_id > current_max )
                   {
                       current_max = iterator_id;
                   }
                }
            }
        int new_id = ++current_max;
        String new_max_id = String.format("%03d", new_id);
        return type_post+new_max_id;
        }


     void handleMethodReply(String reply_post_id , String responder_id)  throws IOException   // ???Handle all cases with specific messages ?????
    {

        //Handle no post found ???????
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        float value;
        for (Post iterator : postCollection) {
            String iterator_id = iterator.getId();
           if(iterator_id.equals(reply_post_id))
           {
               if(iterator_id.substring(0,3).equals("EVE"))
               {

                   System.out.println("Name : " + iterator.getTitle());
                   System.out.println("Enter 1 to Join the event :  ");
                   value = Float.parseFloat(br.readLine());
                   Reply reply = new Reply(reply_post_id,value,responder_id);
                   if(iterator.handleReply(reply))
                   {
                       System.out.println("You have been added to the event");

                   }
                   else{
                       System.out.println("Sorry! Not able to Join Event");
                   }
                    break;

               }

               else if(iterator_id.substring(0,3).equals("JOB")) {
                   System.out.println("Name : " + iterator.getTitle());
                   System.out.println("Enter your Offer :  ");
                   value = Float.parseFloat(br.readLine());
                   Reply reply = new Reply(reply_post_id, value, responder_id);
                   if (iterator.handleReply(reply)) {
                       System.out.println("You offer has been recorded");

                   } else {
                       System.out.println("Sorry! offer not recorded");
                   }
                   break;
               }
                 else if(iterator_id.substring(0,3).equals("SAL")){
                       System.out.println("Name : " + iterator.getTitle());
                       System.out.println("Enter your Offer :  ");
                       value = Float.parseFloat(br.readLine());
                       Reply reply = new Reply(reply_post_id,value,responder_id);
                       if(iterator.handleReply(reply))
                       {
                           System.out.println("You offer has been recorded");

                       }
                       else{
                           System.out.println("Sorry! offer not recorded");
                       }
                       break;

           }
           }

        }






    }
    public  void startUpApplication() throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean menu_open = true, internal_menu = true; //  "False" when user quits

        while (menu_open) {
            System.out.println("** UniLink System **");
            System.out.println("1: Log in");
            System.out.println("2.Quit");
            System.out.println("Enter your choice : ");
            int z = Integer.parseInt(br.readLine());


            switch (z) {
                case 1:
                    System.out.println("Enter Username: ");
                    String creator_id = br.readLine();
                    internal_menu = true;

                    while (internal_menu) {
                        System.out.println("Welcome " + creator_id);
                        System.out.println("** Student Menu **");
                        System.out.println("1: New Event Post");
                        System.out.println("2: New Sale Post");

                        System.out.println("3. New Job Post");
                        System.out.println("4. Reply to Post");
                        System.out.println("5. Display My Posts");
                        System.out.println("6. Display All Posts");




                        System.out.println("7. Close Post"); //To Do
                        System.out.println("8. Delete Post"); //To Do
                        System.out.println("9. Logout");
                        System.out.println("Enter your choice : ");

                        int y = Integer.parseInt(br.readLine());
                        switch (y) {
                            case 1:
                                System.out.println("Enter details of the event below: ");
                                String id_new = generateAutoIncrementId("EVE");
                                System.out.println("Name: ");  //Improvement: Name and description separate method
                                 String title = br.readLine();
                                System.out.println("Description: ");
                                String description = br.readLine();
                                System.out.println("Venue: ");
                                String venue = br.readLine();
                                System.out.println("Date: ");
                                String date = br.readLine();
                                System.out.println("Capacity: ");
                                int capacity = Integer.parseInt(br.readLine());
                                Event newEvent = new Event(id_new, title, description, venue, date, capacity, creator_id);
                                postCollection.add(newEvent);
                                System.out.println("Success! Your event has been created with id: " + id_new);
                                break;
                            case 2:
                                System.out.println("Enter details of the Sale below: ");
                                id_new = generateAutoIncrementId("SAL");
                                System.out.println("Name: ");  //Improvement: Name and description separate method
                                title = br.readLine();
                                System.out.println("Description: ");
                                description = br.readLine();
                                System.out.println("Asking Price: ");
                                double asking_price = Double.parseDouble(br.readLine());
                                System.out.println("Minimum Raise: ");
                                double minimum_raise = Double.parseDouble(br.readLine());
                                Sale newSale = new Sale(id_new, title, description,asking_price, minimum_raise, creator_id);
                                postCollection.add(newSale);
                                System.out.println("Success! Your Sale has been created with id: " + id_new);
                                break;

                            case 3:
                                System.out.println("Enter details of the Job below: ");
                                 id_new = generateAutoIncrementId("JOB");
                                System.out.println("Name: ");  //Improvement: Name and description separate method
                                 title = br.readLine();
                                System.out.println("Description: ");
                                 description = br.readLine();
                                System.out.println("Proposed Price: ");
                                double proposed_price = Double.parseDouble(br.readLine());
                                Job newJob = new Job(id_new, title, description,proposed_price, creator_id);
                                postCollection.add(newJob);
                                System.out.println("Success! Your Job has been created with id: " + id_new);
                                break;

                            case 6:
                                for (Post iterator : postCollection) { //To Display all Post Details
                                    System.out.println(iterator.getPostDetails());
                                    System.out.println("---------------------------------------------");

                                }
                                break;

                            case 4:
                                System.out.println("Enter post id or 'Q' to quit :");
                                String reply_post_id = br.readLine();
                                if(!reply_post_id.equals('Q')) {  //Handle Reply only if 'Q' not pressed
                                    handleMethodReply(reply_post_id, creator_id);
                                }
                                break;

                            case 5:
                                for (Post iterator : postCollection) {
                                    if(iterator.getCreator_id().equals(creator_id) ) { //To show Post and Reply Details of only the current user
                                        System.out.println(iterator.getPostDetails());
                                        System.out.println(iterator.getReplyDetails());
                                        System.out.println("---------------------------------------------");
                                    }

                                }
                                break;

                            case 9:
                                internal_menu = false; //To Return to the log in Menu
                                break;




                        }
                    }
                    break;
                case 2:
                    menu_open = false; //To Quit the Application
                    break;

            }


        }
    }
}