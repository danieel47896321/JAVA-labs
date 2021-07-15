package lab13;

public class ObserverPatternDemo {
	public static void main(String[] args) {
		Subject subject = new Subject();
		new HexaObserver(subject);
		new BinaryObserver(subject);
		new OctalObserver(subject);
		
		for(int i=0 ; i<11;i++) {
			System.out.println("State "+i);
			subject.setState(i);
		}
		
	}

}