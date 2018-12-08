package sma.agents;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

import env.jme.NewEnv;
import jade.core.behaviours.Behaviour;
import sma.AbstractAgent;
import sma.InterestPoint;
import sma.actionsBehaviours.DumbBehavior;
import sma.actionsBehaviours.MyBehavior;
import sma.actionsBehaviours.PrologBehavior;
import utils.Utils;

public class MyAgent extends FinalAgent{
	private static final long serialVersionUID = 5215165765928961044L;

	
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
		String fileLocalisation;
		if(dead) {
			fileLocalisation = System.getProperty("user.dir")+"/ressources/learningBase/defeat";
		}else {
			fileLocalisation = System.getProperty("user.dir")+"/ressources/learningBase/victory";
		}
		Utils.saveSituation(fileLocalisation, MyBehavior.sit);

		super.doDelete();
	}
	
	
	
	
}
