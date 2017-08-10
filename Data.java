import java.util.*;

public class Data
{
	public int parseLetter(String coord)
	{
		return coord.charAt(0) - 65;
	}

	public int parseNumber(String coord)
	{
		try
		{
			return Integer.parseInt(coord.substring(1, 3)) - 1;
		}
		catch (Exception e)
		{
			return Integer.parseInt(coord.substring(1, 2)) - 1;
		}
	}

	public String parseX(int xCoord)
	{
		return "" + (xCoord + 1);
	}

	public String parseY(int yCoord)
	{
		return "" + (char) ('A' + yCoord);
	}

	public void printMap(int[][] gridArray)
	{
		System.out.println("\n    1 2 3 4 5 6 7 8 9 10");
		System.out.println("   ---------------------");

		for (int i = 0; i < 10; i ++)
		{
			System.out.print((char) ('A' + i) + " | ");
			for (int j = 0; j < 10; j ++)
			{
				if (gridArray[i][j] == 0)
					System.out.print(". ");
				else if (gridArray[i][j] == 1)
					System.out.print("o ");
				else if (gridArray[i][j] == 2)
					System.out.print("+ ");
				else if (gridArray[i][j] == 3)
					System.out.print("# ");
			}
			System.out.println("|");
		}

		System.out.println("   ---------------------");
	}

	public String attack(ArrayList<String> attacks)
	{
		System.out.println("Enter coordinates to attack:");
		Scanner in = new Scanner(System.in);
		String attack = in.next().toUpperCase();
		while (!attack.substring(0, 1).matches("[A-J]") || !attack.substring(1, 2).matches("[0-9]+") || attacks.contains(attack))
		{
			System.out.println("Not a valid attack!\nFormat: [Letter][Number]");
			attack = in.next().toUpperCase();
		}
		attacks.add(attack);
		return attack;
	}

	public void parseAttack(String attack, ArrayList<String> attacks, ArrayList<String> ships, int[][] grid)
	{
		int i = parseLetter(attack);
		int j = parseNumber(attack);

		if (ships.contains(attack))
			grid[i][j] = 3;
		else
			grid[i][j] = 2;
	}

	public void parseShips(ArrayList<String> ships, int[][] grid)
	{
		for (int i = 0; i < ships.size(); i ++)
		{
			int a = parseLetter(ships.get(i));
			int b = parseNumber(ships.get(i));

			grid[a][b] = 1;
		}
	}

	public void addUserShips(ArrayList<String> ships, String type)
	{
		Scanner in = new Scanner(System.in);
		int length = 0;

		switch (type.toLowerCase())
		{
			case "aircraft carrier":
				length = 5;
				break;
			case "battleship":
				length = 4;
				break;
			case "submarine":
				length = 3;
				break;
			case "destroyer":
				length = 3;
				break;
			case "patrol boat":
				length = 2;
				break;
		}

		System.out.println("Enter coordinates for " + type + " (" + length + " units)");

		for (int i = 0; i < length; i ++)
		{
			String coord = in.next().toUpperCase();
			while (!coord.substring(0, 1).matches("[A-J]") || !coord.substring(1, 2).matches("[0-9]+") || ships.contains(coord))
			{
				if (ships.contains(coord))
					System.out.println("You already placed a ship on that space!");
				else
					System.out.println("Invalid coordinates!\nFormat: [Letter][Number]");
				coord = in.next().toUpperCase();
			}
			ships.add(coord);
		}
	}

	public boolean shipsContains(ArrayList<String> ships, ArrayList<String> boat)
	{
		boolean res = false;
		for (int i = 0; i < boat.size(); i ++)
			if (ships.contains(boat.get(i)))
				res = true;
		return res;
	}

	public void addCompShips(ArrayList<String> ships, String type)
	{
		ArrayList<String> boat = new ArrayList<String>();
		Random rand = new Random();

		int dir = rand.nextInt(2);
		int length = 0;

		switch (type.toLowerCase())
		{
			case "aircraft carrier":
				length = 5;
				break;
			case "battleship":
				length = 4;
				break;
			case "submarine":
				length = 3;
				break;
			case "destroyer":
				length = 3;
				break;
			case "patrol boat":
				length = 2;
				break;
		}

		if (dir == 0)
		{
			int xCoord = rand.nextInt(10 - length);
			int yCoord = rand.nextInt(10);

			for (int i = 0; i < length; i ++)
				boat.add(parseY(yCoord) + parseX(xCoord + i));

			while (shipsContains(ships, boat))
			{
				boat.clear();
				xCoord = rand.nextInt(10 - length);
				yCoord = rand.nextInt(10);

				for (int i = 0; i < length; i ++)
					boat.add(parseY(yCoord) + parseX(xCoord + i));
			}

			for (int i = 0; i < length; i ++)
				ships.add(boat.get(i));
		}
		else if (dir == 1)
		{
			int xCoord = rand.nextInt(10);
			int yCoord = rand.nextInt(10 - length);

			for (int i = 0; i < length; i ++)
				boat.add(parseY(yCoord + i) + parseX(xCoord));

			while (shipsContains(ships, boat))
			{
				boat.clear();
				xCoord = rand.nextInt(10);
				yCoord = rand.nextInt(10 - length);

				for (int i = 0; i < length; i ++)
					boat.add(parseY(yCoord + i) + parseX(xCoord));
			}

			for (int i = 0; i < length; i ++)
				ships.add(boat.get(i));
		}
	}

	public String compAttack(ArrayList<String> attacks)
	{
		Random rand = new Random();
		String attack = parseY(rand.nextInt(10)) + parseX(rand.nextInt(10));

		while (attacks.contains(attack))
			attack = parseY(rand.nextInt(10)) + parseX(rand.nextInt(10));

		return attack;
	}

	public boolean gameOver(ArrayList<String> ships, ArrayList<String> attacks)
	{
		boolean over = true;
		for (int i = 0; i < ships.size(); i ++)
			if (!attacks.contains(ships.get(i)))
				over = false;
		return over;
	}
}