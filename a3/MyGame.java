package a3;

//import audio
import ray.audio.AudioManagerFactory;
//import com.jogamp.openal.ALFactory;

import ray.audio.AudioResource;
import ray.audio.AudioResourceType;
import ray.audio.IAudioManager;
import ray.audio.Sound;
import ray.audio.SoundType;
//java stuff
import java.io.*;
import java.util.*;
//scripting
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
//read and write to store position and rotations
import ray.rage.rendersystem.states.*;
import java.io.IOException;
//physic stuff
import ray.physics.PhysicsEngine;
import ray.physics.PhysicsObject;
import ray.physics.PhysicsEngineFactory;
//everything from myGameEngine
import myGameEngine.*;
//skeletal entity for animation
import static ray.rage.scene.SkeletalEntity.EndType.LOOP;
//input from keyboard and controller
import net.java.games.input.Controller;
import net.java.games.input.Event;
//use assets and rage and networking class from rage
import ray.rage.asset.texture.*;
import ray.input.*;
import ray.input.action.*;
import ray.networking.IGameConnection.ProtocolType;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import ray.rage.*;
import ray.rage.game.*;
import ray.rage.rendersystem.*;
import ray.rage.rendersystem.Renderable.*;
import ray.rage.scene.*;
import ray.rage.scene.Camera.Frustum.*;
import ray.rage.scene.controllers.*;
import ray.rage.util.Configuration;
import ray.rml.*;
import ray.rage.rendersystem.gl4.GL4RenderSystem;
public class MyGame extends VariableFrameRateGame {

	// to minimize variable allocation in update()
	GL4RenderSystem rs;
	float elapsTime = 0.0f;
	private boolean isClientConnected;
	private static final String SKYBOX_NAME = "SkyBox";
	ArrayList <SceneNode> list= new ArrayList<SceneNode>();
	ArrayList <Integer> physicObjects= new ArrayList<Integer>();
boolean light = true;
	private PhysicsEngine physicsEng;
	private PhysicsObject dolphinP,ground;
	String elapsTimeStr, dispStr,score;
	int elapsTimeSec,cout, pcount = 0;
	int count =1;
	private InputManager im;
	public boolean at= true;
	public boolean model;
	public boolean mode;
	private SceneNode tessN;
	//variable for attach Method
	private Action RY,R,X,Y,spinB,spinF,rotRight,rotLeft,attach,moveForward, camF, camL,camR,camB, quitGameAction,printLoc,lightC,controlForward; //list all actions
	protected Entity dolphinE, planet,ghostE;
	public SceneNode dolphinN;
	protected SceneNode cameraNode;
	protected SceneNode dn;
	protected SceneNode p1;
	protected SceneNode p2;
	protected SceneNode p3;
	protected SceneNode check1,check2,check3,check4,check5,check6,check7,check8,check9,check10,check11,check12,check13,check14,check15,check16,check17,curCheck;
	protected SceneNode ghostN;
	private String serverAddress;
	private int serverPort;
	private ProtocolType serverProtocol;
	private Vector<UUID> gameObjectsToRemove;
	private TimerController dolph;
	private ProtocolClient protClient;
	RotationController rc;
	public Camera camera;
	SkeletalEntity  E;
	 Light flight ;
	SceneNode rcN;
	int effect=0;
	Camera d;
	int counter =1;
    ScriptEngine jsEngine;
    int speed;
	int ch =1;
	float last;
	SceneManager sm;
	RenderWindow rw;
	IAudioManager audioMgr;
	Sound oceanSound, hereSound;
	public static void main(String[] args)
	{ 
		//from command line, ipaddress and port number
		Game game = new MyGame(args[0], Integer.parseInt(args[1]));
		
		
		
		try
		{ 
			game.startup();
			game.run();
		}

		catch (Exception e){ 
			e.printStackTrace(System.err);
		}

		finally
		{ 
			game.shutdown();
			game.exit();
		} 
	}
   

    public MyGame(String serverAddr, int sPort) {
		super();
		this.serverAddress = serverAddr;
		this.serverPort = sPort;
		this.serverProtocol = ProtocolType.UDP;

		//this.serverProtocol = ProtocolType.UDP;
		System.out.println("W,S to move forward, backwards; left and right arrow to move left and right");	
		isClientConnected = false;
	  
		setupNetworking();

    }
	
    
    
    private void initPhysicsSystem()
    { String engine = "ray.physics.JBullet.JBulletPhysicsEngine";
    float[] gravity = {0, -1f, 0};
    physicsEng = PhysicsEngineFactory.createPhysicsEngine(engine);
    physicsEng.initSystem();
    physicsEng.setGravity(gravity);
    }
    
    
    
    
    //MOVING FORWARD
    public void moveForward1(SceneNode dolphinN) throws IOException {
        dolphinP = this.getEngine().getSceneManager().getSceneNode("myDolphinNode").getPhysicsObject();
    	float []position = dolphinN.getWorldPosition().toFloatArray();

    	
    
    	//set the force being applied to physics object by taking the forward facing direction and multiple?
    	float [] rotation = dolphinN.getWorldForwardAxis().toFloatArray();
    	
    //	System.out.println(dolphinP.getLinearVelocity()[0]+" "+dolphinP.getLinearVelocity()[1]+" "+dolphinP.getLinearVelocity()[2]);
       
    	
  	   
    	if(speed<50){
    	
    	   dolphinP.applyForce( rotation[0], rotation[1], rotation[2], 		position[0], position[1], position[2]);

    	  

    	}
    	
    	if(dolphinP.getLinearVelocity()[0]==0.0f && dolphinP.getLinearVelocity()[1] == 0.0f && dolphinP.getLinearVelocity()[2]==0.0f){
    	
        	
    	}
	
    }
    
 
   
public void setSpeed() {
	
	 
	   double x =  50*(double)dolphinP.getLinearVelocity()[0];
	   
	   double y = 50*(double)dolphinP.getLinearVelocity()[1];
	   double z =50*(double) dolphinP.getLinearVelocity()[2];
	 speed =(int)  Math.sqrt((x*x )+ (y*y) + (z*z));
	
	
}




	//MOVING FORWARD
    public void moveBackward1(SceneNode dolphinN) {
        dolphinP = dolphinN.getPhysicsObject();

     
        	float [] stop = dolphinP.getLinearVelocity()  ;

        	stop[0]=stop[0]*.97f;
        	stop[1]=stop[1]*.97f;
        	stop[2]=stop[2]*.97f;
        	dolphinP.setLinearVelocity(stop);
    }
    
    
    
    
  
    
    
    
    
    
    
    
    
    
    
    
    
    private void createRagePhysicsWorld()
    {
    	float mass = 1.0f;
    float up[] = {0,1,0};

    double[] temptf;
    temptf = toDoubleArray(dolphinN.getLocalTransform().toFloatArray());
    										//id,amount of mass, position, radius/size
    
    
   physicObjects.add(physicsEng.nextUID());
    dolphinP = physicsEng.addSphereObject(physicObjects.get(0),
    mass, temptf, .09f);
    
    
    
    float[] gravity = {0, -1f, 0};
    physicsEng.setGravity(gravity);
   // velocity - An array of 3 floats specifying the [x,y,z] linear velocity for the object
    dolphinN.setPhysicsObject(dolphinP);
   
    
    
    
    //initial velocity so physics object wont freeze
	
    float [] velocity = {.05f,.05f,.0f};
    dolphinP.setLinearVelocity(velocity);
    
    

    
    physicObjects.add(physicsEng.nextUID());
    temptf = toDoubleArray(tessN.getLocalTransform().toFloatArray());
    ground = physicsEng.addStaticPlaneObject(physicObjects.get(1),
    temptf, up, 0.0f);
    tessN.setLocalPosition(0, -7000, -2);
    tessN.scale(20f, 20f, 20f);

    tessN.setPhysicsObject(ground);
    // can also set damping, friction, etc.
  //  System.out.println(physicObjects.toString());

    }
    
    

    private float[] toFloatArray(double[] arr)
    { if (arr == null) return null;
    int n = arr.length;
    float[] ret = new float[n];
    for (int i = 0; i < n; i++)
    { ret[i] = (float)arr[i];
    }
    return ret;
    }
    
    
    private double[] toDoubleArray(float[] arr)
    { if (arr == null) return null;
    int n = arr.length;
    double[] ret = new double[n];
    for (int i = 0; i < n; i++)
    { ret[i] = (double)arr[i];
    }
    return ret;
    }
    
    
    
    
    
    
    
    
    
	private String staticIP() throws UnknownHostException {

		
		 InetAddress.getLocalHost().getHostAddress();
		
		return  InetAddress.getLocalHost().getHostAddress();
	}


	@Override
	protected void setupWindow(RenderSystem rs, GraphicsEnvironment ge) {
		GraphicsDevice gd = ge.getDefaultScreenDevice();
	
		
		
		
		DisplaySettingsDialog dsd = new DisplaySettingsDialog(ge.getDefaultScreenDevice());
		dsd.showIt();
		
		//does player want to set up networking server?
	mode = dsd.getPlayerMode();
	model =dsd.getModel();//truck is false, car is true(boolean model)
		
		
		
		
		RenderWindow rw = rs.createRenderWindow(dsd.getSelectedDisplayMode(),
		dsd.isFullScreenModeSelected());
		
		//rs.createRenderWindow(new DisplayMode(1000, 700, 24, 60), false);
	}

    @Override
    protected void setupCameras(SceneManager sm, RenderWindow rw) {
    	
    	
    	
    	
    	
    	 SceneNode rootNode = sm.getRootSceneNode();//created sceneroot node
        
        camera = sm.createCamera("MainCamera", Projection.PERSPECTIVE); //created a camera
        rw.getViewport(0).setCamera(camera); //set camera to this main camera
		//set orientation of main camera
		camera.setRt((Vector3f)Vector3f.createFrom(1.0f, 0.0f, 0.0f));
		camera.setUp((Vector3f)Vector3f.createFrom(0.0f, 1.0f, 0.0f));
		camera.setFd((Vector3f)Vector3f.createFrom(0.0f, 0.0f, -1.0f));
		
		//set position of main camera in world
		camera.setPo((Vector3f)Vector3f.createFrom(0.0f, 0.0f, 0.0f));
        SceneNode cameraNode = rootNode.createChildSceneNode(camera.getName() + "Node"); //cameraNode is a child SceneNode from root SceneNode with main camera
        cameraNode.attachObject(camera);
        
        
       
    }
	

   
  
    
    public void updateVertical(PhysicsObject dolphin) throws IOException {
    	
    	
    		 //prep the height map
    		 SceneNode tessN =this.getEngine().getSceneManager().getSceneNode("tessN");
    		 Tessellation tessE = ((Tessellation) tessN.getAttachedObject("tessE"));

    		 //get current physics object position from transform
    		 //currentP will contain current physics object location x,y,z in last column
    		 double [] currentP = dolphin.getTransform();
    		 //temp will contain each value of current position x,y,z for physics object
    		 double temp;
    
    		 // use  physic object last column coordinates to get coordinates for height from map

    		temp=   dolphin.getTransform()[12]; 
    		currentP[12]=temp;
    		temp=   dolphin.getTransform()[13]; //we just want this value to change!! that is, the y value of location of physics object
    		currentP[13]=temp;
    		temp=   dolphin.getTransform()[14]; 
    		currentP[14]=temp;
    		
    		//p will contain the current physics object location in float form, cuz to get tesselation current y value requires physics object location to be a float, and transform for physics object location returns a double
    		float [] p;
    	p=	toFloatArray(currentP);
    	
    		 
    	
    	//this is the new height value (y) for physics object
   
    float y =	tessE.getWorldHeight(p[12],p[14]);
    	
  
    
    currentP = toDoubleArray(p);
    	
    //set up crash
    if(y>=.1) {
    	
    	//System.out.println("death");
    	//method to crash and respawn
    	
    	respawn();
    	
    }

    }


    
    
    
    public void light() {
    
    	
    	
    	if(!light) {
            flight.setDiffuse(new Color(255, 255, 0));
            light= true;
    	}
    	
    	
    	else if(light){
    		
            flight.setDiffuse(new Color(.7f, .7f, .7f));

    		light= false;
    		
    		
    	
    		
    		
    		
    	}
    
    
    
    
    }
    
    
    
    
    
    
    
    
    public void respawn() throws IOException {
 System.out.println("Respawn");
    	

 //destroy the scenenode and physics object, and then recreate and attach ??
    	SceneManager sm =this.getEngine().getSceneManager();
    	


 
 
 
 //destroy objects
 sm.getSceneNode("myDolphin").detachAllObjects();

 sm.getSceneNode("myDolphinNode").detachAllObjects();
 sm.getSceneNode("flightNode").detachAllObjects();

 sm.destroySceneNode("flightNode");
 sm.destroySceneNode("myDolphin");
 sm.destroySceneNode("myDolphinNode");

 
 sm.destroyEntity("myDolphin");
 
 physicsEng.removeObject(0);
 
 //recreate objects
 
 //recreate entity and dolphinNode
	//set up dolphin for main character
 
 
 if(model) { 
	 dolphinE = sm.createEntity("myDolphin", "raceCar.obj");

 }
 else if(!model) {
 
	 dolphinE = sm.createEntity("myDolphin", "myTruck.obj");
 
 
 }

 dolphinE.setPrimitive(Primitive.TRIANGLES);
 
 dolphinN = sm.getRootSceneNode().createChildSceneNode(dolphinE.getName() + "Node");

 
 dolphinN.setLocalPosition(curCheck.getLocalPosition());
 dolphinN.setLocalRotation(curCheck.getLocalRotation()); 
 dolphinN.attachObject(dolphinE);
 dolphinN.setLocalScale(.02f, .02f, .02f);


 //camera onto dolphin
 /////////////////////////////////////////
 dolphinN.attachObject(camera);
dn= dolphinN.createChildSceneNode("myDolphin");

dn.moveBackward(10f);
dn.moveUp(5f);
dn.attachObject(camera);
camera.setMode('n');
 
 
 //set texture




if(model) { 
	 TextureManager dolph = this.getEngine().getTextureManager();
	 Texture texCyl = dolph.getAssetByPath("car.png");
	 RenderSystem rend = sm.getRenderSystem();
	 TextureState Rate = (TextureState)
	 rend.createRenderState(RenderState.Type.TEXTURE);
	 Rate.setTexture(texCyl);
	 dolphinE.setRenderState(Rate);


}
else {

	TextureManager dolph = this.getEngine().getTextureManager();
	Texture texCyl = dolph.getAssetByPath("myTruck.png");
	RenderSystem rend = sm.getRenderSystem();
	TextureState Rate = (TextureState)
	rend.createRenderState(RenderState.Type.TEXTURE);
	Rate.setTexture(texCyl);
	dolphinE.setRenderState(Rate);
	dolphinN.scale(.1f, .1f, .1f);
	dn.moveBackward(25f);
	dn.moveUp(5f);


}


 
 //recreate physics object
 float mass = 1.0f;

 double[] temptf;
 temptf = toDoubleArray(dolphinN.getLocalTransform().toFloatArray());
 										//id,amount of mass, position, radius/size
 dolphinP = physicsEng.addSphereObject(physicsEng.nextUID(),
 mass, temptf, .09f);

 dolphinN.setPhysicsObject(dolphinP);

 
 
 
 
 
 
 
 
float []a = {0f,0f,0f};
    dolphinP.setLinearVelocity(a);
    
    
    

	SceneNode flightNode = sm.getSceneNode("myDolphinNode").createChildSceneNode("flightNode");
    flightNode.attachObject(flight);
    
    }


	@Override
    protected void setupScene(Engine eng, SceneManager sm) throws IOException {

		

		
		
	
		
		
		
    	//setup checkpoints
    SceneNode root	= this.getEngine().getSceneManager().getRootSceneNode();
     check1 = root.createChildSceneNode("check1");
     check2 = root.createChildSceneNode("check2");
     check3 = root.createChildSceneNode("check3");
     check4 = root.createChildSceneNode("check4");
     check5 = root.createChildSceneNode("check5");
     check6 = root.createChildSceneNode("check6");
     check7 = root.createChildSceneNode("check7");
     check8 = root.createChildSceneNode("check8");
     check9 = root.createChildSceneNode("check9");
     check10 = root.createChildSceneNode("check10"); 
     check11 = root.createChildSceneNode("check11");
     check12 = root.createChildSceneNode("check12");
     check13 = root.createChildSceneNode("check13");
     check14 = root.createChildSceneNode("check14");
     check15 = root.createChildSceneNode("check15");
     check16 = root.createChildSceneNode("check16");
     check17 = root.createChildSceneNode("check17");
     curCheck = root.createChildSceneNode("currentPoint");
//checkpoint positions
      check1.setLocalPosition(4.007412f, 0.089992195f, 1.3694372f);
      check2.setLocalPosition(3.5161817f, 0.08982253f, -0.3711056f);
      check3.setLocalPosition(5.5075955f, 0.08999376f, -0.3296265f);
      check4.setLocalPosition(6.704195f, 0.089722715f, -2.421044f);
      check5.setLocalPosition(7.8425713f, 0.08999997f, -4.3519154f);
      check6.setLocalPosition(6.384251f, 0.08999837f, -5.89266f);
      check7.setLocalPosition(4.736072f, 0.08999744f, -4.8181167f);
      check8.setLocalPosition(3.531605f, 0.08999958f, -5.1981087f);
      check9.setLocalPosition(0.9580379f, 0.08988643f, -8.764926f);
      check10.setLocalPosition(-1.4313705f, 0.08999973f, -8.176576f);
      check11.setLocalPosition(-3.1481874f, 0.08999973f, -6.915914f);
      check12.setLocalPosition(-5.256122f, 0.08982254f, -3.3473806f);
      check13.setLocalPosition(-5.889157f, 0.08994185f, 0.71382356f);
      check14.setLocalPosition(-3.590757f, 0.08999838f, 4.987125f);
      check15.setLocalPosition(-5.247202f, 0.08977817f, 7.8770914f);
      check16.setLocalPosition(-4.2893553f, 0.0899922f, 7.394408f);
      check17.setLocalPosition(1.1328907f, 0.08999998f, 2.9525113f);

    	
    	
      
      //set up rotations
      Vector3 u= Vector3f.createFrom(-0.86602306f, 0.0f, 0.49999887f);
      Vector3 v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      Vector3 n= Vector3f.createFrom(-0.49999887f, 0.0f, -0.86602306f);
      Matrix3 a = Matrix3f.createFrom(u,v,n);
      check1.setLocalRotation(a);
      
      u= Vector3f.createFrom(0.406735f, 0.0f, -0.91354215f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(0.91354215f, 0.0f, 0.406735f);
      a=   Matrix3f.createFrom(u,v,n);   
      check2.setLocalRotation(a);
      
      u= Vector3f.createFrom(-0.8386661f, 0.0f, -0.5446357f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(0.5446357f, 0.0f, -0.8386661f);
      a=   Matrix3f.createFrom(u,v,n);   
      check3.setLocalRotation(a);
      
      u= Vector3f.createFrom(-0.62931573f, 0.0f, -0.77713984f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(0.77713984f, 0.0f, -0.62931573f);
      a=   Matrix3f.createFrom(u,v,n);   
      check4.setLocalRotation(a);
      
      u= Vector3f.createFrom(-0.9335715f, 0.0f, 0.35836482f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.35836482f, 0.0f, -0.9335715f);
      a=   Matrix3f.createFrom(u,v,n);   
      check5.setLocalRotation(a);
      
      u= Vector3f.createFrom(0.96591383f, 0.0f, 0.2588151f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.2588151f, 0.0f, 0.96591383f);
      a=   Matrix3f.createFrom(u,v,n);   
      check6.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(-0.80900484f, 0.0f, -0.58777565f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(0.58777565f, 0.0f, -0.80900484f);
      a=   Matrix3f.createFrom(u,v,n);   
      check7.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(-0.7070929f, 0.0f, 0.7070944f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.7070944f, 0.0f, -0.7070929f);
      a=   Matrix3f.createFrom(u,v,n);   
      check8.setLocalRotation(a);
      
      u= Vector3f.createFrom(0.35836107f, 0.0f, 0.9335588f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.9335588f, 0.0f, 0.35836107f);
      a=   Matrix3f.createFrom(u,v,n);   
      check9.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(.0000014752f, 0.0f, 0.9999745f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.9999745f, 0.0f, .000001475215f);
      a=   Matrix3f.createFrom(u,v,n);   
      check10.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(0.9781172f, 0.0f, 0.20790392f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.20790392f, 0.0f, 0.9781172f);
      a=   Matrix3f.createFrom(u,v,n);   
      check11.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(0.62930053f, 0.0f, 0.77712005f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.77712005f, 0.0f, 0.62930053f);
      a=   Matrix3f.createFrom(u,v,n);   
      check12.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(0.99448776f, 0.0f, 0.10452413f);
      v= Vector3f.createFrom(0.0f, 1.0f, 0.0f);
      n= Vector3f.createFrom(-0.10452413f, 0.0f, 0.99448776f);
      a=   Matrix3f.createFrom(u,v,n);   
      check13.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(-0.2588102f, 0.0f, -0.96588945f);
      n= Vector3f.createFrom(0.96588945f, 0.0f, -0.2588102f);
      a=   Matrix3f.createFrom(u,v,n);   
      check14.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(0.98764414f, 0.0f, 0.1564269f);
      n= Vector3f.createFrom(-0.1564269f, 0.0f, 0.98764414f);
      a=   Matrix3f.createFrom(u,v,n);   
      check15.setLocalRotation(a);
      
      
      u= Vector3f.createFrom(-0.6292893f, 0.0f, -0.7771064f);
      n= Vector3f.createFrom(0.7771064f, 0.0f, -0.6292893f);
      a=   Matrix3f.createFrom(u,v,n);   
      check16.setLocalRotation(a);
      



      u= Vector3f.createFrom(-0.4999715f, 0.0f, -0.8659734f);
      n= Vector3f.createFrom(0.8659734f, 0.0f, -0.4999715f);
      a=   Matrix3f.createFrom(u,v,n);   
      check17.setLocalRotation(a);
     ///////////////////////////////////////////////////// 
//scenenode checks get added to collider for checking current checkpoint
      list.add(check17);
 
      list.add(check1);
        list.add(check2);
        list.add(check3);
        list.add(check4);
        list.add(check5);
        list.add(check6);
        list.add(check7);
        list.add(check8);
        list.add(check9);
        list.add(check10);
        list.add(check11);
        list.add(check12);
        list.add(check13);
        list.add(check14);
        list.add(check15);
        list.add(check16);
        list.add(check17);

      
      ///////////////////////////
      //testing checkpoints visually  
        
        Entity test1 = sm.createEntity("tester1", "dolphinHighPoly.obj");
        Entity test2 = sm.createEntity("tester2", "dolphinHighPoly.obj");
        Entity test3 = sm.createEntity("tester3", "dolphinHighPoly.obj");
        Entity test4 = sm.createEntity("tester4", "dolphinHighPoly.obj");
        Entity test5 = sm.createEntity("tester5", "dolphinHighPoly.obj");
        Entity test6 = sm.createEntity("tester6", "dolphinHighPoly.obj");
        Entity test7 = sm.createEntity("tester7", "dolphinHighPoly.obj");
        Entity test8 = sm.createEntity("tester8", "dolphinHighPoly.obj");
        Entity test9 = sm.createEntity("tester9", "dolphinHighPoly.obj");
        Entity test10 = sm.createEntity("tester10", "dolphinHighPoly.obj");
        Entity test11 = sm.createEntity("tester11", "dolphinHighPoly.obj");
        Entity test12 = sm.createEntity("tester12", "dolphinHighPoly.obj");
        Entity test13 = sm.createEntity("tester13", "dolphinHighPoly.obj");
        Entity test14 = sm.createEntity("tester14", "dolphinHighPoly.obj");
        Entity test15 = sm.createEntity("tester15", "dolphinHighPoly.obj");
        Entity test16 = sm.createEntity("tester16", "dolphinHighPoly.obj");
        Entity test17 = sm.createEntity("tester17", "dolphinHighPoly.obj");

check1.attachObject(test1);

check2.attachObject(test2);
check3.attachObject(test3);
check4.attachObject(test4);
check5.attachObject(test5);
check6.attachObject(test6);
check7.attachObject(test7);
check8.attachObject(test8);
check9.attachObject(test9);
check10.attachObject(test10);
check11.attachObject(test11);
check12.attachObject(test12);
check13.attachObject(test13);
check14.attachObject(test14);
check15.attachObject(test15);
check16.attachObject(test16);
check17.attachObject(test17);

        
        
        
        
        
        
check1.setLocalScale(.1f, .1f, .1f);
check2.setLocalScale(.1f, .1f, .1f);
check3.setLocalScale(.1f, .1f, .1f);
check4.setLocalScale(.1f, .1f, .1f);
check5.setLocalScale(.1f, .1f, .1f);
check6.setLocalScale(.1f, .1f, .1f);
check7.setLocalScale(.1f, .1f, .1f);
check8.setLocalScale(.1f, .1f, .1f);
check9.setLocalScale(.1f, .1f, .1f);
check10.setLocalScale(.1f, .1f, .1f);
check11.setLocalScale(.1f, .1f, .1f);
check12.setLocalScale(.1f, .1f, .1f);
check13.setLocalScale(.1f, .1f, .1f);
check14.setLocalScale(.1f, .1f, .1f);
check15.setLocalScale(.1f, .1f, .1f);
check16.setLocalScale(.1f, .1f, .1f);
check17.setLocalScale(.1f, .1f, .1f);
///////////////////////////////////////////////////////////////



E = sm.createSkeletalEntity("rcAv", "avatar3.rkm", "avatar3.rks");	


////////////////////////        
        
        
        //tesselation for terrain
    	
		Tessellation tessE = sm.createTessellation("tessE", 6);
		tessE.setSubdivisions(0f);
		 tessN =sm.getRootSceneNode().createChildSceneNode("tessN");
   tessN.attachObject(tessE);
	tessE.setHeightMap(this.getEngine(), "nurburgring_color.jpg");
	tessE.setTexture(this.getEngine(), "nurburgring_color.jpg");
	
//		setup the path to the SKYBOX IMAGES----------------------------------|
		

	
	Configuration conf   = eng.getConfiguration();
	TextureManager tm    = getEngine().getTextureManager();
	
	tm.setBaseDirectoryPath(conf.valueOf("assets.skyboxes.path"));
		
		Texture left   = tm.getAssetByPath( "left.jpg" );
		Texture right    = tm.getAssetByPath( "right.jpg" );
		Texture front    = tm.getAssetByPath( "front.jpg" );
		Texture back   = tm.getAssetByPath( "back.jpg" );
		Texture top     = tm.getAssetByPath( "top.jpg" );
		Texture bottom  = tm.getAssetByPath( "bottom.jpg" );
		
//		setup the path to the SKYBOX TEXTURES---------------------------------|
	    tm.setBaseDirectoryPath(conf.valueOf("assets.textures.path"));

		AffineTransform xform = new AffineTransform();
		
		xform.translate(0, front.getImage().getHeight());
		xform.scale(1d, -1d);
		
		front.transform (xform);
		back.transform  (xform);
		left.transform  (xform);
		right.transform (xform);
		top.transform   (xform);
		bottom.transform(xform);

//		actually create the skybox here, then assign its faces----------------|
		SkyBox sb = sm.createSkyBox (SKYBOX_NAME);  
		sb.setTexture(front,  SkyBox.Face. FRONT);
		sb.setTexture(back,   SkyBox.Face.  BACK);
		sb.setTexture(left,   SkyBox.Face.  RIGHT);
		sb.setTexture(right,  SkyBox.Face. LEFT);
		sb.setTexture(top,    SkyBox.Face.   TOP);
		sb.setTexture(bottom, SkyBox.Face.BOTTOM);
		sm.setActiveSkyBox((ray.rage.scene.SkyBox) sb);

    	//set up dolphin for main character
		if(model) { dolphinE = sm.createEntity("myDolphin", "raceCar.obj");
		}else {
		 dolphinE = sm.createEntity("myDolphin", "myTruck.obj");}
        dolphinE.setPrimitive(Primitive.TRIANGLES);
        
        dolphinN = sm.getRootSceneNode().createChildSceneNode(dolphinE.getName() + "Node");
        dolphinN.setLocalPosition(1.265f, 0.09f, 2.86327f);
        
        
        
        
         u= Vector3f.createFrom(-0.54463816f,0.0f,-0.83866924f);
         v= Vector3f.createFrom(0.0f,1.0f,0.0f);
         n= Vector3f.createFrom(.83866924f,0.0f,-0.54463816f);

        
        
        
         a = Matrix3f.createFrom(u,v,n);
        
        
        
        dolphinN.setLocalRotation(a);
        dolphinN.setLocalScale(.02f, .02f, .02f);

        dolphinN.attachObject(dolphinE);
	
        //camera onto dolphin
        /////////////////////////////////////////
        dolphinN.attachObject(camera);
   dn= dolphinN.createChildSceneNode("myDolphin");
   
   dn.moveBackward(10f);
   dn.moveUp(5f);
   dn.attachObject(camera);
   camera.setMode('n');
        
//set up planets aka spheres
        Entity planet1 = sm.createEntity("myPlanet1", "sphere.obj");
        Entity planet2 = sm.createEntity("myPlanet2", "sphere.obj");
        Entity planet3 = sm.createEntity("myPlanet3", "sphere.obj");

        planet1.setPrimitive(Primitive.TRIANGLES);
        planet2.setPrimitive(Primitive.TRIANGLES);
        planet3.setPrimitive(Primitive.TRIANGLES);

         p1 = sm.getRootSceneNode().createChildSceneNode(planet1.getName()+"Node");
         p2 = sm.getRootSceneNode().createChildSceneNode(planet2.getName()+"Node");
         p3 = sm.getRootSceneNode().createChildSceneNode(planet3.getName()+"Node");
      
        
        
        //random size planets/sphere
        
        Vector3f t = (Vector3f) Vector3f.createFrom(8f, 1f, -7f);
        Vector3f op = (Vector3f) t.add((Vector3)p1.getLocalPosition());

        p1.setLocalPosition(op);
        
        
        
         t = (Vector3f) Vector3f.createFrom(7f, 1f, -1f);
         op = (Vector3f) t.add((Vector3)p2.getLocalPosition());

        p2.setLocalPosition(op);        
        
       
         t = (Vector3f) Vector3f.createFrom(8f, 1f, -4f);
         op = (Vector3f) t.add((Vector3)p3.getLocalPosition());

        p3.setLocalPosition(op);
 
        p1.attachObject(planet1);
        p2.attachObject(planet2);
        p3.attachObject(planet3);
        p1.scale(.5f, .5f, .5f);
        p2.scale(.5f, .5f, .5f);
        p3.scale(.5f, .5f, .5f);

dolph = new TimerController(this);

sm.addController(dolph);



        //set up lights
        sm.getAmbientLight().setIntensity(new Color(1, 1, 1)); //good setting for brightness
		//        sm.getAmbientLight().setIntensity(new Color(1f, 1f, 1f)); //very bright, as it gets

		Light plight = sm.createLight("testLamp1", Light.Type.POINT);
		//plight.setAmbient(new Color(.3f, .3f, .3f));
        plight.setDiffuse(new Color(255, 0, 0));//
		plight.setSpecular(new Color(1.0f, 1.0f, 1.0f));

        //plight.setSpecular(new Color(.10f, .10f, .10f));
        plight.setRange(10f);
		
		SceneNode plightNode = p1.createChildSceneNode("plightNode");
       
	plightNode.moveDown(5f);
	plightNode.attachObject(plight);
	 //  p1.attachObject(plightNode);
        
        
        
        
        
        //light on dolphin
        
        
        
        flight = sm.createLight("testLamp2", Light.Type.POINT);
		flight.setAmbient(new Color(.3f, .3f, .3f));//object without light, color
        flight.setDiffuse(new Color(255, 255, 0));//
		flight.setSpecular(new Color(1.0f, 1.0f, 1.0f));
        flight.setRange(1f);
		
		SceneNode flightNode = sm.getSceneNode("myDolphinNode").createChildSceneNode("flightNode");
        flightNode.attachObject(flight);
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /////////////////////////////////////////////////////////////
        

if(model) { 
	 TextureManager dolph = this.getEngine().getTextureManager();
	 Texture texCyl = dolph.getAssetByPath("car.png");
	 RenderSystem rend = sm.getRenderSystem();
	 TextureState Rate = (TextureState)
	 rend.createRenderState(RenderState.Type.TEXTURE);
	 Rate.setTexture(texCyl);
	 dolphinE.setRenderState(Rate);


}
else {

	TextureManager dolph = this.getEngine().getTextureManager();
	Texture texCyl = dolph.getAssetByPath("myTruck.png");
	RenderSystem rend = sm.getRenderSystem();
	TextureState Rate = (TextureState)
	rend.createRenderState(RenderState.Type.TEXTURE);
	Rate.setTexture(texCyl);
	dolphinE.setRenderState(Rate);
	dolphinN.scale(.1f, .1f, .1f);
	dn.moveBackward(25f);
	dn.moveUp(5f);


}

        
        
        
        
        //planet1-3 texturing
        TextureManager zm = eng.getTextureManager();
        Texture redTexture = zm.getAssetByPath("moon.jpeg");
        RenderSystem rt = sm.getRenderSystem();
        TextureState ate = (TextureState)
        rt.createRenderState(RenderState.Type.TEXTURE);
        ate.setTexture(redTexture);
        planet1.setRenderState(ate);
        planet2.setRenderState(ate);
        planet3.setRenderState(ate);
        
        
        //////////////////////////////////////////////
        //scripting
        
        ScriptEngineManager factory = new ScriptEngineManager();
      //  java.util.List<ScriptEngineFactory> list = factory.getEngineFactories();
       ScriptEngine jsEngine = factory.getEngineByName("js");
        // use spin speed setting from the first script to initialize dolphin rotation
       String scriptFile1 = "InitParams.js";
        this.executeScript(jsEngine,scriptFile1);
        last = this.getEngine().getElapsedTimeMillis();
      //  File scriptFile = new File("InitParams.js");
       //   this.runScript(scriptFile);
        
        
        
        rc = new RotationController(Vector3f.createUnitVectorY(),((Double)(jsEngine.get("spinSpeed"))).floatValue());
        rc.addNode(check17);
        sm.addController(rc);
        
        
        
        
        
      ///////////////////////////////////////////////////  
        //setupinputs for jinput
    	setupInputs(); // new function (defined below) to set up input actions
    	//initialize the physics world
            	initPhysicsSystem();
            	createRagePhysicsWorld();
        		initAudio(sm);

    }
	
    
    //connect or disconnect from server
    public void setIsConnected(boolean b){
		isClientConnected = b;
	}
    
    private void executeScript(ScriptEngine engine, String scriptFileName)
    {
    try
    { FileReader fileReader = new FileReader(scriptFileName);
    engine.eval(fileReader); //execute the script statements in the file
    fileReader.close();
    }
    catch (FileNotFoundException e1)
    { System.out.println(scriptFileName + " not found " + e1); }
    catch (IOException e2)
    { System.out.println("IO problem with " + scriptFileName + e2); }
    catch (ScriptException e3)
    { System.out.println("ScriptException in " + scriptFileName + e3); }
    catch (NullPointerException e4)
    { System.out.println ("Null ptr exception in " + scriptFileName + e4); }
    }
    
    
    
    protected void setupNetworking()
    {
    	//array of other players through their ids to remove
    	gameObjectsToRemove = new Vector<UUID>();
    	//set connection to true
    	//isClientConnected = true;
    	try {
    		
    		
    		//Use this method if you want to your own server!
    		
    		///////////////////////////////////
    		//serverAddress = staticIP();
    		//////////////////////////////////
    		
    		System.out.println(InetAddress.getByName(serverAddress).toString()+"  " +serverPort +"  " + serverProtocol);

    		protClient = new ProtocolClient(InetAddress.getByName(serverAddress), serverPort, serverProtocol, this);
    	} catch (UnknownHostException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	if(protClient == null){
    		System.out.println("missing protocol host");
    	} else {
    		System.out.println("creating packet to join");
    		protClient.sendJoinMessage();
    	}
    }
    
    
    
    //method for this player to leave the game
    //notify server/other players you are leaving
    private class SendCloseConnectionPacketAction extends AbstractInputAction
	{ // for leaving the game... need to attach to an input device
		@Override
		public void performAction(float time, Event evt)
		{ 
			if(protClient != null && isClientConnected == true)
			{ 
				protClient.sendByeMessage();
				isClientConnected=false;
			} 
		} 
	}
    
    public void setEarParameters(SceneManager sm)
    { SceneNode dolphinNode = sm.getSceneNode("myDolphinNode");
    Vector3 avDir = dolphinNode.getWorldForwardAxis();
    // note - should get the camera's forward direction
    // - avatar direction plus azimuth
    audioMgr.getEar().setLocation(dolphinNode.getWorldPosition());
    audioMgr.getEar().setOrientation(avDir, Vector3f.createFrom(0,1,0));
    }
    
    public Vector3 getPlayerPosition()
	{ 
		//SceneNode avatarN = sm.getSceneNode("myDolphinNode");
		return dolphinN.getWorldPosition();
	}
    
    
	
	  public Matrix3 getPlayerRotation()
	{ 
		//SceneNode avatarN = sm.getSceneNode("myDolphinNode");
		return dolphinN.getWorldRotation();
	}
	  
	  
	
	  public void walkAction()
		{
			SkeletalEntity avatarE = (SkeletalEntity) this.getEngine().getSceneManager().getEntity("rcAv");
			
			avatarE.stopAnimation();
			avatarE.playAnimation("walkingAnimation", 5.0f, LOOP, 0);
		}
	  
	  
	  public void turnRightAnimation(){
	
		  System.out.println("doing animation");
		  SkeletalEntity rcEntity = (SkeletalEntity)this.getEngine().getSceneManager().getEntity("rcAv");
		  rcEntity.stopAnimation();
		  // 	playAnimation(java.lang.String animName, float animSpeed, SkeletalEntity.EndType endType, int endTypeCount) 
		  rcEntity.playAnimation("turnRAnimation",5.0f,LOOP,0);
}
	
	
    
    
    public void processNetworking(float elapsTime)
    {
    	if (protClient != null)
        {
            protClient.processPackets();
        }
        Iterator<UUID> iterator = gameObjectsToRemove.iterator();
        while(iterator.hasNext())
        {
            sm.destroySceneNode(iterator.next().toString());
        }
        gameObjectsToRemove.clear();
    }
    

	public void addGhostAvatarToGameWorld(GhostAvatar avatar) throws IOException
	{
		if (avatar != null)
		{
			
			effect++;
			SceneManager sm = dolphinN.getManager();
			 Entity ghostE;
			

			 
			 if(avatar.getModel()) { 
				  ghostE = sm.createEntity("ghost"+effect, "raceCar.obj");

			 }
			 else {
			 
				  ghostE = sm.createEntity("ghost"+effect, "myTruck.obj");

			 
			 }
	
			
			System.out.println("created ghost");

			// Entity ghostE = sm.createEntity(avatar.getID().toString(), "dolphinHighPoly.obj");
			ghostE.setPrimitive(Primitive.TRIANGLES);
			 ghostN = sm.getRootSceneNode().createChildSceneNode(avatar.getID().toString() + "Node");

			
			ghostN.attachObject(ghostE);
		//	ghostN.setLocalPosition(0f, 0f, 0f);
			avatar.setNode(ghostN);
			avatar.setEntity(ghostE);
			avatar.setPosition(ghostN.getLocalPosition());

			if(avatar.getModel()) { 
				 TextureManager dolph = this.getEngine().getTextureManager();
				 Texture texCyl = dolph.getAssetByPath("car.png");
				 RenderSystem rend = sm.getRenderSystem();
				 TextureState Rate = (TextureState)
				 rend.createRenderState(RenderState.Type.TEXTURE);
				 Rate.setTexture(texCyl);
				 ghostE.setRenderState(Rate);
				 ghostN.scale(.01f, .01f, .01f);

			}
			else {

				TextureManager dolph = this.getEngine().getTextureManager();
				Texture texCyl = dolph.getAssetByPath("myTruck.png");
				RenderSystem rend = sm.getRenderSystem();
				TextureState Rate = (TextureState)
				rend.createRenderState(RenderState.Type.TEXTURE);
				Rate.setTexture(texCyl);
				ghostE.setRenderState(Rate);
				ghostN.scale(.01f, .01f, .01f);

			}
		}
	}
	//other player
	public void moveGhostAvatarAroundGameWorld(GhostAvatar avatar, Vector3 pos)
	
	{
		avatar.getSceneNode().setLocalPosition(pos);
	}
	
	
	
	
	public void rotateGhostAvatarAroundGameWorld(GhostAvatar avatar, Matrix3 pos)
	
	{
		avatar.getSceneNode().setLocalRotation(pos);
	}
	

	//if another player leaves, get their ghostavatar, add to the remove list
	public void removeGhostAvatarFromGameWorld(GhostAvatar ghostID)
	{
		if (ghostID != null)
		{
			gameObjectsToRemove.add(ghostID.getID());
	    }	
	}
	
	
	private double distance(SceneNode scene, SceneNode camera) {
    	
    	Vector3f scenePo = (Vector3f)scene.getLocalPosition();

		Vector3f camPo = (Vector3f)camera.getLocalPosition();

		
		//camera vector x,y,z
		double	dx = (double)camPo.x(); 
		double	dy = (double)camPo.y();
		double	dz = (double)camPo.z();
		//scenenode vector x,y,z
		double	ex = (double)scenePo.x(); 
		double	ey = (double)scenePo.y();
		double	ez = (double)scenePo.z();
		
		double distance1=(double)Math.sqrt(  (Math.pow((dx-ex),2)) +  (Math.pow((dy-ey),2)) + (Math.pow(dz-ez,2)) ) ;//planet1 to camera
		return distance1;
    }

    private void collider(){
    	SceneNode temp;
    SceneNode	dolphinN = this.getEngine().getSceneManager().getSceneNode("myDolphinNode");
	curCheck=	this.getEngine().getSceneManager().getSceneNode("currentPoint");

	//one scenenode for checkpoint, it will transition from one permanent checkpoint to the next
	
	
    	for(int i =0; i<list.size(); i++) {
    		temp=list.get(i);
    		if(distance(temp,dolphinN) < .3 && list.get(0)==temp) {
    		list.remove(temp);
    		curCheck.setLocalPosition(temp.getLocalPosition());
    		curCheck.setLocalRotation(temp.getLocalRotation());
    			System.out.println("CheckPoint: "+temp.getName());
    			
    			
    			if(temp==check17) {ch++;}
    			

    		}

    		if(ch==3) {ch=1;
    			System.out.println("reset"+ch);

    			list.add(check17);
    			list.add(check1);
    			list.add(check2);
    			list.add(check3);
    			list.add(check4);
    			list.add(check5);
    			list.add(check6);
    			list.add(check7);
    			list.add(check8);
    			list.add(check9);
    			list.add(check10);
    			list.add(check11);
    			list.add(check12);
    			list.add(check13);
    			list.add(check14);
    			list.add(check15);
    			list.add(check16);
    			list.add(check17);
    			count++;
    			
    			try {
					protClient.sendTime(elapsTimeStr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
        		
    		}
    		
    		
    		
    	}
    	
    	
    	
    }
    
    private void initAudio(SceneManager sm)
    {
    	AudioResource resource1;
    	AudioResource resource2;
      //  System.out.println("yesssssssssssssssss");

    	audioMgr = AudioManagerFactory.createAudioManager("ray.audio.joal.JOALAudioManager");
    	

    if (!audioMgr.initialize())
    {
    	System.out.println("Audio Manager failed to initialize!");
   
    	return;
    }
    
    
    
    //resource 1 will have 3d sound effect
    resource1 = audioMgr.createAudioResource("dolphin.wav", AudioResourceType.AUDIO_SAMPLE);
    //resource 2 will be constantly playing and looping
    resource2 = audioMgr.createAudioResource("bensound-summer.wav",AudioResourceType.AUDIO_SAMPLE);
    hereSound = new Sound(resource1,SoundType.SOUND_EFFECT, 100, true);
    oceanSound = new Sound(resource2,SoundType.SOUND_EFFECT, 100, true);
    
    hereSound.initialize(audioMgr);

    oceanSound.initialize(audioMgr);
    hereSound.setMaxDistance(10.0f);
    hereSound.setMinDistance(0.5f);
    hereSound.setRollOff(5.0f);
    oceanSound.setMaxDistance(10.0f);
    oceanSound.setMinDistance(0.5f);
    oceanSound.setRollOff(5.0f);
    SceneNode robotN = sm.getSceneNode("myDolphinNode");
    SceneNode earthN = sm.getSceneNode("check17");
    hereSound.setLocation(robotN.getWorldPosition());
    oceanSound.setLocation(earthN.getWorldPosition());
    setEarParameters(sm);
    hereSound.play();
    oceanSound.play();
    }
    
    
    
    //when method is called, it will instantly trace players direction and location for npc to follow
    //method is not currently called, further progress will require a boolean (true) for when game starts and boolean (false) when lap finished
    //this can be done with update() loop for checking the 17 checkpoints, if all completed
 public void traceMyLap() {
	 
	 
		
		ArrayList<String> cord=new ArrayList<String>(); 
		
		
		
		//add position
		cord.add( Arrays.toString(dolphinN.getLocalPosition().toFloatArray()));

		String file_name = System.getProperty("user.dir")+"\\Networking\\ok.txt"; 

		
		
		
		//add x axis
		cord.add(Arrays.toString(dolphinN.getLocalRightAxis().toFloatArray()));


		
		//y  value
		cord.add(Arrays.toString(dolphinN.getLocalUpAxis().toFloatArray()));


cord.add(Arrays.toString(dolphinN.getLocalForwardAxis().toFloatArray()));
try {

WriteFile data = new WriteFile(file_name,true);	

data.writeToFile(Arrays.toString(cord.toArray()));

}
catch(IOException e) {

System.out.println(e.getMessage());
}

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
 }
    @Override
    protected void update(Engine engine) {
    	collider();
    	setSpeed();
    	SceneManager sm = engine.getSceneManager();

    	SceneNode robotN = this.getEngine().getSceneManager().getSceneNode("check17");
    	SceneNode earthN = sm.getSceneNode("myDolphinNode");
    	

    	hereSound.setLocation(robotN.getWorldPosition());
    	oceanSound.setLocation(earthN.getWorldPosition());
    	setEarParameters(sm);
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
		// build and set HUD
		rs = (GL4RenderSystem) engine.getRenderSystem();
		elapsTime += engine.getElapsedTimeMillis();
		elapsTimeSec = Math.round(elapsTime/1000.0f);
		elapsTimeStr = Integer.toString(elapsTimeSec);
		dispStr = "Time = " + elapsTimeStr + " Laps = " + count+" Speed: "+speed;
		rs.setHUD(dispStr, 15, 15);//dimension of hud

		processNetworking(elapsTime);
		
		im.update(elapsTime);

	//////////////////////////////////////////////////////////	
		
		float time = engine.getElapsedTimeMillis();
	    Matrix4 mat;
	 
	    physicsEng.update(time);
	   
	    //set the scenenodes positions to its physics engine
	    for (SceneNode s : engine.getSceneManager().getSceneNodes())
	    { if (s.getPhysicsObject() != null)
	    { mat = Matrix4f.createFrom(toFloatArray( s.getPhysicsObject().getTransform()));
	   
	    s.setLocalPosition(mat.value(0,3),mat.value(1,3),mat.value(2,3));

	    } 
	    
	    } 
		
	    try {
			updateVertical(this.getEngine().getSceneManager().getSceneNode("myDolphinNode").getPhysicsObject());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		
		
	  
		protClient.sendRotateMessage(dolphinN.getLocalRotation());
		protClient.sendMoveMessage(  dolphinN.getWorldPosition());


	    		SkeletalEntity rcEntity = (SkeletalEntity)engine.getSceneManager().getEntity("rcAv");
	    		rcEntity.update();
		//for npc coordinates for going through track
	    		
	    		
		
	    		
	    		
	    		   ScriptEngineManager factory = new ScriptEngineManager();
      //  java.util.List<ScriptEngineFactory> list = factory.getEngineFactories();
       ScriptEngine jsEngine = factory.getEngineByName("js");
        // use spin speed setting from the first script to initialize dolphin rotation
       String scriptFile1 = "InitParams.js";
        this.executeScript(jsEngine,scriptFile1);
        last = this.getEngine().getElapsedTimeMillis();
      //  File scriptFile = new File("InitParams.js");
       //   this.runScript(scriptFile);
        
        
        
rc.setSpeed(((Double)(jsEngine.get("spinSpeed"))).floatValue());
        
        

				
			/*
			 run script update 
			 
			     //  ScriptEngine jsEngine = factory.getEngineByName("js");

				// run script again in update() to demonstrate dynamic modification
				float modTime = last;
				if (modTime+1000 > modTime)
				{ last = modTime;
				
				executeScript(jsEngine,"InitParams.js");

				rc.setSpeed(((Double)(jsEngine.get("spinSpeed"))).floatValue());
				}*/
				
				
	}

    protected void setupInputs()
    {
    	im = new GenericInputManager();
      
    // build some action objects for doing things in response to user input
    	
    	lightC = new LightController(this);

    	printLoc = new Respawn(this);
    	 RY = new ControllerRY(this);	
    	R = new ControllerYaw(this);	
    X = new ControllerX(this);
    Y = new ControllerBackward(this);
    quitGameAction = new QuitGameAction(this,protClient);
   rotLeft = new RotateCamLeft(this, protClient);
    camF = new CamForward(this, protClient);
   camB = new CamBackward(this, protClient);
    camL = new CamLeft(this, protClient);
    camR = new CamRight(this, protClient);
    attach = new AttachCam(this);
    rotRight = new RotateCamRight(this, protClient);
    spinF = new FlipForward(this);
    spinB = new FlipBackward(this);
    controlForward = new ControllerForward(this);
    
    //keyboard input
    ArrayList <Controller> controllers = im.getControllers();
    for (Controller c : controllers)
    { if (c.getType() == Controller.Type.KEYBOARD)
      { 
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.Q,
    	    quitGameAction,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
   
    
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.L,
    	    lightC,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    
//this button is actually reset
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.P,
    	    printLoc,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
     
    
    
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.UP,
    	    moveForward,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
     
    
  
	
    
	

    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.W,
    	    camF,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.S,
    	    camB,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.D,
    	    camR,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.A,
    	    camL,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.UP,
    	    spinF,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    
    
 
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.DOWN,
    	    spinB,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.SPACE,
    	    attach,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.LEFT,
    	    rotLeft,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.RIGHT,
    	    rotRight,
    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    im.associateAction(c,
    	    net.java.games.input.Component.Identifier.Key.SPACE,
    	    attach,
    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
      }
    
    
    
    
    
    
    
    //xbox controller input for each above
    
    else if (c.getType() == Controller.Type.GAMEPAD || c.getType() == Controller.Type.STICK)
     
    {
    	//remember!!!! button N -1 due to index starting at 0
    	// therefore b button on control panel indicates button 2, jinput says its = 1
    	  
    	  
    	  
    	 
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Button._0,
    	    	    controlForward,
    	    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    	   
    	    
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Button._2,
    	    	    attach,
    	    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    	   
    	    
    	    
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Button._1,
    	    	    Y,
    	    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    	    
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Button._3,
    	    	    printLoc,
    	    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Button._9,
    	    	    lightC,
    	    	    InputManager.INPUT_ACTION_TYPE.ON_PRESS_ONLY);
    	    
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Axis.X,
    	    	    R,
    	    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Axis.RY,
    	    	    R,
    	    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    	    im.associateAction(c,
    	    	    net.java.games.input.Component.Identifier.Axis.RY,
    	    	    RY,
    	    	    InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
    	   
    	    
    }
	
    }
	
    
    
    
    
    
    }


	


	public void incrementCounter(int amt) {
		counter = amt; 		
		
	}
    
 public int getCounter() {
	 return counter;
	 
 }

	public void addNPCAvatarToGameWorld(NPCavatar avatar) throws IOException
	{
		if (avatar != null) {


		//start	
SceneManager m = dolphinN.getManager();
 E = (SkeletalEntity) m.getEntity("rcAv");
ghostN = m.getRootSceneNode().createChildSceneNode(avatar.getID()+ "Node");
ghostN.attachObject(E);
avatar.setNode(ghostN);
avatar.setEntity(E);

ghostN.setLocalScale(.02f, .02f, .02f);
ghostN.translate(0,0.5f,0);
ghostN.setLocalPosition(1.465f, 0.09f, 2.86327f);
E.loadAnimation("walkingAnimation", "Walking.rka");
//rcEntity.loadAnimation("turnRAnimation", "racecar.rka");

walkAction();
System.out.println("created npc");
			
		}
	}




	public void moveNPCAvatarAroundGameWorld(NPCavatar ghostAvatar, Vector3 pos, Matrix3f rot) {
	//	System.out.println("moving to "+pos.x()+" "+pos.y()+" "+pos.z());
		//System.out.println("updated npc");
		ghostAvatar.getSceneNode().setLocalPosition(pos);
		ghostAvatar.getSceneNode().setLocalRotation(rot);

		

	}


    
    }
    

