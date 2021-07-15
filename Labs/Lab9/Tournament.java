package lab9;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Tournament {
	final int NUMBER_OF_PLAYERS = 8;
	private String name, file_name;
	QueueAsList playersQueue;
	ObjectOutputStream playerOut;
	ObjectInputStream playerIn;
	public Tournament(String name, String file_name) {
		this.name = new String(name);
		this.file_name = file_name;
		playersQueue = new QueueAsList();
		playerOut = null;
		playerIn = null;;
		// Fill players queue
		try{			
			playerIn = new ObjectInputStream(new FileInputStream(file_name));
			for(int i=0; i<NUMBER_OF_PLAYERS; i++) 
				playersQueue.enqueue((Player)(playerIn.readObject()));
			playerIn.close();			
		}
		catch(IOException e){
			if(playerIn==null){
				playersQueue.enqueue(new Player("Novak Djokovic",10000));
				playersQueue.enqueue(new Player("Andy Murray",9000));
				playersQueue.enqueue(new Player("Roger Federer",8000));
				playersQueue.enqueue(new Player("Stanislas Wawrinka",7000));
				playersQueue.enqueue(new Player("Rafael Nadal",6000));
				playersQueue.enqueue(new Player("Kei Nishikori",5000));
				playersQueue.enqueue(new Player("Tomas Berdych",4000));
				playersQueue.enqueue(new Player("David Ferrer",3000));
			}
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try{
			FileOutputStream theFile = new FileOutputStream(this.file_name);
			playerOut = new ObjectOutputStream(theFile);
		}
		catch(IOException e){	
		}
	}
	private Player simulateGame(Player first, Player second) {
		 if(first.getTotalScore()>=second.getTotalScore()) {
			 first.updateGameWin();
			 return first;
		 }
		 second.updateGameWin();
		 return second;
	}
	public void simulateTournament() throws IOException {
		System.out.println("*******************************************************");
		System.out.println("Tennis tournament \""+name+"\" is starting now");
		System.out.println("*******************************************************");
		QueueAsList temp = new QueueAsList();
		int size = playersQueue.size()/2-1;
		for(int k=0;k<size;k++) {
			System.out.println("Stage "+(k+1)+" :");
			for(int i=0;i<playersQueue.size()/2;i++) {
				Player player1 = (Player)playersQueue.dequeue();
				System.out.println(player1);
				Player player2 = (Player)playersQueue.dequeue();
				System.out.println(player2);
				Player win = simulateGame(player1,player2);
				win.updateTotalScore();
				playerOut.writeObject(player1);
				playerOut.writeObject(player2);
				temp.add(win);
			}
			playersQueue = temp;
		}
		System.out.println("the winner is :"+playersQueue.dequeue());
	}	
	public void printPlayers() {
		System.out.println("Players ranking:");
		for(int i=0;i<playersQueue.size();i++)
			System.out.println(playersQueue.dequeue().toString());
	}
}