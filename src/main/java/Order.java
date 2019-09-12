public class Order {
    private String function;
    private double xmin;
    private double xmax;
    private double result;
    private long chat_id;

    public Order(String function, long chat_id){
        this.function = function;
        this.chat_id = chat_id;
    }

    public void setXmax(double xmax) {
        this.xmax = xmax;
    }

    public void setXmin(double xmin) {
        this.xmin = xmin;
    }

    public double getResult() {
        return result;
    }

    public long getChat_id() {
        return chat_id;
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
}
