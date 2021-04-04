package lab4;

public class GameDriver {
	public static void main(String[] args) {
		Player player1 = new ConsecutivePlayer("Dani");
		Player player2 = new RandomPlayer("Dor");
		
		System.out.println("-----Rock Paper Scissors:");
		Game RockPaperScissors = new RockPaperScissors(player1,player2);
		RockPaperScissors.play(10);
		System.out.println("Game start");
		System.out.println("After 10 rounds The Points of the players:");
		System.out.println(player1.getName()+" with "+player1.getScore()+" points, "+player2.getName()+" with "+player2.getScore()+" points");
		System.out.println("The winner of the game Rock Paper Scissors is: "+RockPaperScissors.getWinner().getName()+"\n");
		
		System.out.println("-----Prisoner Dilemmas:");
		Game PrisonerDilemmas = new PrisonerDilemmas(player1,player2);
		PrisonerDilemmas.play(10);
		System.out.println("Game start");
		System.out.println("After 10 rounds The Points of the players:");
		System.out.println(player1.getName()+" with "+player1.getScore()+" points, "+player2.getName()+" with "+player2.getScore()+" points");
		System.out.println("The winner of the game Prisoner Dilemmas is: "+PrisonerDilemmas.getWinner().getName());
	}
}
