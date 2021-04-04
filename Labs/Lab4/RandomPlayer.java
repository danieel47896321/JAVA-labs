package lab4;

public class RandomPlayer extends Player{
	public RandomPlayer(String name) { super(name); }
	public Action selectAction(Action[] actions){
		int index = (int) (Math.random() * actions.length);
		return actions[index];
	}
}
