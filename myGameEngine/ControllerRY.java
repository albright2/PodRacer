package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import net.java.games.input.Event;
public class ControllerRY extends AbstractInputAction
{
	private MyGame game;
public ControllerRY(MyGame p)
{ 
game = p;
	
}
public void performAction(float time, Event e)

{ 
	
	System.out.println("pitching");

	if(e.getValue()<-.2) {
	Angle rotAmt = Degreef.createFrom(3.0f);
	if(game.camera.getMode()=='n') {
//	game.dolphinN.pitch(rotAmt);	
	}
	else {
		// .getRt  gets u position
				Vector3 u= game.camera.getRt();
				Vector3 v =game.camera.getUp();
				Vector3 n =game.camera.getFd();

			      // Vector3f fu = (Vector3f) (u.rotate(rotAmt, n)).normalize();
			       Vector3f fv = (Vector3f) (v.rotate(rotAmt, u)).normalize();
			       Vector3f fn = (Vector3f) (n.rotate(rotAmt, u)).normalize();

			     // game.camera.setRt(fu);
			       game.camera.setUp(fv);
			       game.camera.setFd(fn);
	       
	}
	
	}
	
	
	if(e.getValue()>.2) {
		Angle rotAmt = Degreef.createFrom(-3.0f);
		if(game.camera.getMode()=='n') {
		
		//game.dolphinN.pitch(rotAmt);	
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
	}
	

