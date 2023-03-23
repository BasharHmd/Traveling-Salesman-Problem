import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

//Some useful code for the CS2004 (2019-2020) Travelling Salesman Worksheet
public class TSP {
	// Print a 2D double array to the console Window
	static public void PrintArray(double x[][]) {
		for (int i = 0; i < x.length; ++i) {
			for (int j = 0; j < x[i].length; ++j) {
				System.out.print(x[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}

	}

	// This method reads in a text file and parses all of the numbers in it
	// This method is for reading in a square 2D numeric array from a text file
	// This code is not very good and can be improved!
	// But it should work!!!
	// 'sep' is the separator between columns
	static public double[][] ReadArrayFile(String filename, String sep) {
		double res[][] = null;
		try {
			BufferedReader input = null;
			input = new BufferedReader(new FileReader("C:\\Users\\basha\\OneDrive\\Desktop\\Brunel\\CS2004\\CS2004 TSP Data (2019-2020)\\TSP_48.txt"));
			String line = null;
			int ncol = 0;
			int nrow = 0;

			while ((line = input.readLine()) != null) {
				++nrow;
				String[] columns = line.split(sep);
				ncol = Math.max(ncol, columns.length);
			}
			res = new double[nrow][ncol];
			input = new BufferedReader(new FileReader("C:\\Users\\basha\\OneDrive\\Desktop\\Brunel\\CS2004\\CS2004 TSP Data (2019-2020)\\TSP_48.txt"));
			int i = 0, j = 0;
			while ((line = input.readLine()) != null) {

				String[] columns = line.split(sep);
				for (j = 0; j < columns.length; ++j) {
					res[i][j] = Double.parseDouble(columns[j]);
				}
				++i;
			}
		} catch (Exception E) {
			System.out.println("+++ReadArrayFile: " + E.getMessage());
		}
		return (res);
	}

	// This method reads in a text file and parses all of the numbers in it
	// This code is not very good and can be improved!
	// But it should work!!!
	// It takes in as input a string filename and returns an array list of Integers
	static public ArrayList<Integer> ReadIntegerFile(String filename) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		Reader r;
		try {
			r = new BufferedReader(new FileReader("C:\\Users\\basha\\OneDrive\\Desktop\\Brunel\\CS2004\\CS2004 TSP Data (2019-2020)\\TSP_48.txt"));
			StreamTokenizer stok = new StreamTokenizer(r);
			stok.parseNumbers();
			stok.nextToken();
			while (stok.ttype != StreamTokenizer.TT_EOF) {
				if (stok.ttype == StreamTokenizer.TT_NUMBER) {
					res.add((int) (stok.nval));
				}
				stok.nextToken();
			}
		} catch (Exception E) {
			System.out.println("+++ReadIntegerFile: " + E.getMessage());
		}
		return (res);
	}

//		ArrayList<Integer> P = new ArrayList<Integer>();
//		int a = 0;
//		ArrayList<Integer> tour = new ArrayList<Integer>();
//		System.out.println(P.size());
//		for (int i = 0; i < a; i++) {
//			a = CS2004R.UI(1, a);
//			P.add(a);
//
//		}
//		Iterator itr = P.iterator();
//		while (itr.hasNext()) {
//
//			int x = (Integer) itr.next();
//			tour.add(x);
//
//			// tour.add((Integer)itr.next());
//			itr.remove();
//		}
//		// showing array
//		int j = 0;
//		while (j < a) {
//			System.out.println("Data in Tour = " + tour.get(j));
//			j++;
//		}
//		return P;

	public static double TSPfitness(int n, ArrayList<Integer> tour, double[][] d) {

		double s = 0;
		for (int i = 0; i < n - 1; ++i) {

			int a = tour.get(i);
			int b = tour.get(i + 1);

			s = s + d[a][b];
		}

		double end_city = tour.get(n - 1);
		double start_city = tour.get(0);
		s = s + (end_city + start_city);
		return s;

	}

	public static ArrayList<Integer> randPerm(int n) {
		ArrayList<Integer> tour = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			tour.add(i);
		}
		System.out.println("Ordered Tour: " + tour);
		Collections.shuffle(tour);
		System.out.println("Random Tour: " + tour);
//      converts to array
//		Integer[]intArray=new Integer[tour.size()];
//		tour.toArray(intArray);
		return tour;

	}

	public static void TSPsmallChange(ArrayList<Integer> tour) {
		int i = 0;
		int j = 0;
		while (i == j) {
			i = CS2004R.UI(1, tour.size() - 1);
			j = CS2004R.UI(1, tour.size() - 1);
		}

		int temp = tour.get(i);
		tour.set(i, tour.get(j));
		tour.set(j, temp);
		// System.out.println("Solution after change " + tour);

	}

	public static ArrayList<Integer> RMHC(ArrayList<Integer> tour, int n, int iter) {
		double[][] file = ReadArrayFile("C:\\Users\\basha\\OneDrive\\Desktop\\Brunel\\CS2004\\CS2004 TSP Data (2019-2020)\\TSP_48.txt", " ");
		for (int i = 1; i < iter; i++) {
			double oldfit = TSPfitness(n, tour, file);
			System.out.print("   " + oldfit + "  ");
			System.out.println(tour);
			System.out.print("   ");
			ArrayList<Integer> oldtour = new ArrayList<Integer>(tour);
			TSPsmallChange(tour);
			double newfit = TSPfitness(n, tour, file);

			System.out.print("   " + newfit + "  ");
			System.out.println(tour);
			System.out.print("   ");
			System.out.println("*****************************************");

			if (oldfit < newfit) {

				tour = oldtour;

			}

		}

		return (tour);
	}

/*	private static double PR(double newFitness, double fitness, double temp) {
        return Math.exp(-1*Math.abs(fitness-newFitness)/temp);
}

	private static double simulatedAnnealing(double temp, double coolingRate, int n, int iter, ArrayList<Integer> tour) {
		double[][] file = ReadArrayFile("H:/2004/TSP_48.txt", " ");
		ArrayList<Integer> newTour = new ArrayList<Integer>();
		double fitness = -1, newFitness, p;

		for (int i = 0; i < iter; ++i) {
			// Original fitness
		double oldfitness= TSPfitness(n, tour, file);
			// Small change
			 TSPsmallChange(tour);

			// New fitness
			newFitness = TSPfitness(n, tour, file);

			// If new fitness is worse than the old fitness
			if (newFitness > fitness) {
				p = PR(newFitness, fitness, temp);
				if (p < CS2004R.UR(0, 1)) {

				} else {
					fitness = newFitness;
					tour = newTour;
				}
			} else {
				tour = newTour;
			}
			temp = coolingRate * temp;

		}
		return fitness;
	}*/

	public static void main(String args[]) {
		// respresentation of TSP
		int n = 48;
		ArrayList<Integer> tour = randPerm(n);
	   // Object[] tour1 = tour.toArray();   

		// double[][] file = ReadArrayFile("H:/2004/TSP_48.txt", " ");

	     System.out.println(RMHC(tour, n, 15));
		//System.out.println(simulatedAnnealing(15.5, 15.5, 15, 15 , tour));

	}
}
