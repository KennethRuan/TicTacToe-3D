/*
Name: Kenneth Ruan
Due Date: January 10th, 2020
Program Description:
This program is a spin on traditional tic-tac-toe where the game will be played in 3D between two players.
Like traditional Tic-Tac-Toe, players will take turns playing their piece on a grid. The first
player to get 3 pieces in a row wins. However,in this 3-D remix, there will be 3 grids stacked
on top of each other, and the player can win by getting 3 in a row in any direction.
*/

import java.awt.*;
import hsa.Console;
import javax.swing.*;
import java.io.*;
import java.util.*;

class TicTacToe
{
    /*
	Global Variables:
	Name            Type            Description
	c               Console         used to store global console object
	window          char            stores the current screen the user is on
	word            String          stores the string that the user types in in the menus
	p1Name          String          stores the first player's name so it can be displayed in game
	p2Name          String          stores the second player's name so it can be displayed in game
	white           Color           stores a Color object to be used in the graphics
	background      Color           stores a Color object to be used in the graphics
	black           Color           stores a Color object to be used in the graphics
	red             Color           stores a Color object to be used in the graphics
	darkRed         Color           stores a Color object to be used in the graphics
	purple          Color           stores a Color object to be used in the graphics
	blue            Color           stores a Color object to be used in the graphics
	darkBlue        Color           stores a Color object to be used in the graphics
	scores          String[][]      2D array storing username and win pairs, used to get the top users for leaderboard
	moves           int[][][]       3D array storing the moves made by players. The values will either be 0, 1 or 2 representing no play or player 1/2 respectively. Used to fill in grid and calculate winning moves 
	p1Score         int             stores the amount of wins player 1 has gotten, used to update leaderboards
	p2Score         int             stores the amount of wins player 2 has gotten, used to update leaderboards
	winner          int             stores the winner of the current match
	playerCount     int             stores the amount of players that are in the scores database, used to process the information and update leaderboards
    */
    Console c;
    char window = 'm';
    String word = "";
    String p1Name;
    String p2Name;
    Color white = new Color (255, 255, 255);
    Color background = new Color (50, 48, 26);
    Color black = new Color (0, 0, 0);
    Color red = new Color (221, 46, 68);
    Color darkRed = new Color (181, 6, 28);
    Color purple = new Color (203, 108, 230);
    Color blue = new Color (82, 113, 255);
    Color darkBlue = new Color (19, 36, 110);
    String[] [] scores = new String [500] [2];
    int[] [] [] moves = new int[] [] []{{{0, 0, 0},{0, 0, 0},{0, 0, 0}},{{0, 0, 0},{0, 0, 0},{0, 0, 0}},{{0, 0, 0},{0, 0, 0},{0, 0, 0}}};
    int p1Score;
    int p2Score;
    int winner = 0;
    int playerCount = 0;

    /*
	Class Constructor used to initialize Console object
    */
    public TicTacToe ()
    {
	c = new Console ("TicTacToe");
    }

    /*
	splashScreen method displays graphics at the beginning of the program
	Return Type - void
	
	Global Variables:
	Name            Type            Description
	G               final double    Initialized a constant for gravity that affects how fast the objects fall in
	BOUNCE          final double    Initialized a constant for how much the objects bounce/how much energy they lose after hitting the ground
	xY              double          stores the y position of the x symbol
	xYSpeed         double          stores the speed at which the x symbol falls
	oY              double          stores the y position of the o symbol
	oYSpeed         double          stores the speed at which the o symbol falls
	
	Conditionals:
	First nested for loop animates background being filled in
	Second for loop animates the objects bouncing
	Third for loop makes the title slide in
    */
    public void splashScreen ()
    {
	//Bounce effect inspired by //https://www.youtube.com/watch?v=UoUH8UyJYEE, adjusted code is used for the animation
	final double G = 0.8;
	final double BOUNCE = -0.75;

	double xY = -100;
	double xYSpeed = 0;
	double oY = -220;
	double oYSpeed = 0;

	c.setColor (background);
	for (int i = 0 ; i < 8 ; i++)
	{
	    for (int y = 0 ; y < 500 ; y += 10)
	    {
		c.fillRect (0 + 80 * i, y, 80, 10);
		try
		{
		    Thread.sleep (10);
		}
		catch (Exception e){}
	    }
	}
	try
	{
	    Thread.sleep (1000);
	}
	catch (Exception e){}
	for (int i = 0 ; i < 250 ; i++)
	{
	    c.setColor (background);
	    c.fillRect (0, 0, 640, 500);

	    xY += xYSpeed; //Increases the y position by the speed, imitating a falling effect
	    xYSpeed += G; //Increases the speed the object falls at, imitating gravity making an object fall faster
	    if (xY > 320) //If the object passes 320 it bounces and loses energy
	    {
		xYSpeed *= BOUNCE;
		if (xYSpeed < 0 && xYSpeed > -7) //If the speed it bounces up at is too low, set it to 0 so it doesn't jitter forever
		{
		    xYSpeed = 0;
		    xY = 320;
		}
	    }

	    oY += oYSpeed; //Increases the y position by the speed, imitating a falling effect
	    oYSpeed += G; //Increases the speed the object falls at, imitating gravity making an object fall faster
	    if (oY > 200) //If the object passes 200 it bounces and loses energy
	    {
		oYSpeed *= BOUNCE;
		if (oYSpeed < 0 && oYSpeed > -7) //If the speed it bounces up at is too low, set it to 0 so it doesn't jitter forever
		{
		    oYSpeed = 0;
		    oY = 200;
		}
	    }
	    c.setColor (darkRed);
	    c.fillPolygon (new int[]{434, 444, 524, 514}, new int[]{(int) xY + 14, (int) xY + 4, (int) xY + 84, (int) xY + 94}, 4);
	    c.fillPolygon (new int[]{434, 444, 524, 514}, new int[]{(int) xY + 84, (int) xY + 94, (int) xY + 14, (int) xY + 4}, 4);
	    c.fillOval (433, (int) xY + 4, 14, 14);
	    c.fillOval (432, (int) xY + 82, 14, 14);
	    c.fillOval (514, (int) xY + 84, 14, 14);
	    c.fillOval (512, (int) xY + 2, 14, 14);
	    c.setColor (red);
	    c.fillPolygon (new int[]{430, 440, 520, 510}, new int[]{(int) xY + 10, (int) xY, (int) xY + 80, (int) xY + 90}, 4);
	    c.fillPolygon (new int[]{430, 440, 520, 510}, new int[]{(int) xY + 80, (int) xY + 90, (int) xY + 10, (int) xY}, 4);
	    c.fillOval (429, (int) xY, 14, 14);
	    c.fillOval (428, (int) xY + 78, 14, 14);
	    c.fillOval (510, (int) xY + 80, 14, 14);
	    c.fillOval (508, (int) xY - 2, 14, 14);


	    c.setColor (darkBlue);
	    c.fillOval (524, (int) oY + 4, 100, 100);
	    c.setColor (blue);
	    c.fillOval (520, (int) oY, 100, 100);
	    c.setColor (darkBlue);
	    c.fillOval (535, (int) oY + 15, 70, 70);
	    c.setColor (background);
	    c.fillOval (540, (int) oY + 15, 65, 70);
	    try
	    {
		Thread.sleep (15);
	    }
	    catch (Exception e){}
	}
	for (int x = -500 ; x <= 30 ; x += 10)
	{
	    c.setColor (background);
	    c.fillRect (0, 0, 640, 130);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 70));
	    c.setColor (purple);
	    c.drawString ("TIC TAC TOE 3D", x, 104);
	    c.setColor (white);
	    c.drawString ("TIC TAC TOE 3D", x - 4, 100);
	    try
	    {
		Thread.sleep (15);
	    }
	    catch (Exception e)
	    {
	    }
	}
	try
	{
	    Thread.sleep (1500);
	}
	catch (Exception e)
	{
	}
	getScores ();
    }

    /*
	getScores method reads from the score.txt file to get existing scores from the database
	Return type - void
	
	Variables:
	Name            Type            Description
	f               File            used with the built-in method .exists() to check if the scores.txt file has been deleted
	pw              PrintWriter     PrintWriter object allows the file to be created in case it was deleted and preventing the game from load
	br              BufferedReader  BufferedReader object that allows the file to be read and for input to be saved to
	
	Conditionals:
	For loop iterates through each line and reads the data
    */

    private void getScores ()
    {
	try
	{
	    File f = new File("scores.txt");
	    if (!f.exists()){
		PrintWriter pw = new PrintWriter(new FileWriter("scores.txt"));
		pw.println("0");
		pw.close();
	    }
	    BufferedReader br = new BufferedReader (new FileReader ("scores.txt"));
	    playerCount = Integer.parseInt (br.readLine ());
	    for (int i = 0 ; i < playerCount ; i++)
	    {
		scores [i] [0] = br.readLine ();
		scores [i] [1] = br.readLine ();

	    }

	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog (null, "An error occurred while loading the game, please try again!", "Error", JOptionPane.ERROR_MESSAGE);
	    c.close ();
	}

    }

    /*
	scoreToFile method loops through each element in the scores array and writes to the score.txt file to update the file
	Return type - void
	
	Variables:
	Name            Type            Description
	pw              PrintWriter     PrintWriter object that allows the file to be written to
	
	Conditionals:
	For loop iterates through each line and writes the data from the array to the file
    */
    private void scoreToFile ()
    {
	try
	{
	    PrintWriter pw = new PrintWriter (new FileWriter ("scores.txt"));
	    pw.println (playerCount);
	    for (int i = 0 ; i < playerCount ; i++)
	    {
		pw.println (scores [i] [0]);
		pw.println (scores [i] [1]);
	    }
	    pw.close ();

	}
	catch (Exception e)
	{
	    JOptionPane.showMessageDialog (null, "An error occurred while saving the scores!", "Error", JOptionPane.ERROR_MESSAGE);
	}
    }

    /*
	background method draws the elements in the menu background
	Return type - void
    */

    public void background ()
    {
	c.setColor (background);
	c.fillRect (0, 0, 640, 500);
	c.setColor (darkRed);
	c.fillPolygon (new int[]
	{
	    434, 444, 524, 514
	}
	, new int[]
	{
	    334, 324, 404, 414
	}
	, 4);
	c.fillPolygon (new int[]
	{
	    434, 444, 524, 514
	}
	, new int[]
	{
	    404, 414, 334, 324
	}
	, 4);
	c.fillOval (433, 324, 14, 14);
	c.fillOval (432, 402, 14, 14);
	c.fillOval (514, 404, 14, 14);
	c.fillOval (512, 322, 14, 14);
	c.setColor (red);
	c.fillPolygon (new int[]
	{
	    430, 440, 520, 510
	}
	, new int[]
	{
	    330, 320, 400, 410
	}
	, 4);
	c.fillPolygon (new int[]
	{
	    430, 440, 520, 510
	}
	, new int[]
	{
	    400, 410, 330, 320
	}
	, 4);
	c.fillOval (429, 320, 14, 14);
	c.fillOval (428, 398, 14, 14);
	c.fillOval (510, 400, 14, 14);
	c.fillOval (508, 318, 14, 14);
	c.setColor (darkBlue);
	c.fillOval (524, 204, 100, 100);
	c.setColor (blue);
	c.fillOval (520, 200, 100, 100);
	c.setColor (darkBlue);
	c.fillOval (535, 215, 70, 70);
	c.setColor (background);
	c.fillOval (540, 215, 65, 70);
    }

    /*
	mainMenu method allows the user to navigate to different features by typing the options
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed
	validInput      boolean         boolean to evaluate whether or not the user has inputted a valid option
	
	Conditionals:
	while loop allows the user to input characters until enter is pressed and the loop is broken out of
    */
    public void mainMenu ()
    {
	char character;
	boolean validInput = true;

	while (true)
	{
	    background ();
	    c.setFont (new Font ("Monospaced", Font.BOLD, 70));
	    c.setColor (purple);
	    c.drawString ("TIC TAC TOE 3D", 30, 104);
	    c.setColor (white);
	    c.drawString ("TIC TAC TOE 3D", 26, 100);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 50));
	    c.drawString ("| " + word + " |", 320 - 15 * (word.length () + 4), 400); // Monospace character is 6/10 the font size, half is 3/10
	    c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	    c.drawString ("Type a number or word below and then press enter:", 26, 200);
	    c.drawString ("(1) PLAY   (2) INSTRUCTIONS  (3) LEADERBOARD", 56, 270);
	    c.drawString ("(4) QUIT", 272, 295);
	    if (validInput == false) //If an invalid input has been entered, a message is displayed
	    {
		c.setColor (white);
		c.setFont (new Font ("Monospaced", Font.BOLD, 20));
		c.drawString ("Please enter a valid option", 162, 460);
	    }
	    character = c.getChar ();
	    
	    if (character == 8) //If backspace is pressed
	    {
		if (word.length () == 1) //If the word has only 1 character, it removes it
		{
		    word = "";
		}
		else if (word.length () > 1) //If the word is greater than 1 character, it uses substring to remove the last letter
		{
		    word = word.substring (0, word.length () - 1);
		}
	    }
	    if (word.length () < 12) //If the word length is less than 12, it allows the user inputted character to be added, so the string cannot exceed 12 characters
	    {
		if ((character >= 97 && character <= 122))
		{
		    word += (char) (character - 32);
		}
		if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		{
		    word += character;
		}
	    }

	    if (character == 10) //If enter is pressed, the window is changed to the appropriate screen
	    {
		if (word.equals ("EXIT") || word.equals ("E") || word.equals ("4"))
		{
		    window = 'e';
		    word = "";
		    break;
		}
		if (word.equals ("PLAY") || word.equals ("P") || word.equals ("1"))
		{
		    window = 'p';
		    word = "";
		    break;
		}
		if (word.equals ("INSTRUCTIONS") || word.equals ("I") || word.equals ("2"))
		{
		    window = 'i';
		    word = "";
		    break;
		}
		if (word.equals ("LEADERBOARD") || word.equals ("L") || word.equals ("3"))
		{
		    window = 'l';
		    word = "";
		    break;
		}
		else
		{
		    try
		    {
			throw new IllegalArgumentException ();
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a listed option.", "Error",JOptionPane.ERROR_MESSAGE);
			validInput = false;
		    }
		    word = "";
		}
	    }
	}
    }

    /*
	gameMenu method allows the user to navigate to return to menu or play the game and also takes in the player's name to record the score
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed
	validInput      boolean         boolean to evaluate whether or not the user has inputted a valid option
	nameExists      boolean         boolean to check if the name already exists in the database, if it doesn't a new entry is added to the database 
	
	Conditionals:
	while loop allows the user to input characters until enter is pressed and the loop is broken out of
	for loop iterates through the score array to check if the user already exists in the database
    */
    public void gameMenu ()
    {
	char character;
	boolean validInput = true;
	boolean nameExists = false;
	while (true)
	{
	    background ();
	    c.setFont (new Font ("Monospaced", Font.BOLD, 60));
	    c.setColor (purple);
	    c.drawString ("ENTER AN OPTION", 50, 104);
	    c.setColor (white);
	    c.drawString ("ENTER AN OPTION", 46, 100);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 50));
	    c.drawString ("| " + word + " |", 320 - 15 * (word.length () + 4), 300);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.drawString ("(1) CONTINUE", 43, 200);
	    c.drawString ("(2) BACK", 408, 200);
	    if (validInput == false)
	    {
		c.setColor (white);
		c.setFont (new Font ("Monospaced", Font.BOLD, 20));
		c.drawString ("Please enter a valid option", 162, 350);
	    }
	    character = c.getChar ();
	    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
	    if (character == 8)
	    {
		if (word.length () == 1)
		{
		    word = "";
		}
		else if (word.length () > 1)
		{
		    word = word.substring (0, word.length () - 1);
		}
	    }
	    if (word.length () < 12)
	    {
		if ((character >= 97 && character <= 122))
		{
		    word += (char) (character - 32);
		}
		if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		{
		    word += character;
		}
	    }

	    if (character == 10)
	    {
		if (word.equals ("CONTINUE") || word.equals ("C") || word.equals ("1"))
		{
		    validInput = true;
		    word = "";
		    window = 'g';
		    break;
		}
		if (word.equals ("BACK") || word.equals ("B") || word.equals ("2"))
		{
		    window = 'm';
		    word = "";
		    break;
		}
		else
		{
		    try
		    {
			throw new IllegalArgumentException ();
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a listed option!", "Error",
				JOptionPane.ERROR_MESSAGE);
			validInput = false;
		    }
		    word = "";
		}
	    }
	}
	if (window == 'g') //If the user chooses to continue to the game, the players are asked for their name
	{
	    p1Name = "";
	    p2Name = "";
	    while (true)
	    {
		if (p1Name.length () == 0) //Checks if the first player has entered a name as it must be at least 1 character
		{
		    background ();
		    c.setFont (new Font ("Monospaced", Font.BOLD, 60));
		    c.setColor (purple);
		    c.drawString ("ENTER YOUR NAME", 50, 104);
		    c.setColor (white);
		    c.drawString ("ENTER YOUR NAME", 46, 100);
		    c.setFont (new Font ("Monospaced", Font.BOLD, 40));
		    c.setColor (red);
		    c.drawString ("Player 1", 64, 200);
		    c.setColor (white);
		    c.drawString ("| " + word + " |", 160 - 12 * (word.length () + 4), 300);
		    c.setColor (blue);
		    c.drawString ("Player 2", 384, 200);

		    character = c.getChar ();
		    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
		    if (character == 8)
		    {
			if (word.length () == 1)
			{
			    word = "";
			}
			else if (word.length () > 1)
			{
			    word = word.substring (0, word.length () - 1);
			}
		    }
		    if (word.length () < 10)
		    {
			if ((character >= 97 && character <= 122))
			{
			    word += (char) (character - 32);
			}
			if ((character >= 65 && character <= 90) || character == 32
				|| (character >= 48 && character <= 56))
			{
			    word += character;
			}
		    }

		    if (character == 10)
		    {
			if (word.length () == 0)
			{
			    try
			    {
				word = "";
				throw new IllegalArgumentException ();
			    }
			    catch (IllegalArgumentException e)
			    {
				JOptionPane.showMessageDialog (null, "Your name must contain at least one letter!",
					"Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			else
			{
			    p1Name = word;
			    for (int i = 0 ; i < playerCount ; i++)
			    {
				if (scores [i] [0].equals (p1Name))
				{
				    nameExists = true;
				}
			    }
			    if (!nameExists) //If the player's name doesn't exist, a new entry is added
			    {
				scores [playerCount] [0] = p1Name;
				scores [playerCount] [1] = "0";
				playerCount++;
			    }
			    nameExists = false;
			    word = "";

			}
		    }
		}
		else
		{
		    background ();
		    c.setFont (new Font ("Monospaced", Font.BOLD, 60));
		    c.setColor (purple);
		    c.drawString ("ENTER YOUR NAME", 50, 104);
		    c.setColor (white);
		    c.drawString ("ENTER YOUR NAME", 46, 100);
		    c.setFont (new Font ("Monospaced", Font.BOLD, 40));
		    c.setColor (red);
		    c.drawString ("Player 1", 64, 200);
		    c.setColor (white);
		    c.drawString (p1Name, 160 - 12 * p1Name.length (), 300);
		    c.setColor (blue);
		    c.drawString ("Player 2", 384, 200);
		    c.setColor (white);
		    c.drawString ("| " + word + " |", 480 - 12 * (word.length () + 4), 300);
		    character = c.getChar ();
		    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
		    if (character == 8)
		    {
			if (word.length () == 1)
			{
			    word = "";
			}
			else if (word.length () > 1)
			{
			    word = word.substring (0, word.length () - 1);
			}
		    }
		    if (word.length () < 10)
		    {
			if ((character >= 97 && character <= 122))
			{
			    word += (char) (character - 32);
			}
			if ((character >= 65 && character <= 90) || character == 32
				|| (character >= 48 && character <= 56))
			{
			    word += character;
			}
		    }

		    if (character == 10)
		    {
			if (word.length () == 0)
			{
			    try
			    {
				word = "";
				throw new IllegalArgumentException ();
			    }
			    catch (IllegalArgumentException e)
			    {
				JOptionPane.showMessageDialog (null, "Your name must contain at least one letter!",
					"Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			else if (word.equals (p1Name)) //The second player's name cannot match the first
			{
			    try
			    {
				word = "";
				throw new IllegalArgumentException ();
			    }
			    catch (IllegalArgumentException e)
			    {
				JOptionPane.showMessageDialog (null, "Your name can't match the first player's name!",
					"Error", JOptionPane.ERROR_MESSAGE);
			    }
			}
			else
			{
			    p2Name = word;
			    for (int i = 0 ; i < playerCount ; i++)
			    {
				if (scores [i] [0].equals (p2Name))
				{
				    nameExists = true;
				}
			    }
			    if (!nameExists) //If the player's name doesn't exist, a new entry is added
			    {
				scores [playerCount] [0] = p2Name;
				scores [playerCount] [1] = "0";
				playerCount++;
			    }
			    nameExists = false;
			    word = "";

			    break;
			}
		    }
		}
	    }

	}
    }


    /*
	instructions method allows the user to read the instructions and then return to the mainMenu
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed

	Conditionals:
	while loop allows the user to input characters until enter is pressed and the loop is broken out of
    */
    public void instructions ()
    {
	char character;
	background ();
	c.setFont (new Font ("Monospaced", Font.BOLD, 70));
	c.setColor (purple);
	c.drawString ("INSTRUCTIONS", 72, 104);
	c.setColor (white);
	c.drawString ("INSTRUCTIONS", 68, 100);
	c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	c.drawString ("(1) BACK", 248, 400);
	c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	c.drawString ("Like traditional Tic-Tac-Toe, players will take", 38, 170);
	c.drawString ("turns playing their piece on a grid. The first", 44, 200);
	c.drawString ("player to get 3 pieces in a row wins. However,", 44, 230);
	c.drawString ("in this 3-D remix, there will be 3 grids stacked", 32, 260);
	c.drawString ("on top of each other, and the player can win by", 38, 290);
	c.drawString ("getting 3 in a row in any direction.", 104, 320);
	while (true)
	{
	    c.setColor (background);
	    c.fillRect (0, 420, 640, 50);
	    c.setColor (white);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.drawString ("| " + word + " |", 320 - 9 * (word.length () + 4), 450);

	    character = c.getChar ();
	    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
	    if (character == 8)
	    {
		if (word.length () == 1)
		{
		    word = "";
		}
		else if (word.length () > 1)
		{
		    word = word.substring (0, word.length () - 1);
		}
	    }
	    if (word.length () < 12)
	    {
		if ((character >= 97 && character <= 122))
		{
		    word += (char) (character - 32);
		}
		if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		{
		    word += character;
		}
	    }

	    if (character == 10)
	    {
		if (word.equals ("BACK") || word.equals ("B") || word.equals ("1"))
		{
		    window = 'm';
		    word = "";
		    break;
		}
		else
		{
		    try
		    {
			throw new IllegalArgumentException ();
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a listed option.", "Error",
				JOptionPane.ERROR_MESSAGE);
		    }
		    word = "";
		}
	    }
	}
    }

    /*
	leaderboard method figures out the top 10 players based on wins and allows the user to view them
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed
	lb              String[][]      keeps track of leaderboard positions
	pw              PrintWriter     the PrintWriter object allows a file to be written to and consequently cleared, this is used if the user wishes to clear the scores

	Conditionals:
	while loop allows the user to input characters until enter is pressed and the loop is broken out of
	nested for loop iterates through every score and then each leaderboard position and updates it accordingly
	for loop iterates through the leaderboard position and displays them
    */
    public void leaderboard ()
    {
	char character;
	background ();
	c.setFont (new Font ("Monospaced", Font.BOLD, 70));
	c.setColor (purple);
	c.drawString ("LEADERBOARD", 93, 104);
	c.setColor (white);
	c.drawString ("LEADERBOARD", 89, 100);
	c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	c.drawString ("(1) BACK", 248, 420);
	c.drawString("NAME",124,140);
	c.drawString("WINS",444,140);
	c.setFont (new Font ("Monospaced", Font.BOLD, 15));
	c.drawString("*Enter 'CLEAR' or 'C' to clear the scores",135,495);
	String[] [] lb = new String[] []
	{
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	}
	;
	for (int i = 0 ; i < playerCount ; i++) //Loops through every single player in the database so that their score can be retrieved
	{
	    for (int j = 0 ; j < 10 ; j++) //Loops through each leaderboard position
	    {
		if (Integer.parseInt (scores [i] [1]) >= Integer.parseInt (lb [j] [1])) //If the current player has a higher score than a leaderboard player
		{
		    for (int x = 9 ; x > j ; x--) //Loops through every leaderboard player and pushes them down
		    {
			lb [x] [1] = lb [x - 1] [1];
			lb [x] [0] = lb [x - 1] [0];
		    }
		    lb [j] [1] = scores [i] [1]; //Assigns the current player to the appropriate leaderboard spot
		    lb [j] [0] = scores [i] [0];
		    break; //Breaks so that the current player doesn't get filled into every spot below it
		}
	    }
	}
	c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	for (int i = 0 ; i < 10 ; i++)
	{
	    c.setColor (white);
	    if (lb [i] [0].equals (p1Name) && !lb[i][1].equals("0")) //Chooses display colour based on the player
		c.setColor (red);
	    if (lb [i] [0].equals (p2Name) && !lb[i][1].equals("0"))
		c.setColor (blue);
	    if (lb [i] [1].equals ("0"))
	    {
		c.drawString ("--", 148, 170 + 25 * i);
		c.drawString ("--", 468, 170 + 25 * i);
	    }
	    else
	    {
		c.drawString (lb [i] [0], 160 - (lb [i] [0].length () * 6), 170 + 25 * i);
		c.drawString (lb [i] [1], 480 - (lb [i] [1].length () * 6), 170 + 25 * i);
	    }
	}
	while (true)
	{
	    c.setColor (background);
	    c.fillRect (0, 440, 640, 40);
	    c.setColor (white);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.drawString ("| " + word + " |", 320 - 9 * (word.length () + 4), 470);
	    character = c.getChar ();
	    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
	    if (character == 8)
	    {
		if (word.length () == 1)
		{
		    word = "";
		}
		else if (word.length () > 1)
		{
		    word = word.substring (0, word.length () - 1);
		}
	    }
	    if (word.length () < 12)
	    {
		if ((character >= 97 && character <= 122))
		{
		    word += (char) (character - 32);
		}
		if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		{
		    word += character;
		}
	    }

	    if (character == 10)
	    {
		if (word.equals ("BACK") || word.equals ("B") || word.equals ("1"))
		{
		    window = 'm';
		    word = "";
		    break;
		}
		if (word.equals ("CLEAR") || word.equals ("C")) //If the user chooses to clear the high scores
		{
		    try{
			for (int i = 0; i < playerCount; i++){ //Resets scores array
			    scores[i][0] = "";
			    scores[i][1] = "0";
			}
			//Clears the scores.txt file
			PrintWriter pw = new PrintWriter(new FileWriter("scores.txt"));
			pw.println("0");
			pw.close();
			//Redraw the leaderboard with blank entries
			background ();
			c.setFont (new Font ("Monospaced", Font.BOLD, 70));
			c.setColor (purple);
			c.drawString ("LEADERBOARD", 93, 104);
			c.setColor (white);
			c.drawString ("LEADERBOARD", 89, 100);
			c.setFont (new Font ("Monospaced", Font.BOLD, 30));
			c.drawString ("(1) BACK", 248, 420);
			c.drawString("NAME",124,140);
			c.drawString("WINS",444,140);
			c.setFont (new Font ("Monospaced", Font.BOLD, 20));
			for (int i = 0 ; i < 10 ; i++)
			{
			    c.drawString ("--", 148, 170 + 25 * i);
			    c.drawString ("--", 468, 170 + 25 * i);
			}
			
		    }
		    catch(IOException e){
			JOptionPane.showMessageDialog (null, "An error occured while clearing the leaderboard. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		    }
		    word = "";
		}
		else
		{
		    try
		    {
			throw new IllegalArgumentException ();
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a listed option.", "Error",
				JOptionPane.ERROR_MESSAGE);
		    }
		    word = "";
		}
	    }
	}
    }

    /*
	gameLoop method includes all the gameplay and game processing including checking for wins
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed
	lastMove        String          stores the player's last move so that checking for a win can be based on that spot
	
	Conditionals:
	triple nested for loop clears the board by making all moves = 0
	first outter while loop is the game loop that is only broken out of once a user wins
	the two inner while loops run for each player's turn until enter is pressed
    */
    public void gameLoop ()
    {
	char character;
	String lastMove = "";
	//Loops through each element in moves and resets it so that the board is empty
	for (int i = 0 ; i < 3 ; i++)
	{
	    for (int j = 0 ; j < 3 ; j++)
	    {
		for (int k = 0 ; k < 3 ; k++)
		{
		    moves [i] [j] [k] = 0;
		}
	    }
	}

	c.setColor (background);
	c.fillRect (0, 0, 640, 500);
	//Adjusts the display sizes depending on how long the names are
	if (p1Name.length () > 6 || p2Name.length () > 6)
	{
	    c.setFont (new Font ("Monospaced", Font.BOLD, 40));
	    c.setColor (red);
	    c.drawString (p1Name, 284 - 24 * p1Name.length (), 60);
	    c.setColor (white);
	    c.drawString (" VS ", 284, 60);
	    c.setColor (blue);
	    c.drawString (p2Name, 380, 60);
	}
	else
	{
	    c.setFont (new Font ("Monospaced", Font.BOLD, 60));
	    c.setColor (red);
	    c.drawString (p1Name, 248 - 36 * p1Name.length (), 60);
	    c.setColor (white);
	    c.drawString (" VS ", 248, 60);
	    c.setColor (blue);
	    c.drawString (p2Name, 392, 60);
	}


	c.setColor (white);
	c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	c.drawString ("ENTER YOUR MOVE:", 64, 240);
	c.drawString ("FORMAT", 124, 320);
	c.drawString ("1A", 148, 350);
	while (true) //gameLoop
	{
	    c.setColor (background);
	    c.fillRect (80, 160, 160, 50);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.setColor (red);
	    c.drawString ("Player 1", 88, 200);
	    fillBoard ();
	    // Player 1's turn
	    while (true) 
	    {
		c.setColor (background);
		c.setFont (new Font ("Monospaced", Font.BOLD, 20));
		c.fillRect (148, 250, 30, 30);
		c.setColor (white);
		c.drawString (word, 148, 270);
		character = c.getChar ();
		//Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
		if (character == 8)
		{
		    if (word.length () == 1)
		    {
			word = "";
		    }
		    else if (word.length () > 1)
		    {
			word = word.substring (0, word.length () - 1);
		    }
		}
		if (word.length () < 2)
		{
		    if ((character >= 97 && character <= 122))
		    {
			word += (char) (character - 32);
		    }
		    if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		    {
			word += character;
		    }
		}

		if (character == 10)
		{
		    if (word.length () < 2)
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null, "Please follow the format shown in-game!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else if (!(word.charAt (1) >= 65 && word.charAt (1) <= 73 && word.charAt (0) >= 49
				&& word.charAt (0) <= 51))
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null,
				    "Please enter a number or letter that corresponds to a grid space!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else if (moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] == 1
			    || moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] == 2)
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null, "Please pick an empty square to play in!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else
		    {
			moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] = 1; //changes the letter moves to number form where A = 0
			lastMove = "";
			lastMove += word.charAt (0) - 49; //changes the letter moves to number form where A = 0
			lastMove += (word.charAt (1) - 65) / 3; //changes the letter moves to number form where A = 0
			lastMove += (word.charAt (1) - 65) % 3; //changes the letter moves to number form where A = 0
			fillBoard (); //Draws the board
			word = "";
			break;
		    }
		    word = "";
		}
	    }
	    c.setColor (background);
	    c.fillRect (80, 160, 160, 50);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.setColor (blue);
	    c.drawString ("Player 2", 88, 200);
	    if (checkWin (1, lastMove)) //If the first player won
	    {
		for (int i = 0 ; i < playerCount ; i++) //Update the score array
		{
		    if (scores [i] [0].equals (p1Name))
			scores [i] [1] = "" + (Integer.parseInt (scores [i] [1]) + 1);
		}
		winner = 1;
		break;
	    }
	    //Player 2's turn
	    while (true)
	    {
		c.setColor (background);
		c.setFont (new Font ("Monospaced", Font.BOLD, 20));
		c.fillRect (148, 250, 30, 30);
		c.setColor (white);
		c.drawString (word, 148, 270);
		character = c.getChar ();
		//Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
		if (character == 8)
		{
		    if (word.length () == 1)
		    {
			word = "";
		    }
		    else if (word.length () > 1)
		    {
			word = word.substring (0, word.length () - 1);
		    }
		}
		if (word.length () < 2)
		{
		    if ((character >= 97 && character <= 122))
		    {
			word += (char) (character - 32);
		    }
		    if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		    {
			word += character;
		    }
		}

		if (character == 10)
		{
		    if (word.length () < 2)
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null, "Please follow the format shown in-game!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else if (!(word.charAt (1) >= 65 && word.charAt (1) <= 73 && word.charAt (0) >= 49
				&& word.charAt (0) <= 51))
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null,
				    "Please enter a number or letter that corresponds to a grid space!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else if (moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] == 1
			    || moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] == 2)
		    {
			try
			{
			    throw new IllegalArgumentException ();
			}
			catch (IllegalArgumentException e)
			{
			    JOptionPane.showMessageDialog (null, "Please pick an empty square to play in!", "Error",
				    JOptionPane.ERROR_MESSAGE);
			}
		    }
		    else
		    {
			moves [word.charAt (0) - 49] [(word.charAt (1) - 65) / 3] [(word.charAt (1) - 65) % 3] = 2; //changes the letter moves to number form where A = 0
			lastMove = "";
			lastMove += word.charAt (0) - 49; //changes the letter moves to number form where A = 0
			lastMove += (word.charAt (1) - 65) / 3; //changes the letter moves to number form where A = 0
			lastMove += (word.charAt (1) - 65) % 3; //changes the letter moves to number form where A = 0
			fillBoard (); //Draws the board
			word = "";
			break;
		    }
		    word = "";
		}
	    }
	    if (checkWin (2, lastMove)) //If the second player wins
	    {
		for (int i = 0 ; i < playerCount ; i++) //Loop through the score array and then updates their score
		{
		    if (scores [i] [0].equals (p2Name))
			scores [i] [1] = "" + (Integer.parseInt (scores [i] [1]) + 1);
		}
		winner = 2;
		break;
	    }
	}
    }

    /*
	gameOver method shows a navigation menu after a player has won, it also shows a mini leaderboard
	Return type - void

	Variables:
	Name            Type            Description
	character       char            saves the last key pressed by the user so that it can be processed
	validInput      boolean         boolean to evaluate whether or not the user has inputted a valid option
	lb              String[][]      keeps track of leaderboard positions
	
	Conditionals:
	nested for loop iterates through every score and then each leaderboard position and updates it accordingly
	while loop allows the user to input characters until enter is pressed and the loop is broken out of
	for loop iterates through the leaderboard position and displays them
    */
    public void gameOver ()
    {
	char character;
	boolean validInput = true;
	String[] [] lb = new String[] [] //initializes the leaderboard
	{
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	    ,
	    {
		"", "0"
	    }
	}
	;
	for (int i = 0 ; i < playerCount ; i++) //Loops through each player in the database
	{
	    for (int j = 0 ; j < 3 ; j++) //Loops through each leaderboard position
	    {
		if (Integer.parseInt (scores [i] [1]) >= Integer.parseInt (lb [j] [1])) //If the current score is greater than the leaderboard score
		{
		    for (int x = 2 ; x > j ; x--) //Push all of the leaderboard scores down
		    {
			lb [x] [1] = lb [x - 1] [1];
			lb [x] [0] = lb [x - 1] [0];
		    }
		    lb [j] [1] = scores [i] [1]; //Put the current user in the appropriate leaderboard spot
		    lb [j] [0] = scores [i] [0];
		    break;
		}
	    }
	}
	scoreToFile (); //updates the score file


	while (true)
	{
	    background ();
	    c.setFont (new Font ("Monospaced", Font.BOLD, 70));
	    if (winner == 1) //Sets the winner colour according to which player it was
		c.setColor (red);
	    else
		c.setColor (blue);
	    c.drawString ("Player " + winner + " Wins!", 30, 104);
	    c.setColor (white);
	    c.drawString ("Player " + winner + " Wins!", 26, 100);

	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.drawString ("LEADERBOARD", 221, 180);
	    c.drawString ("(1) REMATCH", 61, 350);
	    c.drawString ("(2) MENU", 399, 350);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	    for (int i = 0 ; i < 3 ; i++) //Loops through 3 leaderboard spots and displays them
	    {
		c.setColor (white);
		if (lb [i] [0].equals (p1Name) && !lb[i][1].equals("0")) //Chooses display colour based on the player
		    c.setColor (red);
		if (lb [i] [0].equals (p2Name) && !lb[i][1].equals("0"))
		    c.setColor (blue);
		if (lb [i] [1].equals ("0"))
		{
		    c.drawString ("--", 148, 220 + 30 * i);
		    c.drawString ("--", 468, 220 + 30 * i);
		}
		else
		{
		    c.drawString (lb [i] [0], 160 - (lb [i] [0].length () * 6), 220 + 30 * i);
		    c.drawString (lb [i] [1], 480 - (lb [i] [1].length () * 6), 220 + 30 * i);
		}
	    }
	    c.setColor (white);
	    c.setFont (new Font ("Monospaced", Font.BOLD, 30));
	    c.drawString ("| " + word + " |", 320 - 9 * (word.length () + 4), 400); // Monospace character is 6/10 the font size
	    //Keyboard Code; allows the user to navigate through typing and entering words, reference mainMenu for explanation
	    if (validInput == false)
	    {
		c.setColor (white);
		c.setFont (new Font ("Monospaced", Font.BOLD, 20));
		c.drawString ("Please enter a valid option", 162, 460);
	    }
	    character = c.getChar ();

	    if (character == 8)
	    {
		if (word.length () == 1)
		{
		    word = "";
		}
		else if (word.length () > 1)
		{
		    word = word.substring (0, word.length () - 1);
		}
	    }
	    if (word.length () < 12)
	    {
		if ((character >= 97 && character <= 122))
		{
		    word += (char) (character - 32);
		}
		if ((character >= 65 && character <= 90) || character == 32 || (character >= 48 && character <= 56))
		{
		    word += character;
		}
	    }

	    if (character == 10)
	    {
		if (word.equals ("REMATCH") || word.equals ("R") || word.equals ("1"))
		{
		    window = 'g';
		    word = "";
		    break;
		}
		if (word.equals ("MENU") || word.equals ("M") || word.equals ("2"))
		{
		    window = 'p';
		    word = "";
		    break;
		}
		else
		{
		    try
		    {
			throw new IllegalArgumentException ();
		    }
		    catch (IllegalArgumentException e)
		    {
			JOptionPane.showMessageDialog (null, "Please enter a listed option.", "Error",
				JOptionPane.ERROR_MESSAGE);
			validInput = false;
		    }
		    word = "";
		}
	    }
	}
    }

    /*
	fillBoard method is a private method used to draw the game board
	Return type - void


	Conditionals:
	triple nested for loop iterates through the 3D moves array and fills in the appropriate cells
    */
    private void fillBoard ()
    {
	c.setColor (white);
	c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	c.drawString("1",310,148);
	c.drawString("2",310,288);
	c.drawString("3",310,428);
	fillDiamond (480, 140, 150, 60);
	fillDiamond (480, 280, 150, 60);
	fillDiamond (480, 420, 150, 60);

	for (int i = 1 ; i <= 3 ; i++) //Iterates through layers
	{
	    for (int j = 0 ; j < 3 ; j++) //Iterates through rows
	    {
		for (int k = 0 ; k < 3 ; k++) //Iterates through columns
		{
		    if (moves [i - 1] [j] [k] == 1) //Checks if the current cell location has been played by player 1
		    {
			c.setColor (red); 
			fillDiamond (380 + 50 * k + j * 50, 140 * i + 20 * j - 20 * k, 45, 18); //Fills in the cell
		    }
		    else if (moves [i - 1] [j] [k] == 2) //Checks if the current cell location has been played by player 2
		    {
			c.setColor (blue);
			fillDiamond (380 + 50 * k + j * 50, 140 * i + 20 * j - 20 * k, 45, 18); //Fills in the cell
		    }
		    else //If neither player has played in the cell
		    {
			c.setColor (background);
			fillDiamond (380 + 50 * k + j * 50, 140 * i + 20 * j - 20 * k, 45, 18); //Fills a blank cell
			c.setColor (white);
			c.setFont (new Font ("Monospaced", Font.BOLD, 30));
			c.drawString ("" + ((char) (3 * j + k + 65)), 380 + 50 * k + j * 50 - 9,140 * i + 20 * j - 20 * k + 9); //Writes the appropriate letter by corresponding the cell location with ascii values
		    }
		}
	    }
	}
    }


    /*
	checkWin method is a private method used to determine whether a player has made a winning move
	Return type - Boolean

	Arguments:
	Name            Type            Description
	p               int             stores the player who just played as they are the only one who can win on their move
	lastMove        String          stores the coordinates pointing to the player's last move, the checking algorithm will be based around that cell

	Conditionals:
	quadruple nested for loop to generate all possible win scenarios, the outer three loops are used to determine the direction that will be checked, while the inner loop will determine each step
    */
    private boolean checkWin (int p, String lastMove)
    {
	int lay = lastMove.charAt (0) - 48;
	int row = lastMove.charAt (1) - 48;
	int col = lastMove.charAt (2) - 48;
	boolean win;
	boolean win2;
	
	for (int i = -1 ; i <= 1 ; i++) //Loops through the 3 possible directions for the Y axis; up,down or neutral
	{
	    for (int j = -1 ; j <= 1 ; j++) //Loops through the 3 possible directions for the Z axis; left, right or neutral
	    {
		for (int k = -1 ; k <= 1 ; k++) //Loops through the 3 possible directions for the X axis; forward, back or neutral
		{
		    //The win variables are set to true to start, however, if at any step a cell isn't the player's, the variable will be set to false
		    //Note: win and win2 are set to true for every unique direction
		    win = true; 
		    win2 = true;
		    for (int x = 0 ; x < 3 ; x++) 
		    {
			//All 3 directions can't be 0, otherwise it would check the same cell 3 times and the method would always return true
			if (i != 0 || j != 0 || k != 0)
			{
			    //Win Logic for edge pieces
			    //The win logic for edge pieces consists of checking if 3 in a row can be obtained with the current cell being a non-middle piece
			    if ((row + j * x) <= 2 && (row + j * x) >= 0 && (lay + i * x) <= 2 && (lay + i * x) >= 0 && (col + k * x) <= 2 && (col + k * x) >= 0) //Checks if the current cell being checked is in bounds
			    {
				if (moves [lay + i * x] [row + j * x] [col + k * x] != p) //If the cell is in bound, but doesn't belong to the player, set win to false
				{
				    win = false;
				}
			    }
			    else //If the current cell isn't in bounds, the player can't win in this direction as there's no room to get 3 in a row
			    {
				win = false;
			    }
			    //Win logic for center pieces
			    //The win logic for center pieces consists of checking the adjacent pieces as it assumes the center piece was the one played
			    if ((row - j + j * x) <= 2 && (row - j + j * x) >= 0 && (lay - i + i * x) <= 2 && (lay - i + i * x) >= 0 && (col - k + k * x) <= 2 && (col - k + k * x) >= 0) //Checks if the current cell being checked is in bounds
			    {
				if (moves [lay - i + i * x] [row - j + j * x] [col - k + k * x] != p) //If the cell is in bound, but doesn't belong to the player, set win to false
				{
				    win2 = false;
				}
			    }
			    else //If the current cell isn't in bounds, the player can't win in this direction as there's no room to get 3 in a row
			    {
				win2 = false;
			    }
			}
			else
			{
			    win = false;
			    win2 = false;
			}
		    }
		    //If either win scenarios are met, return that the user has won
		    //Note: the algorithm only has to find one way to get 3 in a row to see if a player wins
		    if (win || win2) 
		    {
			return true;
		    }
		}
	    }
	}
	//If a win scenrio couldn't be found after iterating through every possible direction then return false
	//This line won't be reached unless all the for loop combinations have been tried and none of them resulted in a win
	return false;

    }

    /*
	fillDiamond method is used to make filling the grid easier as the code will me made more modular
	Return type - void

	Arguments:
	Name            Type            Description
	x               int             allows the diamond x position to be specified
	y               int             allows the diamond y position to be specified
	l               int             allows the diamond length to be specified
	h               int             allows the diamond height to be specified

    */
    private void fillDiamond (int x, int y, int l, int h)
    {
	c.fillPolygon (new int[]{x, x - l, x, x + l}, new int[]{y - h, y, y + h, y}, 4);
    }

    /*
	goodbye method is used to display the programmer's name, additional info and will close the window
	Return type - void
    */
    public void goodbye ()
    {
	background ();
	c.setFont (new Font ("Monospaced", Font.BOLD, 50));
	c.setColor (purple);
	c.drawString ("THANKS FOR PLAYING", 52, 102);
	c.setColor (white);
	c.drawString ("THANKS FOR PLAYING", 50, 100);
	c.setFont (new Font ("Monospaced", Font.BOLD, 20));
	c.drawString ("Your support is much appreciated!", 122, 170);
	c.drawString ("If you had any problems or inquiries please", 62, 200);
	c.drawString ("contact us at tictactoe.support@gmail.com", 74, 230);
	c.drawString ("We hope to see you again!", 164, 260);
	c.drawString ("Press any key to close the window", 122, 320);
	c.drawString ("This game was made by Kenneth Ruan", 116, 380);
	c.getChar ();
	c.close ();
    }

    /*
	main method controls the program flow and allows the different features to be accessed
	
	Conditionals:
	the while loop allows the user to navigate any windows besides splashScreen and goodbye as much as they'd like
`   */
    public static void main (String[] args)
    {
	TicTacToe t = new TicTacToe ();
	t.splashScreen ();
	while (true)
	{
	    if (t.window == 'm')
		t.mainMenu ();
	    if (t.window == 'p')
		t.gameMenu ();
	    if (t.window == 'g')
	    {
		t.gameLoop ();
		t.gameOver ();
	    }
	    if (t.window == 'e')
		break;
	    if (t.window == 'i')
		t.instructions ();
	    if (t.window == 'l')
		t.leaderboard ();
	}
	t.goodbye ();
    }
}
