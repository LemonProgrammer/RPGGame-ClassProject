package monsters;

public class Monster
{
	public int rank = 0;
	private boolean isWeak = false;
	private boolean isMediocore = false;
	private boolean isStrong = false;
	/////////////////////////////////////////////////////////////////////////////
	private int monsterStrength;
	private int monsterIntelligence;
	private int monsterDexterity;
	private int [] attributes = {monsterStrength, monsterIntelligence, monsterDexterity};
	/////////////////////////////////////////////////////////////////////////////
	private int monsterDamage;
	private int monsterStrike;
	private int monsterSpellStrength;
	private int monsterDodge;
	/////////////////////////////////////////////////////////////////////////////
	private String monsterName;
	public int baseHP;
	public int baseMP;
	/////////////////////////////////////////////////////////////////////////////
	private int currentHP;
	private int currentMP;
	/////////////////////////////////////////////////////////////////////////////
//	private HealthPotion [] hpPotionInventory = new HealthPotion[5];
//	private MagicPotion [] mpPotionInventory = new MagicPotion[5];
//	private int numberOfHPPotions;
//	private int numberOfMPPotions;
	/////////////////////////////////////////////////////////////////////////////
	private boolean isAlive;
	private boolean isDamageNeg;
	private boolean isStrikeNeg;
	private boolean isSpellNeg;
	private boolean isDodgeNeg;
	public final int baseAttack = 10;
	public final int baseSpecial_Attack = 15;
	/////////////////////////////////////////////////////////////////////////////
	private int damageBonus;
	private int strikeBonus;
	private int dodgeBonus;
	private int spellBonus;
	
	
	
	/**
	 * Constructor for the Monster Class
	 * @param name Name of the Monster
	 * @param baseHP Base Health Points for the Monster
	 * @param baseMP Base Magic Points for the Monster
	 */
	public Monster(String name)
	{
		setMonsterName(name);
		setAlive(true);		
	}
	
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
	
	public int attackNormal()
	{
		return baseAttack + monsterDamage;
	}
	
	public int attackSpecial() 
	{
		int specialAttack = baseSpecial_Attack + monsterSpellStrength;
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

	public boolean isAlive()
	{
		return isAlive;
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
	
	public String printMonsterStats()
	{		
	 damageBonus = isDamageNeg ? Math.negateExact(monsterDamage) : monsterDamage;
	 strikeBonus = isStrikeNeg ? Math.negateExact(monsterStrike) : monsterStrike;
	 dodgeBonus = isDodgeNeg ? Math.negateExact(monsterDodge) : monsterDodge;
	 spellBonus = isSpellNeg ? Math.negateExact(monsterSpellStrength) : monsterSpellStrength;
		
		return "<<<<< Stats >>>>>" + "\n"
		+ "HP: " + currentHP + "\n"
		+ "MP: " + currentMP + "\n"
		+ "Strength: " + monsterStrength + " Damage Bonus: " + damageBonus + "\n"
		+ "Intelligence: " + monsterIntelligence + " Spell Strength: " + spellBonus + "\n"
		+ "Dexterity: " + monsterDexterity + "\nDodge Bonus: " + dodgeBonus + " Strike Bonus: "
		+ strikeBonus + "\n";
	}
	
	@Override
	public String toString()
	{
		return
			  "Monster: " + getMonsterName()
			 + "\n" + printMonsterStats()
			 + "----------------------------------------";
	}
	//////////////////////////////////////////////////////////////////////////////
	public int getBaseHP()
	{
		return baseHP = monsterStrength * 10;
	}
	
	public int getBaseMP()
	{
		return baseMP = monsterIntelligence * 5;
	}
	
	
	public int getMonsterStrength()
	{
		return monsterStrength;
	}

	public void setMonsterStrength(int monsterStrength)
	{
		baseHP = monsterStrength * 10;
		this.monsterStrength = monsterStrength;
	}

	public int getMonsterIntelligence()
	{
		return monsterIntelligence;
	}

	public void setMonsterIntelligence(int monsterIntelligence)
	{
		baseMP = monsterIntelligence * 7;
		this.monsterIntelligence = monsterIntelligence;
	}

	public int getMonsterDexterity()
	{
		return monsterDexterity;
	}

	public void setMonsterDexterity(int monsterDexterity)
	{
		this.monsterDexterity = monsterDexterity;
	}

	public int getMonsterDamage()
	{
		return monsterDamage;
	}

	public void setMonsterDamage(int monsterDamage)
	{
		this.monsterDamage = monsterDamage;
	}

	public int getMonsterStrike()
	{
		return monsterStrike;
	}

	public void setMonsterStrike(int monsterStrike)
	{
		this.monsterStrike = monsterStrike;
	}

	public int getMonsterSpellStrength()
	{
		return monsterSpellStrength;
	}

	public void setMonsterSpellStrength(int monsterSpellStrength)
	{
		this.monsterSpellStrength = monsterSpellStrength;
	}

	public int getMonsterDodge()
	{
		return monsterDodge;
	}

	public void setMonsterDodge(int monsterDodge)
	{
		this.monsterDodge = monsterDodge;
	}
	
	public int getCurrentHP()
	{
		return currentHP;
	}

	public void setCurrentHP(int currentHP)
	{
		this.currentHP = currentHP;
	}

	public int getCurrentMP()
	{
		return currentMP;
	}

	public void setCurrentMP(int currentMP)
	{
		this.currentMP = currentMP;
	}

	public int[] getAttributes()
	{
		return attributes;
	}


	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}

	public boolean isWeak()
	{
		return isWeak;
	}

	public boolean isMediocore()
	{
		return isMediocore;
	}

	public boolean isStrong()
	{
		return isStrong;
	}

	public void setWeak(boolean isWeak)
	{
		this.isWeak = isWeak;
	}

	public void setMediocore(boolean isMediocore)
	{
		this.isMediocore = isMediocore;
	}

	public void setStrong(boolean isStrong)
	{
		this.isStrong = isStrong;
	}

	public int getBaseSpecial_Attack()
	{
		return baseSpecial_Attack;
	}

	public boolean isDamageNeg()
	{
		return isDamageNeg;
	}

	public void setDamageNeg(boolean isDamageNeg)
	{
		this.isDamageNeg = isDamageNeg;
	}

	public boolean isStrikeNeg()
	{
		return isStrikeNeg;
	}

	public void setStrikeNeg(boolean isStrikeNeg)
	{
		this.isStrikeNeg = isStrikeNeg;
	}

	public boolean isSpellNeg()
	{
		return isSpellNeg;
	}

	public void setSpellNeg(boolean isSpellNeg)
	{
		this.isSpellNeg = isSpellNeg;
	}

	public boolean isDodgeNeg()
	{
		return isDodgeNeg;
	}

	public void setDodgeNeg(boolean isDodgeNeg)
	{
		this.isDodgeNeg = isDodgeNeg;
	}

	public String getMonsterName()
	{
		return monsterName;
	}

	public void setMonsterName(String monsterName)
	{
		this.monsterName = monsterName;
	}

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

	public int getDodgeBonus()
	{
		return dodgeBonus;
	}

	public void setDodgeBonus(int dodgeBonus)
	{
		this.dodgeBonus = dodgeBonus;
	}

	public int getSpellBonus()
	{
		return spellBonus;
	}

	public void setSpellBonus(int spellBonus)
	{
		this.spellBonus = spellBonus;
	}

}
