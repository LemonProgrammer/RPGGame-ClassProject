package models;

import java.util.Random;

/*
 * Every die has a 
 * 	-	Set number of sides.
 * 	-	Can be rolled (select a random value in the appropriate range),
 *  and keeps track of the value of the last roll it made.
 *  Once set, the number of sides for that instance of Die should NEVER be able to change.
 */

public class Die
{
	private Random randomNum = new Random();
	private int sides;
	private boolean hasMods = false;
	private int currentVal;
	
	public Die(int sides, boolean hasMods)
	{
		this.sides = sides;
		this.hasMods = hasMods;
//		randomNum =  new Random();
		currentVal = sides;
		rollDie();
	}
	
	public int rollDie()
	{
		currentVal = Math.abs(randomNum.nextInt(currentVal + 1));
		
		return currentVal;
	}
	
	/**
	 * @return the sides
	 */
	public int getSides()
	{
		return sides;
	}

	/**
	 * @return the currentVal
	 */
	public int getCurrentVal()
	{
		return currentVal;
	}

	@Override
	public String toString()
	{
		return "[" + currentVal + "]";
	}
}
