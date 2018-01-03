package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import lib.ConsoleIO;
import models.DiceBag;
import models.Die;
import models.Hero;
import monsters.Monster;

public class Combat
{
	public static int promptForFight()
	{
		String [] menu = {"RatMan", "RatWarrior", "AlphaRat"};
		System.out.println("Choose which monster you want to fight.");
		return ConsoleIO.promptForMenuSelection(menu, false);
	}
	
	public static void displayCombat(Hero hero, Monster monster)
	{
		String heroName = hero.getName();
		String monName = monster.getMonsterName();
		int heroHP = hero.getCurrentHP();
		int monstHP = monster.getCurrentHP();
		int heroMP = hero.getCurrentMP();
		int monstMP = monster.getCurrentMP();
		int heroStren = hero.getHeroStrength();
		int monStren = monster.getMonsterStrength();
		int heroDam = hero.getDamage();
		int monDam = monster.getDamageBonus();
		int heroIntel = hero.getHeroIntelligence();
		int monIntel = monster.getMonsterIntelligence();
		int heroSpell = hero.getSpellBonus();
		int monSpell = monster.getSpellBonus();
		int heroDex = hero.getHeroDexterity();
		int monDex = monster.getMonsterDexterity();
		int heroDod = hero.getDodgeBonus();
		int monDod = monster.getDodgeBonus();
		int heroStrike = hero.getStrikeBonus();
		int monStrike = monster.getStrikeBonus();
		
		
		String comDisplayZero = "---------------------------------------------------\n"
				+ " \t>>>> Hero: " + heroName + "\t vs \t" + monName + " : Monster <<<<";
		
		String strengthLine = "\nStrength: " + heroStren + "\t\t <<>> \t" + "Strength : " + monStren
				+ "\nDamage Bonus: " + heroDam + "\t\t <<>> \t" + " Damage Bonus: " + monDam + "\n";
		
		String intelLine = "Intelligence: " + heroIntel + "\t\t <<>> \t" + "Intelligence: " + monIntel 
				+ "\nSpell Strength: " + heroSpell + "\t <<>> \t" + " Spell Strength: " + monSpell +"\n";
		
		String dexLine = "Dexterity: " + heroDex + "\t\t <<>> \t" + "Dexterity: " + monDex
				+  "\nDodge Bonus: " + heroDod + "\t\t <<>> \t" + "Dodge Bonus: " + monDod
				+ "\nStrike Bonus: " + heroStrike + "\t\t <<>> \t" + " Strike Bonus: "+ monStrike + "\n";
		
		String comDisplayOne = "\n <<<<< Stats >>>>> \n"
				+ "\t\tHP: " + heroHP + "\t <<>> \t" + "HP: "+ monstHP +"\n"
				+ "\t\tMP: " + heroMP + "\t <<>> \t" + "HP: "+ monstMP +"\n"
				+ "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
				+ strengthLine
				+ intelLine
				+ dexLine;
		
		String comDisplayTwo =  "\n++++++++++++++++++++++++++++++++++++++++\n"
				+ hero.printHpPotionInventory()
		 		+ "==========\n"
				+ hero.printMpPotionInventory()
				+ "==========\n";
		System.out.println(comDisplayZero + comDisplayOne + comDisplayTwo);
	}

	public static int displayFightMenu(Hero hero, Monster monster, boolean isHpEmpty, boolean isMPEmpty)
	{
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		boolean isValid = false;
		System.out.println("************************************************");
		String firstLine = "Hero: " + hero.getName() + "\t>><<\t" + "Monster: " + monster.getMonsterName() + "\n";
		String healthBarLine = hero.printHPAndMP(hero.getCurrentHP(), hero.getCurrentMP(), true)
				+ "\t>><<\t" + monster.printHPAndMP(monster.getCurrentHP(), monster.getCurrentMP(), true) + "\n";
		String magicBarLine = hero.printHPAndMP(hero.getCurrentHP(), hero.getCurrentMP(), false)
				+ "\t>><<\t" + monster.printHPAndMP(monster.getCurrentHP(), monster.getCurrentMP(), false) + "\n";
		
		String [] menuOptions = {"1) Norm. Atck.", "2) Sp. Atck.", "Use) H. Pot.", "Use) M. Pot."};
		
		String menuPrompt = "Type a number to pick an option.\n";
		int hpQuantity = hero.getNumberOfHPPotions();
		int mpQuantity = hero.getNumberOfMPPotions();
		int userNum = 0;
		
		do
		{
			try
			{
				
				System.out.println(firstLine + healthBarLine + menuPrompt);
				
				System.out.println(menuOptions[0]);
				System.out.println(menuOptions[1]);
				
				if(hpQuantity == 0 && mpQuantity == 0)
				{
					
					System.out.print(") -----\t");
				
					System.out.println(") -----");
					isHpEmpty = true;
					isMPEmpty = true;
				}
				else if(hpQuantity != 0 && mpQuantity != 0)
				{
					System.out.println(menuOptions[2]);
					System.out.println(menuOptions[3]);
					isHpEmpty = false;
					isMPEmpty = false;
				}
				else if(hpQuantity != 0 && mpQuantity == 0)
				{
					
					System.out.print(menuOptions[2] + "\t");
					
					System.out.println(") -----");
					isHpEmpty = false;
					isMPEmpty = true;
					
				}
				else if(hpQuantity == 0 && mpQuantity != 0)
				{
					
					System.out.print(") -----\t");
					
					System.out.println(menuOptions[3]);
					isHpEmpty = true;
					isMPEmpty = false;
				}
							
				String userInput = inputReader.readLine();
				userNum = Integer.parseInt(userInput);
				System.out.println("************************************************");
				
			} catch (IOException e)
			{
				System.out.println("Invalid Input. Try again: ");
			}
			
		} while(!isValid);
		return userNum;	
	}

	public static boolean initiateCombat(int heroRoll, int monsterRoll)
	{
		Die die = DiceBag.createOneDie(20, false);
		
		boolean isAttacker = false;
		do
		{
			if(heroRoll > monsterRoll)
			{
				return isAttacker = true;
			}
			else if(heroRoll < monsterRoll)
			{
				return isAttacker;
			}
			else if(heroRoll == monsterRoll)
			{
				heroRoll = die.rollDie();
				monsterRoll = die.rollDie();
			}
			
		} while (heroRoll == monsterRoll);
		
		return isAttacker;
	}
	
	
}
