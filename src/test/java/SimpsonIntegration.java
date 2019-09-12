import net.objecthunter.exp4j.*;

public class SimpsonIntegration extends Integration {
    public SimpsonIntegration(String func, double min, double max) {
        super();
        function = func;
        xmin = min;
        xmax = max;
        numberOfPoints = (int)(xmax - xmin)*100;
        System.out.println("Simpson obj created");
    }

    public double Integrate() {
        double dx = (xmax - xmin)/numberOfPoints;
        double sum1 = 0.0;
        double sum2 = 0.0;
        double x = xmin + dx;

        Expression e = new ExpressionBuilder(function).variable("x").build();

        for(int i = 1; i < numberOfPoints; i++){
            if(i%2 != 0){
                e.setVariable("x", x);
                sum1 += e.evaluate();
            } else{
                e.setVariable("x", x);
                sum2 += e.evaluate();
            }
            x += dx;
        }

        //System.out.println(sum1 + " " + sum2);
        e.setVariable("x", xmin);
        A = e.evaluate() + 2.0*sum2 + 4.0*sum1;
        e.setVariable("x", xmax);
        A += e.evaluate();
        A *= dx/3.0;

        System.out.println("Simpson result: " + A);
        return A;
    }
}
