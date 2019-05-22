package Networking;

import java.io.IOException;


import java.net.InetAddress;
import java.util.UUID;

import ray.networking.server.GameConnectionServer;
import ray.networking.server.IClientInfo;

public class GameServerUDP extends GameConnectionServer<UUID>
{	int loco;
 NPCcontroller npcCtrl;
private NPC[] npc; //an array incase if i ever want to make multiple npc
public int players;//index or amount of players
	public GameServerUDP(int localPort, NPCcontroller np ) throws IOException
    { 
        super(localPort, ProtocolType.UDP); 
        loco=localPort;
        npcCtrl = np;
		players=0;
       
    }
    @Override
    public void processPacket(Object o, InetAddress senderIP, int sndPort)
    {
    	
        String message = (String) o;
        String[] msgTokens = message.split(",");
 
        if(msgTokens.length > 0)
        {
            // case where server receives a JOIN message
            // format: join,localid
            if(msgTokens[0].compareTo("join") == 0)
            { 

                try
				{ 
                    IClientInfo ci;
                    
                    ci = getServerSocket().createClientInfo(senderIP, sndPort);
             
                    UUID clientID = UUID.fromString(msgTokens[1]);
                    addClient(ci, clientID);
                    sendJoinedMessage(clientID, true);
                    players++;
                }
                catch (IOException e)
                { 
                    e.printStackTrace();
                }
            }
            // case where server receives a CREATE message
            // format: create,localid,x,y,z
            if(msgTokens[0].compareTo("create") == 0)
            { 
                UUID clientID = UUID.fromString(msgTokens[1]);
                String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4],
                		msgTokens[5], msgTokens[6], msgTokens[7],
                		msgTokens[8], msgTokens[9], msgTokens[10],
                       msgTokens[11], msgTokens[12], msgTokens[13],
                       msgTokens[14]};
               

                sendCreateMessage(clientID, pos);
                sendWantsDetailsMessage(clientID);
            }
         // case where server receives a BYE message
            // format: bye,localid
            if(msgTokens[0].compareTo("bye") == 0)
            { 
                UUID clientID = UUID.fromString(msgTokens[1]);
                sendByeMessage(clientID);
                removeClient(clientID);
            }
            
            
            if(msgTokens[0].compareTo("time") == 0)
            { 
                UUID clientID = UUID.fromString(msgTokens[1]);
               sendFinishTime(clientID,msgTokens[2]);
            }
            
            
            
            // case where server receives a DETAILS-FOR message
            if(msgTokens[0].compareTo("dsfr") == 0)
            { // etc….. 
                UUID clientID = UUID.fromString(msgTokens[1]);
                UUID remoteid = UUID.fromString(msgTokens[2]);
                String[] pos = {
                msgTokens[3], msgTokens[4], msgTokens[5], msgTokens[6],
                msgTokens[7], msgTokens[8], msgTokens[9], msgTokens[10],
                msgTokens[11],msgTokens[12],msgTokens[13],msgTokens[14],msgTokens[15]};
                
                sendDetailsMessage(clientID, remoteid, pos);
            }
            // case where server receives a MOVE message
            if(msgTokens[0].compareTo("move") == 0)
            { // etc….. 
                UUID clientID = UUID.fromString(msgTokens[1]);
                String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4]};
                sendMoveMessage(clientID, pos);
            }
            // case where server receives a MOVE message
            if(msgTokens[0].compareTo("rotate") == 0)
            { // etc….. 
                UUID clientID = UUID.fromString(msgTokens[1]);
                String[] pos = {msgTokens[2], msgTokens[3], msgTokens[4], msgTokens[5], msgTokens[6], msgTokens[7], msgTokens[8], msgTokens[9], msgTokens[10]};
                sendRotateMessage(clientID, pos);
            } 
            
            
            //send client a create npc

            if(msgTokens[0].compareTo("mnpc") == 0)
            {
                UUID npcID = UUID.fromString(msgTokens[1]);
                
                sendNPCinfo(npcID);
            }
            //never gets called, server will update npc when it's ready in its own class

            if(msgTokens[0].compareTo("movenpc") == 0)
            {
                
                newNPCinfo();
            }
            
            
           
        }
    }
    
   
    
	private void sendFinishTime(UUID clientID, String string) {
		
	    String message = new String("finished,"+string);

		 try {
			forwardPacketToAll(message, clientID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void sendJoinedMessage(UUID clientID, boolean success)
	{ // format: join, success or join, failure
		try
		{ 
		    String message = new String("join,");
		    if (success) 
		    {
		    	message += "success,"+players;
		    }
		    else 
		    {
		    	message += "failure";
		    }
		    sendPacket(message, clientID);
		}
		catch (IOException e) 
		{ 
		    e.printStackTrace(); 
	}
	}
	public void sendCreateMessage(UUID clientID, String[] position)
	{ // format: create, remoteId, x, y, z
		try
		{
		    String message = new String("create," + clientID.toString());
		   //xyz position
		    message += "," + position[0];
		    message += "," + position[1];
		    message += "," + position[2];
		    //uvn rotation
		    message += "," + position[3];
		    message += "," + position[4];
		    message += "," + position[5];
		    
		    message += "," + position[6];
		    message += "," + position[7];
		    message += "," + position[8];
		    
		    message += "," + position[9];
		    message += "," + position[10];
		    message += "," + position[11];
		    
		    //entity type as boolean

		    message += "," + position[12];


		    
		    
		    
		    
		    
		    
		    
		    
		    forwardPacketToAll(message, clientID);
		}
		catch (IOException e) 
		{ 
		    e.printStackTrace();
		}
	}
	public void sendDetailsMessage(UUID clientID, UUID remoteid, String[] position)
	{
		try
		{
		    String message = new String("dsfr," + clientID.toString());
		    //xyz position
		    message += "," + position[0];
		    message += "," + position[1];
		    message += "," + position[2];
		    //uvn rotation
		    message += "," + position[3];
		    message += "," + position[4];
		    message += "," + position[5];
		    message += "," + position[6];
		    message += "," + position[7];
		    message += "," + position[8];
		    message += "," + position[9];
		    message += "," + position[10];
		    message += "," + position[11];
		    message += "," + position[12];

		    sendPacket(message, remoteid);
		}
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}
	public void sendWantsDetailsMessage(UUID clientID)
	{
		try
		{
		    String message = new String("wsds," + clientID.toString());
		    forwardPacketToAll(message, clientID);
		}
		catch (IOException e) 
		{
		    e.printStackTrace();
		}
	}
	public void sendMoveMessage(UUID clientID, String[] position)
	{
		try
		{
			String message = new String("move");
			message += "," + clientID;
		    message += "," + position[0];
		    message += "," + position[1];
		    message += "," + position[2];
		    forwardPacketToAll(message, clientID);
		}
		catch (IOException e) 
		{ 
		    e.printStackTrace();
		}
	}
	public void sendByeMessage(UUID clientID)
	{
		try 
		{
			String message = new String("bye," + clientID.toString());
			forwardPacketToAll(message, clientID);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	public void sendRotateMessage(UUID clientID, String[] position)
	{
		
		try
		{
			String message = new String("rotate");
			message += "," + clientID;
		    message += "," + position[0];
		    message += "," + position[1];
		    message += "," + position[2];
		    message += "," + position[3];
		    message += "," + position[4];
		    message += "," + position[5];  
		    message += "," + position[6];
		    message += "," + position[7];
		    message += "," + position[8];
		

		    forwardPacketToAll(message, clientID);
		}
		catch (IOException e) 
		{ 
		    e.printStackTrace();
		}
	}
	
	//creating npc, INITIAL CREATING
	public void sendNPCinfo(UUID remoteid)
	{
		npc = npcCtrl.getNPCs();
		for (int i = 0; i < npcCtrl.getNumberOfNPCs(); i++)
		{
			try
			{
				String message = new String("mnpc," + Integer.toString(i));
				message += "," + (npc[i].getX());
				message += "," + (npc[i].getY());
				message += "," + (npc[i].getZ());
				
				message += "," + (npc[i].getuX());
				message += "," + (npc[i].getuY());
				message += "," + (npc[i].getuZ());
				
				message += "," + (npc[i].getvX());
				message += "," + (npc[i].getvY());
				message += "," + (npc[i].getvZ());
				
				message += "," + (npc[i].getnX());
				message += "," + (npc[i].getnY());
				message += "," + (npc[i].getnZ());
				 sendPacket(message, remoteid);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//sending out new updated npc coordinates to all clients, NOT INITIAL CREATING
		public void newNPCinfo()
		{
			npc = npcCtrl.getNPCs();
			for (int i = 0; i < npcCtrl.getNumberOfNPCs(); i++)
			{
				try
				{
					String message = new String("movenpc," + Integer.toString(i));
					message += "," + (npc[i].getX());
					message += "," + (npc[i].getY());
					message += "," + (npc[i].getZ());
					
					message += "," + (npc[i].getuX());
					message += "," + (npc[i].getuY());
					message += "," + (npc[i].getuZ());
					
					message += "," + (npc[i].getvX());
					message += "," + (npc[i].getvY());
					message += "," + (npc[i].getvZ());
					
					message += "," + (npc[i].getnX());
					message += "," + (npc[i].getnY());
					message += "," + (npc[i].getnZ());
					sendPacketToAll(message);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		public void startMsg(int t) {
			
			
			try{
			
				String message = new String("time," +5+","+t);
		System.out.println(message);
				sendPacketToAll(message);
			
			
			}
		catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}