package myGameEngine;
import a3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ray.rage.Engine;
import ray.rage.asset.texture.Texture;
import ray.rage.asset.texture.TextureManager;
import ray.rage.rendersystem.RenderSystem;
import ray.rage.rendersystem.states.RenderState;
import ray.rage.rendersystem.states.TextureState;
import ray.rage.scene.*;
import ray.rage.scene.controllers.*;
import ray.rml.*;


public class TimerController extends AbstractController
{
	
	MyGame g;
	SceneNode dolphinN,target;
	SceneManager secen;
	Engine pop;
	boolean echo = true;
	String p= "";
	float cycle = 5000;
	float time =0;
	Vector3f dolph;
	boolean over=false;
	
	public TimerController(MyGame game) {
		
		
		g = game;
		
	}
	


	@Override
	protected void updateImpl(float elapsedTimeMillis) {
dolphinN = g.dolphinN;
//need to change this to the for loop for all nodes
		Node n = super.controlledNodesList.get(0);

		
			
			time += elapsedTimeMillis;
			 p = n.getName();

			//System.out.println("incrementing");
		if(time > cycle) {
			over=true;
			super.removeNode(p);

		}
		
			//////////////////////////
			
			
			
			

	        
			
		
			
		
		
			
			
			
			
			
			
			
			}
	public float getOver() {
		return time;
		
		
	}

}