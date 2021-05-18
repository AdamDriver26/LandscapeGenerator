
package ad.landscapegenerator.dto;

public class Coord {
    
    public int i;
    public int j;
    
    public Coord(int i, int j) {
        this.i = i;
        this.j = j;
    }
    
    @Override
    public String toString() {
        return i + "," + j;
    }
    
    public int[] toArray() {
        return new int[] {i,j};
    }
}
