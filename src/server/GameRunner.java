package server;

import java.util.ArrayList;

/**
 * Runs the game.
 * 
 * @author millerlj. Created Apr 12, 2014.
 */
public class GameRunner extends Thread{
	public Coordinate[] teamPositions = new Coordinate[2];
	public Coordinate[] flagPositions = new Coordinate[2];
	public boolean[] isFlagHolded = { false, false };
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player[] flagHolder = new Player[2];

	public GameRunner() {
		this.teamPositions[0] = new Coordinate(40.114700, -88.227987);
		this.teamPositions[1] = new Coordinate(40.115319, -88.226485);
		this.flagPositions[0] = new Coordinate(teamPositions[0].latitude,
				teamPositions[0].longitude);
		this.flagPositions[1] = new Coordinate(teamPositions[1].latitude,
				teamPositions[1].longitude);

	}

	/**
	 * Adds a player to the list
	 * 
	 * @param newPlayer
	 */
	public synchronized void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}

	/**
	 * delete a player from the list
	 * 
	 * @param player
	 */
	public synchronized void deletePlayer(Player player) {
		players.remove(player);
	}

	/**
	 * * @param name
	 * 
	 * @return the player who has that name
	 */
	public Player getPlayer(String name) {
		for (Player p : players) {
			if (p.name.equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

	/**
	 * check if the player is close to flag and can hold flag
	 */
	public void pickFlag() {
		for (Player p : players) {
			if (!isFlagHolded[1 - p.team]
					&& p.location.equals(flagPositions[1 - p.team])) {
				p.setFlagHolder(true);
				flagHolder[p.team] = p;
				isFlagHolded[1 - p.team] = true;
				
			}
		}
	}

	/**
	 * check if the player meets a enemy and them drops flag
	 */
	public void dropFlag() {
		for (Player p : players) {
			if (this.flagHolder[1 - p.team] != null) {
				if (p.location.equals(this.flagHolder[1 - p.team])) {
					this.flagHolder[1 - p.team].setFlagHolder(false);
					this.flagHolder[1 - p.team] = null;
					this.isFlagHolded[p.team] = false;
					this.flagPositions[p.team] = this.teamPositions[p.team];
				}
			}
		}
	}

//	public void checkFlag(String name) {
//		if (name.equals(this.flagHolder[0].name)
//				|| name.equals(this.flagHolder[1].name)) {
//			getPlayer(name).setFlagHolder(true);
//		}
//	}

	/**
	 * 
	 * @return the status of competition
	 */
	public int checkWin() {
		if (this.flagHolder[0] != null)
			if (this.flagHolder[0].location.equals(teamPositions[1]))
				return 0;
		if (this.flagHolder[1] != null)
			if (this.flagHolder[1].location.equals(teamPositions[0]))
				return 1;
		return -1;
	}
	
	@Override
	public void run() {
		pickFlag();
		dropFlag();
	}

}
