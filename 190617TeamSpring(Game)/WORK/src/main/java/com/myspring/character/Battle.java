package com.myspring.character;

import java.util.Random;

public class Battle {

	private Character myCharacter;
	private Character enemyCharacter;

	public Battle(Character myCharacter, Character enemyCharacter) {
		this.myCharacter = myCharacter;
		this.enemyCharacter = enemyCharacter;
	}

	public int[] turnExecute() {
		int mySpeed = this.myCharacter.getSpeed();
		int myGauge = this.myCharacter.getAtkGauge();
		
		if (mySpeed < 1) {
			mySpeed = 1;
		}
		
		int enemySpeed = this.enemyCharacter.getSpeed();
		
		if (enemySpeed < 1) {
			enemySpeed = 1;
		}

		int enemyGauge = this.enemyCharacter.getAtkGauge();

		myGauge += mySpeed;
		enemyGauge += enemySpeed;

		this.myCharacter.setAtkGauge(myGauge);
		this.enemyCharacter.setAtkGauge(enemyGauge);
		
		int[] gauge = { myGauge, enemyGauge };
		
		return gauge;
	}

	public int attackExecute(int whoAttack) {

		Character attacker = new Character();
		Character defender = new Character();
		int battleResult = 0;

		if (whoAttack == 0) { // 0 : my turn
			attacker = myCharacter;
			defender = enemyCharacter;

		} else if (whoAttack == 1) { // 1 : enemy's turn
			attacker = enemyCharacter;
			defender = myCharacter;
		}

		Random random = new Random();
		int getRate;
		// Max Rate = 100%
		getRate = random.nextInt(100);
		if (attacker.getAtkRate() > getRate) {

			// Minimum Damage Is 1
			int damage = 1;

			if (attacker.getAtk() > defender.getDef()) {
				damage = (int) Math.ceil((double) attacker.getAtk() / defender.getDef());
			}

			// Defense Success : Reduce Damage To 50%
			if (damage > 2) {
				// Max Rate = 100%
				getRate = random.nextInt(100);
				if (defender.getDefRate() > getRate) {
					damage = (int) Math.ceil((double) damage / 2);
				}
			}

			if (whoAttack == 0) { // 0 : my turn
				enemyCharacter.setCurrentHp(enemyCharacter.getCurrentHp() - damage);
			} else if (whoAttack == 1) { // 1 : enemy's turn
				myCharacter.setCurrentHp(myCharacter.getCurrentHp() - damage);
			}
			
			battleResult = damage;
			
		}
		
		return battleResult;

	}

}
