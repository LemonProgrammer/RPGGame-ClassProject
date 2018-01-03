package potions;

import java.util.Random;

public class Potion
{
	private Random rng = new Random();
	private int points;
	private int randomNum = (rng.nextInt(10) + 1);
	
	public Potion()
	{
		points = randomNum;
	}
	
	public int use()
	{
		int potionPoints = points;
		points = 0;
		return potionPoints;
	}
	
	public int getRandomNum()
	{
		return randomNum;
	}
	

}
