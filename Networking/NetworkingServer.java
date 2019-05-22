package Networking;

import java.io.IOException;
import ray.networking.IGameConnection.ProtocolType;
public class NetworkingServer
{
	private GameServerUDP thisUDPServer;
	private long lastUpdateTime;
	private long lastUpdateTim;

	int count = 20;//length of time for npc to show up
	
	 private static NPCcontroller npcCtrl;
	 
	 
	public NetworkingServer(int serverPort, String protocol) throws IOException
	{ 
		npcCtrl = new NPCcontroller();
		npcCtrl.setupNPC();


		try
		{ 
			thisUDPServer = new GameServerUDP(serverPort,npcCtrl);
		}
		catch (IOException e)
		{ 
			e.printStackTrace();
		}
	
	
		
		long startTim = System.nanoTime();
		lastUpdateTim = startTim;
	//	lobby();

		long startTime = System.nanoTime();
		lastUpdateTime = startTime;
		 npcLoop();
		
	}
	
	
	//run the npc server
	 public void npcLoop() // NPC control loop
	 { while (true)
	 { 
		 
		 
		 long frameStartTime = System.nanoTime();
	 float elapMilSecs = (frameStartTime-lastUpdateTime)/(1000000.0f);
	 npcCtrl.setLoop();
	 
	 
	 
	 if (elapMilSecs >= 30.0f && thisUDPServer.npcCtrl.startloop)
	 {	 //System.out.println("time clicked");

		 lastUpdateTime = frameStartTime;
		 
	 npcCtrl.updateNPCs();
	 
	 thisUDPServer.newNPCinfo();
	 }
	 
	
	 
	 Thread.yield();
	 } }

	public static NPCcontroller getnpc() {
		return npcCtrl;
	} 
	 
	
	public  void lobby() {
		
		
		while(count>-1) {
		
		
		long frameStartTim = System.nanoTime();
		 float elapMilSec = (frameStartTim-lastUpdateTim)/(1000000.0f);
		 if (elapMilSec >= 1000.0f )
		 {//	 System.out.println("time clicked");

			 lastUpdateTim = frameStartTim;
				thisUDPServer.startMsg(count);
				count--;

		 }
		 
		
		 
		// Thread.yield();
		 } 
		
		System.out.println("lobby time ended");
		
		
		
		
	}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
	
	
	 
	public static void main(String[] args) throws NumberFormatException, IOException
	{ 
		if(args.length > 1)
		{ 
			NetworkingServer app = new NetworkingServer(Integer.parseInt(args[0]), args[1]);
		} 
	}
}