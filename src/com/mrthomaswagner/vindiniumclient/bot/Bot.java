package com.mrthomaswagner.vindiniumclient.bot;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import com.mrthomaswagner.vindiniumclient.BotMove;
import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public abstract class Bot {
	
    private static final Logger LOG = LogManager.getLogger(Bot.class.getName());

	public abstract BotMove move(GameState gameState, BoardRepresentation boardRepresentation);
	
	public BotMove getDirectionTo(Position nextPos, Position heroPos){
		BotMove theMove;
		if(nextPos.getX() < heroPos.getX()){			
			theMove = BotMove.NORTH;
		}else if(nextPos.getX() > heroPos.getX()){
			theMove =  BotMove.SOUTH;
		}else if(nextPos.getY() < heroPos.getY()){			
			theMove =  BotMove.WEST;
		}else if(nextPos.getY() > heroPos.getY()){			
			theMove =  BotMove.EAST;
		}else{
			theMove = BotMove.STAY;
		}
		LOG.info(String.format("Heading %s", theMove));
		return theMove;
	}
}
