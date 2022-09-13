package mng;

import java.util.ArrayList;
import tools.MyTool;

public class Menu extends ArrayList<String> {

    public Menu() {
        super();
    }

    public Menu(String[] items) {
        super();
        for (String item : items) {
            this.add(item);
        }
    }
    
    public int getChoice(String title) {
        System.out.println(title);
        for (int i = 0; i < this.size(); i++) {
            System.out.format("\t%d - %s\n", i + 1, this.get(i));
        }
        System.out.println("\tOther for quit.");
        int choice = Integer.parseInt(MyTool.readNonBlank("\tChoose [1  .  .  " + this.size() + "]"));
        return choice;
    }
}
