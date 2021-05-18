
package ad.landscapegenerator.view;

import java.util.ArrayList;
import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO {
    
    private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public int promptInt(String msg) {
        boolean valid = false;
        int input = 0;
        this.print(msg);
        while (!valid) {
            try {
                input = Integer.parseInt(console.nextLine());
                valid = true;
            }
            catch (Exception e) {
                this.print("Invalid input, try again.");
            }
        }
        return input;  
    }

    @Override
    public int promptInt(String msg, int min, int max) {
        int input = 0;
        do {
            input = this.promptInt(msg);
        } while (input < min || input > max);
        return input;
    }

    @Override
    public String promptString(String msg) {
        this.print(msg);
        return console.nextLine();
    }

    @Override
    public ArrayList<String> promptStringList(String msg) {
        ArrayList<String> inputList = new ArrayList<>();
        boolean exitLoop = false;
        this.print(msg);
        
        while (!exitLoop) {
            String input = this.promptString("Enter an input, or press enter to finish.");
            if (input.equals("")) {
                exitLoop = true;
            }
            else {
                inputList.add(input);
            }
        }
        return inputList;
    }
    
    
    
}
