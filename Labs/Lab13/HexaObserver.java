package lab13;

public class HexaObserver extends Observer {
	public HexaObserver(Subject subject) {
		this.subject = subject;
		this.subject.attach(this);
	}
	@Override
	public void update() { System.out.println("Hexa: "+ Integer.toHexString(subject.getState())); }
}
