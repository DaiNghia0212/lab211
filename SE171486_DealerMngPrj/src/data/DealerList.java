package data;

import java.util.List;
import java.util.ArrayList;
import tools.MyTool;
import mng.LogIn;

public class DealerList extends ArrayList<Dealer> {

    LogIn loginObj = null;
    private static final String PHONEPATTERN = "\\d{9}|\\d{11}";
    private String dataFile = "";
    boolean changed = false;

    public DealerList(LogIn loginObj) {
        this.loginObj = loginObj;
    }

    private void loadDealerFromFile() {
        List<String> lines = MyTool.readLinesFromFile(dataFile);
        for (String line : lines) {
            Dealer dealer = new Dealer(line);
            this.add(dealer);
        }
    }

    public void initWithFile() {
        Config cR = new Config();
        dataFile = cR.getDealerFile();
        loadDealerFromFile();
    }

    public DealerList getContinuingList() {
        DealerList resultList = new DealerList(loginObj);
        for (Dealer dealer : this) {
            if (dealer.isContinuing() == true) {
                resultList.add(dealer);
            }
        }

        return resultList;
    }

    public DealerList getUnContinuingList() {
        DealerList resultList = new DealerList(loginObj);
        for (Dealer dealer : this) {
            if (dealer.isContinuing() == false) {
                resultList.add(dealer);
            }
        }

        return resultList;
    }

    public int searchDealer(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().equals(ID.toUpperCase())) {
                return i;
            }
        }
        return -1;
    }

    public void searchDealer() {
        String ID = MyTool.readPattern("Input dealer's ID for searching: ", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("NOT FOUND!");
        } else {
            System.out.println(this.get(pos));
        }
    }

    public void addDealer() {
        String ID;
        int pos;
        do {
            ID = MyTool.readPattern("ID of new dealer", Dealer.ID_FORMAT);
            ID = ID.toUpperCase();
            pos = searchDealer(ID);
            if (pos >= 0) {
                System.out.println("ID is duplicated!");
            }
        } while (pos >= 0);
        String name = MyTool.readNonBlank("Name of new dealer").toUpperCase();
        String address = MyTool.readNonBlank("Address of new dealer");
        String phone = MyTool.readPattern("Phone number", Dealer.PHONE_FORMAT);
        boolean continuing = true;
        Dealer dealer = new Dealer(ID, name, address, phone, continuing);
        this.add(dealer);
        System.out.println("New dealer has been added.");
        changed = true;
    }

    public void removeDealer() {
        String ID = MyTool.readPattern("Input dealer's ID for removing", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            this.get(pos).setContinuing(false);
            System.out.println("Removed");
            changed = true;
        }
    }

    public void updateDealer() {
        String ID = MyTool.readPattern("Dealer's ID needs updating: ", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("Dealer " + ID + " not found!");
        } else {
            Dealer dealer = this.get(pos);
            String newName = MyTool.readNonBlank("new name, ENTER for omitting");
            if (!newName.isEmpty()) {
                dealer.setName(newName);
                changed = true;
            }
            String newAddress = MyTool.readNonBlank("new address, ENTER for omitting");
            if (!newAddress.isEmpty()) {
                dealer.setAddress(newAddress);
                changed = true;
            }
            String newPhone = MyTool.readPattern("new phone, ENTER for omitting", PHONEPATTERN);
            if (!newPhone.isEmpty()) {
                dealer.setPhone(newPhone);
                changed = true;
            }
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("Empty List!");
        } else {
            System.out.println(this);
        }
    }

    public void printContinuingDealers() {
        this.getContinuingList().printAllDealers();
    }

    public void printUnContinuingDealers() {
        this.getUnContinuingList().printAllDealers();
    }

    public void writeDealerToFile() {
        if (changed) {
            MyTool.writeFile(dataFile, this);
            changed = false;
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
