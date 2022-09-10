package mng;

import data.Account;
import data.AccountChecker;
import data.DealerList;
import tools.MyTool;

public class LogIn {

    private Account account = null;

    public LogIn(Account account) {
        this.account = account;
    }

    public static Account intputAccount() {
        System.out.println("Please Login to System.");
        String accountName = MyTool.readNonBlank("Your account name");
        String password = MyTool.readNonBlank("Your password");
        String role = MyTool.readNonBlank("Your role");
        Account account = new Account(accountName, password, role);
        return account;
    }

    public Account getAcc() {
        return account;
    }

    public static void main(String[] args) {
        Account account = null;
        boolean continuing = false;
        boolean valid = false;
        do {
            AccountChecker accountChecker = new AccountChecker();
            account = intputAccount();
            valid = accountChecker.check(account);
            if (!valid) {
                continuing = MyTool.readBool("Invalid account - Try again?");
            }
            if (!valid && !continuing) {
                System.exit(0);
            }
        } while (continuing);
        LogIn loginObj = new LogIn(account);

        if (account.getRole().equalsIgnoreCase("ACC-1")) {
            String[] options = {"Add new dealer", "Search a dealer",
                "Remove a dealer", "Update a dealer", "Print all dealers",
                "Print continuing dealers", "Print UN-continuing dealers", "Write to file"};
            Menu menu = new Menu(options);
            DealerList dealerList = new DealerList(loginObj);
            dealerList.initWithFile();
            int choice = 0;
            do {
                choice = menu.getChoice("Managing dealers");
                switch (choice) {
                    case 1:
                        dealerList.addDealer();
                        break;
                    case 2:
                        dealerList.searchDealer();
                        break;
                    case 3:
                        dealerList.removeDealer();
                        break;
                    case 4:
                        dealerList.updateDealer();
                        break;
                    case 5:
                        dealerList.printAllDealers();
                        break;
                    case 6:
                        dealerList.printContinuingDealers();
                        break;
                    case 7:
                        dealerList.printUnContinuingDealers();
                        break;
                    case 8:
                        dealerList.writeDealerToFile();
                        break;
                    default:
                        if (dealerList.isChanged()) {
                            boolean res = MyTool.readBool("Data changed. Write to file?");
                            if (res == true) {
                                dealerList.writeDealerToFile();
                            }
                        }
                }
            } while (choice > 0 && choice < menu.size());
            System.out.println("Bye.");
        }
    }
}
