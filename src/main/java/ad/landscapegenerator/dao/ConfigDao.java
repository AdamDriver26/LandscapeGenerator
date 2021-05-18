
package ad.landscapegenerator.dao;

import ad.landscapegenerator.dto.Config;

public interface ConfigDao {
    
    public Config getConfig() throws Exception;
    
    public void setConfig(Config config) throws Exception;
    
}
