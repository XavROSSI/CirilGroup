package main;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
public class Game {
    public static  char[][] Grid;
    public static  char[][] Buffer;
    public static  int EnFeu = 1;
    public static  int nbTour = 1;

	public static void main(String[] args) {
	    try (InputStream input = Game.class.getClassLoader().getResourceAsStream("application.properties")){
	    	Properties prop = new Properties();
	    	prop.load(input);
	    	System.out.println(prop.getProperty("mail"));
	    } catch (IOException ex) {
	    	ex.printStackTrace();
	    }
	    
		int[] size = {10, 10};
		int x = size[0];
		int y = size[1];
		Grid = Init(size);
		Buffer = GridCopy(size, Grid);
		System.out.println("0 correspond à l'état Intact, 1 correspond à l'état en feu et 2 correspond à l'état cendre");
		System.out.println("Voilà la génération de départ");
		for(char[] line : Grid) {
			System.out.println(Arrays.toString(line));
		}
		while(EnFeu == 1) {
			EnFeu = 0;
			Process(size);
			
			Grid = GridCopy(size, Buffer);
			System.out.println("\nVoilà la "+nbTour+"ème génération");
			for(char[] line : Grid) {
				System.out.println(Arrays.toString(line));
			}
			nbTour++;
		}
	}
	
	private static char[][] Init(int[] size) {
		int x = size[0];
		int y = size[1];
		char[][] myTwoDimentionalArray = new char[x][y];
		for (int i = 0; i < x; i++) {
		    for (int j = 0; j < y; j++) {
		        int r = new Random().nextInt(10);
		        if (r < 7) {
			        //70% de chance que la case soit intact
		        	myTwoDimentionalArray[i][j] = 'V';
		        } else {
			        //30% de chance que la case soit en feu
		        	myTwoDimentionalArray[i][j] = 'R';
		        }
		    }
		}
		return myTwoDimentionalArray;
	}
	
	private static char[][] GridCopy(int[] size, char[][] source) {
		int x = size[0];
		int y = size[1];
		char[][] myTwoDimentionalArray = new char[x][y];
		for (int i = 0; i < x; i++) {
		    for (int j = 0; j < y; j++) {
		    	myTwoDimentionalArray[i][j] = source[i][j];
		    }
		}
		return myTwoDimentionalArray;
	}
	
	private static void Process(int[] size) {
		int x = size[0];
		int y = size[1];
		for (int i = 0; i < x; i++) {
		    for (int j = 0; j < y; j++) {
		    	UpdateStatus(size, i, j);
		    }
		}
	}
	
	private static void UpdateStatus(int[] size, int i, int j) {
		//Si la case est en feu
		if(Grid[i][j] == 'R') {
//			System.out.println("\nLa case "+i+" "+j+" passe de l'état en feu à cendre");
//			System.out.println("Check des voisins de "+i+" "+j+" :");
			UpdateNeighbors(size, i-1, j);
			UpdateNeighbors(size, i+1, j);
			UpdateNeighbors(size, i, j-1);
			UpdateNeighbors(size, i, j+1);
			//Passe la case en cendre
			Buffer[i][j] = 'G';
		}
	}
	
	private static void UpdateNeighbors(int[] size, int i, int j) {
		int x = size[0];
		int y = size[1];
		if(i >= 0 && j >= 0 && i < x && j < y){
			//Si la case fait partie du tableau
			if(Grid[i][j] == 'V' && Buffer[i][j] == 'V') {
				//Si la case est intacte
		        int r = new Random().nextInt(10);
		        if (r < 7) {
		        	EnFeu = 1;
			        //70% de chance que la case passe à l'état en feu
		        	Buffer[i][j] = 'R';
//					System.out.println("-La case "+i+" "+j+" passe de l'état intact à en feu");
		        } else {
			        //30% de chance que la case reste à l'état intact
		        	Buffer[i][j] = 'V';
//					System.out.println("-La case "+i+" "+j+" reste intact");
		        }
			}
	    }
	}
}

