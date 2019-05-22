package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import net.java.games.input.Event;
public class ControllerX extends AbstractInputAction
{
private MyGame game;
public ControllerX(MyGame ga)
{
	game=ga;
}
public void performAction(float time, Event e)

//Vector v,p,    p1,p2

{
	System.out.println("moving sideways");

	
	if(e.getValue()<-.1) {

		if(game.camera.getMode()=='n') {
			
			//game.dolphinN.moveRight(.03f);
			
			//game.camera.setPo((Vector3f)Vector3f.createFrom(p.x()-0.01f, p.y(),p.z() ) );

		}else {
			
			
			Vector3f v = game.camera.getRt(); //fd = forward vector of camera
			Vector3f p = game.camera.getPo();   //current position of camera

			Vector3f p1 = (Vector3f) Vector3f.createFrom(-0.03f*v.x(), -0.03f*v.y(), -0.03f*v.z());


			
			
			

	Vector3f p2 = (Vector3f) p.add((Vector3)p1);
	//officially apply this vector to the camera
	game.camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));



			
			
	}
}
	
	
	if(e.getValue()>.1){
		
		

if(game.camera.getMode()=='n') {

//game.dolphinN.moveLeft(.03f);

//	game.camera.setPo((Vector3f)Vector3f.createFrom(p.x()+.01f,p.y(),p.z()));

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

	













}