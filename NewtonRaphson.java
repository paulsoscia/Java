public class NewtonRaphson {

    // Function to find the root of
    static double func(double x) {
        //return x * x * x - 2 * x - 5;
        return Math.sin(x);
    }

    // Derivative of the function
    static double derivFunc(double x) {
        //return 3 * x * x - 2;
        return Math.cos(x);
    }

    // Function to find the root
    static void newtonRaphson(double x, double tolerance, int maxIterations) {
        double h = func(x) / derivFunc(x);
        int iterations = 0;

                    System.out.println("func(x)     : " + func(x)) ;
                    System.out.println("derivFunc(x): " + derivFunc(x));
                    System.out.println("Root(h):    " + h);
                    System.out.println("Iterations: " + iterations);
//        System.out.println("Tolerance: " + tolerance);

        while (Math.abs(h) >= tolerance && iterations < maxIterations) {
            h = func(x) / derivFunc(x);
                    System.out.println("func(x)     : " + func(x)) ;
                    System.out.println("derivFunc(x): " + derivFunc(x));
            x = x - h;
            iterations++;
                    System.out.println("Root(h):    " + h);
                    System.out.println("Root(x):    " + x);
                    System.out.println("Iterations: " + iterations);
                    //System.out.println("Tolerance: " + tolerance);
        }

        if (Math.abs(h) < tolerance) {
            System.out.println("Root: " + x);
            System.out.println("Iterations: " + iterations);
            System.out.println("Tolerance: " + tolerance);
        } else {
            System.out.println("Method failed to converge within the maximum number of iterations.");
        }
    }

    public static void main(String[] args) {
        double initialGuess = 2.0;
        double tolerance = 0.0000001;
        int maxIterations = 100;
        newtonRaphson(initialGuess, tolerance, maxIterations);
                    System.out.println("Root(maxIterations):    " + maxIterations);        
    }
}
