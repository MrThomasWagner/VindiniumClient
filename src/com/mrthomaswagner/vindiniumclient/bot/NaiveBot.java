package com.mrthomaswagner.vindiniumclient.bot;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mrthomaswagner.vindiniumclient.BotMove;
import com.mrthomaswagner.vindiniumclient.board.BoardRepresentation;
import com.mrthomaswagner.vindiniumclient.board.Vertex;
import com.mrthomaswagner.vindiniumclient.board.path.dijkstra.Dijkstra;
import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Hero;
import com.mrthomaswagner.vindiniumclient.dto.GameState.Position;

public class NaiveBot extends Bot{
	
    private static final Logger LOG = LogManager.getLogger(NaiveBot.class.getName());

	@Override
	public BotMove move(GameState gameState, BoardRepresentation boardRep) {
		Hero me = gameState.getHero();
		
		LOG.info("Heading to closest mine");
		List<Position> result = Dijkstra.getPathToClosest(boardRep, me.getPos(), me.getId(), Vertex.Type.MINE);
		
		

		return getDirectionTo(result.get(1), me.getPos());	
	}
}
