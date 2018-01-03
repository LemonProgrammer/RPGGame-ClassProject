package models;

import java.util.ArrayList;

public class DiceBag
{
	
	private static ArrayList<Die> dice = new ArrayList<Die>();
	private static int result;
	
	public static ArrayList<Die> createDice(int numOfDice, int numOfSides, boolean hasMods)
	{	
		numOfDice = numOfDice - 1;
				for(int i = 0; i <= numOfDice; i++)
				{
					dice.add(new Die(numOfSides, hasMods));
				}	
		return dice;
	}
	
	public static int showResults(ArrayList<Die> gameDice)
	{	
		for(int i = 0; i < gameDice.size();)
		{
			result += gameDice.get(i).getCurrentVal();
			if(result > 18)
			{
				i = 0;
				result = 0;
			}else if(result < 3)
			{
				i = 0;
				result = 3;
			}
			else
			{
				i++;
			}
		}
		return result;
	}
	
	public static Die createOneDie(int numOfSides, boolean hasMods)
	{	
		return new Die(numOfSides, hasMods);
		
	}

	public static Die drawDie(int index, ArrayList<Die> dice)
	{
		Die drawnDie = dice.get(index);
		return drawnDie;
	}

}
