package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.scene.*;
import ray.rage.game.*;
import ray.rml.*;
import a3.MyGame;
import net.java.games.input.Event;
public class LightController extends AbstractInputAction
{
	private MyGame game;
public LightController(MyGame p)
{ 
game = p;
	
}
public void performAction(float time, Event e)

{ 
	
	game.light();
	
	
	
	
}
	}
	

