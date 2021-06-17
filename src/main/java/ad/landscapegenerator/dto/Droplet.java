
package ad.landscapegenerator.dto;

public class Droplet {
    
    // Normalised direction vector
    double[] dir;
    
    // Speed of travelling droplet
    double speed;
    
    // Position of droplet
    Coord pos;
    
    // The amount of water in the droplet
    double waterQuant;
    
    // The amount of sediment held in the droplet
    double sedimentQuant;

    public Droplet(Coord pos) {
        this.speed = 0.0;
        this.pos = pos;
        this.waterQuant = 1.0;
        this.sedimentQuant = 0.0;
    }

    public double[] getDir() {
        return dir;
    }

    public void setDir(double[] dir) {
        this.dir = dir;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Coord getPos() {
        return pos;
    }

    public void setPos(Coord pos) {
        this.pos = pos;
    }

    public double getWaterQuant() {
        return waterQuant;
    }

    public void setWaterQuant(double waterQuant) {
        this.waterQuant = waterQuant;
    }

    public double getSedimentQuant() {
        return sedimentQuant;
    }

    public void setSedimentQuant(double sedimentQuant) {
        this.sedimentQuant = sedimentQuant;
    }
    
}
