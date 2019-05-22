package myGameEngine;
import ray.input.action.AbstractInputAction;
import ray.rage.game.*;
import a3.MyGame;
import net.java.games.input.Event;
import a3.ProtocolClient;

public class QuitGameAction extends AbstractInputAction
{
private MyGame game;
private ProtocolClient p;
public QuitGameAction(MyGame g, ProtocolClient prot)
{ game = g;
p =prot;
}
public void performAction(float time, Event event)
{ System.out.println("shutdown requested");
p.sendByeMessage();

game.shutdown();
}
}