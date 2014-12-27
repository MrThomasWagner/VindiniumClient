package com.mrthomaswagner.vindiniumclient.board.path.astar;

import java.util.Comparator;

import com.mrthomaswagner.vindiniumclient.board.path.PathNode;

public class AStarPathNodeComparator implements Comparator<PathNode>
{
    @Override
    public int compare(PathNode x, PathNode y){	    	
        if (x.getFValue() < y.getFValue())
            return -1;
        else if (x.getFValue() > y.getFValue())
            return 1;
        else
        	return 0;	        
    }
}