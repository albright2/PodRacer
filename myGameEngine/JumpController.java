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


public class JumpController extends AbstractController
{
	SceneNode dolphinN,target;
	SceneManager secen;
	Engine pop;
	boolean echo = true;
	String p= "";
	float cycle = 2000f;
	float time =0;
	float scale =0.03f;
	float direction =1f;
	public JumpController(SceneManager m, Engine en) {
		
		
		secen =m;
		pop=en;
		
	}
	


	@Override
	protected void updateImpl(float elapsedTimeMillis) {
//dolphinN = secen.getSceneNode("dolphinNode");
//need to change this to the for loop for all nodes
		Node n = super.controlledNodesList.get(0);

		{ 
			
			time += elapsedTimeMillis;
		//	 p = n.getName();
		
	//	target = secen.getSceneNode(p);
		
		if(time >cycle) {
			direction= -direction;
			time=0f;
			
			
		}
	n.moveUp(direction*scale);
		}
		
			
			
			
			
			
			
			
			}

}