public class Sale extends Post {
    private float asking_price; //Attribute of Type float to Store Asking Price
    private float highest_offer; //Attribute of Type float to Store Highest offer
    private float minimum_raise; //Attribute of Type float to Store Minimum raise


    public Sale() {
    }

    //Parametrized constructor to initialize attributes and create an "Sale Post"
    public Sale(String id, String title, String description, float asking_price, float minimum_raise, String creator_id) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id);
        this.asking_price = asking_price;
        this.minimum_raise = minimum_raise;

    }

    //Getters and Setters
    public float getAsking_price() {
        return asking_price;
    }

    public void setAsking_price(float asking_price) {
        this.asking_price = asking_price;
    }

    public float getHighest_offer() {
        return highest_offer;
    }

    public void setHighest_offer(float highest_offer) {
        this.highest_offer = highest_offer;
    }

    public float getMinimum_raise() {
        return minimum_raise;
    }

    public void setMinimum_raise(float minimum_raise) {
        this.minimum_raise = minimum_raise;
    }

    //Override the getPostDetails() method of Super Class
    @Override
    public String getPostDetails() {
        String highest_offer_value;
        String post_details = super.getPostDetails(); //To call the method from Super Class "Post"
        //If highest offer is empty -> "No Offer" to be displayed
        if (this.getHighest_offer() == 0.0) {
            highest_offer_value = "No OFFER";
        } else {
            highest_offer_value = String.valueOf(this.getHighest_offer());
        }
        String sale_details = post_details + "\nMinimum Raise:\t\t$" + this.minimum_raise + "\nHighest Offer :\t\t$" + highest_offer_value;
        return sale_details; //Contains All Details i.e Post Details + Sale Details
    }

    //Implementation of Abstract method "handleReply" to handle Reply to an "Sale"
    public boolean handleReply(Reply reply) {
        Boolean add_reply = false;
        //To check if Job Post is open and offered Price is a positive number
        if (this.getStatus().equals("OPEN") && reply.getValue() > 0) {
            //To check if proposed price is greater than current highest offer
            if (reply.getValue() > this.highest_offer) {
                this.getReplyList().add(reply);//All Criteria match ,  Adding current "Reply object" to ArrayList "ReplyList"
                this.highest_offer = reply.getValue(); //Update the "Lowest Offer" to current offer

                if (reply.getValue() >= this.asking_price) //To check if proposed price is greater than asking price
                {
                    this.setStatus("CLOSE");   //Close the Sale and Sell the item to the current user
                    System.out.println("ITEM SOLD to you!!");
                }

                return true;
            }

        }
        return false; //One or more criteria not not passed
    }

    //Implementation of Abstract method "getReplyDetails" to display "Offer History" and "Asking Price" of "Sale"
    public String getReplyDetails() {
        String reply_details = "";
        if (this.getReplyList().size() == 0) {
            reply_details = "EMPTY";
        } else {
            //To Display offer history in Descending order , Print in Reverse order
            for (int i = getReplyList().size() - 1; i >= 0; i--) {
                reply_details = reply_details + "\n" + getReplyList().get(i).getResponder_id() + ":\t" + getReplyList().get(i).getValue();
            }
        }
        String askingprice = "\nAsking Price: \n $" + this.asking_price + "\n";
        return askingprice + "-- Offer History --  \n" + reply_details;  //Return the Offer history
    }
}
