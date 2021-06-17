
package ad.landscapegenerator.service;

import ad.landscapegenerator.dao.ConfigDaoFileImpl;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapSpace;

public enum MapStyle {
    
    PLAINS {
        @Override
        public MapSpace createBaseLayer(Config config) throws Exception {
            MapSpace baseLayer = new MapSpace(config);
            
            double northWestCorner = Math.random();
            double northEastCorner = Math.random();
            double southEastCorner = Math.random();
            double southWestCorner = Math.random();
            
            int xMax = config.getBlockSize()*config.getMapShape()[0];
            int yMax = config.getBlockSize()*config.getMapShape()[1];
            
            for (int y = 0; y < yMax; y++) {
                for (int x = 0; x < xMax; x++) {
                    double topEdge = ServiceLayer.interpolate(northWestCorner, northEastCorner, (double) x/xMax);
                    double bottomEdge = ServiceLayer.interpolate(southWestCorner, southEastCorner, (double) x/xMax);
                    
                    baseLayer.getPoints()[x][y] = ServiceLayer.interpolate(topEdge, bottomEdge, (double) y/yMax);
                }
            }
            
            return baseLayer;
        }
    },
    MOUNTAIN {
        @Override
        public MapSpace createBaseLayer(Config config) throws Exception {
            MapSpace baseLayer = new MapSpace(config);
            
            return baseLayer;
        }
        
    }, 
    ISLAND {
        @Override
        public MapSpace createBaseLayer(Config config) throws Exception {
            MapSpace baseLayer = new MapSpace(config);
            
            return baseLayer;
        }
    }, 
    ARCHIPELAGO {
        @Override
        public MapSpace createBaseLayer(Config config) throws Exception {
            // *** Fix implementation for inversion of control ***
            ServiceLayer service = new ServiceLayer(new ConfigDaoFileImpl());
            MapSpace baseLayer = new MapSpace(config);
            
            service.generateMapBlocks(baseLayer);
            service.generatePerlinNoise(baseLayer);
            service.knitBlocks(baseLayer);
            
            return baseLayer;
        }
    };
    
    abstract MapSpace createBaseLayer(Config config) throws Exception;
    
}
