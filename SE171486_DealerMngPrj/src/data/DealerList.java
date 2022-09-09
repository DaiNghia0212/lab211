package data;
import java.util.List;
import java.util.ArrayList;
import java.sql.Date;
import tools.MyTool;
import mng.LogIn;

public class DealerList extends ArrayList<Dealer> {
    LogIn loginObj = null;
    private static final String PHONEPATTERN = "\\d{9} | \\d{11}";
    private String dataFIle = "";
    boolean changed = false;

    public DealerList(LogIn loginObj) {
        this.loginObj = loginObj;
    } 
    
    private void loadDealerFromFile() {
        List<String> lines = MyTool.readLinesFromFile(dataFIle);
        for (String line : lines) {
            Dealer dealer = new Dealer(line);
            this.add(dealer);
        }
    }
    
    
}
