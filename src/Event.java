public class Event extends Post {
    private String venue;
    private String date; //Format dd/mm/yyyy
    private int capacity;
    private int attendee_count;

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

    public boolean handleReply(Reply reply){
        Boolean add_reply = false;
        if(reply.getValue() == 1 && this.getStatus().equals("OPEN"))
        {
            for (Reply iterator : this.getMarksList()) {
                {
                    if(!iterator.getResponder_id().equals(reply.getResponder_id()))
                    {
                        add_reply = true;
                    }
                }
            }
        }
        if(this.getMarksList().size()==0  || add_reply)
        {
            this.getMarksList().add(reply);
            this.attendee_count ++;
            if(this.attendee_count == this.capacity)
            {
                this.setStatus("CLOSE");
            }
            return true;
        }
        return false;


    }

    public String getReplyDetails()
    {
        String reply_details = "";
        for (Reply iterator : this.getMarksList()) {
            reply_details = reply_details + ","+ iterator.getResponder_id();
        }
        return reply_details;

    }



}
