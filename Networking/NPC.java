package Networking;

import java.util.Random;

public class NPC {
	
	float locX, locY, locZ;
	float uX, uY, uZ;
	float vX, vY, vZ;
	float nX, nY, nZ;

	Random r = new Random();
	
	public void setLocation(float x, float y, float z){
		locX = x;
		locY = y;	
		locZ = z;	
	}
	
	public void setRotation(float uuX, float uuY, float uuZ, float vvX, float vvY, float vvZ, float nnX, float nnY, float nnZ){
		 uX=uuX; uY=uuY; uZ=uuZ;
		 vX=vvX; vY=vvY; vZ=vvZ;
		 nX=nnX; nY=nnY; nZ=nnZ;

	}
	//location
	public float getX() {
		return locX;
	}
	public float getY() {
		return locY;
	}
	public float getZ() {
		return locZ;
	}
	
//rotation
	public float getuX() {
		return uX;
	}
	public float getuY() {
		return uY;
	}
	public float getuZ() {
		return uZ;
	}

	public float getvX() {
		return vX;
	}
	public float getvY() {
		return vY;
	}
	public float getvZ() {
		return vZ;
	}
	

	public float getnX() {
		return nX;
	}
	public float getnY() {
		return nY;
	}
	public float getnZ() {
		return nZ;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void updateLocation(float f, float g, float h, float i, float j, float k, float l, float m, float n, float o,
			float p, float q) {

		this.setLocation(f, g, h);
		this.setRotation(i, j, k, l, m, n, o, p, q);
		
		
		
		
	}

}