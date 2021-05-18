
package ad.landscapegenerator.dto;

public class Config {
    
    private int blockSize;
    private int[] mapShape;
    private int seaLevel;
    private MapStyle style;
    private String name;
    
    public Config(int blockSize, int[] mapShape, int seaLevel, MapStyle style, String name) {
        
        this.blockSize = blockSize;
        this.mapShape = mapShape;
        this.seaLevel = seaLevel;
        this.style = style;
        this.name = name;
        
    }

    public int getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int[] getMapShape() {
        return mapShape;
    }

    public void setMapShape(int[] mapShape) {
        this.mapShape = mapShape;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public MapStyle getStyle() {
        return style;
    }

    public void setStyle(MapStyle style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Config{" + "blockSize=" + blockSize + ", blockCount=" + mapShape + ", seaLevel=" + seaLevel + ", style=" + style + ", name=" + name + '}';
    }
    
    
    
}
