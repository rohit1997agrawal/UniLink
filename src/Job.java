public class Job extends Post {
    private double proposed_price;    //2 decimal points ?????? Try ????
    private double lowest_offer;

    public double getProposed_price() {
        return proposed_price;
    }

    public void setProposed_price(double proposed_price) {
        this.proposed_price = proposed_price;
    }

    public double getLowest_offer() {
        return lowest_offer;
    }

    public void setLowest_offer(double lowest_offer) {
        this.lowest_offer = lowest_offer;
    }

    public Job()
    {
    }

    public Job(String id , String title , String description , double proposed_price , String creator_id) {
        super(id, title , description , creator_id);
        this.proposed_price = proposed_price;

    }

    @Override
    public String getPostDetails(){
        String post_details = super.getPostDetails();
        String job_details = post_details +  "\nProposed Price :\t\t$"+this.proposed_price+"\nLowest Offer:\t\t$"+this.lowest_offer;
        return job_details;
    }

    public boolean handleReply(Reply reply){  //Can creator of job join his / her event ??????
        Boolean add_reply = false;



        if(this.getStatus().equals("OPEN")   && reply.getValue() > 0 )  //To check if Job Post is open and is a positive number
        {

            if( reply.getValue()  < this.getLowest_offer() || this.getMarksList().size()==0) { //To check if proposed price is less than current lowest offer or there are no offers present
                this.getMarksList().add(reply);   //All Criteria match , so add the offer
                this.setLowest_offer(reply.getValue()); //set the lowest offer
                return true;
            }

        }
        return false; //Proposed price not lower than current lowest price or is negative


    }

    public String getReplyDetails()
    {
        String reply_details = "";
        if(this.getMarksList().size() == 0)
        {
            reply_details = "EMPTY";
        }
        else {
            for (int i = getMarksList().size() - 1; i >= 0 ; i--) {  //To Display offer history in Ascending order , Print in Reverse order
                reply_details = reply_details + "\n" + getMarksList().get(i).getResponder_id() +":\t" + getMarksList().get(i).getValue() ;
            }


        }
        return "-- Offer History --  \n"+ reply_details;  //Return the Offer history

    }



}
