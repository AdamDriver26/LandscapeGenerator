
package ad.landscapegenerator.service;

/**
 * Utility package containing primarily mathematical helper functions in support 
 * of the service layer logic. Default/package-private accessibility and static
 * methods only.
 */
class ServiceUtility {
    
    /**
     * Generates a random vector in [-1,1)x[-1,1) then normalises it to lie on
     * the unit circle.
     * @return a 2-dimensional unit vector in a random direction.
     */
    static double[] generateRandUnitVector() {
        double x = 2.0*Math.random() - 1.0;
        double y = 2.0*Math.random() - 1.0;
        double length = Math.sqrt(x*x + y*y);
        return new double[] {x/length, y/length};
    }
    
    static double[] generateClockVector(int hour) {
        double x = -Math.cos(Math.PI*(3*hour+6)/12.0);
        double y = Math.sin(Math.PI*(3*hour+6)/12.0);
        return new double[] {x, y};
    }
    
    /**
     * Computes the dot product of two vectors.
     * @param u the first double[], representing a 2-dimensional vector.
     * @param v the second double[], representing a 2-dimensional vector.
     * @return the dot product of vectors u and v.
     */
    static double dotProduct(double[] u, double[] v) {
        return u[0]*v[0] + u[1]*v[1];      
    }
    
    /**
     * Ease curve using the function 3x^2 - 2x^3 (gradual). Domain is
     * constricted to the interval [0,1].
     * @param t the value to parse into the ease curve.
     * @return the value of the ease curve at point t.
     */
    static double easeCubic(double t) {
        if (t <= 0.0) {
            return 0.0;
        }
        else if (t >= 1.0) {
            return 1.0;
        }
        return 3.0*Math.pow(t, 2.0) - 2.0*Math.pow(t, 3.0);
    }
    
    /**
     * Ease curve using the function 6x^5 - 15x^4 + 10x^3 (steep). Domain is 
     * constricted to the interval [0,1]. 
     * @param t the value to parse into the ease curve.
     * @return the value of the ease curve at point t.
     */
    static double easeQuintic(double t) {
        if (t <= 0.0) {
            return 0.0;
        }
        else if (t >= 1.0) {
            return 1.0;
        }
        return 6.0*Math.pow(t, 5.0) - 15.0*Math.pow(t, 4.0) + 10.0*Math.pow(t, 3.0);
    }
    
    static double disruptCos(double t, double d) {
        return -(Math.cos(Math.PI*t/d)-1.0)/2.0;
    }
    
    /**
     * Linearly interpolates distance t between the starting point a and
     * terminating point b.
     * @param a the starting point.
     * @param b the terminating point.
     * @param t the proportion of distance (in range [0,1]) between a and b.
     * @return  
     */
    static double interpolate(double a, double b, double t) {
        return t*(b - a) + a;
    }
    
}
