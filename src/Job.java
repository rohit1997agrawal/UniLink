public class Job extends Post {
    private float proposed_price; //Attribute of type float to store the Proposed Price
    private float lowest_offer; //Attribute of type float to store the Lowest Offer

    public Job() {
    }

    //Parametrized constructor to initialize attributes and create an "Job Post"
    public Job(String id, String title, String description, float proposed_price, String creator_id) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id);
        this.proposed_price = proposed_price;

    }

    //Getters and Setters
    public float getProposed_price() {
        return proposed_price;
    }

    public void setProposed_price(float proposed_price) {
        this.proposed_price = proposed_price;
    }

    public double getLowest_offer() {
        return lowest_offer;
    }

    public void setLowest_offer(float lowest_offer) {
        this.lowest_offer = lowest_offer;
    }

    //Override the getPostDetails() method of Super Class
    @Override
    public String getPostDetails() {
        String post_details = super.getPostDetails(); //To call the method from Super Class "Post"
        String job_details = post_details + "\nProposed Price :\t\t$" + this.getProposed_price() + "\nLowest Offer:\t\t$" + this.getLowest_offer();
        return job_details; //Contains All Details i.e Post Details + Job Details
    }

    //Implementation of Abstract method "handleReply" to handle Reply to an "Job"
    public boolean handleReply(Reply reply) {
        //To check if Job Post is open and offered Price is a positive number
        if (this.getStatus().equals("OPEN") && reply.getValue() > 0) {
            //To check if proposed price is less than current lowest offer / No offers present
            if (reply.getValue() < this.getLowest_offer() || this.getReplyList().size() == 0) {
                this.getReplyList().add(reply);   //Adding current "Reply object" to ArrayList "ReplyList"
                this.setLowest_offer(reply.getValue()); //Update the "Lowest Offer" to current offer
                return true;
            }
        }
        return false; //One or more criteria not not passed


    }

    //Implementation of Abstract method "getReplyDetails" to display "Offer History" of "Job"
    public String getReplyDetails() {
        String reply_details = "";
        if (this.getReplyList().size() == 0) {
            reply_details = "EMPTY";
        } else {
            //To Display offer history in Ascending order , Print the Array List "replyList" in Reverse order
            for (int i = getReplyList().size() - 1; i >= 0; i--) {
                reply_details = reply_details + "\n" + getReplyList().get(i).getResponder_id() + ":\t" + getReplyList().get(i).getValue();
            }


        }
        return "-- Offer History --  \n" + reply_details;  //Return the Offer history

    }


}
