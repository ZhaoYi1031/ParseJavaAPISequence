import java.sql.*;

public class Con {
    Connection con;
    Statement st;
    public PreparedStatement preSt;
    private String url = "jdbc:mysql://192.168.7.118:3306/tianyf";
    private String user = "root";
    private String password = "cooper2017";
    private String driver = "com.mysql.jdbc.Driver";
    public Con() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        this.con = DriverManager.getConnection(url, user, password);
        this.st = this.con.createStatement();
    }
    public void close() throws SQLException {
        this.con.close();
    }

    public void executeSql(String sqlOrder) throws SQLException {
//        System.out.println("execute hashcode" + Integer.toString(this.con.hashCode()));
//        System.out.println(sqlOrder);
        this.st.executeUpdate(sqlOrder);
    }
    public void executeSql() throws SQLException {
//        System.out.println(this.preSt.toString());
        this.preSt.executeUpdate();
    }
    public int executeSqlRetInt(int projectId) throws SQLException {
        String sqlOrder = "select disk from java_repositories where projectId = " + Integer.toString(projectId);
//        System.out.println(sqlOrder);
        ResultSet res = this.st.executeQuery(sqlOrder);
        if(res.next()) return res.getInt(1);
        else return -123;
    }

//    public static void main(String args[] ) throws SQLException, ClassNotFoundException {
//        Con c = new Con();
//        int ret = c.executeSqlRetInt( 942601);
//        System.out.println(ret);
//    }
}


