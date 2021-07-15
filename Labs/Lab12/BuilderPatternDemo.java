package lab12;

public class BuilderPatternDemo {
	  public static void main(String[] args) {
		  Thread meal[] = new Thread[5];
		  for(int i=0;i<5;i++) {
			  meal[i] = new Thread(new MealBuilder());
			  meal[i].start();
			  try { meal[i].join();}
			  catch (InterruptedException e) { e.printStackTrace();}
		  }
	  }
}
