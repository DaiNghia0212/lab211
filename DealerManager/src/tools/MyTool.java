package tools;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.io.IOException;

public class MyTool {

    public static final Scanner SC = new Scanner(System.in);

    public static boolean validStr(String str, String regEx) {
        return str.matches(regEx);
    }

    public static boolean validPassword(String str, int minLen) {
        if (str.length() < minLen) {
            return false;
        }
        return str.matches(".*[a-zA-Z]+.*")
                && // at least 1 character
                str.matches(".*[\\d]+.*")
                && // at least 1 digit
                str.matches(".*[\\W]+.*"); // at least 1 special char 
    }

    public static Date parseDate(String dateStr, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        try {
            long t = dF.parse(dateStr).getTime();
            return new Date(t);
        } catch (ParseException e) {
            System.out.println(e);
        }
        return null;
    }

    public static String dataToStr(Date date, String dateFormat) {
        SimpleDateFormat dF = (SimpleDateFormat) SimpleDateFormat.getInstance();
        dF.applyPattern(dateFormat);
        return dF.format(date);
    }

    public static boolean parseBool(String boolStr) {
        char c = boolStr.trim().toUpperCase().charAt(0);
        return (c == '1' || c == 'Y' || c == 'T');
    }

    public static String readNonBlank(String message) {
        String input = "";
        do {
            System.out.print(message + ": ");
            input = SC.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }

    public static String readPattern(String message, String pattern) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message + ": ");
            input = SC.nextLine().trim();
            valid = validStr(input, pattern);
            if (!valid) {
                System.out.println("Invalid, please type again!");
            }
        } while (!valid);
        return input;
    }

    public static boolean readBool(String message) {
        String input;
        System.out.print(message + "[1/0-Y/N-T/F]: ");
        input = SC.nextLine().trim();
        if (input.isEmpty()) {
            return false;
        }
        return parseBool(input);
    }

    public static List<String> readLinesFromFile(String filename) {
        ArrayList<String> list;
        list = new ArrayList();
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);
                String line;
                while ((line = bf.readLine()) != null) {
                    if (!line.equals("")) {
                        list.add(line);
                    }
                }
                bf.close();
                fr.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        return list;
    }

    public static void writeFile(String filename, List list) {
        if (!list.isEmpty()) {
            try {
                File file = new File(filename);
                FileWriter fw = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fw);
                for (Object item : list) {
                    pw.print(item);
                }
                pw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
