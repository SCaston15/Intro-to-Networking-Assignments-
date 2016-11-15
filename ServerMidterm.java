/*
 * Stephanie Caston
 * ITCS 3166
 * MIDTERM
 * 3/7/2016
 */
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerMidterm
{
	public static void main(String args[]) throws IOException
	{
		try
		{
		
		//Variable declarations 
		String input;
		float number; 
		double temp = 0; 
		
		//ServerSocket creation and connection 
		ServerSocket s1 = new ServerSocket(9995);
		Socket ss = s1.accept();
		
		//Gets user input 
		Scanner keyboard = new Scanner(ss.getInputStream());
		input = keyboard.next();
		
		//Sets number to part of string that is a number
		number = Float.parseFloat(input.substring(0, input.length()-1));
		
		//sets input to last character in string or F/C
		input = input.substring(input.length()-1, input.length());
		
		//Creates Printstream object 
		PrintStream p = new PrintStream(ss.getOutputStream());
		
		//Sets up condition to convert to either celsius or fahrenheit
		if(input.equals("F") || input.equals("f"))
		{
			temp = (number - 32) / 1.8;
		}
		else if(input.equals("C") || input.equals("c"))
		{
			temp = (number * 1.8) + 32;  
		}
		
		//prints converted number
		p.print(temp);
	}
	catch(IOException ex)
    {
        //Inform the user of the error
        System.out.println("Error connecting to server.");
    }
	
	}
}
