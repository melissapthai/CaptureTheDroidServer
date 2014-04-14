package server;
import java.net.*;
import java.io.*;

import com.google.gson.*;
/**
 * Handles users.
 *
 * @author millerlj.
 *         Created Apr 12, 2014.
 */
public class UserHandler extends Thread{
	 private Socket socket = null;
	 private GameRunner game=null;
	 public UserHandler(Socket socket, GameRunner game) {
	        super("UserHandler");
	        System.out.println("UserHandler Established");
	        this.socket = socket;
	        this.game=game;
	    }
	public void run() {
//		System.out.println("Running run()");

        try (
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        		InputStreamReader isr=new InputStreamReader(
                        this.socket.getInputStream());
            BufferedReader in = new BufferedReader(isr
                );
        ) {
//        	System.out.println("In the try block");
            String inputLine=null, outputLine;
           InterfaceProtocol ip=new InterfaceProtocol();
           Gson gson=new Gson();  
           boolean processing=true;

            while (processing) {
//            	System.out.println("In processing "+in.ready()+" "+isr.ready());
            	inputLine=in.readLine();
//            	System.out.println("Read line: "+inputLine);
            	if(inputLine==null) {//if the input is null, wait a bit, then try again
            		System.out.println("input is empty");
            		for(int i=0;i<=16;i=i*2) {
            			inputLine=in.readLine();
            			if(inputLine==null)
							try {
								Thread.sleep(i*1000);
							} catch (InterruptedException exception) {
								// TODO Auto-generated catch-block stub.
								exception.printStackTrace();
							}
            			else {
            				break;
            			}
            		}
            		if(inputLine==null)
            			processing=false;
            		}
            	if(processing==false)
            		break;
//            	System.out.println("Input is not null"+inputLine);
                Player updatedPlayer = ip.processInput(inputLine);
                Player p=this.game.getPlayer(updatedPlayer.name);
                if(p==null) {
                	p=updatedPlayer;
                	this.game.addPlayer(p);
                }
                p.location=updatedPlayer.location;
                
                
                //create JSON object holding: flagpos, flagholder?, gameover(-1:game running, 0:team 0 wins,1: team 1 wins)
                JsonHolder jsonvals=new JsonHolder(p.flagHolder, this.game.flagPositions,this.game.checkWin());
                String json= gson.toJson(jsonvals);
                //write json
                System.out.println("JSON: "+json);
                out.println(json);
                try {
					Thread.sleep(1000);//wait one second 
				} catch (InterruptedException exception) {
					// TODO Auto-generated catch-block stub.
					exception.printStackTrace();
				}
            }
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
        	System.out.println("Other Exception: ");
        	e.printStackTrace();
        }
    }

}
