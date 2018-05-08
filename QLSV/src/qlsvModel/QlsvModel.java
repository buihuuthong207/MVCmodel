package qlsvModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import qlsvDao.ConnectToDB;


public class QlsvModel {
	ConnectToDB myConnect = new ConnectToDB();
	private JTable table;
	private JTextField tfId, tfName, tfPoint,tfSex,tfBirth;
	public JButton btnOk;
	public JButton btnCancel;
	private boolean isUpdate = false;
	public QlsvModel() {
		myConnect.connect();
		
	}
	
	public void loadData() {
        // create table model
        DefaultTableModel model = new DefaultTableModel();
        
        // get data from database
        ResultSet rs = myConnect.getData();
        try {

                // load column name
                ResultSetMetaData rsMD = rs.getMetaData();
                int colNumber = rsMD.getColumnCount();
                String[] arr = new String[colNumber];
                for (int i = 0; i < colNumber; i++) {
                        arr[i] = rsMD.getColumnName(i + 1);
                }

                model.setColumnIdentifiers(arr);

                // load data from database to table
                while (rs.next()) {
                        for (int i = 0; i < colNumber; i++) {
                                arr[i] = rs.getString(i + 1);
                        }
                        model.addRow(arr);
                }

        } catch (SQLException e) {
        }
        table.setModel(model);
	}
	public void delete() {
		int row = table.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(null,
					"You must select a row in table", "Error delete",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		int select = JOptionPane.showOptionDialog(null, "You don't reget this, do you?",
				"Delete", 0, JOptionPane.YES_NO_OPTION, null, null, 1);
		if (select == 0) {
			myConnect.deleteId((String) table.getValueAt(row, 0));
			loadData();
		}
    }
    
    public Student getStudent() {
		String id = tfId.getText().trim().toUpperCase();
		String name = tfName.getText().trim();
		String sex = tfSex.getText().trim();
		if (id.equals("") || name.equals("")) {
			return null;
		}

		double point;
		try {
			point = Double.parseDouble(tfPoint.getText().trim());
		} catch (Exception e) {
			return null;
		}

		Student st = new Student(id, name, point, sex);
		return st;
    }
    
    public void update() {
		if (setDisplayInput(true, true)) {
			isUpdate = true;
		} else {
			JOptionPane.showMessageDialog(null, "You do choose nothing, human!!", "Error update",
					JOptionPane.ERROR_MESSAGE);
		}
    }
    
    public void add() {
		setDisplayInput(true, false);
    }
    
    public void addOrUpdate() {
		Student st = getStudent();
		if (st != null) {
			if (isUpdate) {
				myConnect.updateId(st.getId(), st);
				loadData();
				isUpdate = false;
			} else {
				myConnect.insert(st);
				loadData();
			}
			clear();
			setDisplayInput(false, false);
		} else {
			JOptionPane.showMessageDialog(null, "Infomation is error",
					"Error info", JOptionPane.ERROR_MESSAGE);
		}

    }
    
    public void clear() {
		tfId.setText("");
		tfName.setText("");
		tfPoint.setText("");
		tfSex.setText(null);
    }
    
    public void cancel() {
		clear();
		setDisplayInput(false, false);
    }
    
    private boolean setDisplayInput(boolean display, boolean update) {
		if (update && table.getSelectedRow() < 0) {
			return false;
		} else if (update) {
			int row = table.getSelectedRow(); 
			tfId.setText((String) table.getValueAt(row, 0));
			tfName.setText((String) table.getValueAt(row, 1));
			tfPoint.setText((String) table.getValueAt(row, 2));
			tfSex.setText((String) table.getValueAt(row, 3));
		}
		tfId.setEnabled(display);
		tfName.setEnabled(display);
		tfPoint.setEnabled(display);
		tfSex.setEnabled(display);
		btnOk.setEnabled(display);
		btnCancel.setEnabled(display);

		return true;

    }
	
}
