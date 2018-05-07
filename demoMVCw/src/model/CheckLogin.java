package model;


public class CheckLogin {
    public boolean checkLogin(User user) {
    	if("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}
