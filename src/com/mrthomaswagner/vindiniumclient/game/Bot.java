package com.mrthomaswagner.vindiniumclient.game;

import com.mrthomaswagner.vindiniumclient.dto.BotMove;
import com.mrthomaswagner.vindiniumclient.dto.GameState;

public interface Bot {
	
	public BotMove move(GameState gameState);
	
}
