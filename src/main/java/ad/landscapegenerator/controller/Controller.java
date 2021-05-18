
package ad.landscapegenerator.controller;

import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dao.MapSpaceDao;
import ad.landscapegenerator.dto.MapSpace;
import ad.landscapegenerator.service.ServiceLayer;
import ad.landscapegenerator.view.UserIO;

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
        MapSpace map = new MapSpace(configDao.getConfig());
        // view - map creation announcement, give seed for recreation?
        service.generateMapBlocks(map);
        // view - process announcement?
        service.generatePerlinNoise(map);
        // view - process announcement?
        service.knitBlocks(map);
        // view - process announcement?
        mapSpaceDao.drawMap(map);
        // view - success banner
    }
    
    private void processUpdateConfig() throws Exception {
        io.print("NO IMPLEMENTATION");
    }
    
    private void processResetConfig() throws Exception {
        io.print("NO IMPLEMENTATION");
    }
    
}