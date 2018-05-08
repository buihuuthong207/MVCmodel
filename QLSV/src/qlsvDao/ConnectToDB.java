package qlsvDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.*;


public class ConnectToDB {
	
	private final String className = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/qlsv?useUnicode=true&characterEncoding=UTF-8";
	
	private Connection connection; 
	
	private final String username = "root";
	private final String pass = "0961282897";
	
	 public void connect()  {
		try {
			Class.forName(className);
			connection = DriverManager.getConnection(url,username,pass);
//			System.out.println("thanh cong");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("that bai");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public ResultSet getData() {
		ResultSet rs = null;
        String sqlCommand = "select * from  sinhvien";
        Statement st;
        try {
                st = connection.createStatement();
                rs = st.executeQuery(sqlCommand);
        } catch (SQLException e) {
                System.out.println("select error \n" + e.toString());
        }
        return rs;
	}
	
	public void showData(ResultSet rs) {
		try {
			while(rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(2) 
			    + "  " + rs.getDouble(3));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	ConnectToDB conNec = new ConnectToDB();
    	conNec.connect();
    	conNec.showData(conNec.getData());
    }
}
