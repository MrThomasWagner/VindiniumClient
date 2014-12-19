package com.mrthomaswagner.vindiniumclient;
 
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.http.GenericUrl;
import com.mrthomaswagner.vindiniumclient.bot.Bot;

@Controller
public class VindiniumClientController {
    private static final Logger LOG = LogManager.getLogger(VindiniumClientController.class.getName());

    private final static String TRAINING_URL = "http://vindinium.org/api/training";
    private final static String COMPETITION_URL = "http://vindinium.org/api/arena";

    @Autowired
    private BotFactory botFactory;
    
	@RequestMapping("/main")
	public ModelAndView startGame(@RequestParam Map<String,String> allRequestParams) {
		
		String type = allRequestParams.get("bot_type");
		Bot bot = botFactory.getBot(type);
		
		String mode = allRequestParams.get("mode");
		GenericUrl url;
		if(mode.equals("training"))
			url = new GenericUrl(TRAINING_URL);
		else
			url = new GenericUrl(COMPETITION_URL);
		
		String apiKey = allRequestParams.get("api_key");
		LOG.info(apiKey);
		String viewUrl = (new GameStarter(bot, url, apiKey)).startGame();
		LOG.info(String.format("New game started with viewUrl: %s", viewUrl));
		
		return new ModelAndView("main", "viewUrl", viewUrl);
	} 
}