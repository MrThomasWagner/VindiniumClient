package com.mrthomaswagner.vindiniumclient.board.path.dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.board.Vertex;
import com.mrthomaswagner.vindiniumclient.board.path.PathNode;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public class Dijkstra {
	
	public static List<Position> getPathToClosest(BoardRepresentation boardRep, Position heroPos, int heroId, 
			Vertex.Type targetType){
		Map<Position, Vertex> boardSpaces = boardRep.getSpaces();
		Map<Position, Double> dists = new HashMap<Position, Double>();
		
		for (Position p : boardSpaces.keySet()){
			dists.put(p, Double.POSITIVE_INFINITY);
		}	
		dists.put(heroPos, 0.0);		
		
		Queue<PathNode> toVisit = new PriorityQueue <PathNode>(
				boardRep.getSize()*boardRep.getSize(), 
				new DijkstraPathNodeComparator());
		
		toVisit.add(new PathNode(boardSpaces.get(heroPos), null));
		boolean found = false;
		
		while (!found){
			PathNode nextNode = toVisit.remove();
			List<Vertex> neighbors = nextNode.getNeighbors();
			for(Vertex v: neighbors){
				if (boardSpaces.get(v.getPos()).getType() == targetType){					
					if ( boardRep.getMinePositionToOwnerMap().get(v.getPos()) != heroId){
						return getPath(new PathNode(v, nextNode));
					}					
				}
				if(dists.get(v.getPos()) > nextNode.getDistSoFar() + 1){
					dists.put(v.getPos(), nextNode.getDistSoFar() + 1);			
					PathNode p = new PathNode(v, nextNode);
					p.setDistSoFar(nextNode.getDistSoFar() + 1);
					toVisit.add(p);					
				}				
			}			
		}
		return null;
	}
	
	private static List<Position> getPath(PathNode p){
		List<Position> result = new ArrayList<Position>();
		PathNode next = p;
		while (next != null){
			result.add(next.getPos());
			next = next.getParent();			
		}
		Collections.reverse(result);
		return result;
	}
}
