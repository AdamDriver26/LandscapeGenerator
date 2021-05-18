
package ad.landscapegenerator;

import ad.landscapegenerator.controller.Controller;
import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dao.ConfigDaoFileImpl;
import ad.landscapegenerator.dao.MapSpaceDao;
import ad.landscapegenerator.dao.MapSpaceDaoFileImpl;
import ad.landscapegenerator.service.ServiceLayer;
import ad.landscapegenerator.view.UserIO;
import ad.landscapegenerator.view.UserIOConsoleImpl;

public class App {
    
    public static void main(String[] args) {
        
        
        ConfigDao configDao = new ConfigDaoFileImpl();
        MapSpaceDao mapSpaceDao = new MapSpaceDaoFileImpl(configDao);
        ServiceLayer service = new ServiceLayer(configDao);
        UserIO io = new UserIOConsoleImpl();
        Controller controller = new Controller(configDao, mapSpaceDao, service, io);
        
        
//        int testBlockSize = 10;
//        int[] testMapShape = {4,3};
//        int testSeaLevel = 50;
//        MapStyle testStyle = MapStyle.MOUNTAIN;
//        String testName = "test_name";

        controller.run();

        
    }
    
}
