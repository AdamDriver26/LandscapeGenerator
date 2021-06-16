package ad.landscapegenerator.dto;

import java.awt.Color;

public enum ColourTheme {

    // ColourTheme fields
    WARM(
            new Color(200, 225, 255),
            new Color(75, 110, 160),
            new Color(41, 64, 93),
            new Color(77, 59, 58),
            new Color(135, 121, 104),
            new Color(130, 151, 78),
            new Color(61, 93, 41)
    ),
    COLD(
            new Color(87, 139, 160),
            new Color(0, 46, 75),
            new Color(0, 0, 0),
            new Color(198, 214, 222),
            new Color(149, 158, 148),
            new Color(110, 156, 94),
            new Color(64, 101, 70)
    ),
    GREYSCALE(
            new Color(90, 90, 90),
            new Color(60, 60, 60),
            new Color(30, 30, 30),
            new Color(225, 225, 225),
            new Color(200, 200, 200),
            new Color(160, 160, 160),
            new Color(120, 120, 120)
    );
    
    public final Color shallowWater;
    public final Color deepWater;
    public final Color coast;
    public final Color highGround;
    public final Color upperMidGround;
    public final Color lowerMidGround;
    public final Color lowGround;

    private ColourTheme(
            Color shallowWater,
            Color deepWater,
            Color coast,
            Color highGround,
            Color upperMidGround,
            Color lowerMidGround,
            Color lowGround) {
        this.shallowWater = shallowWater;
        this.deepWater = deepWater;
        this.coast = coast;
        this.highGround = highGround;
        this.upperMidGround = upperMidGround;
        this.lowerMidGround = lowerMidGround;
        this.lowGround = lowGround;
    }

}
