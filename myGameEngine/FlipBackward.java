package myGameEngine;
import a3.MyGame;

import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import net.java.games.input.Event;
public class FlipBackward extends AbstractInputAction
{
	private MyGame game;
public FlipBackward(MyGame p)
{ 
game = p;
	
}
public void performAction(float time, Event e)

{ 	System.out.println("flipping");

	Angle rotAmt = Degreef.createFrom(-3.0f);
	if(game.camera.getMode()=='n') {
	
	}
	else {
		// .getRt  gets u position
				Vector3 u= game.camera.getRt();
				Vector3 v =game.camera.getUp();
				Vector3 n =game.camera.getFd();

			       Vector3f fv = (Vector3f) (v.rotate(rotAmt, u)).normalize();
			       Vector3f fn = (Vector3f) (n.rotate(rotAmt, u)).normalize();

			       game.camera.setUp(fv);
			       game.camera.setFd(fn);


	       
	}
	
	
}
}