import net.objecthunter.exp4j.*;

public class ThreeThirdsIntegration extends Integration {
    public ThreeThirdsIntegration(String func, double min, double max) {
        super();
        function = func;
        xmin = min;
        xmax = max;
        numberOfPoints = (int)(xmax - xmin)*100;
        System.out.println("ThreeThirds obj created");
    }

    public double Integrate() {
        double dx = (xmax - xmin)/numberOfPoints;
        double sum = 0.0;

        Expression e = new ExpressionBuilder(function).variable("x").build();

        e.setVariable("x", xmin);
        sum += e.evaluate();
        e.setVariable("x", xmax);
        sum += e.evaluate();

        double x;
        for(int i = 1; i < numberOfPoints + 1; i++){
            try{
                x = xmin + i*dx;
                e.setVariable("x", x);

                if(i % 3 == 0){
                    sum += 2*e.evaluate();
                } else {
                    sum += 3*e.evaluate();
                }
            }
            catch(ArithmeticException exception){
                e.setVariable("x", 0.0001);
                if(i % 3 == 0){
                    sum += 2*e.evaluate();
                } else {
                    sum += 3*e.evaluate();
                }
                System.out.println("Division by zero? kinda fixed in 3/8");
            }
        }

        A = (3*dx/8)*sum;
        System.out.println("ThreeThirds result: " + A);
        return A;
    }
}
