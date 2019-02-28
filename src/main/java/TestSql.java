import java.sql.SQLException;

import static java.lang.Integer.parseInt;

public class TestSql {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        String s = "12___121___es_________^$110";
        String[] ans = s.split("___");
        System.out.println( ans[ans.length-1].replace('_', '\0'));
        String temp = ans[ans.length-1].replaceAll("[^0-9]", "");
        System.out.println(temp);
//        temp = "110";
        parseInt(temp);
        System.out.println(parseInt(temp));
    }
}
