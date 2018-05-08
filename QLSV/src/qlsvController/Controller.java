package qlsvController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import qlsvModel.Student;
import qlsvView.MyFrame;
public class Controller {
	private MyFrame theView;

	public Controller(MyFrame theView) {
		this.theView = theView;	         
			        // Tell the View that when ever the calculate button
			        // is clicked to execute the actionPerformed method
			        // in the CalculateListener inner class
		this.theView.addCalculateListener(new CalculateListener());
	}
	class CalculateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand() == "Delete") {
				theView.delete();
				return;
			}
			if (arg0.getActionCommand() == "Update") {
				theView.update();
				return;
			}
			if (arg0.getActionCommand() == "Add") {
				theView.add();
				return;
			}
			if (arg0.getSource() == theView.btnOk) {
				theView.addOrUpdate();
			}
			if (arg0.getSource() == theView.btnCancel) {
				theView.cancel();
			}
			
		}
		
	}
}
