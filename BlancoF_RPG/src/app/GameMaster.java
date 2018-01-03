package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import lib.ConsoleIO;
import models.DiceBag;
import models.Die;
import models.Hero;
import monsters.Monster;
import potions.HealthPotion;
import potions.MagicPotion;

public class GameMaster
{
	private Die currentDie;
	private ArrayList<Die> gameDice = new ArrayList<Die>();
	public boolean isStrikeNeg;
	private int result;
	private Monster monster;
	private Hero currentHero;
	private int turnCount;
	private int roundCount;
	private int userNumber;
	private HealthPotion hpPotion;
	private MagicPotion mpPotion;
	private boolean canStartOver = false;
	// private Hero defaultHero = new Hero();
	// private Hero playerHero = new Hero();

	/*
	 * Runs and Starts the program/game. Auto creates a random Hero for the user.
	 * Then it displays the Hero to the user in the console. Then it prompts the
	 * User to either accept the Hero as is or to re-roll for another New Hero. If
	 * user decides to accept the Hero as is, the program prompts the user to name
	 * the their Hero. Else if the user is unsatisified, the user asks the
	 * GameMaster to re-roll the Hero base on criteria from the die rolls.
	 */

	public void start()
	{
		String[] menu = { "Begin RPG" };
		int userchoice = ConsoleIO.promptForMenuSelection(menu, true);
		do
		{
			switch (userchoice) {
			case 1:
				run();
				break;
			case 0:
				System.exit(0);
				break;
			}
			
			canStartOver = promptToContinue("Would you like to make a new hero and continue or not? (Y/N)");
		} while(!canStartOver);
		
		
	}

	private void run()
	{
		Hero randomHero = createRandomHero();
		applyHeroBonuses(randomHero);
		displayHero(randomHero);
		boolean userChoice = promptUser();
		if (userChoice == true)
		{
			// if they say yes to re-roll
			Hero player;
			do
			{
				player = createRandomHero();
				displayHero(player);
				userChoice = promptUser();
			} while (userChoice == true);
			String heroName = ConsoleIO.promptForInput("Go ahead and enter a name. The name cannot be empty: ", false);
			player.setName(heroName);
			applyHeroBonuses(player);
			displayHero(player);
			currentHero = player;
			// System.out.println("Congrats on making this Hero!");
		}
		else if (userChoice == false)
		{
			// if they say no and accept
			String heroName = ConsoleIO.promptForInput("Go ahead and enter a name. The name cannot be empty: ", false);
			Hero player = randomHero;
			player.setName(heroName);
			applyHeroBonuses(player);
			displayHero(player);
			currentHero = player;
			// System.out.println("Congrats on making this Hero!");
		}
		// this is where part 2 begins
		boolean canContinue = false;
		do
		{
			int monsterChoice = Combat.promptForFight();
			monster = createMonster(monsterChoice);
			//displayMonster(monster);
			Combat.displayCombat(currentHero, monster);
			fight();
			
			if(monster.isAlive() == false)
			{
				givePotionToPlayer();
			}
			else if(currentHero.getIsAlive() == false)
			{
				System.out.println(currentHero.getName() + " has perished.");
				System.out.println("Game Over");
				
			}		
			canContinue = promptToContinue("Would you like to fight another monster (Y/N)?");
		} while(!canContinue && currentHero.getIsAlive() == true);
		
	}
	
	/*
	 * The game creates and builds a random generated hero for the user. This random
	 * Hero will include its attributes, HP, MP and any bonuses. All of this, based
	 * on the GM rolling dice from the DiceBag. After building the Hero from rolling
	 * the Dice, the method returns the Hero itself.
	 */
	private Hero createRandomHero()
	{
		Hero randomHero = new Hero(null);
		// How many dice, how many sides, has mods
		gameDice = DiceBag.createDice(3, 6, false);
		for (int attribute : randomHero.getAttributes())
		{
			rollDice(gameDice);
			result = DiceBag.showResults(gameDice);
			Die bonusDie = DiceBag.createOneDie(6, false);
			// System.out.println(result + "///");
			if (result >= 16)
			{
				int bonusResult = bonusDie.rollDie();
				if (bonusResult == 6)
				{
					Die anotherBonusDie = DiceBag.createOneDie(6, false);
					bonusResult += anotherBonusDie.rollDie();
				}
				result += bonusResult;
			}
			// System.out.println(result);
			// System.out.println("------");
			if (randomHero.getHeroStrength() == 0)
			{
				randomHero.setHeroStrength(result);
			}
			else if (randomHero.getHeroStrength() > 0 && randomHero.getHeroIntelligence() == 0)
			{
				randomHero.setHeroIntelligence(result);
			}
			else if (randomHero.getHeroDexterity() == 0)
			{
				randomHero.setHeroDexterity(result);
			}
			randomHero.setBaseHP(randomHero.getHeroStrength());
			randomHero.setBaseMP(randomHero.getHeroIntelligence());
			
			randomHero.setCurrentHP(randomHero.getBaseHP());
			randomHero.setCurrentMP(randomHero.getBaseMP());
			result = 0;
		}

		// name,hp,mp, attribute
		return randomHero;
	}

	/*
	 * Displays the generated Hero to the console. includes the amount of HP, MP,
	 * AP(Attribute Points), and bonuses granted from the dice rolls. Though the
	 * generated Hero has no name yet. If the User accepts the Hero, then this
	 * method can display the name among the stats.
	 * 
	 */
	private void displayHero(Hero hero)
	{
		System.out.println(hero.toString());
	}

	/*
	 * Once the random generated Hero is created and displayed, the GM asks the
	 * player to either: Accept the Hero as is - Or To re-roll for another Hero with
	 * different stats.
	 * 
	 * If user accepts the Hero (:>), User will be prompted to name the Hero. Else
	 * if the User rejects (:<), The whole process is repeated until User accepts.
	 */
	private boolean promptUser()
	{

		System.out.println("\n ---------------------------- \n");
		boolean userResponse = ConsoleIO.promptForBool(
		        "Would you like to re-roll for another hero with different stats or are you okay with this hero? (y/n)",
		        "y", "n");
		System.out.println("\n ---------------------------- \n");
		return userResponse;
	}

	/*
	 * Rolls the Dice to get values from its faces. Faces will return number values
	 * for stats in Hero.
	 * 
	 */
	private void rollDice(ArrayList<Die> gameDice)
	{
		for (Die die : gameDice)
		{
			die.rollDie();
		}
	}

	private void applyHeroBonuses(Hero hero)
	{
		int damage = hero.getHeroStrength() - 15;
		int currentDamage = hero.getHeroStrength() > 15 ? damage : 0;
		hero.setDamage(currentDamage);
		int negDamage = 10 - hero.getHeroStrength();
		int currentNegDamage = hero.getHeroStrength() < 10 ? negDamage : 0;
		hero.setNegDamage(currentNegDamage);
		boolean isDamageNeg = (hero.getHeroStrength()) > 15 ? false : (hero.getHeroStrength() < 10) ? true : false;
		hero.setIsDamageNeg(isDamageNeg);
		///////////////////////////////////////////////////////
		// For Strike
		if (hero.getHeroDexterity() > 14)
		{
			int points = 0;
			int pointRange = ((hero.getHeroDexterity() - 14) / 2);
			for (int i = 0; i != pointRange; i++)
			{
				points++;
			}
			if ((hero.getHeroDexterity() - 14) % 2 > 4)
			{
				points--;
			}
			hero.setStrike(points);
			hero.setIsStrikeNeg(false);

		}
		else if (hero.getHeroDexterity() < 10)
		{
			int negPoints = 0;
			int range = ((10 - hero.getHeroDexterity()) / 2);
			for (int i = 0; i != range; i++)
			{
				negPoints++;
			}
			if ((10 - hero.getHeroDexterity()) % 2 > 4)
			{
				negPoints--;
			}
			hero.setNegDamage(negPoints);
			hero.setIsStrikeNeg(true);
		}
		// For Dodge
		if (hero.getHeroDexterity() > 15)
		{
			int points = 0;
			int range = ((hero.getHeroDexterity() - 15) / 2);
			for (int i = 0; i != range; i++)
			{
				points++;
			}
			if ((hero.getHeroDexterity() - 15) % 2 > 4)
			{
				points -= 1;
			}
			hero.setDodge(points);
			hero.setIsDodgeNeg(false);
		}
		else if (hero.getHeroDexterity() < 11)
		{
			int negPoints = 0;
			int range = ((11 - hero.getHeroDexterity()) / 2);
			for (int i = 0; i != range; i++)
			{
				negPoints++;
			}
			if ((11 - hero.getHeroDexterity()) % 2 > 4)
			{
				negPoints--;
			}
			hero.setNegdodge(negPoints);
			hero.setIsDodgeNeg(true);
		}
		// For Spell Strength
		if (hero.getHeroIntelligence() > 15)
		{
			int points = 0;
			int range = (hero.getHeroIntelligence() - 15) / 2;
			for (int i = 0; i < range; i++)
			{
				points += 2;
			}
			if (range % 2 > 4)
			{
				points -= 2;
			}
			hero.setSpellStrength(points);
			hero.setIsSpellNeg(false);
		}
		else if (hero.getHeroIntelligence() < 10)
		{
			int negPoints = 0;
			int range = (10 - hero.getHeroIntelligence()) / 2;
			for (int i = 0; i < range; i++)
			{
				negPoints += 2;
			}
			if (range % 2 > 4)
			{
				negPoints -= 2;
			}
			hero.setNegSpellStrength(negPoints);
			hero.setIsSpellNeg(true);
		}
	}

	private Monster createMonster(int choice)
	{
		switch (choice) {
		case 1:
			// RatMan
			monster = createRatMan();
			applyMonsterBonuses(monster);
			return monster;
		case 2:
			// Rat Warrior
			monster = createRatWarrior();
			applyMonsterBonuses(monster);
			return monster;
		case 3:
			// Alpha Rat
			monster = createAlphaRat();
			applyMonsterBonuses(monster);
			return monster;
		default:
			return monster;
		}
	}

	private Monster createRatMan()
	{
		Monster currentMonster = new Monster("RatMan");
		gameDice = DiceBag.createDice(3, 6, false);
		for (int attribute : currentMonster.getAttributes())
		{
			rollDice(gameDice);
			result = DiceBag.showResults(gameDice);
			Die bonusDie = DiceBag.createOneDie(6, false);
			// System.out.println(result + "///");
			if (result >= 16)
			{
				int bonusResult = bonusDie.rollDie();
				if (bonusResult == 6)
				{
					Die anotherBonusDie = DiceBag.createOneDie(6, false);
					bonusResult += anotherBonusDie.rollDie();
				}
				result += bonusResult;
			}
			result = result / 3;
			if (currentMonster.getMonsterStrength() == 0)
			{
				currentMonster.setMonsterStrength(result);
			}
			else if (currentMonster.getMonsterStrength() > 0 && currentMonster.getMonsterIntelligence() == 0)
			{
				currentMonster.setMonsterIntelligence(result);
			}
			else if (currentMonster.getMonsterDexterity() == 0)
			{
				currentMonster.setMonsterDexterity(result);
			}
			currentMonster.setCurrentHP(currentMonster.baseHP);
			currentMonster.setCurrentMP(currentMonster.baseMP);
			result = 0;
		}
		currentMonster.rank = 1;
		return currentMonster;
	}

	private Monster createRatWarrior()
	{
		Monster currentMonster = new Monster("RatWarrior");
		gameDice = DiceBag.createDice(3, 6, false);
		for (int attribute : currentMonster.getAttributes())
		{
			rollDice(gameDice);
			result = DiceBag.showResults(gameDice);
			Die bonusDie = DiceBag.createOneDie(6, false);
			// System.out.println(result + "///");
			if (result >= 16)
			{
				int bonusResult = bonusDie.rollDie();
				if (bonusResult == 6)
				{
					Die anotherBonusDie = DiceBag.createOneDie(6, false);
					bonusResult += anotherBonusDie.rollDie();
				}
				result += bonusResult;
			}

			if (currentMonster.getMonsterStrength() == 0)
			{
				currentMonster.setMonsterStrength(result);
			}
			else if (currentMonster.getMonsterStrength() > 0 && currentMonster.getMonsterIntelligence() == 0)
			{
				currentMonster.setMonsterIntelligence(result);
			}
			else if (currentMonster.getMonsterDexterity() == 0)
			{
				currentMonster.setMonsterDexterity(result);
			}
			currentMonster.setCurrentHP(currentMonster.baseHP);
			currentMonster.setCurrentMP(currentMonster.baseMP);
			result = 0;
		}
		currentMonster.rank = 2;
		return currentMonster;
	}

	private Monster createAlphaRat()
	{
		Monster currentMonster = new Monster("AlphaRat");
		gameDice = DiceBag.createDice(3, 6, false);
		for (int attribute : currentMonster.getAttributes())
		{
			rollDice(gameDice);
			result = DiceBag.showResults(gameDice);
			Die bonusDie = DiceBag.createOneDie(6, false);
			// System.out.println(result + "///");
			if (result >= 16)
			{
				int bonusResult = bonusDie.rollDie();
				if (bonusResult == 6)
				{
					Die anotherBonusDie = DiceBag.createOneDie(6, false);
					bonusResult += anotherBonusDie.rollDie();
				}
				result += bonusResult;
			}
			result = result * 3;
			if (currentMonster.getMonsterStrength() == 0)
			{
				currentMonster.setMonsterStrength(result);
			}
			else if (currentMonster.getMonsterStrength() > 0 && currentMonster.getMonsterIntelligence() == 0)
			{
				currentMonster.setMonsterIntelligence(result);
			}
			else if (currentMonster.getMonsterDexterity() == 0)
			{
				currentMonster.setMonsterDexterity(result);
			}
			currentMonster.setCurrentHP(currentMonster.baseHP);
			currentMonster.setCurrentMP(currentMonster.baseMP);
			result = 0;
		}
		currentMonster.rank = 3;
		return currentMonster;
	}

	private void applyMonsterBonuses(Monster currentMonster)
	{
		int damage = currentMonster.getMonsterStrength() - 15;
		int currentDamage = currentMonster.getMonsterStrength() > 15 ? damage : 0;
		currentMonster.setMonsterDamage(currentDamage);
		int negDamage = 10 - currentMonster.getMonsterStrength();
		int currentNegDamage = currentMonster.getMonsterStrength() < 10 ? negDamage : 0;
		currentMonster.setMonsterDamage(currentNegDamage);
		currentMonster.setDamageNeg((currentMonster.getMonsterStrength()) > 15 ? false 
				: (currentMonster.getMonsterStrength() < 10) ? true : false);
		///////////////////////////////////////////////////////
		// For Strike
		if (currentMonster.getMonsterDexterity() > 14)
		{
			int points = 0;
			int pointRange = ((currentMonster.getMonsterDexterity() - 14) / 2);
			for (int i = 0; i != pointRange; i++)
			{
				points++;
			}
			if ((currentMonster.getMonsterDexterity() - 14) % 2 > 4)
			{
				points--;
			}
			currentMonster.setMonsterStrike(points);
			currentMonster.setStrikeNeg(false);

		}
		else if (currentMonster.getMonsterDexterity() < 10)
		{
			int negPoints = 0;
			int range = ((10 - currentMonster.getMonsterDexterity()) / 2);
			for (int i = 0; i != range; i++)
			{
				negPoints++;
			}
			if ((10 - currentMonster.getMonsterDexterity()) % 2 > 4)
			{
				negPoints--;
			}
			currentMonster.setMonsterStrike(negPoints);
			currentMonster.setStrikeNeg(true);
		}
		// For Dodge
		if (currentMonster.getMonsterDexterity() > 15)
		{
			int points = 0;
			int range = ((currentMonster.getMonsterDexterity() - 15) / 2);
			for (int i = 0; i != range; i++)
			{
				points++;
			}
			if ((currentMonster.getMonsterDexterity() - 15) % 2 > 4)
			{
				points -= 1;
			}
			currentMonster.setMonsterDodge(points);
			currentMonster.setDodgeNeg(false);
		}
		else if (currentMonster.getMonsterDexterity() < 11)
		{
			int negPoints = 0;
			int range = ((11 - currentMonster.getMonsterDexterity()) / 2);
			for (int i = 0; i != range; i++)
			{
				negPoints++;
			}
			if ((11 - currentMonster.getMonsterDexterity()) % 2 > 4)
			{
				negPoints--;
			}
			currentMonster.setMonsterDodge(negPoints);
			currentMonster.setDodgeNeg(true);
		}
		// For Spell Strength
		if (currentMonster.getMonsterIntelligence() > 15)
		{
			int points = 0;
			int range = (currentMonster.getMonsterIntelligence() - 15) / 2;
			for (int i = 0; i < range; i++)
			{
				points += 2;
			}
			if (range % 2 > 4)
			{
				points -= 2;
			}
			currentMonster.setMonsterSpellStrength(points);
			currentMonster.setSpellNeg(false);
		}
		else if (currentMonster.getMonsterIntelligence() < 10)
		{
			int negPoints = 0;
			int range = (10 - currentMonster.getMonsterIntelligence()) / 2;
			for (int i = 0; i < range; i++)
			{
				negPoints += 2;
			}
			if (range % 2 > 4)
			{
				negPoints -= 2;
			}
			currentMonster.setMonsterSpellStrength(negPoints);
			currentMonster.setSpellNeg(true);
		}
	}

	private void fight()
	{
		Die combatDie = DiceBag.createOneDie(20, false);
		int heroRoll = combatDie.rollDie();
		int monsterRoll = combatDie.rollDie();
		boolean isHeroAttacker = Combat.initiateCombat(heroRoll, monsterRoll);
		
		Die monsterDie = DiceBag.createOneDie(6, false);
		Die fightDie = DiceBag.createOneDie(20, false);
		int hRoll = fightDie.rollDie();
		int mRoll = fightDie.rollDie();
		
		boolean isHCrit = hRoll == 20;
		boolean isMCrit = mRoll == 20;
		boolean isHBotch = hRoll == 1;
		boolean isMBotch = mRoll == 1;
		
		int monsterAttack = 0;
		boolean doesMonsterAttackNorm = false;
		boolean isHPotionEmpty = false;
		boolean isMPotionEmpty = false;
		do
		{
			for(int i = 0; i <= 4; i++)
			{
				if(isHeroAttacker == true)
				{	
					System.out.println(currentHero.getName() + " rolls to attack....");
					 hRoll = combatDie.rollDie() + currentHero.getStrikeBonus();
					 mRoll = combatDie.rollDie() + monster.getDodgeBonus();
					userNumber = Combat.displayFightMenu(currentHero, monster, isHPotionEmpty, isMPotionEmpty);
					takeCombatInput(hRoll, mRoll, isHPotionEmpty, isMPotionEmpty, isMBotch, isHCrit);
				}
				else if(isHeroAttacker == false)
				{
					//If the monster is the attacker
					
					startMonsterAIAttack(combatDie, monsterDie, hRoll, mRoll, monsterAttack,
							doesMonsterAttackNorm, isMCrit, isHCrit, isHBotch);
				}
				
				isHeroAttacker = !isHeroAttacker;
				/////////////////////////////////////
			}		
			isHeroAttacker = Combat.initiateCombat(heroRoll, monsterRoll);
		}while(currentHero.getIsAlive() == true && monster.isAlive() == true);
	}
	
	private void takeCombatInput(int heroRoll, int monstRoll, boolean isHEmpty, 
			boolean isMEmpty, boolean isMBotch, boolean isHCrit)
	{	
		switch(userNumber)
		{
		case 1:
			if(monstRoll > heroRoll)
			{	
				System.out.println(currentHero.getName() + " attack missed! No damage given.");
				monster.receiveDamage(0);
			}
			else if(heroRoll > monstRoll)
			{
				if(isHCrit == true)
				{
					if(isMBotch == true)
					{
						System.out.println(monster.getMonsterName() + " botches!");
						System.out.println("Quadruple Damage!!!");
						System.out.println("Critical Hit!");
						monster.receiveDamage(currentHero.attackNormal() * 4);
					}
					else
					{
						System.out.println("Critical Hit!");
						monster.receiveDamage(currentHero.attackNormal() * 2);
					}
				}
				else
				{
					if(isMBotch == true)
					{
						System.out.println(monster.getMonsterName() + " botches!");
						System.out.println("Double Damage!");
						monster.receiveDamage(currentHero.attackNormal() * 2);
					}
					else
					{	
						System.out.println(currentHero.getName() + "'s attack landed successfully!");
						monster.receiveDamage(currentHero.attackNormal());
					}
				}
			}
			else if(heroRoll == monstRoll)
			{
				if(isHCrit == true)
				{
					System.out.println("Critical Hit!");
					monster.receiveDamage(currentHero.attackNormal() * 2);
				}
				else
				{
					System.out.println(currentHero.getName() + " attack missed! No damage given.");
					monster.receiveDamage(0);
				}
			}
			break;
		case 2:
			if(monstRoll > heroRoll)
			{
				System.out.println(currentHero.getName() + "'s special attack failed! No damage given.");
				monster.receiveDamage(currentHero.attackSpecial() * 0);
			}
			else if (heroRoll > monstRoll)
			{
				if(isHCrit == true)
				{
					if(isMBotch == true)
					{
						System.out.println(monster.getMonsterName() + " botches!");
						System.out.println("Quadruple Damage!!!");
						System.out.println("Critical Hit!");
						monster.receiveDamage(currentHero.attackSpecial() * 4);
					}
					else
					{
						System.out.println("Critical Hit!");
						monster.receiveDamage(currentHero.attackSpecial() * 2);
					}
				}
				else
				{
					if(isMBotch == true)
					{
						System.out.println(monster.getMonsterName() + " botches!");
						System.out.println("Double Damage!");
						monster.receiveDamage(currentHero.attackSpecial() * 2);
					}
					else
					{
						System.out.println(currentHero.getName() + "'s special attack landed successfully!");
						monster.receiveDamage(currentHero.attackSpecial());
					}
				}
			}
			else if(heroRoll == monstRoll)
			{
				if(isHCrit == true)
				{
					System.out.println("Critical Hit!");
					monster.receiveDamage(currentHero.attackSpecial() * 2);
				}
				else
				{
					System.out.println(currentHero.getName() + "'s special attack failed! No damage given.");
					monster.receiveDamage(currentHero.attackSpecial() * 0);
				}
			}
			break;
		case 3:
			if(isHEmpty == true)
			{
				System.out.println("Sorry, there's no Health Potions to use!");
			}
			else
			{
				currentHero.useHealthPotion();
			}
			break;
		case 4:
			if(isMEmpty == true)
			{
				System.out.println("Sorry, there's no Magic Potions to use!");
			}
			else
			{
				currentHero.useMagicPotion();
			}
			break;
		}

	}
	
	private void displayMonster(Monster opponent)
	{
		System.out.println(opponent.toString());
	}

	private void startMonsterAIAttack(Die combatDie, Die monsterDie, int hRoll, int mRoll, int monsterAttack,
			boolean doesMonsterAttackNorm, boolean isMCrit, boolean isHCrit, boolean isHBotch)
	{
		System.out.println("Monster rolls to attack.");
		hRoll = combatDie.rollDie() + currentHero.getDodgeBonus();
		mRoll = combatDie.rollDie() + monster.getStrikeBonus();
		////////////////////////
		monsterAttack = monsterDie.rollDie();
		if(monsterAttack % 2 == 0)
		{
			doesMonsterAttackNorm = true;
		}
		else if(monsterAttack % 2 != 0)
		{
			doesMonsterAttackNorm = false;
		}
		////////////////////////
		if(mRoll > hRoll)
		{
			System.out.println( monster.getMonsterName() + " landed a hit!");
			if(doesMonsterAttackNorm == true)
			{
				System.out.println(monster.getMonsterName() + " attacks normally....");
				if(isMCrit)
				{
					if(isHBotch)
					{
						System.out.println(currentHero.getName() + " botches!");
						currentHero.receiveDamage(monster.attackNormal() * 4);
						System.out.println("Quadruple Damage!!!");
						System.out.println("Critical Hit!");
					}
					else
					{
						currentHero.receiveDamage(monster.attackNormal() * 2);
						System.out.println("Critical Hit!");
					}
				}
				else
				{
					if(isHBotch)
					{
						System.out.println(currentHero.getName() + " botches!");
						currentHero.receiveDamage(monster.attackNormal() * 2);
						System.out.println("Double Damage!!!");
					}
					else
					{
						System.out.println("Attack landed successfully");
						currentHero.receiveDamage(monster.attackNormal());
					}
					
				}
			}
			else
			{
				System.out.println(monster.getMonsterName() + " performances a special attack....");
				if(isMCrit)
				{
					if(isHBotch)
					{
						System.out.println(currentHero.getName() + " botches!");
						currentHero.receiveDamage(monster.attackSpecial() * 4);
						System.out.println("Quadruple Damage!!!");
						System.out.println("Critical Hit!");
					}
					else
					{
						System.out.println("Special attack landed successfully");
						currentHero.receiveDamage(monster.attackNormal() * 2);
						System.out.println("Critical Hit!");
					}
				}
				else
				{
					if(isHBotch)
					{
						System.out.println(currentHero.getName() + " botches!");
						currentHero.receiveDamage(monster.attackSpecial() * 2);
						System.out.println("Double Damage!!!");
					}
					else
					{
						System.out.println("Special attack landed successfully");
						currentHero.receiveDamage(monster.attackNormal());
					}
				}
			}
		}
		else if(mRoll < hRoll)
		{
			if(doesMonsterAttackNorm == true)
			{
				System.out.println(monster.getMonsterName() + "'s attack missed! No Damage.");
				currentHero.receiveDamage(0);
			}
			else
			{
				System.out.println(monster.getMonsterName() + "'s special attack failed. No Damage.");
				currentHero.receiveDamage(monster.attackSpecial() * 0);
			}
		}
		else if(hRoll == mRoll)
		{
			if(doesMonsterAttackNorm == true)
			{
				if(isMCrit == true)
				{
					System.out.println(monster.getMonsterName()+ "'s attack landed!");
					System.out.println("Critical Hit!");
					currentHero.receiveDamage(monster.attackNormal() * 2);
				}
				else
				{
					System.out.println(monster.getMonsterName()+ "'s attack missed! No damage given.");
					currentHero.receiveDamage(0);
				}
			}
			else
			{
				if(isMCrit == true)
				{
					System.out.println(monster.getMonsterName()+ "'s special attack landed!");
					System.out.println("Critical Hit!");
					currentHero.receiveDamage(monster.attackSpecial() * 2);
				}
				else
				{
					System.out.println(monster.getMonsterName()+ "'s special attack failed! No damage given.");
					currentHero.receiveDamage(0);
				}
			}
			
		}
	}
	
	private int dropPotion(int pChance, Die cDie) 
	{
		
			pChance = cDie.rollDie() * monster.rank;
			
			int potionAmount = 0;
			switch(pChance)
			{
			case 6:
				if(pChance <= 4)
				{
					potionAmount = 1;
				}
				else if(pChance == 5)
				{
					potionAmount = 2;
				}
				else if(pChance == 6)
				{
					potionAmount = 3;
				}
				break;
			case 12:
				if(pChance <= 6)
				{
					potionAmount = 2;
				}
				else if(pChance <= 10 && pChance > 6)
				{
					potionAmount = 3;
				}
				else if(pChance > 10)
				{
					potionAmount = 4;
				}
				break;
			case 24:
				if(pChance == 3)
				{
					potionAmount = 3;
				}
				else if(pChance <= 6 && pChance > 3)
				{
					potionAmount = 4;
				}
				else if(pChance >= 9)
				{
					potionAmount = 5;
				}
				break;
			}
			
			return potionAmount;
	}
	
	private void givePotionToPlayer()
	{
		Die chanceDie = DiceBag.createOneDie(6, false);
		int potionChance = chanceDie.rollDie();
		boolean isHealthPotion = potionChance % 2 == 0;
		boolean isMagicPotion = potionChance % 2 != 0;
		int pAmount = 0;
		switch(monster.rank)
		{
		case 1:
			if(isHealthPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 1:
					currentHero.receiveHealthPotion(hpPotion);
					break;
				case 2:
					for(int i = 0; i < 2; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				}
			}
			else if(isMagicPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 1:
					currentHero.receiveMagicPotion(mpPotion);
					break;
				case 2:
					for(int i = 0; i < 2; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				}
			}
			break;
		case 2:
			if(isHealthPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 2:
					for(int i = 0; i < 2; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				case 4:
					for(int i = 0; i < 4; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				}
			}
			else if(isMagicPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 2:
					for(int i = 0; i < 2; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				case 4:
					for(int i = 0; i < 4; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				}
			}
			break;
		case 3:
			if(isHealthPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				case 4:
					for(int i = 0; i < 4; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				case 5:
					for(int i = 0; i < 5; i++)
					{
						currentHero.receiveHealthPotion(hpPotion);
					}
					break;
				}
			}
			else if(isMagicPotion)
			{
				pAmount = dropPotion(potionChance, chanceDie);
				switch(pAmount)
				{
				case 3:
					for(int i = 0; i < 3; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				case 4:
					for(int i = 0; i < 4; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				case 5:
					for(int i = 0; i < 5; i++)
					{
						currentHero.receiveMagicPotion(mpPotion);
					}
					break;
				}
			}
			break;
		}
	}
	
	private boolean promptToContinue(String prompt)
	{
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		boolean isInputValid = false;
		boolean userChoice = false;
		do
		{
			try
			{
				System.out.println(prompt);
				String userInput = inputReader.readLine();
				String userAnswer = userInput.equalsIgnoreCase("Yes") || userInput.equalsIgnoreCase("Y")
						? "Yes" : "No";
				if(userAnswer == "Yes")
				{
					return userChoice = true;
					
				}
				else if(userAnswer == "No")
				{
					return userChoice = false;
				}
				
				isInputValid = true;
			} catch (IOException e)
			{
				System.out.println("Bad input. Try Again.");
			}
		} while(!isInputValid);
		
		return userChoice;
		
	}
	
}
