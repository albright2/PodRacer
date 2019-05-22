package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;

import java.io.IOException;

import a3.MyGame;
import net.java.games.input.Event;
public class ControllerForward extends AbstractInputAction
{
private MyGame game;
public ControllerForward(MyGame ga)
{
	game=ga;
}
public void performAction(float time, Event e)
//Vector v,p,    p1,p2

{
	System.out.println("forward");


	
	  
		if(e.getValue() >0.1) {
		//	System.out.println(e.getValue()+"moving forward");
		Vector3f v = game.camera.getFd(); //fd = forward vector of camera
		Vector3f p = game.camera.getPo();   //current position of camera

											
		
		if(game.camera.getMode()=='n') {
			
			try {
				game.moveForward1(game.dolphinN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		//	game.camera.setPo((Vector3f)Vector3f.createFrom(p.x()-0.01f,p.y()-0.01f,p.z()-.01f ) );

			
		}
									

								//creating a new vector,p, from current camera forward vector
												//  0.01*forward vector only x out of x,y,z;
												//  0.01*forward vector only y
												//  0.01*forward vector only z
	Vector3f p1 = (Vector3f) Vector3f.createFrom(0.01f*v.x(), 0.01f*v.y(), 0.01f*v.z());

	//p2 is the new vector,p1, added to current position of camera

	Vector3f p2 = (Vector3f) p.add((Vector3)p1);
	//officially apply this vector to the camera
	game.camera.setPo((Vector3f)Vector3f.createFrom(p2.x(),p2.y(),p2.z()));



	}
		
		
		
	
	
}


}

