package qlsvView;
import qlsvDao.ConnectToDB;
import qlsvModel.Student;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
 
public class MyFrame extends JFrame {//implements ActionListener 
 
		private boolean isUpdate = false;
        private JTable table;
        private JTextField tfId, tfName, tfPoint,tfSex,tfBirth;
    	public JButton btnOk;
    	public JButton btnCancel;
    	
    	JButton btn = new JButton("Add");
    	JButton btn1 = new JButton("Update");
    	JButton btn2 = new JButton("Delete");
    	JButton btn3 = new JButton("Ok");
    	JButton btn4 = new JButton("Cancel");
    	
        ConnectToDB myConnect = new ConnectToDB();
 
        public MyFrame() {
                // add main panel to JFrame
                add(createMainPanel());
                setDisplayInput(false, false);
                // connect database
                myConnect.connect();
 
                // set dipslay
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pack();
                setLocationRelativeTo(null);
//                setVisible(true);
        }
 
        // create main panel
        private JPanel createMainPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(createTitlePanel(), BorderLayout.PAGE_START);
                panel.add(createTablePanel(), BorderLayout.CENTER);
                panel.add(createBottomPanel(), BorderLayout.PAGE_END);
                return panel;
        }
 
        // create title panel
        private JPanel createTitlePanel() {
                JPanel panel = new JPanel();
                JLabel lbTitle = new JLabel("Connect Database");
                panel.add(lbTitle);
                return panel;
        }
 
        // create table panel
        private JPanel createTablePanel() {
                JPanel panel = new JPanel();
                panel.add(new JScrollPane(table = createTabel()));
                return panel;
        }
 
        // create button panel
        private JPanel createButtonPanel() {
                JPanel panel = new JPanel(new GridLayout(1, 4, 10, 10));
                panel.setBorder(new EmptyBorder(5, 5, 10, 5));
                panel.add(btn);
                panel.add(btn1);
                panel.add(btn2);
                return panel;
        }
 
        // create a table
        private JTable createTabel() {
                JTable table = new JTable();
                return table;
        }
 
        // create a button
//        public JButton createButton(String text) {
//                JButton btn = new JButton(text);
////                btn.addActionListener(this);
//                return btn;
//        }
        
        private JPanel createBottomPanel() {
        	JPanel panel = new JPanel(new BorderLayout(10,10));
        	panel.setBorder(new EmptyBorder(5, 70, 10, 70));
    		panel.add(createInputPanel(), BorderLayout.CENTER);
    		panel.add(createButtonPanel(), BorderLayout.PAGE_END);
        	return panel;
        }
        
        private JPanel createInputPanel() {
    		JPanel panel = new JPanel(new BorderLayout(10, 10));

    		// panel left
    		JPanel panelLeft = new JPanel(new GridLayout(4, 1, 5, 5));
    		panelLeft.add(new JLabel("Id"));
    		panelLeft.add(new JLabel("Name"));
    		panelLeft.add(new JLabel("Point"));
    		panelLeft.add(new JLabel("Sex"));

    		// panel right
    		JPanel panelRight = new JPanel(new GridLayout(4, 1, 5, 5));
    		panelRight.add(tfId = new JTextField());
    		panelRight.add(tfName = new JTextField());
    		panelRight.add(tfPoint = new JTextField());
    		panelRight.add(tfSex = new JTextField());

    		// panel ok
    		JPanel panelOk = new JPanel(new GridLayout(1, 4, 5, 5));
    		panelOk.setBorder(new EmptyBorder(0, 50, 0, 50));
    		panelOk.add(btnOk = btn3);
    		panelOk.add(btnCancel = btn4);

    		panel.add(panelLeft, BorderLayout.WEST);
    		panel.add(panelRight, BorderLayout.CENTER);
    		panel.add(panelOk, BorderLayout.PAGE_END);
    		return panel;
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
        

//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			if (arg0.getActionCommand() == "Delete") {
//				delete();
//				return;
//			}
//			if (arg0.getActionCommand() == "Update") {
//				update();
//				return;
//			}
//			if (arg0.getActionCommand() == "Add") {
//				add();
//				return;
//			}
//			if (arg0.getSource() == btnOk) {
//				addOrUpdate();
//			}
//			if (arg0.getSource() == btnCancel) {
//				cancel();
//			}
//			
//		}
		
		public void addCalculateListener(ActionListener listenForCalcButton){
			this.btn.addActionListener(listenForCalcButton);
			this.btn1.addActionListener(listenForCalcButton);
			this.btn2.addActionListener(listenForCalcButton);
			this.btn3.addActionListener(listenForCalcButton);
			this.btn4.addActionListener(listenForCalcButton);
		}
		
//		public static void main(String[] args) {
//            MyFrame myFrame = new MyFrame();
//            myFrame.loadData();
//		}
}