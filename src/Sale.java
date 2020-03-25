public class Sale extends Post {
    private double asking_price;    //2 decimal points ?????? Try ????
    private double highest_offer;
    private double minimum_raise;
// ????? Add getters and setters and use them for all cases


    public Sale()
    {
    }

    public Sale(String id , String title , String description , double asking_price  ,  double minimum_raise,String creator_id) {
        super(id, title , description , creator_id);
        this.asking_price = asking_price;
        this.minimum_raise = minimum_raise;

    }
//?????? Display "NO OFFER" for highest offer ???
    @Override
    public String getPostDetails(){
        String post_details = super.getPostDetails();
        String sale_details = post_details+"\nMinimum Raise:\t\t$"+this.minimum_raise + "\nHighest Offer :\t\t$"+this.highest_offer;
        return sale_details;
    }

    public boolean handleReply(Reply reply){  //Can creator of  Sale join his / her event ??????
        Boolean add_reply = false;



        if(this.getStatus().equals("OPEN")   && reply.getValue() > 0 )  //To check if Sale Post is open and is a positive number
        {

            if( reply.getValue()  > this.highest_offer ) { //To check if proposed price is greater than current highest offer
                this.getMarksList().add(reply);//All Criteria match , so add the offer
                this.highest_offer=reply.getValue(); //set the lowest offer

                if(reply.getValue() >= this.asking_price) //To check if proposed price is greater than asking price
                {
                    this.setStatus("CLOSE");   //Close the Sale and Sell it to the current user
                    System.out.println("ITEM SOLD to you!!");
                }

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
            for (int i = getMarksList().size() - 1; i >= 0 ; i--) {  //To Display offer history in Descending order , Print in Reverse order
                reply_details = reply_details + "\n" + getMarksList().get(i).getResponder_id() +":\t" + getMarksList().get(i).getValue() ;
            }


        }

        String askingprice = "\nAsking Price: \n $"+this.asking_price + "\n";
        return askingprice + "-- Offer History --  \n"+ reply_details;  //Return the Offer history

    }



}
