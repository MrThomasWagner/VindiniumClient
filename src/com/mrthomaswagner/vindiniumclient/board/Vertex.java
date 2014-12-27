package com.mrthomaswagner.vindiniumclient.board;

import java.util.ArrayList;
import java.util.List;

import com.mrthomaswagner.vindiniumclient.dto.GameState;

public class Vertex {
	/*	 
	## Impassable wood
	@1 Hero number 1
	[] Tavern
	$- Gold mine (neutral)
	$1 Gold mine (belonging to hero 1)	
	*/	
	public enum Type {BLANK, TAVERN, MINE};
	
	private GameState.Position pos;
	private List<Vertex> neighbors;	
	private Vertex.Type type;
	
	public Vertex(GameState.Position pos, String typeString){
		this.pos = pos;
		this.neighbors = new ArrayList<Vertex>();
		
		if(typeString.equals("  ") || typeString.startsWith("@")){
			this.type = Vertex.Type.BLANK;
		}else if(typeString.startsWith("$")){
			this.type = Vertex.Type.MINE;
		}else if(typeString.startsWith("[")){
			this.type = Vertex.Type.TAVERN;					
		}
	}

	public Vertex(Vertex v){
		this.pos = v.getPos();
		this.type = v.getType();
		this.neighbors = v.getNeighbors();
	}
	public boolean hasSameCoordinates(Vertex v){
		return (v.getPos().getX() == this.getPos().getX() && v.getPos().getY() == this.getPos().getY());
	}
	public Vertex.Type getType(){ return type; }
	public GameState.Position getPos() { return pos; }
	public int getX(){ return getPos().getX(); }
	public int getY(){ return getPos().getY(); }
	public List<Vertex> getNeighbors() { return neighbors; }	
	public void addNeighbor(Vertex neighbor) { this.neighbors.add(neighbor); }
}
