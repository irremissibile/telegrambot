import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class RectangularIntegration extends Integration {
    public RectangularIntegration(String func, double min, double max) {
        super();
        function = func;
        xmin = min;
        xmax = max;
        numberOfPoints = (int)(xmax - xmin)*100;
        System.out.println("Rectangular obj created");
    }

    public double Integrate() {
        double dx = (xmax - xmin)/numberOfPoints;
        double sum = 0.0;

        Expression e = new ExpressionBuilder(function).variable("x").build();

        for(int i = 1; i < numberOfPoints + 1; i++){
            try{
                double x = xmin + (i - 1)*dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx;
            }
            catch(ArithmeticException exception){
                double x = 0.00 - 1.00/3.00*dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                x = 0.00 + 1.00/3.00*dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                x = dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                i++;
                System.out.println("\nDivision by zero? kinda fixed in rectangular\n");
            }

        }

        A = sum;
        System.out.println("Rectangular result: " + A);
        return A;
    }
}
