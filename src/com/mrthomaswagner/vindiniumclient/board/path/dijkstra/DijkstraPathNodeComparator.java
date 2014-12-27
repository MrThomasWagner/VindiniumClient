package com.mrthomaswagner.vindiniumclient.board.path.dijkstra;

import java.util.Comparator;

import com.mrthomaswagner.vindiniumclient.board.path.PathNode;

public class DijkstraPathNodeComparator implements Comparator<PathNode>
{
    @Override
    public int compare(PathNode x, PathNode y){	    	
        if (x.getDistSoFar() < y.getDistSoFar())
            return -1;
        else if (x.getDistSoFar() > y.getDistSoFar())
            return 1;
        else
        	return 0;	        
    }
}