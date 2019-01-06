package sma.actionsBehaviours;

import java.util.ArrayList;
import java.util.Random;

import com.jme3.math.Vector3f;

import env.jme.Situation;
import jade.core.Agent;
import sma.AbstractAgent;
import sma.InterestPoint.Type;

public class ExploreTopBehavior extends ExploreBehavior{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1419914857706391831L;
	private static Random rand = new Random();
	public ExploreTopBehavior(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		super.onTick();
	}
	
	


	/*
	@Override
	boolean setTarget() {
		// TODO Auto-generated method stub
		target = findInterestingNeighbor();
		if(target != null){
			agent.goTo(target);
			agent.lastAction = Situation.EXPLORE_OFF;
		}
		return target != null;
	}*/



	@Override
	Vector3f findInterestingNeighbor(){
		if(rand.nextFloat() <= 0.8){
			return findHighestNeighbor();
		}else{
			ArrayList<Vector3f> points = agent.sphereCast(agent.getSpatial(), AbstractAgent.NEIGHBORHOOD_DISTANCE, AbstractAgent.CLOSE_PRECISION, AbstractAgent.VISION_ANGLE);
			return points.get(rand.nextInt(points.size()));
		}
	}

}
