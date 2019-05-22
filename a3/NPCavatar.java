package a3;

import java.util.UUID;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SkeletalEntity;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class NPCavatar 
{
	private int id;
	private SceneNode node;
	private SkeletalEntity entity;
	private Vector3 position;
	private Matrix3f rotate;
	
	public NPCavatar(int ghostID, Vector3 pos, Matrix3f rot)
	{
		id = ghostID;
		position = pos;
		rotate = rot;
	}
	public void setNode(SceneNode ghostN)
	{
		node = ghostN;
	}
	public void setEntity(SkeletalEntity ghostE)
	{
		entity = ghostE;
	}
	public void setPosition(Vector3 ghostN)
	{
		node.setLocalPosition(ghostN);
	}
	
	
	
	
	
	public void setRotation(Matrix3 ghostN)
	{
		node.setLocalRotation(ghostN);
	}
	
	
	
	public int getID()
	{
		return id;
	}
	public SceneNode getSceneNode()
	{
		return node;
	}
	public Entity getEntity()
	{
		return entity;
	}
	public Vector3 getPosition()
	{
		return position;
	}
	public Matrix3f getRotation()
	{
		return rotate;
	}
}