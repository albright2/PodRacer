package myGameEngine;
import java.io.IOException;
import java.util.Arrays;

import a3.MyGame;

import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import net.java.games.input.Event;
public class Respawn extends AbstractInputAction
{
	private MyGame game;
public Respawn(MyGame p)
{ 
game = p;
	
}
public void performAction(float time, Event e)

{
	
	
	
	
	
	
	if(game.camera.getMode()=='n') {
		//current location and rotation
try {
	game.respawn();
} catch (IOException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
			
			
		
		}
		else {
		
			
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
}