package qlsvModel;

public class Student {
    private String id;
    private String name;
    private double point;
    private String sex;
    
    public Student() {
    	
    }
    
	public Student(String id, String name, double point, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.point = point;
		this.sex = sex;
	}
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPoint() {
		return point;
	}
	public void setPoint(double point) {
		this.point = point;
	}
    
    
}
