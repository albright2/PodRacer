package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import net.java.games.input.Event;
public class ControllerYaw extends AbstractInputAction
{
	private MyGame game;
public ControllerYaw(MyGame p)
{ 
game = p;
	
}
public void performAction(float time, Event e)



{
	if(e.getValue()<-.2) {
	Angle rotAmt = Degreef.createFrom(1.5f);
	if(game.camera.getMode()=='n') {
		System.out.println("controller rotate left");

	game.dolphinN.yaw(rotAmt);	
	}
	else {
		// .getRt  gets u position
		Vector3 u= game.camera.getRt();
		Vector3 v =game.camera.getUp();
		Vector3 n =game.camera.getFd();
		Vector3 f = (Vector3)Vector3f.createFrom(0.0f, 0.01f, 0.0f);

	       Vector3f fu = (Vector3f) (u.rotate(rotAmt, f)).normalize();
	       Vector3f fv = (Vector3f) (v.rotate(rotAmt, f)).normalize();
	       Vector3f fn = (Vector3f) (n.rotate(rotAmt, f)).normalize();

	       game.camera.setRt(fu);
	       game.camera.setUp(fv);
	       game.camera.setFd(fn);

	       
	}
	

}


if(e.getValue()>.2) {
	Angle rotAmt = Degreef.createFrom(-1.5f);
	//game.dolphinN.yaw(rotAmt);	
	
	if(game.camera.getMode()=='n') {
		game.dolphinN.yaw(rotAmt);	
		System.out.println("controller rotate right");

		}
	else {
	Vector3 u= game.camera.getRt();
	Vector3 v =game.camera.getUp();
	Vector3 n =game.camera.getFd();
	Vector3 f = (Vector3)Vector3f.createFrom(0.0f, 0.01f, 0.0f);

       Vector3f fu = (Vector3f) (u.rotate(rotAmt, f)).normalize();
       Vector3f fv = (Vector3f) (v.rotate(rotAmt, f)).normalize();
       Vector3f fn = (Vector3f) (n.rotate(rotAmt, f)).normalize();

       game.camera.setRt(fu);
       game.camera.setUp(fv);
       game.camera.setFd(fn);
	
	
	
	}
	}
	}
}
