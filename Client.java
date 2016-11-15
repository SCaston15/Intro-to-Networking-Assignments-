/*
 * Stephanie Caston
 * ITCS 3166 
 * FINAL PROGRAM 
 * 5/3/2016 
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client 
{ 
	//Variable declaration 
	static Socket s; 
    static DataInputStream reader;
    static DataOutputStream writer; 
    static String playerChoice; 
    static String hit = "H"; 
    static String stand = "S";
    static String prevC = "A";
    static int cScore; 
    static int pScore; 
    static int curPlayer; 
    static int prevPlayer; 
	 
	public static void main(String[] args){
	
	try{
	
	do{
		
	//Creation of socket 
	s = new Socket("127.0.0.1", 9992);
	
	//Reader and writer variables for data exchange with server 
	reader = new DataInputStream(s.getInputStream());
    writer = new DataOutputStream(s.getOutputStream());
    
    //Receives the String that tells the player which player they are (1 or 2) 
	String p1 = reader.readLine(); 
	
	//Informs the player of which player they are 
	System.out.println(p1);
	
	//Sends the player number of the current player playing 
	int playerNum = reader.readInt(); 
	curPlayer = playerNum; 
	
	if(playerNum == 2){
		playerNum = playerNum - 1; 
	}
	else if (playerNum == 1){
		playerNum = playerNum + 1; 
	}
	
	prevPlayer = playerNum; 

	//Creates scanner object 
    Scanner keyboard = new Scanner(System.in);
    
    //Shows the current player what the other player did
    String prevChoice = reader.readLine(); 
    prevC = prevChoice; 
    
    if(prevChoice.equals("N/A")){
    	System.out.println("Player 2 is waiting.");
    }
    else if(prevChoice.equals("H")){
    	System.out.println("Player " + playerNum + " chose to hit.");
    }
    else if(prevChoice.equals("S")){
    	System.out.println("Player " + playerNum  + " chose to stand.");
    }
    //Prompts the player to ender 'S' or 'H' 
    System.out.println("It's your turn, Enter 'S' for stand for 'H' for hit"); 
    
    //Reads in the score of the other player 
    int prevScore = reader.readInt(); 
    pScore = prevScore; 
    
    //Reads in the score of the current player and displays it to current player 
    int score = reader.readInt(); 
    System.out.println("Score: " + score);
    
    //Stores user input of 'H' or 'S' 
    playerChoice = keyboard.next(); 
    
    //Data validation 
    while(playerChoice.charAt(0) != 'S' && playerChoice.charAt(0) != 'H' || playerChoice.length() > 1){
    	System.out.println("Your input is not valid, press 'H' or 'S'");
    	playerChoice = keyboard.next();
    }
    
    //Sends choice to server. 
    writer.writeBytes(playerChoice + '\n');
    
    //Handling of when player chooses to hit
    if(playerChoice.equals("H")){
    	
    	//Reads in random card from server and displays it to player  
    	int card = reader.readInt(); 
    	System.out.println("Drew the card: " + card);
    	
    	//Shows new score after card is drawn 
    	int curScore = reader.readInt(); 
    	cScore = curScore; 
    	System.out.println("Score: " + curScore); 
    }
    
    //If the player chooses to stand send them back their current/unchanged score 
    else if (playerChoice.equals("S")){
    
    	System.out.println("You chose to stand");
    	
    	//reads in current/unchanged score from server and prints it to the player 
    	int curScore = reader.readInt(); 
    	System.out.println("Your Score: " + curScore);
    	
    	//if the other player also chose to stand, end the game, display scores, declare win/lose to current player
    	if(prevChoice.equals("S")){
    		
    		//Conditions for win/lose/draw statements
    		if(curScore > prevScore){
    			System.out.println("Game Over! You win!");
    		}
    		else if(curScore < prevScore){
    			System.out.println("Game Over! You lose!");
    		}
    		else if(curScore == prevScore){
    			System.out.println("Game Over! It's a draw!");
    		}
    		System.out.println("Player " + curPlayer + "'s Score: "+ curScore);
    		System.out.println("Player " + playerNum + "'s Score: " + prevScore);
    		break; 
    	}
    	
    }
    	System.out.println(" ");
	
	}while(cScore <= 21);
	
	//If the player's current score goes above 21, end game, and display scores. 
	if(cScore > 21){
    	System.out.println("Game Over! You Lose!");
    	System.out.println("Player " + curPlayer + "'s Score: "+ cScore);
		System.out.println("Player " + prevPlayer + "'s Score: " + pScore);
    }
	
    	//Close socket/inputStream/outputStream
    	s.close(); 
    	reader.close();
    	writer.close(); 
		}
			catch(Exception ex){
			System.out.println("Error with connection...");
		}
	}
}