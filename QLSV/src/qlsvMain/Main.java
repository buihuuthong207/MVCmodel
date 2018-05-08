package qlsvMain;

import qlsvController.Controller;
import qlsvModel.Student;
import qlsvView.MyFrame;
import qlsvModel.QlsvModel;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame view = new MyFrame();
		view.loadData();
		Controller control = new Controller(view);
		view.setVisible(true);
	}

}
