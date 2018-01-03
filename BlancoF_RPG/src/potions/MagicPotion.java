package potions;

public class MagicPotion extends Potion
{
	private int magicPoints;
	
	public MagicPotion()
	{
		magicPoints = super.getRandomNum() * 7;
	}
	
	public int getMagicPoints()
	{
		return magicPoints;
	}
	
	private void setMagicPoints(int points)
	{
		magicPoints = points;
	}
	
	public int use()
	{
		setMagicPoints(0);
		return super.use();
	}
	
}
