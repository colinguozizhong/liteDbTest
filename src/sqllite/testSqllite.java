package sqllite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.Function;

public class testSqllite {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ConnectAccessFile();
	}
	
	public static void ConnectAccessFile() throws Exception   
    {  
        Class.forName("org.sqlite.JDBC");  
        /** 
         * 直接连接access文件。 
         */  
        String dbur1 = "jdbc:sqlite://E:/hrms.db";  
        Connection conn = DriverManager.getConnection(dbur1, "", "");  
        Statement stmt = conn.createStatement();  
        
        Function.create(conn, "TO_CHAR", new Function() {
            protected void xFunc() throws SQLException {
            	int args = this.args();
                if (args < 2) {
                    this.error("argument is too little");
                    return;
                }
                String date = this.value_text(0);
                String format = this.value_text(1);
                if("yyyy-mm-dd".equals(format.toLowerCase())){
                	this.result(date);
                }
            }
        });
        ResultSet rs = stmt.executeQuery("select TO_CHAR('2017-01-02','YYYY-MM-DD')");
        //ResultSet rs = stmt.executeQuery("select LOGIN_TIME from M_USER");  
        while (rs.next()) {  
            System.out.println(rs.getString(1));  
        }  
        rs.close();  
        stmt.close();  
        conn.close();  
    }  

}
