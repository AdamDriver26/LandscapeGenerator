
package ad.landscapegenerator.service;

import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dto.Block;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.Coord;
import ad.landscapegenerator.dto.MapSpace;
import java.util.ArrayList;
import java.util.List;

public class MapGenerationService {
    
    ConfigDao configDao;
    
    public MapGenerationService(ConfigDao configDao) {
        this.configDao = configDao;
    }
    
    public void knitBlocks(MapSpace map) throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        double[][] mapPoints = map.getPoints();        
        
        // i,j describe the block in the map space.
        for (int j=0; j<config.getMapShape()[1]; j++) {
            for (int i=0; i<config.getMapShape()[0]; i++) {
                
                double[][] blockPoints = map.getBlocks().get(i+","+j).getPoints();

                // x,y describe the point on the block
                for (int y = 0; y < config.getBlockSize(); y++) {
                    for (int x = 0; x < config.getBlockSize(); x++) {
                        
                        // X,Y describe the point on the map
                        int X = i*config.getBlockSize() + x;    // possibly add i
                        int Y = j*config.getBlockSize() + y;    // possibly add j - if seam
                        
                        mapPoints[X][Y] = blockPoints[x][y];
                        
                    }
                }
                
            } 
        }
    }
    
    public void generateMapBlocks(MapSpace map) throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        // The total number of corners to the blocks within the map.
        int cornerCount = (config.getMapShape()[0] + 1)*(config.getMapShape()[1] + 1);
        double[][] unassignedVectors = new double[cornerCount][2];
                
        
        // Generates a random unit vector for each corner point needed.
        for (int n=0; n<cornerCount; n++) {
            unassignedVectors[n] = ServiceUtility.generateRandUnitVector();
            //unassignedVectors[n] = generateClockVector(n);
        }
        
        // Iteratates over each block required, as defined in the mapShape dimensions
        for (int j=0; j<config.getMapShape()[1]; j++) {
            for (int i=0; i<config.getMapShape()[0]; i++) {
                
                Coord blockCoord = new Coord(i,j);                
                
                // Finds the appropriate corner vectors for the block (i,j) from the unassigned vectors array.
                double[][] cornerVectors = new double[][] {
                    unassignedVectors[i + j*(config.getMapShape()[0] + 1)],             // NW vector
                    unassignedVectors[i + j*(config.getMapShape()[0] + 1) + 1],         // NE vector
                    unassignedVectors[i + (j + 1)*(config.getMapShape()[0] + 1) + 1],   // SE vector
                    unassignedVectors[i + (j + 1)*(config.getMapShape()[0] + 1)]        // SW vector              
                };                
                
                map.getBlocks().put(blockCoord.toString(), new Block(blockCoord, cornerVectors, config.getBlockSize()));
            }
        }
    }
    
    public void generatePerlinNoise(MapSpace map) throws Exception {
        for (Block block: map.getBlocks().values()) {
            generatePerlinNoise(block);
        }
    }
    
    /**
     * Assigns values in the range [0,1) to each point of the block by taking
     * the dot product of vectors depending on the corner vectors of the block 
     * to produce Perlin noise.
     * @param block the Block for which point values are calculated using the 
     * Block's corner vectors.
     * @throws Exception ********
     */
    private void generatePerlinNoise(Block block) throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        for (int y = 0; y < config.getBlockSize(); y++) {
            for (int x = 0; x < config.getBlockSize(); x++) {
                
                // x,y as a proportion of the total block size.
                double xScaled = x/(double) config.getBlockSize();
                double yScaled = y/(double) config.getBlockSize();
                
                // The maximum (relative) distance from one corner of the block to the opposite.
                // i.e. the hypotenuse of a right-angled triangle with both sides of length 1.
                double maxDist = Math.sqrt(2.0);
                
                // Gets the relative distance of point (x,y) from each corner of the block, scaled to lie in [0,1]
                // by dividing by maxDist=sqrt(2).
                double[] relDistNW = new double[] {xScaled/maxDist, yScaled/maxDist};
                double[] relDistNE = new double[] {(xScaled - 1.0)/maxDist, yScaled/maxDist};
                double[] relDistSE = new double[] {(xScaled - 1.0)/maxDist, (yScaled - 1.0)/maxDist};
                double[] relDistSW = new double[] {xScaled/maxDist, (yScaled - 1.0)/maxDist};
                
                // Calculates the impact, defined to be the dot product of the unit vector with the displacement, for each corner of the block
                double impactNW = ServiceUtility.dotProduct(block.getCornerVectors()[0], relDistNW);
                double impactNE = ServiceUtility.dotProduct(block.getCornerVectors()[1], relDistNE);
                double impactSE = ServiceUtility.dotProduct(block.getCornerVectors()[2], relDistSE);
                double impactSW = ServiceUtility.dotProduct(block.getCornerVectors()[3], relDistSW);
                
                // Shifts values towards integral points.
                double xEased = ServiceUtility.easeQuintic(xScaled);
                double yEased = ServiceUtility.easeQuintic(yScaled);
                
                // Takes average of impact from each corner using linear interpolation first horizontally, then vertically
                double northAverage = ServiceUtility.interpolate(impactNW, impactNE, xEased);   // S_x
                double southAverage = ServiceUtility.interpolate(impactSW, impactSE, xEased);   // S_y
                double combinedAverage = ServiceUtility.interpolate(northAverage, southAverage, yEased);
                
                // Assigns value after scaling [-1,1)->[0,1) then easing to exaggerate heights.
                block.getPoints()[x][y] = ServiceUtility.easeQuintic((combinedAverage + 1.0)/2.0);
            }
        }
    }
    
    public MapSpace createBaseLayer() throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        return config.getMapStyle().createBaseLayer(config);
    }
    
    public List<MapSpace> createNoiseLayers() throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        // n the number of layers defined in the config, with n-1 noiseLayers of
        // noise. Used as a factor in calculating the blockSize of subsequent 
        // noiseLayers.
        int n = config.getLayerCount();
        List<MapSpace> noiseLayers = new ArrayList<>();
        
        // Iterates from k=1 to allow the 0th layer be defined by the map style.
        for (int k = 1; k < n; k++) {
            int scale = (int) Math.pow(2, k);
            int layerBlockSize = config.getBlockSize()/scale;
            int[] layerMapShape = new int[] {config.getMapShape()[0]*scale, config.getMapShape()[1]*scale};
            
            Config scaledConfig = new Config(layerBlockSize, layerMapShape, config.getLayerCount(), config.getSeaLevel(), config.getMapStyle(), config.getTheme(), config.getName());
            
            // Creates layer map object
            MapSpace layer = new MapSpace(scaledConfig);
            
            // Temporarily updates the config file
            configDao.setConfig(scaledConfig);
            
            // Generates the perlin noise for the layer using the scaled config paramaters
            generateMapBlocks(layer);
            generatePerlinNoise(layer);
            knitBlocks(layer);
            
            noiseLayers.add(layer);            
        }
        configDao.setConfig(config);
        return noiseLayers;
    }
    
    public MapSpace mergeLayers(List<MapSpace> layers) throws Exception {
        Config config = configDao.getConfig();  // throws exception here
        
        // n the total number of noiseLayers, used as a factor in calculating the impact of each layer
        int n = layers.size();
        MapSpace mergedMap = new MapSpace(config);
        double[][] mergedPoints = mergedMap.getPoints();
        
        for (int y = 0; y < config.getBlockSize()*config.getMapShape()[1]; y++) {
            for (int x = 0; x < config.getBlockSize()*config.getMapShape()[0]; x++) {
                
                // k describes the layer
                for (int k = 0; k < n; k++) {
                    double[][] layerPoints = layers.get(k).getPoints();
                    // Uses normalised weighting calculated with 2(n-k) / n(n-1)
                    mergedPoints[x][y] += 2.0*(n-k)*layerPoints[x][y]/(n*(n+1));
                }
                
                mergedPoints[x][y] = ServiceUtility.easeQuintic(mergedPoints[x][y]);
            }
        }
        
        
        return mergedMap;
    }
}
