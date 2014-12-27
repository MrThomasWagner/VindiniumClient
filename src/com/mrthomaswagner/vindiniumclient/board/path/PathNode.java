package com.mrthomaswagner.vindiniumclient.board.path;

import com.mrthomaswagner.vindiniumclient.board.Vertex;

public class PathNode extends Vertex {	
	private PathNode parent;
	private double distSoFar = 0;
	private double estimatedRemainingDist = 0;
	
	public PathNode(Vertex v, PathNode parent){
		super(v);
		this.parent = parent;
	}
	
	public PathNode getParent(){return parent;}
	public void setDistSoFar(double j){this.distSoFar = j;}
	public double getDistSoFar(){return this.distSoFar;}
	public void setEstimatedRemainingDist(double j){this.estimatedRemainingDist = j;}
	public double getFValue(){return distSoFar + estimatedRemainingDist;}
}
