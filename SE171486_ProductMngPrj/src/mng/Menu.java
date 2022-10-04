package mng;

import tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;

public class Menu extends ArrayList<String> {
    public Menu(String[] items) {
        super();
        this.addAll(Arrays.asList(items));
    }

    public int getChoice(String title) {
        System.out.println(title);
        for (int i = 0; i < this.size(); i++) {
            System.out.format("\t%d - %s\n", i + 1, this.get(i));
        }
        System.out.println("\tOther for quit.");
        return Integer.parseInt(Tools.readNonBlank("\tChoose [1  .  .  " + this.size() + "]"));
    }
}
