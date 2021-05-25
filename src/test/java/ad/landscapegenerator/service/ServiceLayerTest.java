
package ad.landscapegenerator.service;

import ad.landscapegenerator.dao.ConfigDao;
import ad.landscapegenerator.dao.ConfigDaoFileImpl;
import ad.landscapegenerator.dto.Block;
import ad.landscapegenerator.dto.Config;
import ad.landscapegenerator.dto.MapSpace;
import ad.landscapegenerator.dto.MapStyle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ServiceLayerTest {
    
    Config config;
    MapSpace map;
    ConfigDao configDao;
    ServiceLayer service;
    
    public ServiceLayerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        config = new Config(25, new int[] {3,4}, 4, 50, MapStyle.PLAINS, "service_test");
        map = new MapSpace(config);
        
        configDao = new ConfigDaoFileImpl();
        service = new ServiceLayer(configDao);
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGenerateMapBlocks() {
        
        try {
            configDao.setConfig(config);
            service.generateMapBlocks(map);
        }
        catch (Exception e) {
            fail("Exception: " + e.getMessage());
        }
        
        for (Block b: map.getBlocks().values()) {
            System.out.println(b.getBlockCoord().i + "," + b.getBlockCoord().j);
            System.out.println(b.getCornerVectors()[0][0]);
            System.out.println(b.getCornerVectors()[1][0]);
            System.out.println(b.getCornerVectors()[2][0]);
            System.out.println(b.getCornerVectors()[3][0]);
        }
        
        
        Block block00 = map.getBlocks().get("0,0");
        Block block11 = map.getBlocks().get("1,1");
        
        Block block12 = map.getBlocks().get("1,2");
        Block block22 = map.getBlocks().get("2,2");
        
        assertArrayEquals(block00.getCornerVectors()[2], block11.getCornerVectors()[0], "The SE vector of block (0,0) should be equal to the NW vector of block (1,1).");
        assertArrayEquals(block12.getCornerVectors()[1], block22.getCornerVectors()[0], "The NE vector of block (1,2) should be equal to the NW vector of block (2,2).");
    }
    
}
