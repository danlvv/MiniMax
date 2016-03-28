package controller;

import java.util.ArrayList;

import model.State;

public class MiniMaxSearch {
	private int nodeCount = 0;
	private int totalNodes = 0;
//	01 function alphabeta(node, depth, α, β, maximizingPlayer)
//	02      if depth = 0 or node is a terminal node
//	03          return the heuristic value of node
//	04      if maximizingPlayer
//	05          v := -∞
//	06          for each child of node
//	07              v := max(v, alphabeta(child, depth - 1, α, β, FALSE))
//	08              α := max(α, v)
//	09              if β ≤ α
//	10                  break (* β cut-off *)
//	11          return v
//	12      else
//	13          v := ∞
//	14          for each child of node
//	15              v := min(v, alphabeta(child, depth - 1, α, β, TRUE))
//	16              β := min(β, v)
//	17              if β ≤ α
//	18                  break (* α cut-off *)
//	19          return v
	
	State solution;
	
	public State search(State current, int depth, int alpha, int beta, boolean maximizingPlayer, int side, int heuristicVal){
		int highestValue = Integer.MIN_VALUE;
		solution = new State();
		
		ArrayList<State> moves = new ArrayList<State>();
		moves.clear();
		moves = current.produceChildren(side);
				
		for(State child: moves){
			totalNodes++;
			child.setHeuristic(miniMax(child, depth, alpha, beta, maximizingPlayer, side, heuristicVal));
			if(highestValue < child.getHeuristic()){
				highestValue = child.getHeuristic();
				System.out.println("Changes were mades.");
			}
		}
		
		if(!differentChildren(moves)){
			System.out.println("they're all the same!");
		}
		
		
		for(State child: moves){
			child.printState();
			nodeCount++;
			if(child.getHeuristic() == highestValue){
				if(current.isDifferent(child.getGrid())){
					solution.setGrid(child.getGrid());
					solution.setExtraTurn(child.isExtraTurn());
					return solution;
				}
			}
			
		}				
		return null;
	}
	
	// Alpha is Integer.MIN_VALUE && Beta is Integer.MAX_VALUE;
	public int miniMax(State current, int depth, int alpha, int beta, boolean maximizingPlayer, int side, int heuristicVal){		
		ArrayList<State> children = current.produceChildren(side);
		
		if(depth == 0 || current.isTerminalNode(side)){
			int heuristic = Integer.MIN_VALUE;
			// COMPUTE HEURISTIC HERE
			if(heuristicVal == 0){
				heuristic = current.getMancalaStones(side);
			}else{
				heuristic = current.extraTurnCount(side);
			}
			return heuristic;
		}		
		
		if(maximizingPlayer){
			int v = Integer.MIN_VALUE;
			for(State child: children){
				v = max(v, miniMax(child, depth - 1, alpha, beta, false, side, heuristicVal));
				alpha = max(alpha, v);
				if(beta <= alpha){
					break;
				}
			}
			return v;
		}else{
			int v = Integer.MAX_VALUE;
			for(State child: children){
				v = min(v, miniMax(child, depth - 1, alpha, beta, true, side, heuristicVal));
				beta = min(beta, v);
				if(beta <= alpha){
					break;
				}
			}
			return v;
		}
	}
	
	public int max(int first, int second){
		if(first > second)
			return first;
		return second;
	}
	
	public int min(int first, int second){
		if(first < second)
			return first;
		return second;
	}
	
	public int getNodeCount(){
		return nodeCount;
	}
	
	public int getNodesMade(){
		return totalNodes;
	}
	
	public boolean differentChildren(ArrayList<State> children){
		State tempState = new State();
		tempState.setGrid(children.get(0).getGrid());
		
		for(State child: children){
			if(child.isDifferent(tempState.getGrid())){
				return true;
			}
		}
		return false;		
	}
	
}
