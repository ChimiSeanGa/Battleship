import java.util.*;

public class Battleship
{
	public static void main(String[] args)
	{
		int[][] userArray = new int[10][10];
		int[][] compArray = new int[10][10];

		ArrayList<String> userAttacks = new ArrayList<String>();
		ArrayList<String> userShips = new ArrayList<String>();
		ArrayList<String> compAttacks = new ArrayList<String>();
		ArrayList<String> compShips = new ArrayList<String>();

		Data game = new Data();
		boolean gameOver = false;
		String userAttack;
		String compAttack;

		game.printMap(compArray);
		System.out.println("         COMPUTER");
		System.out.println("___________________________");
		game.printMap(userArray);
		System.out.println("          PLAYER\n");

		game.addUserShips(userShips, "Aircraft Carrier");
		game.addUserShips(userShips, "Battleship");
		game.addUserShips(userShips, "Submarine");
		game.addUserShips(userShips, "Destroyer");
		game.addUserShips(userShips, "Patrol Boat");
		game.parseShips(userShips, userArray);

		game.addCompShips(compShips, "Aircraft Carrier");
		game.addCompShips(compShips, "Battleship");
		game.addCompShips(compShips, "Submarine");
		game.addCompShips(compShips, "Destroyer");
		game.addCompShips(compShips, "Patrol Boat");

		while (!gameOver)
		{
			game.printMap(compArray);
			System.out.println("         COMPUTER");
			System.out.println("___________________________");
			game.printMap(userArray);
			System.out.println("          PLAYER\n");

			userAttack = game.attack(userAttacks);
			compAttack = game.compAttack(compAttacks);

			game.parseAttack(userAttack, userAttacks, compShips, compArray);
			game.parseAttack(compAttack, compAttacks, userShips, userArray);

			if (game.gameOver(compShips, userAttacks))
			{
				game.printMap(compArray);
				System.out.println("         COMPUTER");
				System.out.println("___________________________");
				game.printMap(userArray);
				System.out.println("          PLAYER\n");
				System.out.println("** YOU WIN **");
				gameOver = true;
			}
			else if (game.gameOver(userShips, compAttacks))
			{
				game.printMap(compArray);
				System.out.println("         COMPUTER");
				System.out.println("___________________________");
				game.printMap(userArray);
				System.out.println("          PLAYER\n");
				System.out.println("** YOU LOSE **");
				gameOver = true;
			}
		}
	}
}