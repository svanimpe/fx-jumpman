package world;

import java.io.Serializable;

public class Launch implements Serializable {
    
    private static final long serialVersionUID = 1;
    
    private final double x;
    private final double y;
    private final double velocity;
    
    public Launch(double x, double y, double velocity) {
        this.x = x >= 100 ? x : 100;
        this.y = y > 0 ? y : 0;
        this.velocity = velocity <= 0 ? velocity : -velocity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }
}
