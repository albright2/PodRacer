package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import a3.ProtocolClient;

import net.java.games.input.Event;
public class CamRight extends AbstractInputAction
{
private MyGame game;
private ProtocolClient protClient;

public CamRight(MyGame ga,ProtocolClient p)
{
	game=ga;
	protClient =p;
}
public void performAction(float time, Event e)

//Vector v,p,    p1,p2

{
	
	
											

	if(game.camera.getMode()=='n') {
		

	}else {
			
		
		
		
		

		Vector3f v = game.camera.getRt(); //fd = forward vector of camera
	
		Vector3f p = game.camera.getPo();   //current position of camera

		Vector3f p1 = (Vector3f) Vector3f.createFrom(0.03f*v.x(), 0.03f*v.y(), 0.03f*v.z());


		
		
		

Vector3f p2 = (Vector3f) p.add((Vector3)p1);
//officially apply this vector to the camera
game.camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));
}

}





}