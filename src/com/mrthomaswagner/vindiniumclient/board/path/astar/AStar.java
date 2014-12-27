package com.mrthomaswagner.vindiniumclient.board.path.astar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.board.Vertex;
import com.mrthomaswagner.vindiniumclient.board.path.PathFinder;
import com.mrthomaswagner.vindiniumclient.board.path.PathNode;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;


public class AStar extends PathFinder{

	public static List<Position> getPath(BoardRepresentation boardRep, Vertex startVertex, Vertex goalVertex) {
		
		Queue<PathNode> open = new PriorityQueue<PathNode>((int) Math.pow(boardRep.getSize(),2), new AStarPathNodeComparator());
		List<PathNode> closed = new ArrayList<PathNode>();
		Map<List<Integer>, Double> bestValsSoFar = new HashMap<List<Integer>, Double>();//should be keyed on Position
		
		open.add(new PathNode(startVertex, null));
		PathNode goal = new PathNode(goalVertex, null);	
		
		Map<Position, Vertex> vertices = boardRep.getSpaces();		

		while(open.size() > 0){
			
			PathNode currentNode = open.remove();
			Position currentPos = currentNode.getPos();
			List<Vertex> successors = vertices.get(currentPos).getNeighbors();
			
			for(Vertex successor : successors){
				PathNode pathnodeNeighbor = new PathNode(successor, currentNode);
				if(pathnodeNeighbor.hasSameCoordinates(goal)){					
					return getPathCoordinates(pathnodeNeighbor);
				}
				
				pathnodeNeighbor.setDistSoFar(currentNode.getDistSoFar() + 1);				
				pathnodeNeighbor.setEstimatedRemainingDist(pathnodeNeighbor.getDistSoFar() + euclidean(pathnodeNeighbor, goal));
				
				List<Integer> xy = Arrays.asList(pathnodeNeighbor.getX(), pathnodeNeighbor.getY());
				if (bestValsSoFar.get(xy) == null || bestValsSoFar.get(xy) > pathnodeNeighbor.getFValue()){
					open.add(pathnodeNeighbor);
					bestValsSoFar.put(Arrays.asList(pathnodeNeighbor.getX(), pathnodeNeighbor.getY()), pathnodeNeighbor.getFValue());
				}
				
				closed.add(currentNode);
			}
		}
		
		return null;
	}
	
	private static List<Position> getPathCoordinates(PathNode endNode){
		List<Position> result = new ArrayList<Position>();
		PathNode n = endNode;
		while(n != null){
			result.add(n.getPos());
			n = n.getParent();
		}
		return result;
	}	
	
	public static double euclidean(PathNode a, PathNode b){
		return Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
	}
}
