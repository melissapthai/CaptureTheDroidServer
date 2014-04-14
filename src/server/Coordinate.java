package server;

public class Coordinate {
	public double latitude;
	public double longitude;
	
	public Coordinate(double lat,double longi){
		this.latitude=lat;
		this.longitude=longi;
	}
	
	public boolean equals(Coordinate position){
		if (Math.sqrt(Math.pow((position.latitude-this.latitude),2)+Math.pow((position.longitude-this.longitude),2))<=0.00005){
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub.
		return "["+this.latitude+", "+this.longitude+"]";
	}
	
}
