package server;

import com.google.gson.Gson;

/**
 * Used to translate String to Player object
 *
 * @author millerlj.
 *         Created Apr 12, 2014.
 */
public class InterfaceProtocol {
	//define any constants and stuff
    Gson gson=new Gson();
	
	//what to do with the input, JSON input
	 public Player processInput(String theInput) {
	        String theOutput = null;
	        Player player=gson.fromJson(theInput,Player.class);
	        System.out.println(
	        		"Name: "+player.name+
	        		" team: "+player.team+
	        		" Location: "+player.location.toString());
	        return player;
	    }

}
