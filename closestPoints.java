package stats;

import java.lang.Math;
import java.util.LinkedList;
import java.util.Scanner;

/* A class that takes in a value n, 2D array of size n representing points, a value k and a center point. The value k is how many closest points you wish to find
   returns a Sorted list of size k in the case of a LinkedList and of size n in the case of bubbleSort.
*/

public class closestPoints {														// We are expecting 3 arguments. A 2D array of n points, an integer k and a int[2] centerPoint

	int[][] givenPoints;															// 2D array to become points passed to constructor
	
	Point[] points;																	// Array of Object Point. Point has 3 double characteristics, x, y and distance
	
	int[] centerPoint = new int[2];													// Given centerPoint treated as origin. x,y values of givenPoints will be subtracted by x,y value of center point to find distance.
	
	int k = 0;																		// Given k value initialized to 0
	
	LinkedList<Point> linkedSortedPoints = new LinkedList<Point>();					// LinkedList of sorted Points. We will insert points (object) one at a time at an index according to their distance
	
	public closestPoints(int n, int[][] givenPoints, int k, int[] centerPoint) {	// Constructor that takes in the 4 arguments
		
		points = new Point[n];																// Points (object) array initialized to size n												

		this.givenPoints = new int[n][2];													// 2D array initialized to size n
		
		this.givenPoints = givenPoints;														// Make global variable givenPoints equal to argument passed
		this.k = k;																			// Make global variable k	   		equal to argument passed
		this.centerPoint = centerPoint;														// Make global variable centerPoint equal to argument passed
		
		if(k > givenPoints.length) {														// exit if k > n
			System.out.println("k must be less than the number of points");
			System.exit(0);
		}		
	}
	
	public void quickSort(Point[] pArray) { // NOT FINISHED
		
		Point temp = new Point();
		boolean a = false;
		boolean b = false;
		if(pArray.length == 1) {
			return;
		}
		
		double pivot = (pArray[pArray.length/2].distance);
		int ptr1 = 0;
		int ptr2 = pArray.length-1;
		
		for(int i =0; i<pArray.length; i++) {

			if(pArray[ptr1].distance<pivot) {
				ptr1++;
			}else{
				a = true;
			}
			
			if(pArray[ptr2].distance>pivot) {
				ptr2++;
			}else {
				b = true;
			}
			
			if(a == true && b == true) {
				temp = pArray[ptr1];
				pArray[ptr1] = pArray[ptr2];
				pArray[ptr2] = temp;
				a = false; b = false;
			}
		}	
	}
	
	public void bubbleSort() {

		initializePoints();
		
		Point temp = new Point();
		
		for(int i =0; i<givenPoints.length; i++) {											// For every given point
			
			setPointDistance((double) givenPoints[i][0], (double) givenPoints[i][1], i);	// Find the distance and set point distance
		}
		
		for(int i = 0; i<givenPoints.length -1; i++) {
			
			for(int j=0; j<givenPoints.length - i - 1; j++) {
				
				if(points[j].distance > points[j+1].distance) {
					
					temp = points[j];
					points[j] = points[j+1];
					points[j+1] = temp;
				}
			}
		}
		printBubbleSortedPoints();
	
	}
	
	public void linkedListSort() {

		initializePoints();
		
		for(int i =0; i<givenPoints.length; i++) {											// For every given point
			
			setPointDistance((double) givenPoints[i][0], (double) givenPoints[i][1], i);		// Find the distance and set point distance
			
			insertPointDLL(points[i]);														// Call insertPointDLL method to place into linkedSortedPoints DLL
		}
		printLinkedSortedPoints();																		// Print points
	}
	
	public void selectionSort() {
		
		initializePoints();
		
		double minDistance;
		int index = 0;
		Point temp = new Point();
		
		for(int i =0; i<givenPoints.length; i++) {											// For every given point
			
			setPointDistance((double) givenPoints[i][0], (double) givenPoints[i][1], i);	// Find the distance and set point distance
		}	
		
		for(int i =0; i<points.length; i++) {
			
			minDistance = points[i].distance;
			for(int j = i; j<points.length; j++) {
				
				if(points[j].distance < minDistance) {

				minDistance = points[j].distance;
				index = j;
				}
			}
			temp = points[i];
			points[i] = points[index];
			points[index] = temp;
		}
		printSelectionSort();
		
	}
	
	//Helper Methods
	public void initializePoints() {
		
		for(int i =0; i<points.length; i++) {												// Initialize points[] with points. Data values are initialized to 0 by Point constructor
			Point temp = new Point();
			points[i] = temp;
		}

		for(int i =0; i<givenPoints.length; i++) {											// Update x and y values of Point object in points[]
			
			points[i].x = givenPoints[i][0];												// set Point.x = givenPoint.x
			points[i].y = givenPoints[i][1];												// set Point.y = givenPoint.y 
		}
	}

	public void setPointDistance(double x, double y, int i) {
	
		
		double distance;
		
		x = x - centerPoint[0];																// Find x from centerPoint to givenPoint
		y = y - centerPoint[1];																// Find y from centerPoint to givenPoint
		x = x * x;																			// Square x 
		y = y * y;																			// Square y
																							//  ** We don't need to get abs value because square will guarantee us a positive Real Number

		distance = Math.sqrt(x + y);														// Calculate: distance = Sqrt(x^2 + y^2)
		
		points[i].distance = distance;
	}
	
	public void insertPointDLL(Point p){
	
	
		for(int i =0; i<=linkedSortedPoints.size() && i < k; i++) {								// for every node within linkedSortedPoints
		
			if(linkedSortedPoints.isEmpty()) {													// if linkedSortedPoints is empty
				
				linkedSortedPoints.addFirst(p);													// set p as head
				break;
			}
			
			if(p.distance >= linkedSortedPoints.getLast().distance && linkedSortedPoints.size() < k) {	// if linkedSortedPoints was not empty, and p distance was not smaller than any of the nodes' in the list	
				
				linkedSortedPoints.addLast(p);													// then p distance is the largest and will go at the end of the list												
				break;																
			}
			
			if(p.distance < linkedSortedPoints.get(i).distance) {									// if p distance is less than currentNode distance
				
				linkedSortedPoints.add(i, p);														// p will go "behind" currentNode and currentNode will now be at next index			
				break;
			}
		}
		keepListSizeK();
	}
	
	public void keepListSizeK() {													// Most important method. Keeps linkedSortedPoints list size of k. This reduces the number of comparisons we have to do dramatically if k << n 
		
		if(linkedSortedPoints.size() > k) {											// If the list is > k 
			linkedSortedPoints.removeLast();										// Remove the largest element in the list (last element)
		}
	}
	
	public void printLinkedSortedPoints() {

		
		System.out.print("The closest " + k + " point(s) to " + "(" + centerPoint[0] + "," + centerPoint[1] + ") " + "in increasing order of distance are: ");
		for(int i=0; i<k; i++) {
			System.out.print("(");
			System.out.print((int) linkedSortedPoints.get(i).x);
			System.out.print(",");
			System.out.print((int) linkedSortedPoints.get(i).y );
			if(i == k-1) {
				System.out.println(") ");
			}else {
				System.out.print("), ");
			}
		}
	}
	
	
	public void printBubbleSortedPoints() {

		System.out.print("The closest " + k + " point(s) to " + "(" + centerPoint[0] + "," + centerPoint[1] + ") " + "in increasing order of distance are: ");
		for(int i=0; i<k; i++) {
			System.out.print("(");
			System.out.print((int) points[i].x);
			System.out.print(",");
			System.out.print((int) points[i].y);
			if(i == k-1) {
				System.out.println(") ");
			}else {
				System.out.print("), ");
			}
		}
	}
	public void printSelectionSort() {

		System.out.print("The closest " + k + " point(s) to " + "(" + centerPoint[0] + "," + centerPoint[1] + ") " + "in increasing order of distance are: ");
		for(int i=0; i<k; i++) {
			System.out.print("(");
			System.out.print((int) points[i].x);
			System.out.print(",");
			System.out.print((int) points[i].y);
			if(i == k-1) {
				System.out.println(") ");
			}else {
				System.out.print("), ");
			}
		}
	}
	
	public static void main(String args[]) {
		
		System.out.println("This program will generate n random points and find the 'k' closest points to a center point '(x,y)'.");
		System.out.println("It will print them in ascending order according to distance along with the average runtime.");
		System.out.println("You may choose to solve this with several different data structures.");
		
		int dataStructure = 5;
		
		double totalTime = 0;																// Total run time initialized to 0
		double averageTime = 0;																// Average run time initialized to 0
		
		int nbRuns = 0;																		// User given number of runs
		int n = 0;																			// Number of points
		
		int k = 0;																			// User given k value
		
		int[] centerPoint = new int[2];														// User given centerPoint
		
		Scanner s = new Scanner(System.in);
		
		System.out.println("");
		
		while(dataStructure > 4) {
		System.out.println("Type a number to select the data structure you would like to use");
		System.out.println("1: DoublyLinkedList    2: BubbleSort    3. SelectionSort    4. Let program optimize");
		dataStructure = s.nextInt();
		}
		System.out.println("Type a value for n. The amount of points you wish to generate.");
		n = s.nextInt();
		System.out.println("Type a value for k.");
		k = s.nextInt();	
		System.out.println("Type a value for x.  (x ∈ N | -200 < x < 200)" );
		centerPoint[0] = s.nextInt();
		if(centerPoint[0] > 200) {
			System.out.println("You rebel you..");
		}
		System.out.println("Type a value for y.   (y ∈ N | -200 < y < 200)" );
		centerPoint[1] = s.nextInt();
		if(centerPoint[1] > 200) {
			System.out.println("I see how it is.");
		}
		System.out.println("Type how many runs you would like to do.");
		nbRuns = s.nextInt();
		s.close();
		
		int[][] givenPoints = new int[n][2];												// 2D array that will be filled with n randomly generated points
		
		for(int j=0; j<nbRuns; j++) {														// for every run
	
			System.out.println();
			System.out.println("Run: " + (j+1));
			
			for(int i = 0; i<n; i++) {														// Generate random x,y values and fill givenPoints array
			
				givenPoints[i][0] = -200 + (int) (Math.random() * 400);
				givenPoints[i][1] = -200 + (int) (Math.random() * 400);
			}
		
			double startTime = System.nanoTime();											// Record current time in nanoseconds
		
			closestPoints x = new closestPoints(n,givenPoints,k,centerPoint);				// Where the magic happens
			
			switch(dataStructure) {
			
			case 1:
				x.linkedListSort();
				break;
			case 2:
				x.bubbleSort();
				break;
			case 3:
				x.selectionSort();
				break;
			case 4:
				if((double) ((double) k/ (double) n) < 0.05) {
				x.linkedListSort();
				}else{
				x.selectionSort();
				}
				break;
			}
			
			System.out.println();
			System.out.println((double) ((double) k/ (double) n));
			System.out.println();
			
			double stopTime = System.nanoTime();											// Record current time in nanoseconds
		
			double timeElapsed = (stopTime - startTime) / 1000000000.0;						// Get the difference and divide by 10^9 to get runtime in seconds
			
			System.out.println("Time to execute was: " + timeElapsed +  " nanoseconds");
		
			totalTime = totalTime + timeElapsed;											// Add run times together to get average
		
			System.out.println();
			System.out.println("The " + n + " randomly generated points are: ");
			
			//for(int i = 0; i<n; i++) {													// Print randomly generated points in givenPoints array
			
				//System.out.print("(" + givenPoints[i][0] + "," + givenPoints[i][1] + "), ");
			
			//}
			System.out.println();
			System.out.println();
		}
		averageTime = totalTime/nbRuns;														// Average run time
		System.out.println("Average time in " + nbRuns + " runs was: " + averageTime + " seconds");
		System.out.println();
		System.out.println("Program has finished running.");
	}
	
}
