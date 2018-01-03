package models;

import potions.HealthPotion;
import potions.MagicPotion;

public class Hero
{
	private boolean isDamageNeg, isStrikeNeg, isDodgeNeg, isSpellNeg;
	private int heroStrength;
	private int heroIntelligence;
	private int heroDexterity;
	private int [] attributes = {heroStrength, heroIntelligence, heroDexterity};
	/////////////////////////////////////////////////////////////////////////////
	private int damage;
	private int strike;
	private int spellStrength;
	private int dodge;
	/////////////////////////////////////////////////////////////////////////////
	private int negDamage;
	private int negStrike;
	private int negSpellStrength;
	private int negdodge;
	private String name;
	private int baseHP;
	private int baseMP;
	/////////////////////////////////////////////////////////////////////////////
	private int currentHP;
	private int currentMP;
	/////////////////////////////////////////////////////////////////////////////
	private HealthPotion [] hpPotionInventory = new HealthPotion[5];
	private MagicPotion [] mpPotionInventory = new MagicPotion[5];
	private int numberOfHPPotions;
	private int numberOfMPPotions;
	/////////////////////////////////////////////////////////////////////////////
	private boolean isAlive;
	private int baseAttack = 10;
	private int baseSpecial_Attack = 15;
	////////////////////////////////////////////////////////////////////////////
	private int damageBonus;
	private int strikeBonus;
	private int spellBonus;
	private int dodgeBonus;
	
	
	
	public Hero(String name)
	{
		this.name = name;
		numberOfHPPotions = 0;
		numberOfMPPotions = 0;
		isAlive = true;
	}
	
	/**
	 * @return the heroStrength
	 */
	public int getHeroStrength()
	{
		// return heroStrength = strength.getAttributeValue();
		return heroStrength;
	}

	/**
	 * @return the heroIntelligence
	 */
	public int getHeroIntelligence()
	{
		// return heroIntelligence = intelligence.getAttributeValue();
		return heroIntelligence;
	}

	/**
	 * @return the heroDexterity
	 */
	public int getHeroDexterity()
	{
		// return heroDexterity = dexterity.getAttributeValue();
		return heroDexterity;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the currentHP
	 */
	public int getCurrentHP()
	{
		return currentHP;
	}

	/**
	 * @return the currentMP
	 */
	public int getCurrentMP()
	{
		return currentMP;
	}

	public int getBaseHP()
	{
		return baseHP = heroStrength * 10;
	}

	public int getBaseMP()
	{
		return baseMP = heroIntelligence * 5;
	}

	public int [] getAttributes()
	{
		return attributes;
	}
	
	public void setBaseHP(int base)
	{
		baseHP = base * 10;
	}
	
	public void setBaseMP(int base)
	{
		baseMP = base * 7;
	}
	
	/**
	 * @param heroStrength
	 *            the heroStrength to set
	 */
	public void setHeroStrength(int heroStrength)
	{
		this.heroStrength = heroStrength;
	}


	/**
	 * @param heroIntelligence
	 *            the heroIntelligence to set
	 */
	public void setHeroIntelligence(int heroIntelligence)
	{
		this.heroIntelligence = heroIntelligence;
	}

	/**
	 * @param heroDexterity
	 *            the heroDexterity to set
	 */
	public void setHeroDexterity(int heroDexterity)
	{
		this.heroDexterity = heroDexterity;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param currentHP
	 *            the currentHP to set
	 */
	public void setCurrentHP(int currentHP)
	{
		this.currentHP = currentHP;
	}

	/**
	 * @param currentMP
	 *            the currentMP to set
	 */
	public void setCurrentMP(int currentMP)
	{
		this.currentMP = currentMP;
	}
	
	/**
	 * @return the damage
	 */
	public int getDamage()
	{
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage)
	{
		this.damage = damage;
	}

	/**
	 * @return the strike
	 */
	public int getStrike()
	{
		return strike;
	}

	/**
	 * @param strike the strike to set
	 */
	public void setStrike(int strike)
	{
		this.strike = strike;
	}

	/**
	 * @return the spellStrength
	 */
	public int getSpellStrength()
	{
		return spellStrength;
	}

	/**
	 * @param spellStrength the spellStrength to set
	 */
	public void setSpellStrength(int spellStrength)
	{
		this.spellStrength = spellStrength;
	}

	/**
	 * @return the negDamage
	 */
	public int getNegDamage()
	{
		return negDamage;
	}

	/**
	 * @param negDamage the negDamage to set
	 */
	public void setNegDamage(int negDamage)
	{
		this.negDamage = negDamage;
	}

	/**
	 * @return the negStrike
	 */
	public int getNegStrike()
	{
		return negStrike;
	}

	/**
	 * @param negStrike the negStrike to set
	 */
	public void setNegStrike(int negStrike)
	{
		this.negStrike = negStrike;
	}

	/**
	 * @return the negSpellStrength
	 */
	public int getNegSpellStrength()
	{
		return negSpellStrength;
	}

	/**
	 * @param negSpellStrength the negSpellStrength to set
	 */
	public void setNegSpellStrength(int negSpellStrength)
	{
		this.negSpellStrength = negSpellStrength;
	}

	/**
	 * @return the dodge
	 */
	public int getDodge()
	{
		return dodge;
	}
	
	/**
	 * @param dodge the dodge to set
	 */

	public void setDodge(int dodge)
	{
		this.dodge = dodge;
	}

	/**
	 * @return the negdodge
	 */
	public int getNegdodge()
	{
		return negdodge;
	}

	/**
	 * @param negdodge the negdodge to set
	 */
	public void setNegdodge(int negdodge)
	{
		this.negdodge = negdodge;
	}
	
	/**
	 * @param isDamageNeg the isDamageNeg to set
	 */
	public void setIsDamageNeg(boolean isDamageNeg)
	{
		this.isDamageNeg = isDamageNeg;
	}

	/**
	 * @param isStrikeNeg the isStrikeNeg to set
	 */
	public void setIsStrikeNeg(boolean isStrikeNeg)
	{
		this.isStrikeNeg = isStrikeNeg;
	}

	/**
	 * @param isDodgeNeg the isDodgeNeg to set
	 */
	public void setIsDodgeNeg(boolean isDodgeNeg)
	{
		this.isDodgeNeg = isDodgeNeg;
	}

	/**
	 * @param isSpellNeg the isSpellNeg to set
	 */
	public void setIsSpellNeg(boolean isSpellNeg)
	{
		this.isSpellNeg = isSpellNeg;
	}

	////////////////////////////////////////////////
	public int getDamageBonus()
	{
		return damageBonus;
	}

	public void setDamageBonus(int damageBonus)
	{
		this.damageBonus = damageBonus;
	}

	public int getStrikeBonus()
	{
		return strikeBonus;
	}

	public void setStrikeBonus(int strikeBonus)
	{
		this.strikeBonus = strikeBonus;
	}

	public int getSpellBonus()
	{
		return spellBonus;
	}

	public void setSpellBonus(int spellBonus)
	{
		this.spellBonus = spellBonus;
	}

	public int getDodgeBonus()
	{
		return dodgeBonus;
	}

	public void setDodgeBonus(int dodgeBonus)
	{
		this.dodgeBonus = dodgeBonus;
	}

//	public HealthPotion[] getHpPotionInventory()
//	{
//		return hpPotionInventory;
//	}
//
//	public MagicPotion[] getMpPotionInventory()
//	{
//		return mpPotionInventory;
//	}

	//////////////////////////////////////////////
	public void receiveDamage(int damage)
	{
		currentHP = currentHP - damage;
		if(currentHP <= 0)
		{
			isAlive = false;
		}
		else
		{
			isAlive = true;
		}
	}
	
	public int useHealthPotion()
	{
		int hpVal = 0;
		for(int i = 0; i < hpPotionInventory.length;)
		{
			if(hpPotionInventory[i] == null)
			{
				i++;
			} 
			else
			{
				//Checks to see if the sum of the values of potion and current HP exceeds baseHP.
				hpVal = (currentHP + hpPotionInventory[i].getHealthPoints()) > baseHP
						? (baseHP - hpPotionInventory[i].use()) 
						: hpPotionInventory[i].use();
				hpPotionInventory[i] = null;
				return hpVal;
			}
		}
		return hpVal;
	}
	
	public int useMagicPotion()
	{
		int mpVal = 0;
		for(int i = 0; i < mpPotionInventory.length;)
		{
			if(mpPotionInventory[i] == null)
			{
				i++;
			}
			else
			{
				mpVal = (currentMP + mpPotionInventory[i].getMagicPoints()) > baseMP
						? (baseMP - mpPotionInventory[i].use())
						: mpPotionInventory[i].use();
				mpPotionInventory[i] = null;
				return mpVal;
			}
		}
		return mpVal;
	}
	
	public boolean receiveHealthPotion(HealthPotion potion)
	{
		for(int i = 0; i < hpPotionInventory.length;)
		{
			if(hpPotionInventory[i] == null)
			{
				hpPotionInventory[i] = potion;
				return true;
			}
			else if(hpPotionInventory[i] != null)
			{
				i++;
			}
		}
		currentHP = (currentHP + potion.getHealthPoints()) > baseHP ? + (baseHP - potion.use()) : + potion.use();
		return false;
	}
	public boolean receiveMagicPotion(MagicPotion potion)
	{
		for(int i = 0; i < mpPotionInventory.length;)
		{
			if(mpPotionInventory[i] == null)
			{
				mpPotionInventory[i] = potion;
				return true;
			}
			else if(mpPotionInventory[i] != null)
			{
				i++;
			}
		}
		currentMP = (currentMP + potion.getMagicPoints()) > baseMP ? +(baseMP - potion.use()) : potion.use();
		
		return false;
	}
	
	public int attackNormal()
	{
		return baseAttack + damageBonus;
	}
	
	public int attackSpecial() 
	{
		int specialAttack = baseSpecial_Attack + spellBonus;
		if(currentMP - 10 < 0)
		{
			return 0;
		}
		else if(currentMP - 10 >= 0)
		{
			currentMP -= 10;
			return specialAttack;
		}
		return 0;
	}
	
	public boolean getIsAlive()
	{
		return isAlive;
	}
	//////////////////////////////////////////////
	//////////////////////////////////////////////
	public int getNumberOfHPPotions()
	{
		for(int i = 0; i < hpPotionInventory.length; i++)
		{
			if(hpPotionInventory[i] == null)
			{
				numberOfHPPotions += 0;
			}
			else
			{
				numberOfHPPotions++;
			}
			
		}
		
		return numberOfHPPotions;
	}
	
	public int getNumberOfMPPotions()
	{
		for(int i = 0; i < mpPotionInventory.length; i++)
		{
			if(mpPotionInventory[i] == null)
			{
				numberOfMPPotions += 0;
			}
			else
			{
				numberOfMPPotions++;
			}
		}
		
		
		return numberOfMPPotions;
	}
	
	public String printHPAndMP(int health, int magic, boolean choice)
	{
		String healthPacket = " [H] ";
		String healthBar = "";
		if(health == baseHP)
		{
			for(int i = 0; i < 4; i++)
			{
				healthBar += healthPacket;
			}
		}
		else if(health % 5 == 1)
		{
			 healthBar = healthPacket;
		}
		else if(health % 5 <= 4 && health % 5 >= 2)
		{
			 healthBar = healthPacket + healthPacket;
		}
		else if(health % 5 >= 5 && health % 5 <= 9)
		{
			 healthBar = healthPacket + healthPacket + healthPacket;
		}
		else if(health % 5 >= 10)
		{
			 healthBar = healthPacket + healthPacket + healthPacket + healthPacket;
		}
		
		String magicPacket = " [M] ";
		String magicBar = "";
		if(magic == baseMP)
		{
			for(int i = 0; i < 4; i++)
			{
				magicBar += magicPacket;
			}
			
		}
		else if(magic % 5 == 1)
		{
			magicBar = magicPacket;
		}
		else if(magic % 5 <= 4 && magic % 5 >= 2)
		{
			magicBar = magicPacket + magicPacket;
		}
		else if(magic % 5 >= 5 && magic % 5 <= 9)
		{
			magicBar = magicPacket + magicPacket + magicPacket;
		}
		else if(health % 5 >= 10)
		{
			 magicBar = magicPacket + magicPacket + magicPacket + magicPacket;
		}
		
		if(choice == true)
		{
			return new String("Health: " + healthBar);
		}
		else
		{
			return new String("Magic: " + magicBar);
		}
	}
	
	public String printHeroStats()
	{		
	 damageBonus = isDamageNeg ? Math.negateExact(negDamage) : damage;
	 strikeBonus = isStrikeNeg ? Math.negateExact(negStrike) : strike;
	 dodgeBonus = isDodgeNeg ? Math.negateExact(negdodge) : dodge;
	 spellBonus = isSpellNeg ? Math.negateExact(negSpellStrength) : spellStrength;
		
		return "<<<<< Stats >>>>>" + "\n"
		+ "HP: " + currentHP + "\n"
		+ "MP: " + currentMP + "\n"
		+ "Strength: " + heroStrength + " Damage Bonus: " + damageBonus + "\n"
		+ "Intelligence: " + heroIntelligence + " Spell Strength: " + spellBonus + "\n"
		+ "Dexterity: " + heroDexterity + "\nDodge Bonus: " + dodgeBonus + " Strike Bonus: "
		+ strikeBonus + "\n";
	}
	
	public String printHpPotionInventory()
	{
		String hpPotions = "";
		for(HealthPotion potion : hpPotionInventory)
		{
			if(potion != null)
			{
				hpPotions += "[+" + potion.getHealthPoints() + "] ";
			}
			else
			{
				hpPotions += "[ ] ";
			}
		}
		
		String hpInventory = "Number of HP Potions currently in stock: " + numberOfHPPotions + "\n"
							+ "HP Potions: " + hpPotions;
		
		
		return hpInventory;
	}
	
	public String printMpPotionInventory()
	{
		String mpPotions = "";
		for(MagicPotion potion : mpPotionInventory)
		{
			if(potion != null)
			{
				mpPotions += "[+" + potion.getMagicPoints() + "] ";
			}
			else
			{
				mpPotions += "[ ] ";
			}
		}
		
		String mpInventory = "Number of MP Potions currently in stock: " + numberOfMPPotions + "\n"
							+ "MP Potions: " + mpPotions;
		
		
		return mpInventory;
	}
	
	
	@Override
	public String toString()
	{

		if(name == null)
		{
			name = " ";
			return "\n-------------------------------------------\n"
			+ "Hero's name: " + name + "\n"
			+ printHeroStats()
				  +  "-------------------------------------------\n";

		} else if(name != null)
		{
			return "-------------------------------------------\n"
					+ "Hero's name: " + name + "\n"
					+ printHeroStats()
					+ "++++++++++++++++++++++++++++++++++++++++\n"
					+ printHpPotionInventory()
					+ "==========\n"
					+ printMpPotionInventory()
					+ "----------\n";
		}
		return null;
	}
	
}