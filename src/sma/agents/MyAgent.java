package sma.agents;

import env.jme.NewEnv;
import sma.actionsBehaviours.MyBehavior;

public class MyAgent extends FinalAgent{
	private static final long serialVersionUID = 5215165765928961044L;

	
	@Override
	void deploiment(){
		final Object[] args = getArguments();
		if(args[0]!=null){
			
			addBehaviour(new MyBehavior(this,PERIOD, "requete2.pl"));
			
			deployAgent((NewEnv) args[0], true);
			
			System.out.println("Agent "+getLocalName()+" deployed !");
			
			
		}else{
			System.err.println("Malfunction during parameter's loading of agent"+ this.getClass().getName());
			System.exit(-1);
		}
	}


	@Override
	public void doDelete() {
		// TODO Auto-generated method stub
		

		super.doDelete();
	}
	
	
	
	
}
