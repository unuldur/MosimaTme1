package sma.actionsBehaviours;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jpl7.Query;

import env.jme.NewEnv;
import env.jme.Situation;
import jade.core.Agent;
import sma.InterestPoint;
import sma.agents.FinalAgent;

public class MyBehavior extends PrologBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4019253755958914965L;
	private static final boolean tree = true;
	
	public static Class nextMyBehavior;

	public static Situation sitMy;
	
	public MyBehavior(Agent a, long period, String prologFile) {
		super(a, period, prologFile);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onTick() {
		try {
			String prolog = "consult('./ressources/prolog/duel/"+prologFile+"')";
			
			if (!Query.hasSolution(prolog)) {
				System.out.println("Cannot open file " + prolog);
			}
			else {
				sitMy = Situation.getCurrentSituation(agent);
				List<String> behavior = Arrays.asList("explore", "hunt", "attack");
				ArrayList<Object> terms = new ArrayList<Object>();

				for (String b : behavior) {
					terms.clear();
					// Get parameters 
					if (b.equals("explore")) {
						terms.add(sitMy.timeSinceLastShot);
						terms.add(((ExploreBehavior.prlNextOffend)?sitMy.offSize:sitMy.defSize ));
						terms.add(InterestPoint.INFLUENCE_ZONE);
						terms.add(NewEnv.MAX_DISTANCE);
					}
					else if (b.equals("hunt")) {
						terms.add(sitMy.life);
						terms.add(sitMy.timeSinceLastShot);
						terms.add(sitMy.offSize);
						terms.add(sitMy.defSize);
						terms.add(InterestPoint.INFLUENCE_ZONE);
						terms.add(NewEnv.MAX_DISTANCE);
						terms.add(sitMy.enemyInSight);
					}else if(b.equals("attack")){
						//terms.add(sitMy.life);
						terms.add(sitMy.enemyInSight);
						//terms.add(sit.impactProba);
					}
					else { // RETREAT
						terms.add(sitMy.life);
						terms.add(sitMy.timeSinceLastShot);
					}

					String query = prologQuery(b, terms);
					if (Query.hasSolution(query)) {
						//System.out.println("has solution");
						setNextBehavior();

					}
				}
			}
		}catch(Exception e) {
			System.err.println("Behaviour file for Prolog agent not found");
			System.exit(0);
		}
	}

	@Override
	public void setNextBehavior() {
		if (agent.currentBehavior != null && agent.currentBehavior.getClass() == nextMyBehavior) {
			return;
		}

		if (agent.currentBehavior != null) {
			agent.removeBehaviour(agent.currentBehavior);
		}
		
		if ((tree && nextMyBehavior == ExplorerTreeBehavior.class) || (!tree && nextMyBehavior == ExploreTopBehavior.class)) {
			ExploreBehavior ex = tree?new ExplorerTreeBehavior(agent, FinalAgent.PERIOD):new ExploreTopBehavior(agent, FinalAgent.PERIOD);
			agent.addBehaviour(ex);
			agent.currentBehavior = ex;

		} else if (nextMyBehavior == HuntBehavior.class) {
			HuntBehavior h = new HuntBehavior(agent, FinalAgent.PERIOD, tree ? ExplorerTreeBehavior.class: ExploreTopBehavior.class);
			agent.currentBehavior = h;
			agent.addBehaviour(h);

		} else if (nextMyBehavior == Attack.class) {

			Attack a = new Attack(agent, FinalAgent.PERIOD, sitMy.enemy, true);
			agent.currentBehavior = a;
			agent.addBehaviour(a);

		}
	}

	public static void executeExplore() {
		nextMyBehavior = tree? ExplorerTreeBehavior.class : ExploreTopBehavior.class;
	}
	
	public static void executeHunt() {
		//System.out.println("hunt");
		nextMyBehavior = HuntBehavior.class;
	}

	public static void executeAttack() {
		//System.out.println("attack");
		nextMyBehavior = Attack.class;
	}


	public static void executeRetreat() {
		//System.out.println("retreat");
		//nextBehavior = RetreatBehavior.class;
	}
	
	

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("On finish behaviours");
		super.stop();
	}

}
