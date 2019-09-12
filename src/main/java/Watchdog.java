import java.util.ArrayList;
import java.util.List;

public class Watchdog {
    private final Responser responser;
    private List<Order> orders = new ArrayList<Order>();

    public Watchdog(Responser responser){
        this.responser = responser;
    }

    public boolean checkIfUserHasInputFunction(long chatID){
        for(Order o : orders){
            if(o.getChatID() == chatID)
                return true;
                //if there's an order, the function is already set
                //need to only input points
        }
        return false;
    }

    public void addPointsToOrder(long chatID, double xmin, double xmax){
        for(int i = 0; i < orders.size(); i++){
            if(orders.get(i).getChatID() == chatID){
                orders.get(i).setXmin(xmin);
                orders.get(i).setXmax(xmax);


                //Before concurrency hasn't been implemented
                //Later will be deprecated
                orders.get(i).execute(responser);
                orders.remove(i);
            }
        }
    }

    public void addOrder(long chatID, String function){
        Order order = new Order(chatID, function);
        orders.add(order);
    }
}
