package a3;

import java.util.UUID;
import ray.rml.Matrix3;
import ray.rml.Matrix3f;
import ray.rage.scene.Entity;
import ray.rage.scene.SceneNode;
import ray.rage.scene.SkeletalEntity;
import ray.rml.Vector3;
import ray.rml.Vector3f;

public class GhostAvatar 
{
	private UUID id;
	private SceneNode node;
	private Entity entity;
	private Vector3 position;
	private Matrix3f rotate;
	private boolean model;
	public GhostAvatar(UUID ghostID, Vector3 pos, Matrix3f rot, boolean a)
	{
		id = ghostID;
		position = pos;
		rotate = rot;
		this.model = a;
	}
	public void setNode(SceneNode ghostN)
	{
		node = ghostN;
	}
	public void setEntity(Entity ghostE)
	{
		entity = ghostE;
	}
	public void setPosition(Vector3 ghostN)
	{
		node.setLocalPosition(ghostN);
	}
	
	
	
	
	
	public void setRotation(Matrix3 ghostN)
	{
		//node.yaw(arg0);
		node.setLocalRotation(ghostN);
	}
	
	
	
	public UUID getID()
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
	public boolean getModel()
	{
		return model;
	}
}