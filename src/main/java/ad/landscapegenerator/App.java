
package ad.landscapegenerator;

import ad.landscapegenerator.controller.Controller;
import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dao.ConfigDaoFileImpl;
import ad.landscapegenerator.dao.MapSpaceDao;
import ad.landscapegenerator.dao.MapSpaceDaoFileImpl;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapSpace;
import ad.landscapegenerator.service.ServiceLayer;
import ad.landscapegenerator.view.UserIO;
import ad.landscapegenerator.view.UserIOConsoleImpl;
import java.util.List;

public class App {
    
    public static void main(String[] args) throws Exception{
        
        
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
//        Config config = configDao.getConfig();
//        List<MapSpace> layers  = service.createNoiseLayers();
//        for (int i = 0; i < configDao.getConfig().getLayerCount(); i++) {
//            String layerName = "layer" + i;
//            Config layerConfig = new Config(config.getBlockSize(), config.getMapShape(), config.getLayerCount(), config.getSeaLevel(), config.getStyle(), layerName);
//            configDao.setConfig(layerConfig);
//            mapSpaceDao.drawMap(layers.get(i));
//        }

        controller.run();

    }
    
}
