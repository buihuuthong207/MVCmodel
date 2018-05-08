package qlsvView;
import qlsvDao.ConnectToDB;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
 
public class MyFrame extends JFrame {
 
        private JTable table;
        ConnectToDB myConnect = new ConnectToDB();
 
        public MyFrame() {
                // add main panel to JFrame
                add(createMainPanel());
 
                // connect database
                myConnect.connect();
 
                // set dipslay
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
        }
 
        // create main panel
        private JPanel createMainPanel() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(createTitlePanel(), BorderLayout.PAGE_START);
                panel.add(createTablePanel(), BorderLayout.CENTER);
                panel.add(createButtonPanel(), BorderLayout.PAGE_END);
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
                JPanel panel = new JPanel(new GridLayout(1, 3, 5, 5));
                panel.setBorder(new EmptyBorder(5, 50, 10, 50));
                panel.add(createButton("Add"));
                panel.add(createButton("Update"));
                panel.add(createButton("Delete"));
                return panel;
        }
 
        // create a table
        private JTable createTabel() {
                JTable table = new JTable();
                return table;
        }
 
        // create a button
        private JButton createButton(String text) {
                JButton btn = new JButton(text);
                return btn;
        }
 
        private void loadData() {
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
 
        public static void main(String[] args) {
                MyFrame myFrame = new MyFrame();
                myFrame.loadData();
        }
}