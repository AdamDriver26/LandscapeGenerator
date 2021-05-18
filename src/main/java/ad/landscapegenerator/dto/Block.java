
package ad.landscapegenerator.dto;

import java.util.Arrays;
import java.util.Objects;

public class Block {
    
    // An integer array representing the grid coordinates of the block within the map space, working as a unique identifier.
    private final Coord blockCoord;
    
    // Unit vectors at each of the block's four corners, in the order NW, NE, SE, SW.
    private double[][] cornerVectors;
    
    // The points within the boundaries of the block.
    private double[][] points;
    
    public Block(Coord blockCoord) {
        this.blockCoord = blockCoord;
    }

    public Block(Coord blockCoord, double[][] cornerVectors, int blockSize) {
        this.blockCoord = blockCoord;
        this.cornerVectors = cornerVectors;
        this.points = new double[blockSize][blockSize];
    }
    
    public Coord getBlockCoord() {
        return blockCoord;
    }

    public double[][] getCornerVectors() {
        return cornerVectors;
    }

    public void setCornerVectors(double[][] cornerVectors) {
        this.cornerVectors = cornerVectors;
    }

    public double[][] getPoints() {
        return points;
    }

    public void setPoints(double[][] points) {
        this.points = points;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.blockCoord);
        hash = 67 * hash + Arrays.deepHashCode(this.cornerVectors);
        hash = 67 * hash + Arrays.deepHashCode(this.points);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Block other = (Block) obj;
        if (!Objects.equals(this.blockCoord, other.blockCoord)) {
            return false;
        }
        if (!Arrays.deepEquals(this.cornerVectors, other.cornerVectors)) {
            return false;
        }
        if (!Arrays.deepEquals(this.points, other.points)) {
            return false;
        }
        return true;
    }

    
}
