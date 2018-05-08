package qlsvDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import qlsvModel.Student;

import java.sql.*;


public class ConnectToDB {
	
	private final String className = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/qlsv?useUnicode=true&characterEncoding=UTF-8";
	
	private Connection connection; 
	
	private final String username = "root";
	private final String pass = "0961282897";
	
	//ket noi Eclipse voi MySql
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
	
	 //lay tat ca du lieu cua sinh vien
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
	
	//lay thong tin sinh vien dua vao id
	public ResultSet getDataId(String id) {
		ResultSet rs = null;
		String sqlCommand = "select * from sinhvien where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, id);
			rs = pst.executeQuery();
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return rs;
	}
	
	//xoa dua vao id cua sinh vien
	public void deleteId(String id) {
		String sqlCommand = "delete from sinhvien where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			pst.setString(1, id);
			if (pst.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "You will pay for this!!","Delete successed",JOptionPane.OK_OPTION);
			} else {
				System.out.println("delete error \n");
			}
		} catch (SQLException e) {
			System.out.println("delete error \n" + e.toString());
		}
	}
	
	//hien thi du lieu
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
	
	//them sinhvien
	public void insert(Student s) {
		String sqlCommand = "insert into sinhvien value(?, ?, ?, ?)";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace three "?" by id, Name and point of Studnet s
			pst.setString(1, s.getId());
			pst.setString(2, s.getName());
			pst.setDouble(3, s.getPoint());
			pst.setString(4, s.getSex());
			if (pst.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Something's inserted, hmm!! Let's hope nothing bad happen!", "Success", JOptionPane.WARNING_MESSAGE);
			} else {
				System.out.println("insert error \n");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "You insert same ID or something wrong, hmm!! you should not do that again", "Error", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//update sinhvien
	public void updateId(String id, Student s) {
		String sqlCommand = "update sinhvien set name = ?, point = ?, sex = ? where id = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			pst.setString(1, s.getName());
			pst.setDouble(2, s.getPoint());
			pst.setString(3, s.getSex());
			pst.setString(4, s.getId());
			if (pst.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "You changed something, hmm!! Let's hope nothing bad happen", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "ID is untouchable, you should not do that!!!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			System.out.println("update error \n" + e.toString());
		}
	}
	
    
}
