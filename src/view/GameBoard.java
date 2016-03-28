package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.State;
import controller.MiniMaxSearch;

public class GameBoard extends JFrame implements ActionListener{

	private JButton[][] tiles; 
	private State curState;
	private boolean turn;
	private MiniMaxSearch miniMax;
	private boolean extraTurn;
	private int totalStones;
	
	JButton clockwise, move;
	
	public GameBoard(int nStones){
		setPreferredSize(new Dimension(700,300));
		setTitle("Mancala");
		turn = true;
		extraTurn = false;
		totalStones = nStones;
		miniMax = new MiniMaxSearch();
		
		curState = new State();
		
		JPanel options = new JPanel();
		JPanel board = new JPanel();
		
		board.setLayout(new GridLayout(2,8));
		options.setLayout(new FlowLayout());		
		setLayout(new GridLayout(2,1));
		
		clockwise = new JButton("Clockwise");
		clockwise.addActionListener(this);
		clockwise.setActionCommand("Clockwise");
		
		move = new JButton("Move");
		move.addActionListener(this);
		move.setActionCommand("move");
		
		tiles = new JButton[8][2];
		
		JButton opponent = new JButton("Opponent");
		opponent.setEnabled(false);
		
		board.add(opponent);
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				if(i != 0 && i != 7){
					tiles[i][j] = new JButton("4");
				}
				if(i == 0 && j == 1){
					JButton player = new JButton("Player");
					player.setEnabled(false);
					board.add(player);
				}
				if(i == 0 || i == 7){
					tiles[i][j] = new JButton("");
					tiles[i][j].setEnabled(false);
				}
				tiles[i][j].addActionListener(this);
				tiles[i][j].setActionCommand(i + ", " + j);
				board.add(tiles[i][j]);
			}
		}
		
		options.add(clockwise);
		options.add(move);
		
		add(options);
		add(board);
		
		setResizable(false);
		setVisible(true);
		pack();
	}
	
	public void disablePlayer(int player){
		for(int i=0; i < 8; i++){
			tiles[i][player].setEnabled(false);
		}
	}
	
	public void enablePlayer(int player){
		for(int i=0; i < 8; i++){
			tiles[i][player].setEnabled(true);
		}
	}
	
	public State initializeState(){
		curState = new State();
		curState.initialize();
		
		return curState;
	}
	
	public State getCurState(){
		return curState;
	}
	
	public boolean humanTurn(){
		return turn;
	}
	
	public void setHuman(boolean val){
		turn = val;
	}
	
	public void updateView(State current){ 
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				tiles[i][j].setText("" + current.getStones(i,j));
			}
		}
		current.printState();
	}
	
	public boolean checkEndgame(){
		int count = 0;		
		
		for(int j=0; j<2; j++){
			for(int i=1; i<7; i++){
				count += curState.getStones(i, j);
			}			
			if(count == 0){
				System.out.println("The game has ended!");
				return true;
			}
			count = 0;
		}
		return false;
		
	}
	
	public void computerGame() {
		clockwise.setEnabled(false);
		move.setEnabled(false);
		while(!checkEndgame()){
			curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0, 0);
//			if(curState.countState() != totalStones) break;
			updateView(curState);			
			if(checkEndgame()) break;
			
			System.out.println("////**----First Player T1----**////");
			
			while(curState.isExtraTurn()){
				curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0, 0);
//				if(curState.countState() != totalStones) break;
				updateView(curState);
				if(checkEndgame()) break;
			}
			
			System.out.println("////**----P1 Extra Turns----**////");
			
			curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1, 1);
//			if(curState.countState() != totalStones) break;
			updateView(curState);
			if(checkEndgame()) break;
			
			System.out.println("////**----Second Player T1----**////");
			
			while(curState.isExtraTurn()){
				curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 1, 1);
//				if(curState.countState() != totalStones) break;
				updateView(curState);
				if(checkEndgame()) break;
			}
			
			System.out.println("////**----Round loop----**////");
		}
		System.out.println("The game has ended!");
		curState.printState();
		System.out.println(miniMax.getNodeCount() + " Nodes were searched!");
		System.out.println(miniMax.getNodesMade() + " Nodes were possible!");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				if(e.getActionCommand().equals(i + ", " + j)){
					if(clockwise.getText().equals("Clockwise")){
						curState.move(i, j, true);
						updateView(curState);						
					}else{
						curState.move(i, j, false);
						updateView(curState);
					}
					if(!curState.isExtraTurn()){
						disablePlayer(1);
					}
//					System.out.println("Moving from: " + i + ", " + j);
				}
				
				if(checkEndgame()){
					disablePlayer(1);
					System.out.println(miniMax.getNodeCount() + " Nodes were searched!");
					System.out.println(miniMax.getNodesMade() + " Nodes were possible!");
				}
//				updateView(curState);
			}
		}
		
		if(e.getActionCommand().equals("move")){
			curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0, 0);
			updateView(curState);
			System.out.println("extra turn? " + curState.isExtraTurn());
			while(curState.isExtraTurn()){
				curState = miniMax.search(curState, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, 0, 0);
				updateView(curState);
				if(checkEndgame()) break;
			}
			
			if(checkEndgame()){
				disablePlayer(1);
				move.setEnabled(false);
				System.out.println("The game has ended!");
				System.out.println(miniMax.getNodeCount() + " Nodes were searched!");
				System.out.println(miniMax.getNodesMade() + " Nodes were possible!");
			}
			
			System.out.println("//////////////////////////////");
			enablePlayer(1);
		}
		
		if(e.getActionCommand().equals("Clockwise")){
			clockwise.setText("Counter-Clockwise");
			clockwise.setActionCommand("CounterClockwise");
		}
		if(e.getActionCommand().equals("CounterClockwise")){
			clockwise.setText("Clockwise");
			clockwise.setActionCommand("Clockwise");
		}
	}
}
