/**
 * 
 */
package potions;

/**
 * @author Fernando
 *
 */
public class HealthPotion extends Potion
{
	private int healthPoints;
	
	public HealthPotion()
	{
		healthPoints = super.getRandomNum() * 10;
	}
	
	private void setHealthPoints(int points)
	{
		healthPoints = points;
	}
	
	public int getHealthPoints()
	{
		return healthPoints;
	}
	
	public int use()
	{
		setHealthPoints(0);
		return super.use();
	}
	
}

