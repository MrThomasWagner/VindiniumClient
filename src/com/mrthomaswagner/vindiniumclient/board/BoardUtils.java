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
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public class BoardUtils {
	
	public static BoardRepresentation constructBoardRepresentation(Board board){
		int size = board.getSize();
		String representation = board.getTiles();
		Map<Position, Vertex> result = new HashMap<Position, Vertex>();
		Set<Position> mineLocations = new HashSet<Position>();
		Set<Position> pubs = new HashSet<Position>();
		Set<Position> characterSpawns = new HashSet<Position>();
		
		int x = 0;
		int y = 0;
		
		while(x < size){		
			int stringIndex = xyToStringIndex(x, y, size); 
	        if (representation.charAt(stringIndex) != '#') {	        	
	        	Position pos = new Position(x, y);
	        	
	        	String type = new StringBuilder()
	        		.append(representation.charAt(stringIndex))
	        		.append(representation.charAt(stringIndex+1)).toString();
	        	
	        	if(type.charAt(0) == '@'){ characterSpawns.add(pos); }
	        	
	        	result.put(pos, new Vertex(pos, type));
	        }	        
	        y ++;
	        if(y == size){
				x ++;
				y = 0;
			}
	    }
		
		for(Position p1 : result.keySet()){
			Vertex.Type type = result.get(p1).getType();
			
			if(type == Vertex.Type.MINE){ mineLocations.add(p1); continue; }
			if(type == Vertex.Type.TAVERN){ pubs.add(p1);	continue; }				
			
			for(Position p2 : getNeighborCoors(p1, size, result)){
				result.get(p1).addNeighbor(result.get(p2));
			}
		}
		
		BoardRepresentation b = new BoardRepresentation(size, result, mineLocations, pubs, characterSpawns);
		return b;
	}
	
	private static int xyToStringIndex(int x, int y, int size){
		return 2*y + 2*x*size;
	}
	
	private static List<Position> getNeighborCoors(
			Position pos, int size, Map<Position, Vertex> vertices){
		
		List<Position> result = new ArrayList<Position>();
		int x = pos.getX();
		int y = pos.getY();
		
		for (int i : Arrays.asList(y-1, y+1)){
			Position nextPos = new Position(x, i);
			if(vertices.get(nextPos) != null){
				result.add(nextPos);
			}
		}
		
		for (int i : Arrays.asList(x-1, x+1)){
			Position nextPos = new Position(i, y);
			if(vertices.get(nextPos) != null){
				result.add(nextPos);
			}
		}
		return result;
	}
	
	public static void updateMineOwners(BoardRepresentation b, GameState gs){
		Map<Position, Integer> result = new HashMap<Position, Integer>();
	
		Set<Position> mineLocations = b.getMineLocations();		
		
		String tileRep = gs.getGame().getBoard().getTiles();
		
		for(Position p: mineLocations){			
			result.put(p, Character.getNumericValue(
					tileRep.charAt(xyToStringIndex(p.getX(), p.getY(), b.getSize())+1)
					));			
		}
		
		b.setMinePositionToOwnerMap(result);
	}
}
