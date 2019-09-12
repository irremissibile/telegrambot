public abstract class Integration {
    protected double A;
    protected double xmin, xmax;
    protected long numberOfPoints;
    protected String function;

    public Integration(String f, double x1, double x2){
        function = f;
        xmin = x1;
        xmax = x2;
    }

    public Integration() {
    }

    public abstract double Integrate();

}
