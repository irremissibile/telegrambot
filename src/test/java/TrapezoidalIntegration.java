import net.objecthunter.exp4j.*;

public class TrapezoidalIntegration extends Integration {
    public TrapezoidalIntegration(String func, double min, double max) {
        super();
        function = func;
        xmin = min;
        xmax = max;
        numberOfPoints = (int)(xmax - xmin)*100;
        System.out.println("Trapezoidal obj created");
    }

    public double Integrate() {
        double dx = (xmax - xmin)/numberOfPoints;
        double sum = 0.0;
        double x = xmin + dx;

        Expression e = new ExpressionBuilder(function).variable("x").build();

        for(int i = 1; i < numberOfPoints; i++){
            try{
                e.setVariable("x", x);
                double s = e.evaluate();
                sum += s;
                x += dx;
            }
            catch(ArithmeticException exception){
                /*e.setVariable("x", 0.00001);
                double s = e.evaluate();
                sum += s;
                x += dx;
                System.out.println("\n\nDivision by zero?\n\n");*/

                x = 0.00 - 1.00/3.00*dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                x = 0.00 + 1.00/3.00*dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                x = dx;
                e.setVariable("x", x);
                sum += e.evaluate()*dx*2/3;

                i++;
                System.out.println("\nDivision by zero? kinda fixed in trapez\n");
            }
        }

        e.setVariable("x", xmin);
        A = e.evaluate() + 2*sum;
        e.setVariable("x", xmax);
        A += e.evaluate();
        A *= dx/2.0;

        System.out.println("Trapezoidal result: " + A);
        return A;
    }
}
