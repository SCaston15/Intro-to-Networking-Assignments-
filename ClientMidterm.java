/* 
 * Stephanie Caston
 * ITCS 3166
 * MIDTERM
 * 3/7/2016
 */

//Imports the scanner class
import java.util.regex.Matcher;
import java.util.regex.Pattern; 
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner; 

public class ClientMidterm
{
public static void main(String[] args) throws IOException
{ 
	try
	{
  //Declares variables
   String input;
   String temp;
   
   //Creates scanner object
   Scanner keyboard = new Scanner(System.in); 
   
   //Sets up socket and gives it place to connect 
   Socket s = new Socket("127.0.0.1", 9995); 
   
   //Allows for data transfer 
   Scanner sc1 = new Scanner(s.getInputStream());
   
   //Prompts user for input 
   System.out.println("Enter number to be converted "
   		+ "from fahrenheit ");
   System.out.println("to celsius, or from celsius to fahrenheit.");
   System.out.println("Examples: 32F or 78C");
   input = keyboard.next();
   
   //Checks the string to see if it is in the correct format of a number
   //And then the letter F or C. 
   String pattern = "^[0-9]+(.[0-9]+)?([CcFf]{1}$)";
   Pattern r = Pattern.compile(pattern);
   Matcher m = r.matcher(input);
   
   //If input is not in the right format continues to prompts user. 
   while(!m.find()){
	   System.out.println("Incorrect format, try again.");
	   input = keyboard.next();
	   m = r.matcher(input);
   }
   
   //Creates Printstream object 
   PrintStream p = new PrintStream(s.getOutputStream());
   p.println(input);
   temp = sc1.next();
   
   //Prints output after server conversion. 
   System.out.println(temp);
	}
	
	catch(IOException ex){
		System.out.println("Error connecting to server.");
	}
}
} 
