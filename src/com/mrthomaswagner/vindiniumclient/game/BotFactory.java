package com.mrthomaswagner.vindiniumclient.game;

import java.util.Map;

import com.mrthomaswagner.vindiniumclient.game.naivebot.NaiveBot;

public class BotFactory {

	public Bot getBot(String type){
		if(type.equals("naive")){
			return new NaiveBot();
		}
		return null;
	}
}
