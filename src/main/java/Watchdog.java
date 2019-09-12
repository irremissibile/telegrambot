import java.util.ArrayList;
import java.util.List;

public class Watchdog {
    private final Responser responser;
    private List<Order> orders = new ArrayList<Order>();

    public Watchdog(Responser responser){
        this.responser = responser;
    }

    public boolean checkIfUserHasInputFunction(long chat_id){
        for(Order o : orders){
            if(o.getChat_id() == chat_id)
                return true;
                //if there's an order, the function is already set
                //need to only input points
        }
        return false;
    }

    public void addPointsToOrder(long chat_id, double xmin, double xmax){
        for(Order o : orders){
            if(o.getChat_id() == chat_id){
                o.setXmin(xmin);
                o.setXmax(xmax);
            }
        }
    }

    public void addOrder(long chat_id, String function){

    }
}
