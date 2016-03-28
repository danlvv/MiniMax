package model;

import java.util.ArrayList;

public class State {
	private int[][] tiles;
	private int heuristicValue;
	private int mancalaStones;
	private boolean takeStones;
	private boolean extraTurn;
	private int[] stonesToTake; 	
	
	public void initialize(){
		tiles = new int[8][2];
		mancalaStones = 0;
		extraTurn = false;
		takeStones = false;
		stonesToTake = new int[2];
		
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				if(i != 0 && i != 7){
					tiles[i][j] = 4;;
				}else{
					tiles[i][j] = 0;					
				}
			}
		}
	}
	
	/*  
	 *  Still losing one of the stones each time it switched
	 */
	public void move(int x, int y, boolean clockwise){
		int count = 0;
		int new_y = y;
		int new_x = x;
		int mancala_gap = 0;
		boolean player_side = false;
		extraTurn = false;
		
		if(y == 1) player_side = true;
		
		for(int i=0; i<tiles[x][y]; i++){
			if(clockwise){
				if(player_side){					
					if(new_x - (++count) - mancala_gap >= 0 + mancala_gap){
						
//						if(new_x - count - mancala_gap == 0 || new_x - count - mancala_gap == 7){
//							if((i + 1) == tiles[x][y])
//								extraTurn = true;
//						}
						if((i + 1) == tiles[x][y]){
							if(new_x - count - mancala_gap == 0 || new_x - count - mancala_gap == 7){
								extraTurn = true;
							}else{
								if(tiles[new_x - count - mancala_gap][new_y] == 0){
									if(new_x - count - mancala_gap != x && switch_gap(new_y) != y){
										tiles[0][new_y] += tiles[new_x - count - mancala_gap][switch_gap(new_y)];
										tiles[new_x - count - mancala_gap][switch_gap(new_y)] = 0;
									}
								}
							}
						}
						tiles[new_x - count - mancala_gap][new_y] += 1;
					}
					if(new_x - count - mancala_gap == 0){
						player_side = false;
						new_y = 0;
						new_x = 0;
						mancala_gap = switch_gap(mancala_gap);
						count = 0 - mancala_gap;
					}
				}else{					
					if(new_x + (++count) + mancala_gap <= 7 - mancala_gap){
						//tiles[new_x + count + mancala_gap][new_y] += 1;
//						if(new_x + count + mancala_gap == 0 || new_x + count + mancala_gap == 7){
//							if((i + 1) == tiles[x][y])
//								extraTurn = true;
//						}
						if((i + 1) == tiles[x][y]){
							if(new_x + count + mancala_gap == 0 || new_x + count + mancala_gap == 7){
								extraTurn = true;
							}else{
								if(tiles[new_x + count + mancala_gap][new_y] == 0){
									if(new_x + count + mancala_gap != x && switch_gap(new_y) != y){
										tiles[0][new_y] += tiles[new_x + count + mancala_gap][switch_gap(new_y)];
										tiles[new_x + count + mancala_gap][switch_gap(new_y)] = 0;
									}
								}
							}
						}		
						tiles[new_x + count + mancala_gap][new_y] += 1;
					}
					if(new_x + count + mancala_gap >= 7 - mancala_gap){
						player_side = true;
						new_y = 1;
						new_x = 7;
						mancala_gap = switch_gap(mancala_gap);
						count = 0 - mancala_gap;
					}
				}
			}else{
				if(!player_side){
					if(new_x - (++count) - mancala_gap >= 0 + mancala_gap){
						//tiles[new_x - count - mancala_gap][new_y] += 1;
//						if(new_x - count - mancala_gap == 0 || new_x - count - mancala_gap == 7){
//							if((i + 1) == tiles[x][y])
//								extraTurn = true;
//						}
						if((i + 1) == tiles[x][y]){
							if(new_x - count - mancala_gap == 0 || new_x - count - mancala_gap == 7){
								extraTurn = true;
							}else{
								if(tiles[new_x - count - mancala_gap][new_y] == 0){
									if(new_x - count - mancala_gap != x && switch_gap(new_y) != y){
										tiles[0][new_y] += tiles[new_x - count - mancala_gap][switch_gap(new_y)];
										tiles[new_x - count - mancala_gap][switch_gap(new_y)] = 0;
									}
								}
							}
						}
						tiles[new_x - count - mancala_gap][new_y] += 1;
					}
					if(new_x - count == 0){
						player_side = true;
						new_y = 1;
						new_x = 0;
						mancala_gap = switch_gap(mancala_gap);
						count = 0 - mancala_gap;
					}
				}else{
					if(new_x + (++count) + mancala_gap <= 7 - mancala_gap){
						//tiles[new_x + count + mancala_gap][new_y] += 1;
//						if(new_x + count + mancala_gap == 0 || new_x + count + mancala_gap == 7){
//							if((i + 1) == tiles[x][y])
//								extraTurn = true;
//						}
						if((i + 1) == tiles[x][y]){
							if(new_x + count + mancala_gap == 0 || new_x + count + mancala_gap == 7){
								extraTurn = true;
							}else{
								if(tiles[new_x + count + mancala_gap][new_y] == 0){
									if(new_x + count + mancala_gap != x && switch_gap(new_y) != y){
										tiles[0][new_y] += tiles[new_x + count + mancala_gap][switch_gap(new_y)];
										tiles[new_x + count + mancala_gap][switch_gap(new_y)] = 0;
									}
								}
							}
						}
						tiles[new_x + count + mancala_gap][new_y] += 1;
					}
					if(new_x + count >= 7 - mancala_gap){
						player_side = false;
						new_y = 0;
						new_x = 7;
						mancala_gap = switch_gap(mancala_gap);
						count = 0 - mancala_gap;
					}
				}
			}
		}
		tiles[x][y] = 0;
	}
	
	public boolean isTerminalNode(int player){
		for(int i=0; i<8; i++){
			if(i != 0 && i != 7)
				if(tiles[i][player] != 0)
					return false;
		}
		return true;
	}
	
	public boolean isExtraTurn(){
		return extraTurn;
	}
	
	public void setHeuristic(int value){
		heuristicValue = value;
	}
	
	public int getHeuristic(){
		return heuristicValue;
	}
	
	public void printState(){
		String temp = "";
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				temp += tiles[i][j] + " ";
			}
			System.out.println(temp);
			temp = "";
		}
		System.out.println("--------------------");
	}
	
	/*
	 * Perhaps the source of the null state error.
	 */
	
	public ArrayList<State> produceChildren(int player){
		ArrayList<State> temp = new ArrayList<State>();
		
		for(int i=1; i <= 12; i++){
			State child = new State();
			child.setGrid(tiles);
			if(i<7){
				child.move(i, player, true);
			}else{
				child.move(i-6, player, false);
			}
			temp.add(child);
		}
		return temp;
	}
	
	public boolean isDifferent(int[][] compTiles){
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				if(compTiles[i][j] != tiles[i][j])
					return true;
			}
		}
		return false;
	}
	
	public void setGrid(int[][] newTiles){
		tiles = new int[8][2];
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				tiles[i][j] = newTiles[i][j];
			}
		}
	}
	
	public void setExtraTurn(boolean turn){
		extraTurn = turn;
	}
	
	public int[][] getGrid(){
		return tiles;
	}
	
	public int switch_gap(int manc){
		if(manc == 0){
			return 1; 
		}
		return 0;
	}
	
	public int getStones(int x, int y){
		return tiles[x][y];
	}
	
	public int countState(){
		int temp = 0;
		for(int j=0; j<2; j++){
			for(int i=0; i<8; i++){
				temp += tiles[i][j];
			}
		}
		return temp;
	}
	
	public int extraTurnCount(int player){
		State tempState = new State();
		tempState.setGrid(tiles);
		boolean extraTurn = true, clockwise = false;
		int count = 0;
		
		if(player == 0) clockwise = true;
		
		outerloop:
		while(extraTurn){
			int grid[][] = tempState.getGrid();
			for(int i=1; i<7; i++){
				if(grid[i][player] == (7-i)){
					count++;
					tempState.move(i, player, clockwise);
					continue outerloop;
				}else if(grid[i][player] == (0+i)){
					count++;
					tempState.move(i, player, !clockwise);
					continue outerloop;
				}
			}
			extraTurn = false;
		}
		
		return count;
	}
	
	public int getMancalaStones(int player){
		int temp = 0;
		
		temp += tiles[0][player];
		temp += tiles[7][player];
		
		return temp;
	}
}
