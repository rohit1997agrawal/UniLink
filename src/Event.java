public class Event extends Post {
    private String venue;
    private String date; //Format dd/mm/yyyy
    private int capacity;
    private int attendee_count;
// ????? Add getters and setters and use them for all cases
    public Event()
    {
    }

    public Event(String id , String title , String description , String venue , String date , int capacity , String creator_id) {
        super(id, title , description , creator_id);
        this.venue = venue;
        this.date=date;
        this.capacity=capacity;
    }

    @Override
    public String getPostDetails(){
        String post_details = super.getPostDetails();
        String event_details = post_details +  "\nVenue:\t\t"+this.venue+"\nDate:\t\t"+this.date+"\nCapacity:\t\t"+this.capacity+"\nAttendees:\t\t"+this.attendee_count;
        return event_details;
    }

    public boolean handleReply(Reply reply){  //Can creator of event join his / her event ??????
        Boolean add_reply = false;
        if(reply.getValue() == 1 && this.getStatus().equals("OPEN"))  //To check if Reply is in "1" and Event is still "OPEN"
        {

            if(this.getMarksList().size() == 0) //If attendees list is Empty , no need to check if "Student id" is already present
            {
                add_reply = true;
            }
            for (Reply iterator : this.getMarksList()) {
                {
                    if(!iterator.getResponder_id().equals(reply.getResponder_id())) //To check if "Student id" is already in the Attendee list
                    {
                        add_reply = true;
                    }
                }
            }
        }
        if(add_reply)
        {
            this.getMarksList().add(reply);   //All Criteria match , so add the student id to attendee list
            this.attendee_count ++;
            if(this.attendee_count == this.capacity)  //To Close the event if Attendees match the capacity
            {
                this.setStatus("CLOSE");
            }
            return true;
        }
        return false; //One or more criteria not passed, not able to add "student id" in attendee list


    }

    public String getReplyDetails()
    {
        String reply_details = "";
        if(this.getMarksList().size() == 0)
        {
            reply_details = "EMPTY";
        }
        else {
            for (Reply iterator : this.getMarksList()) {
                reply_details = reply_details + "," + iterator.getResponder_id();
            }

            reply_details = reply_details.substring(1);   //To Remove the first character "," from the String
        }
        return "Attendee list: \t"+ reply_details;

    }



}
