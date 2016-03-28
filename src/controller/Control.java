package controller;

import java.util.Scanner;

import model.State;
import view.GameBoard;

public class Control {
	
	/*
	 * 	My work here is done.
	 */
	
	public static void main(String[] args) {		
		String play = "";
		System.out.println("Is there a human player? :");
		Scanner scan = new Scanner(System.in);
		play = scan.nextLine();
		
		if(play.equals("yes")){
			GameBoard board = new GameBoard(48);		
			State gameState = board.initializeState();
			//board.disablePlayer(0);
		}else{
			GameBoard board = new GameBoard(48);		
			State gameState = board.initializeState();
			
			board.disablePlayer(0);
			board.disablePlayer(1);
			
			board.computerGame();
		}
	}
	
}
