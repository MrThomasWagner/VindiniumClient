package com.mrthomaswagner.vindiniumclient.board;

import java.util.ArrayList;
import java.util.List;

import com.mrthomaswagner.vindiniumclient.dto.GameState;

public class Vertex {
	
	private GameState.Position pos;
	private List<Vertex> neighbors;	
	private String type;
	
	public Vertex(GameState.Position pos, String type){
		this.pos = pos;
		this.type = type;
		this.neighbors = new ArrayList<Vertex>();
	}

	public String getType(){ return type; }
	public GameState.Position getPos() { return pos; }
	public List<Vertex> getNeighbors() { return neighbors; }	
	public void addNeighbor(Vertex neighbor) { this.neighbors.add(neighbor); }	
}
