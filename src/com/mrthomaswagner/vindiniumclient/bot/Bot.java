package com.mrthomaswagner.vindiniumclient.bot;

import com.mrthomaswagner.vindiniumclient.BotMove;
import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.dto.GameState;

public interface Bot {
	
	public BotMove move(GameState gameState, BoardRepresentation boardRepresentation);
	
}
