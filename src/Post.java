import java.util.ArrayList;
// ????? Add getters and setters and use them for all cases
public abstract class Post {
    private String id;
    private String title;

    public String getCreator_id() {
        return creator_id;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Reply> getMarksList() {
        return marksList;
    }

    public void setMarksList(ArrayList<Reply> marksList) {
        this.marksList = marksList;
    }

    private String description;
    private String creator_id;
    private String status;
    private ArrayList<Reply> marksList=new ArrayList<Reply>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Post()
    {
    }

    public Post(String id , String title, String description , String creator_id  ) // Constructor to be used by sub Classes to to initialize the attributes
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator_id = creator_id;
        this.status = "OPEN";  //By Default Status is set to "OPEN"

    }

    public String getPostDetails() {
        String post_details = "ID:\t\t"+this.id+"\nTitle:\t\t"+this.title+"\nDescription:\t\t"+this.description+"\nCreator Id:\t\t"+this.creator_id+"\nStatus:\t\t"+this.status;
        return post_details;
    }


    public abstract boolean handleReply(Reply reply);

    public abstract String getReplyDetails();





}
