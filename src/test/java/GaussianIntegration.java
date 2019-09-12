import net.objecthunter.exp4j.*;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class GaussianIntegration extends Integration {
    static private int N;
    //static double[] lroots = new double[N];
    //static double[] weight = new double[N];
    //static double[][] lcoef = new double[N + 1][N + 1];
    static double[] lroots;
    static double[] weight;
    static double[][] lcoef;

    public GaussianIntegration(String func, double min, double max) {
        super();
        function = func;
        xmin = min;
        xmax = max;
        numberOfPoints = (int)(xmax - xmin)*100;
        //N = (int)numberOfPoints/100;
        N = 5;
        lroots = new double[N];
        weight = new double[N];
        lcoef = new double[N + 1][N + 1];

        legeCoef();
        legeRoots();
        System.out.println("Gaussian obj created");
    }

    static void legeCoef() {
        lcoef[0][0] = lcoef[1][1] = 1;

        for (int n = 2; n <= N; n++) {

            lcoef[n][0] = -(n - 1) * lcoef[n - 2][0] / n;

            for (int i = 1; i <= n; i++) {
                lcoef[n][i] = ((2 * n - 1) * lcoef[n - 1][i - 1]
                        - (n - 1) * lcoef[n - 2][i]) / n;
            }
        }
    }

    static double legeEval(int n, double x) {
        double s = lcoef[n][n];
        for (int i = n; i > 0; i--)
            s = s * x + lcoef[n][i - 1];
        return s;
    }

    static double legeDiff(int n, double x) {
        return n * (x * legeEval(n, x) - legeEval(n - 1, x)) / (x * x - 1);
    }

    static void legeRoots() {
        double x, x1;
        for (int i = 1; i <= N; i++) {
            x = cos(PI * (i - 0.25) / (N + 0.5));
            do {
                x1 = x;
                x -= legeEval(N, x) / legeDiff(N, x);
            } while (x != x1);

            lroots[i - 1] = x;

            x1 = legeDiff(N, x);
            weight[i - 1] = 2 / ((1 - x * x) * x1 * x1);
        }
    }

    /*static double legeInte(Function<Double, Double> f, double a, double b) {
        double c1 = (b - a) / 2, c2 = (b + a) / 2, sum = 0;
        for (int i = 0; i < N; i++)
            sum += weight[i] * f.apply(c1 * lroots[i] + c2);
        return c1 * sum;
    }*/

    public double Integrate() {
        double c1 = (xmax - xmin) / 2;
        double c2 = (xmax + xmin) / 2;
        double sum = 0;

        Expression e = new ExpressionBuilder(function).variable("x").build();

        double x;
        for(int i = 0; i < N; i++){
            try {
                x = c1 * lroots[i] + c2;
                e.setVariable("x", x);
                sum += weight[i] * e.evaluate();
            }
            catch(ArithmeticException exception){
                e.setVariable("x", 0.0001);
                sum += weight[i] * e.evaluate();
                System.out.println("Division by zero?");
            }
        }

        A = c1*sum;
        System.out.println("Gaussian result: " + A);
        return A;
    }
}
