package util;

public class Vector2D {
    
    public static final Vector2D ZERO = new Vector2D(0, 0);
    
    private final double x;
    private final double y;
    
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }
        
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }
    
    public Vector2D normalize() {
        return times(1 / getLength());
    }
    
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }
    
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }
    
    public Vector2D times(double s) {
        return new Vector2D(s * x, s * y);
    }
    
    public double dotProduct(Vector2D v) {
        return x * v.getX() + y * v.getY();
    }
    
    public Vector2D flip() {
        return new Vector2D(-x, -y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector2D other = (Vector2D) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "{x=" + x + ", y=" + y + '}';
    }  
}
