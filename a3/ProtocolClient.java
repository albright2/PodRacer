package a3;

import ray.networking.client.GameConnectionClient;
import ray.rml.Vector3;
import ray.rml.Vector3f;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;

import javax.vecmath.Tuple3d;

public class ProtocolClient extends GameConnectionClient
{
	private MyGame game;
	private UUID id;
	private Vector<GhostAvatar> ghostAvatars;
	private Vector<NPCavatar>   npcs;

	// private Vector<GhostNPC> ghostNPCs; later use if want to keep track of all npcs
	public ProtocolClient(InetAddress remAddr, int remPort, ProtocolType pType, MyGame game) throws IOException
	{
		super(remAddr, remPort, pType);
		this.game = game;
		this.id = UUID.randomUUID();
		this.ghostAvatars = new Vector<GhostAvatar>();
		this.npcs = new Vector<NPCavatar>();

	}
	
	public void processPacket(Object msg)
	{              

		String strMessage = (String) msg;
		String[] msgTokens = strMessage.split(",");
		
		if (msgTokens.length > 0)
		{
			if (msgTokens[0].compareTo("join") == 0)
			{
				if (msgTokens[1].compareTo("success") == 0)
				{
					game.setIsConnected(true);//connect
					setThisPlayer(msgTokens[2]);//player#
					float model=1f;
					if(game.model) {model=0f;}
					sendCreateMessage(game.getPlayerPosition(),(Matrix3f) game.getPlayerRotation(),model);//send the server this players position
/////////////////////////////////////////////////////////////
					//now that successfully joined, request server for npc
					sendWantNPC();
					
				}
				if (msgTokens[1].compareTo("failure") == 0)
				{
					game.setIsConnected(false);//disconnect
				}
			}
			if (msgTokens[0].compareTo("bye") == 0)
			{
				UUID ghostID = UUID.fromString(msgTokens[1]);
				removeGhostAvatar(ghostID);
			}
			
			//show all players that have joined before this player
			if (msgTokens[0].compareTo("dsfr") == 0)
			{
				
				UUID ghostID = UUID.fromString(msgTokens[1]);
				Vector3 ghostPosition = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				
				//rotation
				//5811,6912,71013

				//258,369,4710
				Vector3 u = Vector3f.createFrom(Float.parseFloat(msgTokens[5]), Float.parseFloat(msgTokens[6]), Float.parseFloat(msgTokens[7]));
				Vector3 v = Vector3f.createFrom(Float.parseFloat(msgTokens[8]), Float.parseFloat(msgTokens[9]), Float.parseFloat(msgTokens[10]));
				Vector3 n = Vector3f.createFrom(Float.parseFloat(msgTokens[11]), Float.parseFloat(msgTokens[12]), Float.parseFloat(msgTokens[13]));
				boolean model=true;
				float temp =Float.parseFloat(msgTokens[14]);

				if(temp==0f) { model =true;}
				else { model =false;}
				Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
				
				
				
				
				createGhostAvatar(ghostID, ghostPosition,rot,model);
			}
			if (msgTokens[0].compareTo("create") == 0)
			{
				UUID ghostID = UUID.fromString(msgTokens[1]);
				//position
				Vector3 position = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				//rotation
			
				Vector3 u = Vector3f.createFrom(Float.parseFloat(msgTokens[5]), Float.parseFloat(msgTokens[6]), Float.parseFloat(msgTokens[7]));
				Vector3 v = Vector3f.createFrom(Float.parseFloat(msgTokens[8]), Float.parseFloat(msgTokens[9]), Float.parseFloat(msgTokens[10]));
				Vector3 n = Vector3f.createFrom(Float.parseFloat(msgTokens[11]), Float.parseFloat(msgTokens[12]), Float.parseFloat(msgTokens[13]));

				Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
				float temp =Float.parseFloat(msgTokens[14]);
				boolean model = true;
				if( temp==0f) { model =true;}
				else { model =false;}
				createGhostAvatar(ghostID, position,rot,model);
			}
			
			if (msgTokens[0].compareTo("time") == 0)
			{
				
				
			int times =   Integer.parseInt(	msgTokens[2]);
				System.out.println("lobby ending in: "+times);
				
				
				
			}
			
			if (msgTokens[0].compareTo("finished") == 0)
			{
				
				
			float times =   Float.parseFloat(	msgTokens[1]);
				System.out.println("a player crossed finish line at:"+times+ " seconds");
				
				
				
			}
			
			if (msgTokens[0].compareTo("wsds") == 0)
			{
				UUID ghostID = UUID.fromString(msgTokens[1]);
			float model=0f;
				if( game.model) { model =0f;}
				else { model =1f;}
				
				sendDetailsForMessage(ghostID, game.getPlayerPosition(),(Matrix3f) game.getPlayerRotation(),model)	;
				
			
			}
			if (msgTokens[0].compareTo("move") == 0)
			{
				
				UUID ghostID = UUID.fromString(msgTokens[1]);
				Vector3 position = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				moveGhostAvatar(ghostID, position);
			}
			
			
			
			
			if (msgTokens[0].compareTo("rotate") == 0)
			{
				 
				UUID ghostID = UUID.fromString(msgTokens[1]);
				

				//258,369,4710
				Vector3 u = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				Vector3 v = Vector3f.createFrom(Float.parseFloat(msgTokens[5]), Float.parseFloat(msgTokens[6]), Float.parseFloat(msgTokens[7]));
				Vector3 n = Vector3f.createFrom(Float.parseFloat(msgTokens[8]), Float.parseFloat(msgTokens[9]), Float.parseFloat(msgTokens[10]));

				Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
				rotateGhostAvatar(ghostID, rot);

				
			}
			
			
			
			
			
			
			
			//recieved npc player from server, create npc
			if (msgTokens[0].compareTo("mnpc") == 0)
			{
				
				 int ghostID = Integer.parseInt(msgTokens[1]);			
				 
				 Vector3 ghostPosition = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				
				//rotation

				Vector3 u = Vector3f.createFrom(Float.parseFloat(msgTokens[5]), Float.parseFloat(msgTokens[6]), Float.parseFloat(msgTokens[7]));
				Vector3 v = Vector3f.createFrom(Float.parseFloat(msgTokens[8]), Float.parseFloat(msgTokens[9]), Float.parseFloat(msgTokens[10]));
				Vector3 n = Vector3f.createFrom(Float.parseFloat(msgTokens[11]), Float.parseFloat(msgTokens[12]), Float.parseFloat(msgTokens[13]));

				Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
				
				
				
				//bottom of page
				createNPCavatar(ghostID, ghostPosition,rot);
			}
			

			//recieved npc update
			if (msgTokens[0].compareTo("movenpc") == 0)
			{
				
				 int id = Integer.parseInt(msgTokens[1]);			
				 
				 Vector3 ghostPosition = Vector3f.createFrom(Float.parseFloat(msgTokens[2]), Float.parseFloat(msgTokens[3]), Float.parseFloat(msgTokens[4]));
				
				//rotation

				Vector3 u = Vector3f.createFrom(Float.parseFloat(msgTokens[5]), Float.parseFloat(msgTokens[6]), Float.parseFloat(msgTokens[7]));
				Vector3 v = Vector3f.createFrom(Float.parseFloat(msgTokens[8]), Float.parseFloat(msgTokens[9]), Float.parseFloat(msgTokens[10]));
				Vector3 n = Vector3f.createFrom(Float.parseFloat(msgTokens[11]), Float.parseFloat(msgTokens[12]), Float.parseFloat(msgTokens[13]));

				Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
			

				moveNPCavatar(id,ghostPosition,rot);
				//bottom of page
			}
			//
			//methods for ghost avatar in MyGame to pass from here
			/////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
		}
	}

//set up starting positions
	public void setThisPlayer(String player) {

		
		if(player=="1") {
			Vector3 one = Vector3f.createFrom(0.7425638f, 0.08999966f, 3.1145186f);
			game.dolphinN.setLocalPosition(one);
			
			
			//258,369,4710
			Vector3 u = Vector3f.createFrom(-0.5446342f, 0.0f, -0.83866185f);
			Vector3 v = Vector3f.createFrom(0.0f, 1.0f, 0.0f);
			Vector3 n = Vector3f.createFrom(0.83866185f, 0.0f, -0.5446342f);

			Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
			
			
			
			game.dolphinN.setLocalRotation(rot);

			
			
		}
		
		if(player=="2") {
			Vector3 one = Vector3f.createFrom(0.78944325f, 0.08999896f, 3.2733028f);
			game.dolphinN.setLocalPosition(one);
			
			
			//258,369,4710
			Vector3 u = Vector3f.createFrom(-0.4539824f, 0.0f, -0.89098954f);
			Vector3 v = Vector3f.createFrom(0.0f, 1.0f, 0.0f);
			Vector3 n = Vector3f.createFrom(0.89098954f, 0.0f, -0.4539824f);

			Matrix3f rot = (Matrix3f) Matrix3f.createFrom(u, v, n);
			
			
			
			game.dolphinN.setLocalRotation(rot);

			
			
		}
		
		
		
		
		
	}

	public void createGhostAvatar(UUID ghostID, Vector3 position,Matrix3f rot,boolean model)
	{
		GhostAvatar ghostAvatar = new GhostAvatar(ghostID, position,rot,model);
		ghostAvatars.add(ghostAvatar);
		try
		{
			game.addGhostAvatarToGameWorld(ghostAvatar);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void removeGhostAvatar(UUID ghostID)
	{
		Iterator<GhostAvatar> iterator = ghostAvatars.iterator();
		while (iterator.hasNext())
		{
			GhostAvatar ghostAvatar = iterator.next();
			if (ghostAvatar.getID() == ghostID)
			{
				ghostAvatars.removeElement(ghostAvatar);
				game.removeGhostAvatarFromGameWorld(ghostAvatar);
				break;
			}
		}
	}
	
	
	
	
	
	public void moveGhostAvatar(UUID ghostID, Vector3 pos)
	{
		Iterator<GhostAvatar> iterator = ghostAvatars.iterator();
		while (iterator.hasNext())
		{
			GhostAvatar ghostAvatar = iterator.next();
			UUID id3 = ghostAvatar.getID();
			if (ghostAvatar.getID().equals(ghostID));
			{
				ghostAvatar.setPosition(pos);
				game.moveGhostAvatarAroundGameWorld(ghostAvatar, pos);
				break;
			}
		}
	}
	
	
	
	
	public void rotateGhostAvatar(UUID ghostID, Matrix3 pos)
	{
		Iterator<GhostAvatar> iterator = ghostAvatars.iterator();
		while (iterator.hasNext())
		{
			GhostAvatar ghostAvatar = iterator.next();
			UUID id3 = ghostAvatar.getID();
			if (ghostAvatar.getID().equals(ghostID));
			{
				ghostAvatar.setRotation(pos);
				game.rotateGhostAvatarAroundGameWorld(ghostAvatar, pos);
				break;
			}
		}
	}
	public void createNPCavatar(int ghostID, Vector3 position,Matrix3f rot)
	{
		NPCavatar ghostAvatar = new NPCavatar(ghostID, position,rot);
		//System.out.println("starting point  "+position.x()+" "+position.y()+" "+position.z());
		npcs.add(ghostAvatar);
		try
		{
			game.addNPCAvatarToGameWorld(ghostAvatar);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	private void moveNPCavatar(int ghostID,Vector3 pos, Matrix3f rot) {
		Iterator<NPCavatar> iterator = npcs.iterator();
		while (iterator.hasNext())
		{
			NPCavatar ghostAvatar = iterator.next();
			int id3 = ghostAvatar.getID();
			if (id3==(ghostID));
			{
			//	System.out.println("moving to "+pos.x()+" "+pos.y()+" "+pos.z());

				ghostAvatar.setRotation(rot);
				ghostAvatar.setPosition(pos);
				game.moveNPCAvatarAroundGameWorld(ghostAvatar,pos, rot);
				break;
			}
		}		
	}

	
	
	//
	//Sending to server
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void sendJoinMessage()
	{
		try
		{
			sendPacket(new String("join,"+id));

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void sendCreateMessage(Vector3 rot,Matrix3f pos,float model)
	{
		try
		{
			//create,id,x,y,z,p1x,p1y,p1z,p2x,p2y,p2z,p3x,p3y,p3z 14 items =13 indexes
			String message = new String("create," + id.toString());
			message += "," + rot.x() + "," + rot.y() + "," + rot.z()+","+ pos.column(0).x() + ","  + pos.column(0).y() + ","  + pos.column(0).z() +","+ pos.column(1).x() + ","  + pos.column(1).y() + ","  + pos.column(1).z() +","+ pos.column(2).x() + ","  + pos.column(2).y() + ","  + pos.column(2).z()+","+model;
			sendPacket(message);
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void sendByeMessage()
	{
		try
		{
			String message = new String("bye," + id.toString());
			sendPacket(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void sendDetailsForMessage(UUID remId, Vector3 pos,Matrix3f rot, float model)
	{
		try
		{
			String message = new String("dsfr," + id.toString() + "," + remId.toString());
			message += "," + pos.x() + "," + pos.y() + "," + pos.z()+ "," + rot.column(0).x()+ ","+ rot.column(0).y()+ ","+ rot.column(0).z()+ ","+ rot.column(1).x()+ ","+ rot.column(1).y()+ ","+ rot.column(1).z()+ ","+ rot.column(2).x()+ ","+ rot.column(2).y()+ ","+ rot.column(2).z()+ ","+model;
			sendPacket(message);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	//client to server: 
	public void sendWantsDetailsMessages()
	{
		try
		{	
			String message = new String("details," + id.toString());
			sendPacket(message);

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMoveMessage(Vector3 pos)
	{
		try
		{
			String message = new String("move," + id.toString());
			message += "," + pos.x() + "," + pos.y() + "," + pos.z();
			sendPacket(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public void sendRotateMessage(Matrix3 pos)
	{
		try
		{//rotate,id,x,y,z,x,y,z,x,y,z 
			String message = new String("rotate," + id.toString());
			message += "," + pos.column(0).x() + ","  + pos.column(0).y() + ","  + pos.column(0).z()+ ","  + pos.column(1).x() + ","  + pos.column(1).y() + ","  + pos.column(1).z()+ ","  + pos.column(2).x() + ","  + pos.column(2).y() + ","  + pos.column(2).z();
			sendPacket(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void sendWantNPC()
	 { try
	 { 
		 sendPacket(new String("mnpc," + id.toString()));
	 }
	 catch (IOException e)
	 { e.printStackTrace();
	 } }

	public void sendTime(String time) throws IOException {
		String message = new String("time," + id.toString()+","+time);
		sendPacket(message);
	}
	
	
	
	
	
	
	
	
	
	

	
}
