package com.mrthomaswagner.vindiniumclient.board;

import java.util.Map;
import java.util.Set;

import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public class BoardRepresentation {
	private int size;
	private Map<GameState.Position, Vertex> spaces;
	private Set<GameState.Position> mineLocations;
	private Set<GameState.Position> pubLocations;
	private Set<GameState.Position> characterSpawns;
	
	private Map<GameState.Position, Integer> minePositionToOwnerMap;
	
	public BoardRepresentation(
			int size,
			Map<GameState.Position, Vertex> spaces, 
			Set<GameState.Position> mineLocations, 
			Set<GameState.Position> pubLocations,
			Set<GameState.Position> characterSpawns){
		
		this.size = size;
		this.spaces = spaces;
		this.mineLocations = mineLocations;
		this.pubLocations = pubLocations;
		this.characterSpawns = characterSpawns;
	}

	public int getSize() {
		return size;
	}

	public Set<GameState.Position> getCharacterSpawns() {
		return characterSpawns;
	}

	public Map<GameState.Position, Integer> getMinePositionToOwnerMap() {
		return minePositionToOwnerMap;
	}

	public void setMinePositionToOwnerMap(
			Map<GameState.Position, Integer> minePositionToOwnerMap) {
		this.minePositionToOwnerMap = minePositionToOwnerMap;
	}

	public Map<GameState.Position, Vertex> getSpaces() {
		return spaces;
	}

	public Set<GameState.Position> getMineLocations() {
		return mineLocations;
	}

	public Set<GameState.Position> getPubLocations() {
		return pubLocations;
	}
	
	public void printBoard(){
		for (int x =0; x < size; x ++){
			for (int y =0; y < size; y ++){
				Position  p = new Position(x, y);
				if(spaces.get(p)==null){
					System.out.print("##");
				}else{
					System.out.print(spaces.get(p).getType());
				}
			}
			System.out.println();
		}
	}
	
	
}
