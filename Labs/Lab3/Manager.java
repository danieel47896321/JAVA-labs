package lab3;

public class Manager extends Employee{
	private float kmult;
	 public Manager(float salary, float kmult) { 
		 super(salary);
		 this.kmult = kmult ;
	 }
	 public float getKmult() { return kmult; }
	 public float ComputeSalary() { return super.ComputeSalary()*kmult; }
	 public String getName() { return "Manager"; }

}
