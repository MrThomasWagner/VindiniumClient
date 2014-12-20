package com.mrthomaswagner.vindiniumclient.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Board;

public class BoardUtils {
	/*	 
	## Impassable wood
	@1 Hero number 1
	[] Tavern
	$- Gold mine (neutral)
	$1 Gold mine (belonging to hero 1)	
	*/	
	public static BoardRepresentation constructBoardRepresentation(Board board){
		int size = board.getSize();
		String representation = board.getTiles();
		Map<GameState.Position, Vertex> result = new HashMap<GameState.Position, Vertex>();
		Set<GameState.Position> mineLocations = new HashSet<GameState.Position>();
		Set<GameState.Position> pubs = new HashSet<GameState.Position>();
		Set<GameState.Position> characterSpawns = new HashSet<GameState.Position>();
		
		int x = 0;
		int y = 0;
		
		while(x < size){		
			int stringIndex = 2*y + 2*x*size; 
	        if (representation.charAt(stringIndex) != '#') {	        	
	        	GameState.Position pos = new GameState.Position(x, y);
	        	
	        	String type = new StringBuilder()
	        		.append(representation.charAt(stringIndex))
	        		.append(representation.charAt(stringIndex+1)).toString();

	        	result.put(pos, new Vertex(pos, type));
	        }	        
	        y ++;
	        if(y == size){
				x ++;
				y = 0;
			}
	    }
		
		for(GameState.Position p1 : result.keySet()){
			char c = result.get(p1).getType().charAt(0);
			if(c == '$'){
				mineLocations.add(p1);
				continue;
			}
			
			if(c == '['){
				pubs.add(p1);
				continue;
			}
			
			if(c == '@'){
				characterSpawns.add(p1);
				continue;
			}
			
			for(GameState.Position p2 : getNeighborCoors(p1, size, result)){
				result.get(p1).addNeighbor(result.get(p2));
			}
		}
		
		BoardRepresentation b = new BoardRepresentation(result, mineLocations, pubs, characterSpawns);
		return b;
	}
	
	private static List<GameState.Position> getNeighborCoors(
			GameState.Position pos, int size, Map<GameState.Position, Vertex> vertices){
		
		List<GameState.Position> result = new ArrayList<GameState.Position>();
		int x = pos.getX();
		int y = pos.getY();
		
		for (int i : Arrays.asList(y-1, y+1)){
			GameState.Position nextPos = new GameState.Position(x, i);
			if(vertices.get(nextPos) != null){
				result.add(nextPos);
			}
		}
		
		for (int i : Arrays.asList(x-1, x+1)){
			GameState.Position nextPos = new GameState.Position(i, y);
			if(vertices.get(nextPos) != null){
				result.add(nextPos);
			}
		}
		return result;
	}
	
	public static void updateMineOwners(BoardRepresentation b){
		Map<GameState.Position, Integer> result = new HashMap<GameState.Position, Integer>();
	
		Set<GameState.Position> mineLocations = b.getMineLocations();
		Map<GameState.Position, Vertex> spaces = b.getSpaces();
		
		for(GameState.Position p: mineLocations){
			result.put(p, Character.getNumericValue(spaces.get(p).getType().charAt(1)));			
		}
		
		b.setMinePositionToOwnerMap(result);
	}
}
