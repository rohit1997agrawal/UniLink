public class Reply {

    private String post_id;
    private float value;
    private String responder_id;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getResponder_id() {
        return responder_id;
    }

    public void setResponder_id(String responder_id) {
        this.responder_id = responder_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public Reply()
    {

    }
    public Reply(String post_id ,float value ,String responder_id)
    {
        this.post_id = post_id;
        this.value = value;
        this.responder_id = responder_id;

    }
}
