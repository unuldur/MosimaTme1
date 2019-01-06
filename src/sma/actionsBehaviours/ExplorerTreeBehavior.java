package sma.actionsBehaviours;

import java.util.ArrayList;
import java.util.Random;

import com.jme3.math.Vector3f;

import jade.core.Agent;
import sma.AbstractAgent;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class ExplorerTreeBehavior extends ExploreTopBehavior{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8097567488156906694L;
	private FilteredClassifier fc;
	private Instances data;
	private static Random rand = new Random();
	private static final boolean SEETOUCH = false;
	
	public ExplorerTreeBehavior(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
		DataSource source;
		try {
			if(SEETOUCH) {
				source = new DataSource("ressources/wekaFile/duelSeeTouch.arff");
			}else {
				source = new DataSource("ressources/wekaFile/duel.arff");
			}
			data = source.getDataSet();
			if (data.classIndex() == -1)
				  data.setClassIndex(data.numAttributes() - 1);
			Remove remove = new Remove();
			if(SEETOUCH) {
				remove.setAttributeIndices("1-4,9-13");
			}else {
				remove.setAttributeIndices("1-4,9-12");
			}
			J48 j48 = new J48();
			j48.setUnpruned(true);
			// meta-classifier
			fc = new FilteredClassifier();
			fc.setFilter(remove);
			fc.setClassifier(j48);
			// train and make predictions
			fc.buildClassifier(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		super.onTick();
	}



	@Override
	Vector3f findInterestingNeighbor(){
		ArrayList<Vector3f> points = agent.sphereCast(agent.getSpatial(), AbstractAgent.NEIGHBORHOOD_DISTANCE, AbstractAgent.CLOSE_PRECISION, AbstractAgent.VISION_ANGLE);
		Vector3f pointChoose = null;
		double maxVictory = 0;
		for(Vector3f point : points) {
			Instance i = createInstance(point);
			i.setDataset(data);
			try {
				double[] pred = fc.distributionForInstance(i);
				if(pred[0] > maxVictory) {
					maxVictory = pred[0];
					pointChoose = point;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		if(pointChoose != null) return pointChoose;
		return points.get(rand.nextInt(points.size()));
	}
	
	private Instance createInstance(Vector3f point) {
		Instance i;
		if(SEETOUCH)
			i = new Instance(13);
		else
			i = new Instance(14);
		if(MyBehavior.sitMy.minAltitude > point.y) {
			i.setValue(6, point.y);
		}else {
			i.setValue(6, MyBehavior.sitMy.minAltitude);
		}
		if(MyBehavior.sitMy.maxAltitude < point.y) {
			i.setValue(7, point.y);
		}else {
			i.setValue(7, MyBehavior.sitMy.maxAltitude);
		}
		i.setValue(8, point.y);
		i.setValue(5, point.y + MyBehavior.sitMy.averageAltitude);
		return i;
	}
}
