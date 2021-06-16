
package ad.landscapegenerator.dao;

import ad.landscapegenerator.dto.ColourTheme;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapSpace;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapSpaceDaoFileImpl implements MapSpaceDao {
    
    private final Color LIGHTEST_BLUE = new Color(200,225,255);
    private final Color DARKEST_BLUE = new Color(75,110,160);
    
    private final Color COASTAL_BLUE = new Color(41,64,93);
    
//    private final Color HIGHEST_GROUND = new Color(179,129,25);
//    private final Color MIDDLE_GROUND = new Color(240,234,169);
//    private final Color LOWEST_GROUND = new Color(85,138,79);
    
//    private final Color HIGHEST_GROUND = new Color(77,59,58);
//    private final Color MIDDLE_GROUND = new Color(130,151,78);
//    private final Color LOWEST_GROUND = new Color(61,93,41);
    
    private final Color HIGHEST_GROUND = new Color(77,59,58);
    private final Color UPPER_MIDDLE_GROUND = new Color(135,121,104);
    private final Color LOWER_MIDDLE_GROUND = new Color(130,151,78);
    private final Color LOWEST_GROUND = new Color(61,93,41);
    
    private final Color LIGHTEST_GREEN = new Color(132,194,120);
    private final Color DARKEST_GREEN = new Color(85,140,50);
    private final Color LIGHTEST_BROWN = new Color(200,225,255);
    private final Color DARKEST_BROWN = new Color(75,110,160);
    
    private ConfigDao configDao;
    
    public MapSpaceDaoFileImpl(ConfigDao configDao) {
        this.configDao = configDao;
    }
    
    /**
     * Interprets the point values of a given map to create and shade an output
     * png image.
     * @param map the MapSpace object to interpret the points of.
     * @throws Exception 
     */
    @Override
    public void drawMap(MapSpace map) throws Exception {
        Config config = configDao.getConfig();  // Throws exception
        
        // Defines the dimensions of the image to be drawn
        int rangeX = config.getBlockSize()*config.getMapShape()[0];
        int rangeY = config.getBlockSize()*config.getMapShape()[1];
        
        double[][] points = map.getPoints();
        
        // Creates the buffered image object to draw upon.
        BufferedImage img = new BufferedImage(rangeX, rangeY, BufferedImage.TYPE_INT_RGB);
        Graphics2D gfx = img.createGraphics();
        
        // Iterates over the points on the map.
        for (int y = 0; y < rangeY; y++) {
            for (int x = 0; x < rangeX; x++) {
                
                double val = points[x][y];
                int colourInt = 0;
                
                // Checks for a crossing of sea level within a points neighbourhood
                // Accounts for points on the map border using min/max
                if (checkCoast(points[x][Math.max(0,y-1)], points[Math.min(x+1,rangeX-1)][y], points[x][Math.min(y+1,rangeY-1)], points[Math.max(x-1,0)][y])) {
                    colourInt = config.getTheme().coast.getRGB();
                }
                
                // Deals with shading for values below sea level
                else if (255.0*val <= config.getSeaLevel()) {
                    colourInt = shadeSea(val);
                }
                
                // Deals with shading for values above sea level
                else {
                    colourInt = shadeLand(val);
                }

                img.setRGB(x, y, colourInt);

            }
        }
        
        File file = new File(config.getName() + ".png");
        try {
            ImageIO.write(img, "png", file);
            
        }
        catch (IOException e) {
            // *** IMPLEMENT CONFIG FILE PERSISTENCE EXCEPTION ***
            throw new Exception("Could not read config", e);
        }
        
    }
    
    /**
     * Returns the int representation of the shaded colour for a point value 
     * below the config defined sea level.
     * @param val the value at a point.
     * @return the int representation of an RGB colour.
     * @throws Exception 
     */
    private int shadeSea(double val) throws Exception {
        Config config = configDao.getConfig();  // Throws exception
        ColourTheme theme = config.getTheme();
        
        // Finds the proportional distance of the value between 0 and sea level 
        double relativeDepth = val/(config.getSeaLevel()/255.0);
        
        return new Color(
                (int) Math.floor(theme.shallowWater.getRed()*relativeDepth + theme.deepWater.getRed()*(1-relativeDepth)),
                (int) Math.floor(theme.shallowWater.getGreen()*relativeDepth + theme.deepWater.getGreen()*(1-relativeDepth)),
                (int) Math.floor(theme.shallowWater.getBlue()*relativeDepth + theme.deepWater.getBlue()*(1-relativeDepth))
        ).getRGB();
    }
    
    /**
     * Returns the int representation of the shaded colour for a point value 
     * above the config defined sea level.
     * @param val the value at a point.
     * @return the int representation of an RGB colour.
     * @throws Exception 
     */
    private int shadeLand(double val) throws Exception {
        Config config = configDao.getConfig();  // Throws exception
        ColourTheme theme = config.getTheme();
        
        // Finds the proportional distance of the value between sea level and 1
        double relativeHeight = (val - (config.getSeaLevel()/255.0))/(1.0 - (config.getSeaLevel()/255.0));
        
        return new Color(
                interpolateCubic46(theme.lowGround.getRed(), theme.lowerMidGround.getRed(), theme.upperMidGround.getRed(), theme.highGround.getRed(), relativeHeight),
                interpolateCubic46(theme.lowGround.getGreen(), theme.lowerMidGround.getGreen(), theme.upperMidGround.getGreen(), theme.highGround.getGreen(), relativeHeight),
                interpolateCubic46(theme.lowGround.getBlue(), theme.lowerMidGround.getBlue(), theme.upperMidGround.getBlue(), theme.highGround.getBlue(), relativeHeight)
        ).getRGB();
    }
    
    private boolean checkCoast(double north, double east, double south, double west) throws Exception {
        Config config = configDao.getConfig();
        double scaledSeaLevel = config.getSeaLevel()/255.0;
        
        // Returns true if opposite neighbours have different signs once scaled 
        // to be negative if less than sea level.
        if ((north-scaledSeaLevel)*(south-scaledSeaLevel) < 0 || (east-scaledSeaLevel)*(west-scaledSeaLevel) < 0) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * Quadratic interpolation between the points (0,a), (0.5,b) and (1,c) using
     * Lagrange's interpolating polynomial.
     * @param a the y value of the point with x=0
     * @param b the y value of the point with x=0.5
     * @param c the y value of the point with x=1
     * @param x the x value in [0,1] for which the point on the polynomial is found
     * @return the y value of the given point on the interpolating polynomial
     */
    private int interpolateQuadratic(int a, int b, int c, double x) {
        return (int) Math.floor(
            2*a*(x-0.5)*(x-1) - 4*b*x*(x-1) + 2*c*x*(x-0.5)
        );
    }
    
    /**
     * Cubic interpolation between the points (0,a), (0.4,b), (0.6,c) and (1,d) 
     * using Lagrange's interpolating polynomial.
     * @param a the y value of the point with x=0
     * @param b the y value of the point with x=0.4
     * @param c the y value of the point with x=0.6
     * @param d the y value of the point with x=1
     * @param x the x value in [0,1] for which the point on the polynomial is found
     * @return the y value of the given point on the interpolating polynomial
     */
    private int interpolateCubic46(int a, int b, int c, int d, double x) {
        return (int) Math.floor(
            -25.0*a*(x-0.4)*(x-0.6)*(x-1.0)/6.0
            +125.0*b*x*(x-0.6)*(x-1.0)/6.0
            -125.0*c*x*(x-0.4)*(x-1.0)/6.0
            +25.0*d*x*(x-0.4)*(x-0.6)/6.0
        );
    }
    
}
