package com.mrthomaswagner.vindiniumclient.board.path;

import java.util.Comparator;

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