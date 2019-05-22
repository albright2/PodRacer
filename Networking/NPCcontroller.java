package Networking;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Vector;

import ray.ai.behaviortrees.BTCompositeType;
import ray.ai.behaviortrees.BTSequence;
import ray.ai.behaviortrees.BehaviorTree;


public class NPCcontroller 
{

   
   private ArrayList<Float> stuff = new ArrayList<Float>();    
    
    
    
    
    
  public void  initialize() throws IOException {
	  
		String path = System.getProperty("user.dir")+"\\Networking\\ok.txt"; 
	  
		//String file_name = "C:\\Users\\zabuz\\eclipse-workspace\\animations\\src\\Networking\\ok.txt";
		try{
			
			ReadFile file = new ReadFile(path);
			String [] aryLines = file.OpenFile();
			//System.out.println(aryLines.length);

			int i;
			for(i=0;i<aryLines.length;i++) {
				

			
					
					
					
					
					
					
					String[] str = aryLines[i].split(" ");
					
				
				int ko=0;
					while(str.length>ko) {
				
					stuff.add(Float.parseFloat(str[ko]));

					ko++;
					

					
					}
				
			}
			System.out.println("ready");

			}
			catch(IOException e) {System.out.println(e.getMessage());}
	  
	  
  }
    	
    	
    boolean startloop=false;
    
    		
    
    
    
    
    
    //amount of npcs here
    private int npcCount = 1;
    int update=0;
  //  private Random rn = new Random();
    private NPC[] npc = new NPC[npcCount];
    
   
    
    public void setupNPC() throws IOException
    {
    	initialize();
        for (int i = 0; i < npcCount; i++)
        {
        	
            npc[i] = new NPC();
            npc[i].setLocation(1.365f, 0.09f, 2.86327f);
            npc[i].setRotation(-0.54463816f,0.0f,-0.83866924f, 0.0f,1.0f,0.0f,.83866924f,0.0f,-0.54463816f);
        }
    }
    
    public void updateNPCs() {
    	for(int i = 0; i < npcCount; i++) {
    		
    		
    		
    		
    		npc[i].setLocation(stuff.get(update), stuff.get(update+1), stuff.get(update+2));
    		npc[i].setRotation(stuff.get(update+3), stuff.get(update+4), stuff.get(update+5), stuff.get(update+6), stuff.get(update+7), stuff.get(update+8), stuff.get(update+9), stuff.get(update+10), stuff.get(update+11));
    		
    		
    		
    		//npc[i].updateLocation(stuff[update-11],stuff[update-10],stuff[update-9],stuff[update-8],stuff[update-7],stuff[update-6],stuff[update-5],stuff[update-4],stuff[update-3],stuff[update-2],stuff[update-1],stuff[update]);
    	//	System.out.println(npc[i].getuX()+" "+npc[i].getuY()+" "+npc[i].getuZ()+" "+npc[i].getvX()+" "+npc[i].getvY()+" "+npc[i].getvZ()+" "+npc[i].getnX()+" "+npc[i].getnY()+" "+npc[i].getnZ()+" ");
    		
    		
    		
    		
    		
    		
    		
    	//make npc loop around track
    	if(update+13>stuff.size()) {update=0;}
    	else {
    	update=update+12;
    			}
    }
    }
    
 
    
public void setLoop() {
	
	startloop=true;
	
}
   
    
    public NPC[] getNPCs()
    {
        return npc;
    }
    
    public int getNumberOfNPCs()
    {
        return npcCount;
    }
}