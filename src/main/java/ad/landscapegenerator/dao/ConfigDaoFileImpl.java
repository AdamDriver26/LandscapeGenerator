
package ad.landscapegenerator.dao;

import ad.landscapegenerator.dto.ColourTheme;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapStyle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConfigDaoFileImpl implements ConfigDao {
    
    private static final String CONFIG_FILE = "config.txt";
    private static final String DELIMITER = ": ";
    private static final String[] DESCRIPTORS = {
        "Block size",
        "Map block dimensions",
        "Layer count",
        "Sea level",
        "Map style",
        "Colour Theme",
        "File name"
    };
    
    private Config config;
    
    @Override
    public Config getConfig() throws Exception {
        readConfig();
        return config;
    }
    
    @Override
    public void setConfig(Config config) throws Exception {
        writeConfig(config);
    }

    private void readConfig() throws Exception {
        Scanner scanner;
        
        try {
            scanner = new Scanner( new BufferedReader( new FileReader(CONFIG_FILE) ) );    
        }
        catch (FileNotFoundException e) {
            // *** IMPLEMENT CONFIG FILE PERSISTENCE EXCEPTION ***
            throw new Exception("Could not read config", e);
        }
        
        // Convert each line of the config to the appropriate type.
        int blockSize = Integer.parseInt(scanner.nextLine().split(DELIMITER)[1]);
        
        String[] mapShapeStrings = scanner.nextLine().split(DELIMITER)[1].split(",");
        int[] mapShape = {Integer.parseInt(mapShapeStrings[0]), Integer.parseInt(mapShapeStrings[1])};
        
        int layerCount = Integer.parseInt(scanner.nextLine().split(DELIMITER)[1]);
        
        int seaLevel = Integer.parseInt(scanner.nextLine().split(DELIMITER)[1]);
        
        MapStyle style = MapStyle.valueOf(scanner.nextLine().split(DELIMITER)[1]);
        
        ColourTheme theme = ColourTheme.valueOf(scanner.nextLine().split(DELIMITER)[1]);
        
        String name = scanner.nextLine().split(DELIMITER)[1];
        
        // Close the scanner.
        scanner.close();
        
        config = new Config(blockSize, mapShape, layerCount, seaLevel, style, theme, name);
    }

    private void writeConfig(Config config) throws Exception {
        
        PrintWriter out;

        // Handles the event that the CONFIG_FILE does not exist.
        try {
            out = new PrintWriter(new FileWriter(CONFIG_FILE));
        } 
        catch (IOException e) {
            throw new Exception("Could not write config.", e);
        }
        
        // Writes and flushes the block size.
        out.println(DESCRIPTORS[0] + DELIMITER + config.getBlockSize()); 
        out.flush();
        
        // Writes and flushes the block count.
        out.println(DESCRIPTORS[1] + DELIMITER + config.getMapShape()[0] + "," + config.getMapShape()[1]); 
        out.flush();
        
        // Writes and flushes the layer count.
        out.println(DESCRIPTORS[2] + DELIMITER + config.getLayerCount()); 
        out.flush();
        
        // Writes and flushes the map sea level.
        out.println(DESCRIPTORS[3] + DELIMITER + config.getSeaLevel()); 
        out.flush();
        
        // Writes and flushes the map style.
        out.println(DESCRIPTORS[4] + DELIMITER + config.getStyle().toString()); 
        out.flush();
        
        // Writes and flushes the colour theme.
        out.println(DESCRIPTORS[5] + DELIMITER + config.getTheme().toString()); 
        out.flush();
        
        // Writes and flushes the file name.
        out.println(DESCRIPTORS[6] + DELIMITER + config.getName()); 
        out.flush();
        
        out.close();
    }
    
}
