
package ad.landscapegenerator.controller;

import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dao.MapSpaceDao;
import ad.landscapegenerator.dto.MapSpace;
import ad.landscapegenerator.service.ServiceLayer;
import ad.landscapegenerator.view.UserIO;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {

    ConfigDao configDao;
    MapSpaceDao mapSpaceDao;
    ServiceLayer service;
    UserIO io;
    
    public Controller(ConfigDao configDao, MapSpaceDao mapSpaceDao, ServiceLayer service, UserIO io) {
        this.configDao = configDao;
        this.mapSpaceDao = mapSpaceDao;
        this.service = service;        
        this.io = io;
    }
    
    public void run() {
        
        boolean exitProcess = false;
        
        while (!exitProcess) {
            
            int choice;
            
            choice = io.promptInt("--- Menu ---\nWould you like to:\n1 - Generate a landscape\n2 - Update config settings\n3 - Reset config settings to defaults\n4 - Exit", 1, 4);
            
            try {
                switch (choice) {
                    case 1 -> processGenerateLandscape();
                    case 2 -> processUpdateConfig();
                    case 3 -> processResetConfig();
                    case 4 -> exitProcess = true;                        
                } 
            }
            catch (Exception e) {
                io.print("Exception: " + e.getMessage());
            }
            
                  
        }
        
        io.print("EXIT MESSAGE");
    }
    
    
    private void processGenerateLandscape() throws Exception {
        // view - banner
        
        // Creates the layers with decreasing block size and proportionally increasing map shape dimesnsions
        // Merges the layers to create the landscape map space (here blocks and corners are null)
        
        // view - "Generating the *MapStyle.description*
        io.print("Generating base layer...");
        MapSpace baseLayer = service.createBaseLayer();
        io.print("Done\n");
        
        // view - "Generating noise..."
        io.print("Generating noise layers...");
        List<MapSpace> noiseLayers = service.createNoiseLayers();
        io.print("Done\n");
        
        // view - "Merging layers..."
        io.print("Merging layers...");
        // Concats baseLayer with the noiseLayers list
        List<MapSpace> layers = Stream.concat(Stream.of(baseLayer), noiseLayers.stream())
                                      .collect(Collectors.toList());
        MapSpace landscape = service.mergeLayers(layers);
        io.print("Done\n");
        
        // view - map creation announcement, give seed for recreation?
        
        mapSpaceDao.drawMap(landscape);
        
        // view - success banner
    }
    
    private void processUpdateConfig() throws Exception {
        io.print("NO IMPLEMENTATION");
    }
    
    private void processResetConfig() throws Exception {
        io.print("NO IMPLEMENTATION");
    }
    
}
