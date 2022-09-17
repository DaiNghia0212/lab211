package data;

import tools.MyTool;
import java.util.List;

public class AccountChecker {

    private String accountFile;
    private static String SEPARATOR = ",";

    public AccountChecker() {
        setupAccountFile();
    }

    private void setupAccountFile() {
        Config cR = new Config();
        accountFile = cR.getAccountFile();
    }

    public boolean check(Account acc) {
        List<String> lines = MyTool.readLinesFromFile(accountFile);
        for (String line : lines) {
            String[] parts = line.split(this.SEPARATOR);
            if (parts.length < 3) {
                return false;
            }
            if (parts[0].equalsIgnoreCase(acc.getAccName())
                    && parts[1].equals(acc.getPwd())
                    && parts[2].equalsIgnoreCase(acc.getRole())) {
                return true;
            }
        }

        return false;
    }
}
