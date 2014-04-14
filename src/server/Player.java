package server;

/**
 * A holder for all player info
 * @author millerlj.
 *         Created Apr 12, 2014.
 */
public class Player {
	public Coordinate location;
	public boolean flagHolder;
	public int team;
	public String name;
	
	public Player(double lati, double longi,String name,int team) {
		this.location=new Coordinate(lati,longi);
		this.flagHolder=false;
		this.name=name;
		this.team=team;
	}
	
	public void setFlagHolder(boolean flag){
		this.flagHolder=flag;
	}
	
	public void setLocation(double lati,double longi){
		this.location=new Coordinate(lati,longi);
	}
	
	
}
