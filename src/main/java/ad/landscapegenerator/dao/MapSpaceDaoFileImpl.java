
package ad.landscapegenerator.dao;

import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapSpace;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapSpaceDaoFileImpl implements MapSpaceDao {
    
    ConfigDao configDao;
    
    public MapSpaceDaoFileImpl(ConfigDao configDao) {
        this.configDao = configDao;
    }

    @Override
    public void drawMap(MapSpace map) throws Exception {
        Config config = configDao.getConfig();  // Throws exception
        
        // Defines the dimensions of the image to be drawn
        int rangeX = config.getBlockSize()*config.getMapShape()[0];
        int rangeY = config.getBlockSize()*config.getMapShape()[1];
        
        // Creates the buffered image object to draw upon.
        BufferedImage img = new BufferedImage(rangeX, rangeY, BufferedImage.TYPE_INT_RGB);
        Graphics2D gfx = img.createGraphics();
        
        // Iterates through the points on the map.
        for (int y = 0; y < rangeY; y++) {
            for (int x = 0; x < rangeX; x++) {
                
                // Assigns the colour to be drawn using the value of the point scaled to [0,255).
                int val = (int) Math.floor(255.0*map.getPoints()[x][y]);
                if (val <= config.getSeaLevel()) {
                    val = 0;
                }
                img.setRGB(x, y, new Color(val,val,val).getRGB());

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
    
}
