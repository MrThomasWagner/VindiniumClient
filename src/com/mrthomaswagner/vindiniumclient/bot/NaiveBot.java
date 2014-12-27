package com.mrthomaswagner.vindiniumclient.bot;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mrthomaswagner.vindiniumclient.BotMove;
import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.board.Vertex;
import com.mrthomaswagner.vindiniumclient.board.path.Dijkstra;
import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Hero;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public class NaiveBot extends Bot{
	
    private static final Logger LOG = LogManager.getLogger(NaiveBot.class.getName());

	@Override
	public BotMove move(GameState gameState, BoardRepresentation boardRep) {
		Hero me = gameState.getHero();
		List<Position> result;
		
		if (me.getLife() > 20){
			LOG.info("Heading to closest mine");
			result = Dijkstra.getPathToClosest(boardRep, me.getPos(), me.getId(), Vertex.Type.MINE);	
		}else{
			LOG.info("Heading to closest tavern");
			result = Dijkstra.getPathToClosest(boardRep, me.getPos(), me.getId(), Vertex.Type.TAVERN);
		}
		return getDirectionTo(result.get(1), me.getPos());	
	}
}
