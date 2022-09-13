package data;

import tools.MyTool;

public class Dealer implements Comparable<Dealer> {

    public static final char SEPARATOR = ',';
    public static final String ID_FORMAT = "D\\d{3}";
    public static final String PHONE_FORMAT = "\\d{9}|\\d{11}";
    private String ID;
    private String name;
    private String address;
    private String phone;
    private boolean continuing;

    public Dealer(String ID, String name, String address, String phone, boolean continuing) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.continuing = continuing;
    }

    public Dealer(String line) {
        String[] parts = line.split("" + this.SEPARATOR);
        ID = parts[0].trim();
        name = parts[1].trim();
        address = parts[2].trim();
        phone = parts[3].trim();
        continuing = MyTool.parseBool(parts[4]);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isContinuing() {
        return continuing;
    }

    public void setContinuing(boolean continuing) {
        this.continuing = continuing;
    }

    @Override
    public String toString() {
        return ID + SEPARATOR + name + SEPARATOR + address + SEPARATOR + phone + SEPARATOR + continuing + "\n";
    }
    
    @Override
    public int compareTo(Dealer o) {
        return this.getID().compareToIgnoreCase(o.getID());
    }
}
