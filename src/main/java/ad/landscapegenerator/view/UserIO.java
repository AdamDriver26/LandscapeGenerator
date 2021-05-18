package ad.landscapegenerator.view;

import java.util.ArrayList;

public interface UserIO {
    
    void print(String msg);
    
    int promptInt(String msg);
    
    int promptInt(String msg, int min, int max);
    
    String promptString(String msg);
    
    ArrayList<String> promptStringList(String msg);
    
}
