package data;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
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

    public void searchDealers() {
        String ID = MyTool.readPattern("Input dealer's ID for searching", Dealer.ID_SEARCH_FORMAT);
//        int pos = searchDealer(ID);
        DealerList resultList = new DealerList(loginObj);
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getID().contains(ID)) {
                resultList.add(this.get(i));
            }
        }

        if (resultList.isEmpty()) {
            System.out.println("NOT FOUND!");
        } else {
            resultList.printAllDealers();
        }
    }

    public void addDealer() {
        String ID;
        int pos;
        System.out.print("1 - Type ID of new dealer\n2 (default) - Let ID of new dealer be automatically set\nYour choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {
            case 1:
                do {
                    ID = MyTool.readPattern("ID of new dealer", Dealer.ID_FORMAT);
                    ID = ID.toUpperCase();
                    pos = searchDealer(ID);
                    if (pos >= 0) {
                        System.out.println("ID is duplicated!");
                    }
                } while (pos >= 0);
                break;
            default:
                int num = 0;
                do {
                    String numberID = Integer.toString(num);
                    while (numberID.length() < 3) {
                        numberID = "0" + numberID;
                    }
                    ID = "D" + numberID;
                    pos = searchDealer(ID);
                    num++;
                } while (pos >= 0);
        }

        String name = MyTool.readNonBlank("Name of new dealer").toUpperCase();
        String address = MyTool.readNonBlank("Address of new dealer");
        String phone = MyTool.readPattern("Phone number", Dealer.PHONE_FORMAT);
        boolean continuing = true;
        Dealer dealer = new Dealer(ID, name, address, phone, continuing);
        this.add(dealer);
        Collections.sort(this);
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
        String ID = MyTool.readPattern("Dealer's ID needs updating", Dealer.ID_FORMAT);
        int pos = searchDealer(ID);
        if (pos < 0) {
            System.out.println("Dealer " + ID + " not found!");
        } else {
            Scanner sc = new Scanner(System.in);
            Dealer dealer = this.get(pos);
            System.out.print("new name, ENTER for omitting:");
            String newName = sc.nextLine().trim();

            if (!newName.isEmpty()) {
                dealer.setName(newName);
                changed = true;
            }
            System.out.print("new address, ENTER for omitting:");
            String newAddress = sc.nextLine().trim();
            if (!newAddress.isEmpty()) {
                dealer.setAddress(newAddress);
                changed = true;
            }
            boolean valid;
            do {
                valid = true;
                System.out.print("new phone, ENTER for omitting:");
                String newPhone = sc.nextLine().trim();
                if (!newPhone.isEmpty()) {
                    valid = MyTool.validStr(newPhone, PHONEPATTERN);
                    if (valid) {
                        dealer.setPhone(newPhone);
                        changed = true;
                    } else {
                        System.out.println("Invalid, please type again!");
                    }
                }
            } while (!valid);

            String format = "1|0|Y|N|T|F";
            boolean newContinuing = true;
            do {
                System.out.print("new continuing status [1/0-Y/N-T/F], ENTER for omitting: ");
                String input = sc.nextLine().trim();
                if (input.isEmpty()) {
                    newContinuing = false;
                    break;
                }
                input = input.trim().toUpperCase().charAt(0) + "";
                valid = MyTool.validStr(input, format);
                if (!valid) {
                    System.out.println("Invalid, please type again!");
                } else {
                    newContinuing = true;
                }
            } while (!valid);

            if (newContinuing != dealer.isContinuing()) {
                dealer.setContinuing(newContinuing);
                changed = true;
            }
        }
    }

    public void printAllDealers() {
        if (this.isEmpty()) {
            System.out.println("Empty List!");
        } else {
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            System.out.println("|    ID    |   NAME   |      ADDRESS       |     PHONE     |CONTINUING|");
            System.out.println("+----------+----------+--------------------+---------------+----------+");
            for (int i = 0; i < this.size(); i++) {
                this.get(i).showInfor();
            }
            System.out.println("+----------+----------+--------------------+---------------+----------+");
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
            System.out.println("Done");
        } else {
            System.out.println("No change to write");
        }
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }
}
