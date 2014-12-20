package com.mrthomaswagner.vindiniumclient.board;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mrthomaswagner.vindiniumclient.dto.GameState;

public class BoardRepresentation {
	private Map<GameState.Position, Vertex> spaces;
	private Set<GameState.Position> mineLocations;
	private Set<GameState.Position> pubLocations;
	private Set<GameState.Position> characterSpawns;
	
	private Map<GameState.Position, Integer> minePositionToOwnerMap;
	
	public BoardRepresentation(
			Map<GameState.Position, Vertex> spaces, 
			Set<GameState.Position> mineLocations, 
			Set<GameState.Position> pubLocations,
			Set<GameState.Position> characterSpawns){
		
		this.spaces = spaces;
		this.mineLocations = mineLocations;
		this.pubLocations = pubLocations;
		this.characterSpawns = characterSpawns;
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
	
	
}
