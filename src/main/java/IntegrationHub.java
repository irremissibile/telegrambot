public class IntegrationHub {
    private double result1, result2, result3, result4, result5;

    public IntegrationHub(String f, double x1, double x2){
        Integration method1, method2, method3, method4, method5;
        method1 = new RectangularIntegration(f, x1, x2);
        method2 = new TrapezoidalIntegration(f, x1, x2);
        method3 = new SimpsonIntegration(f, x1, x2);
        method4 = new ThreeThirdsIntegration(f, x1, x2);
        method5 = new GaussianIntegration(f, x1, x2);

        result1 = method1.Integrate();
        result2 = method2.Integrate();
        result3 = method3.Integrate();
        result4 = method4.Integrate();
        result5 = method5.Integrate();
    }

    public double getResult1() {
        return result1;
    }

    public double getResult2() {
        return result2;
    }

    public double getResult3() {
        return result3;
    }

    public double getResult4() {
        return result4;
    }

    public double getResult5() {
        return result5;
    }
}
