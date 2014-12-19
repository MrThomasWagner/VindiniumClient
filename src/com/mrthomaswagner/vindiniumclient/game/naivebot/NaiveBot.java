package com.mrthomaswagner.vindiniumclient.game.naivebot;

import com.mrthomaswagner.vindiniumclient.dto.BotMove;
import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.game.Bot;

public class NaiveBot implements Bot{

	@Override
	public BotMove move(GameState gameState) {
		return BotMove.NORTH;
	}

}
