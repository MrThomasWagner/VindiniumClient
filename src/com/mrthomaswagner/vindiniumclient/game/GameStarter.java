package com.mrthomaswagner.vindiniumclient.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.mrthomaswagner.vindiniumclient.VindiniumClientController;
import com.mrthomaswagner.vindiniumclient.dto.ApiKey;
import com.mrthomaswagner.vindiniumclient.dto.BotMove;
import com.mrthomaswagner.vindiniumclient.dto.GameState;
import com.mrthomaswagner.vindiniumclient.dto.Move;

public class GameStarter {	
    private static final Logger LOG = LogManager.getLogger(GameStarter.class.getName());

    private static final HttpTransport HTTP_TRANSPORT = new ApacheHttpTransport();
    private static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final HttpRequestFactory REQUEST_FACTORY =
            HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                }
            });
    
    private Bot bot;
    private GenericUrl gameUrl;
    private ApiKey apiKey;
    
    public GameStarter(Bot bot, GenericUrl gameUrl, String apiKey){
    	this.bot = bot;
    	this.gameUrl = gameUrl;
    	this.apiKey = new ApiKey(apiKey);
    }
    
    public String startGame(){
    	String viewUrl = "";
    	HttpContent content;
        HttpRequest request;
        HttpResponse response;
        GameState gameState = null;

        try {
            LOG.info("Sending initial request...");
            content = new UrlEncodedContent(apiKey);
            request = REQUEST_FACTORY.buildPostRequest(gameUrl, content);
            request.setReadTimeout(0); // Wait forever to be assigned to a game
            response = request.execute();
            gameState = response.parseAs(GameState.class);
            viewUrl = gameState.getViewUrl();
            
            LOG.info(String.format("Spinning off new game thread for game %s", gameState.getGame().getId()));
            RunnableGame runnableGame = new RunnableGame(gameState);
            (new Thread(runnableGame)).start();

		} catch (Exception e) {
		    LOG.error("Error initiating game ", e);
		}
		
		return viewUrl;
    }
    
    private class RunnableGame implements Runnable{
    	private GameState gameState = null;
    	
    	public RunnableGame(GameState gameState){
    		this.gameState = gameState;
    	}
    	
    	@Override
    	public void run() {
            try {
                while (!gameState.getGame().isFinished() && !gameState.getHero().isCrashed()) {
                    LOG.info("Taking turn " + gameState.getGame().getTurn());
                    BotMove direction = bot.move(gameState); //heres the beef, all AI logic initiated here
                    Move move = new Move(apiKey.getKey(), direction.toString());

                    HttpContent turn = new UrlEncodedContent(move);
                    HttpRequest turnRequest = REQUEST_FACTORY.buildPostRequest(new GenericUrl(gameState.getPlayUrl()), turn);
                    HttpResponse turnResponse = turnRequest.execute();

                    gameState = turnResponse.parseAs(GameState.class);
                }
            } catch (Exception e) {
                LOG.error("Error during game play", e);
            }

            LOG.info("Game over");
            //insert game results into a db
        }
    }
    
}
