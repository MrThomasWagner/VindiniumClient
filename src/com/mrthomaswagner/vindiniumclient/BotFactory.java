package com.mrthomaswagner.vindiniumclient;

import com.mrthomaswagner.vindiniumclient.bot.Bot;
import com.mrthomaswagner.vindiniumclient.bot.NaiveBot;

public class BotFactory {

	public Bot getBot(String type){
		if(type.equals("naive")){
			return new NaiveBot();
		}
		return null;
	}
}
