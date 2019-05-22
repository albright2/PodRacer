package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import a3.ProtocolClient;
import ray.rml.Matrix3;

import net.java.games.input.Event;
public class RotateCamLeft extends AbstractInputAction
{
	private MyGame game;
	private ProtocolClient protClient;
public RotateCamLeft(MyGame p,ProtocolClient t)
{ 
game = p;
		protClient =t;
}
public void performAction(float time, Event e)

{ 	System.out.println("kb yaw left");

	Angle rotAmt = Degreef.createFrom(3.0f);
	if(game.camera.getMode()=='n') {
		game.dolphinN.yaw(rotAmt);	


	}
	else {
		// .getRt  gets u position
		Vector3 u= game.camera.getRt();
		Vector3 v =game.camera.getUp();
		Vector3 n =game.camera.getFd();

	       Vector3f fu = (Vector3f) (u.rotate(rotAmt, v)).normalize();
	       Vector3f fn = (Vector3f) (n.rotate(rotAmt, v)).normalize();

	       game.camera.setRt(fu);
	       game.camera.setFd(fn);

	       
	}
	
	
}
}