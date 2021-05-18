
package ad.landscapegenerator.dto;

import java.util.HashMap;

public class MapSpace {
    
    // The collection of blocks subdividing the map space.
    HashMap<String, Block> blocks;
    
    // The points within the boundaries of the map space.
    double[][] points;

    public MapSpace(Config config) {
        this.blocks = new HashMap<>();
        this.points = new double[config.getBlockSize()*config.getMapShape()[0]][config.getBlockSize()*config.getMapShape()[1]];
    }

    public HashMap<String, Block> getBlocks() {
        return blocks;
    }

    public double[][] getPoints() {
        return points;
    }

    public void setPoints(double[][] points) {
        this.points = points;
    }
    
    
    
}
