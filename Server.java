/*
 * Stephanie Caston
 * ITCS 3166
 * FINAL PROGRAM
 * 5/3/2016
 */
import java.io.*;
import java.net.*;
import java.util.Random;

public class Server 
{
	//Variable declaration 
	static ServerSocket s; 
	static Socket pl1; 
	static Socket pl2; 
	static DataInputStream reader;
    static DataOutputStream writer;
    static int score1 = 0;
    static int score2 = 0; 
    static String prev = "N/A"; 
    static int count = 0; 
    static String plChoice1 = "A";
    static String plChoice2 = "B";
    static int player1 = 1; 
    static int player2 = 2; 
	
public static void main(String[] args){
	
	try{
	
	//PLAYER 1 HANDLING
	//PLAYER 1 HANDLING
	//PLAYER 1 HANDLING
	s = new ServerSocket(9992);
	System.out.println("Game Start");
	
	do{
	
	//Accept first connection and verify it 
	pl1 = s.accept(); 
	System.out.println("Connected to player 1 ");
	
	//Declare reader and writer variables for data exchange 
	reader = new DataInputStream(pl1.getInputStream());
    writer = new DataOutputStream(pl1.getOutputStream());
    
    //Tells client 1 they are player 1 
    String p1 = "You are player 1";
    writer.writeBytes(p1 + '\n'); 
    writer.writeInt(player1);
    
    if(count == 0){
    	writer.writeBytes(prev + '\n');
    	writer.writeInt(score2);
    }
    else if(count > 0){
    	writer.writeBytes(plChoice2  + '\n');
    	writer.writeInt(score2);
    }
    
    //Write initial score of 0 to player 1
    writer.writeInt(score1);
    
    //Prints out on server side that it is player 1's turn 
    System.out.println("Player 1's Turn.");
    
    //Receive player choice  
    String playerChoice1 = reader.readLine(); 
    System.out.println("Player 1 chose: " + playerChoice1);
    
    //If player chose to hit give them a card and add to current score. 
    if(playerChoice1.equals("H") || playerChoice1.equals("h")){
        
    	plChoice1 = playerChoice1; 
    	
    	//Creates number between 1-13 for card values
        Random rn1 = new Random();
    	int player1Card = rn1.nextInt(13) + 1;
    	
    	//Sends random card to Player 1 
    	writer.writeInt(player1Card);
    	
    	//Displays the card drawn 
    	System.out.println("Drew the Card: " + player1Card); 
    	
    	//Change cards with 11-13 value to 10 value
    	if(player1Card > 10){
    		player1Card = 10; 
    	}
    	else if(player1Card <= 10){
    		player1Card = player1Card; 
    	} 
    	
    	//Add card value to current score
    	score1 = score1 + player1Card;
    	
    	//Display updated player 1 score 
    	System.out.println("Player 1's score: " + score1);
    	writer.writeInt(score1);
    }
    
    // If player 1 chooses to stand, show them current score 
    else if (playerChoice1.equals("S")){
    	
    	plChoice1 = playerChoice1; 
    	
    	System.out.println("Player 1's score: " + score1); 
    	
    	writer.writeInt(score1);
    
    // If player 2 also chose to stand, break the loop, end the game. 
    	if(plChoice2.equals("S")){
    		System.out.println("Both players stand. Game over!");
    		break; 
    	}
    }
    // If player 1's score passes 21, immediately break loop. Ending the game. 
    if(score1 > 21){
    	break; 
    }
    
     
    //PLAYER 2 HANDLING 
    //PLAYER 2 HANDLING 
    //PLAYER 2 HANDLING 
	pl2 = s.accept(); 
	System.out.println("Connected to player 2 ");
	
	//Declare reader and writer variables for data exchange 
	reader = new DataInputStream(pl2.getInputStream());
    writer = new DataOutputStream(pl2.getOutputStream());
    
    //Tells client 1 they are player 2 
    String p2 = "You are player 2";
    writer.writeBytes(p2 + '\n');
    
    //Assigns player 2 the number 2
    writer.writeInt(player2);
    
    //Sends over player 1's choice 
    writer.writeBytes(playerChoice1 + '\n');
   
    //sends over player 1's score. 
	writer.writeInt(score1);
    
    //Write initial score of 0 to player 2
    writer.writeInt(score2);
  
    //Prints out on server side that it is player 2's turn 
    System.out.println("Player 2's Turn.");
    
    //Receive player choice  
    String playerChoice2 = reader.readLine(); 
    
    System.out.println("Player 2 chose: " + playerChoice2);
    
    //If player 2 chooses hit, send random card to player 
    if(playerChoice2.equals("H")){
    	
    	plChoice2 = playerChoice2; 
    	
    	//Creates numbers between 1-13 for card values 
    	Random rn2 = new Random();
    	int player2Card = rn2.nextInt(13) + 1;
    	
    	//Displays value of card drawn 
    	System.out.println("Drew the card: " + player2Card);
      	
    	//Sends card to player 2 
    	writer.writeInt(player2Card); 
    	
    	//Makes cards that are 11-13 worth 10 
    	if(player2Card > 10){
    		player2Card = 10; 
    	}
    	else if(player2Card <= 10){
    		player2Card = player2Card; 
    	}
    	
    	//Changes player 2 score to new score 
    	score2 = score2 + player2Card;
    	//Displays Player 2's current score 
    	System.out.println("Player 2's score: " + score2);
    	
    	//Sends new score to player 2 
    	writer.writeInt(score2); 
    }
    //If player 2 stands displays the current score 
    else if (playerChoice2.equals("S")){
    	plChoice2 = playerChoice2; 
    	
    	System.out.println("Player 2's score: " + score2);
    	
    	//Sends unaltered score to player 2
    	writer.writeInt(score2);
        
    	//If player 1's choice was also to stand, end the game 
    	if(plChoice1.equals("S")){
    		System.out.println("Both players stand. Game over!");
    		break; 
    	}
    }
    //Checks which loop program is on 
    count = count + 1; 
    
    //keep looping until 1 score goes above 21, or if a break isn't met. 
	}while(score1 <= 21 && score2 <= 21);
	
	//If either player's score is above 21 end the game. 
	if(score1 > 21){
	System.out.println("Player 2 wins! Game over!");
	}
	else if(score2 > 21){
		System.out.println("Player 1 wins! Game over!");
	}
	
	//If both players choose to stand end the game
	else if(plChoice1.equals("S") && plChoice2.equals("S")){
		
		//Displays end game scores of both players 
		System.out.println("Player 1 Score: " + score1);
	    System.out.println("Player 2 Score: " + score2);
	   
	    //Declaration of who wins or if it's a tie depending on score values
	    if(score1 > score2){
	    	System.out.println("Player 1 wins!");
	    }
	    else if(score2 > score1){
	    	System.out.println("Player 2 wins!");
	    }
	    else if(score1 == score2){
	    	System.out.println("It's a draw!");
	    }
	}
		
		//Close sockets/serverSocket/inputStream/outputStream 
		s.close();
		pl1.close();
		pl2.close();
	    writer.close();
	    reader.close();
	}
	catch(Exception ex){
		System.out.println("Error with connection...");
	}
}
}
