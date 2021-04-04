package lab4;

public class ConsecutivePlayer extends Player {
	private int lastIdx;
	
	public ConsecutivePlayer(String name) { 
		super(name);
		lastIdx=0;
	}
	public Action selectAction(Action[] actions) {
		lastIdx+=1;
		if(lastIdx == actions.length)
			lastIdx = 0;
		return actions[lastIdx];
	}
}
