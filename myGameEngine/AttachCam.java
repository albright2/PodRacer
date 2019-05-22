package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.Projection;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import net.java.games.input.Event;
public class AttachCam extends AbstractInputAction
{
private MyGame game;

public AttachCam(MyGame c)
{ 
	game = c;
}
public void performAction(float time, Event e)
{ 
	
  //getting onto dolphin
     if(game.at) {
     game.camera.setMode('n'); 
     game.at=false;
     }
     else {
    	 //getting off dolphin
    	 
    	 game.camera.setMode('c');
    	 
    	 
    	 //camera set off of dolphin
    	 Vector3 loc = game.dolphinN.getLocalPosition();
    	 Vector3f p1  = (Vector3f) Vector3f.createFrom(0f, 2f, 1.0f);//move slightly away from dolphin when getting off

    	 Vector3f p2 = (Vector3f) loc.add((Vector3)p1);
    	 game.camera.setPo((Vector3f) p2);
    	 
    	 
    	 //camera has same orientation as dolphin when getting off
    	 
    	 
    	 
    	
    	 
    	 
    	 
    	 game.at=true;
    	
    	 
     }
}
}