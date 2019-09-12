public class Order {
    private String function;
    private double xmin;
    private double xmax;
    private long chatID;

    public Order(long chatID, String function){
        this.function = function;
        this.chatID = chatID;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public long getChatID() {
        return chatID;
    }

    public double getXmax() {
        return xmax;
    }

    public double getXmin() {
        return xmin;
    }

    public String getFunction() {
        return function;
    }

    public void execute(Responser responser){
        IntegrationHub hub = new IntegrationHub(function, xmin, xmax);
        responser.sendResult(chatID, hub.getResult());
    }
}
