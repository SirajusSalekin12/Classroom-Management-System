import java.util.ArrayList;

public class Student {
	
	private String name;
	private String id;
	private String grade = "I";
	public ArrayList<Integer> mark = new ArrayList<>(); // Arraylist that stores each quiz's mark
	
	public Student(String name, String id){
		this.name = name;
		this.id = id;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getGrade() { return grade; }

	public void setMark(int mark){
		this.mark.add(mark);
	}

	public int getAverage(){
		if(mark.isEmpty()) return 0;
		int sum = 0;
		for(int i=0; i<mark.size(); i++){
			sum += mark.get(i);
		}
		return sum/mark.size();
	}

	public void setGrade(){
		int avg = this.getAverage();
		if(avg>92) grade = "A";
		else if(avg>=90) grade = "A-";
		else if(avg>=87) grade = "B+";
		else if(avg>=83) grade = "B";
		else if(avg>=80) grade = "B-";
		else if(avg>=77) grade = "C+";
		else if(avg>=73) grade = "C";
		else if(avg>=70) grade = "C-";
		else if(avg>=67) grade = "D+";
		else if(avg>=60) grade = "D";
		else grade = "F";
	}
}