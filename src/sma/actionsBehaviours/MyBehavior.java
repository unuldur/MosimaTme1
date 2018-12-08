package sma.actionsBehaviours;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import sma.agents.FinalAgent;

public class MyBehavior extends PrologBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4019253755958914965L;

	public MyBehavior(Agent a, long period, String prologFile) {
		super(a, period, prologFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setNextBehavior() {
		if (agent.currentBehavior != null && agent.currentBehavior.getClass() == nextBehavior) {
			return;
		}

		if (agent.currentBehavior != null) {
			agent.removeBehaviour(agent.currentBehavior);
		}

		if (nextBehavior == ExploreTopBehavior.class) {
			ExploreBehavior ex = new ExploreTopBehavior(agent, FinalAgent.PERIOD);
			agent.addBehaviour(ex);
			agent.currentBehavior = ex;

		} else if (nextBehavior == HuntBehavior.class) {
			HuntBehavior h = new HuntBehavior(agent, FinalAgent.PERIOD, ExploreTopBehavior.class);
			agent.currentBehavior = h;
			agent.addBehaviour(h);

		} else if (nextBehavior == Attack.class) {

			Attack a = new Attack(agent, FinalAgent.PERIOD, sit.enemy);
			agent.currentBehavior = a;
			agent.addBehaviour(a);

		}
	}

	public static void executeExplore() {
		System.out.println("exploreTop");
		nextBehavior = ExploreTopBehavior.class;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("On finish behaviours");
		super.stop();
	}

}
