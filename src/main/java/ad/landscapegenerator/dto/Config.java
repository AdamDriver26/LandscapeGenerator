
package ad.landscapegenerator.dto;

public class Config {
    
    private int blockSize;
    private int[] mapShape;
    private int layerCount;
    private int seaLevel;
    private MapStyle style;
    private ColourTheme theme;
    private String name;
    
    public Config(int blockSize, int[] mapShape, int layerCount, int seaLevel, MapStyle style, ColourTheme theme, String name) {
        
        this.blockSize = blockSize;
        this.mapShape = mapShape;
        this.layerCount = layerCount;
        this.seaLevel = seaLevel;
        this.style = style;
        this.theme = theme;
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
    
    public int getLayerCount() {
        return layerCount;
    }

    public void setLayerCount(int layerCount) {
        this.layerCount = layerCount;
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

    public ColourTheme getTheme() {
        return theme;
    }

    public void setTheme(ColourTheme theme) {
        this.theme = theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Config{" + "blockSize=" + blockSize + ", mapShape=" + mapShape + ", layerCount=" + layerCount + ", seaLevel=" + seaLevel + ", style=" + style + ", name=" + name + '}';
    }
    
}
