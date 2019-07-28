package miscellaneous;

import java.io.File;
import dataStructures1.Graph;
import dataStructures1.EdgeNode;
import java.lang.Math;

public class BandwidthAnneal {
	private double temp;
	private final int NUM_COOLING;
	private final double COOL_RATIO;
	private final int ITERATIONS;
	private final int BOLTZMANN;
	private final int COST_CONST;
	private int[] order;
	private Graph graph;
	private double best_cost;
	private int[] best_order;
	
	/*public construction available through builder class*/
	public static Builder getBuilder() {
		return new Builder();
	}
	
	private BandwidthAnneal(Builder builder){
		best_cost = Double.MAX_VALUE;
		temp = builder.initial_temp;
		NUM_COOLING = builder.num_cooling;
		COOL_RATIO = builder.cool_ratio;
		ITERATIONS = builder.iterations;
		BOLTZMANN = builder.boltzmann;
		COST_CONST =  builder.cost_const;
		graph = builder._graph;
		order = new int[graph.nverts];
		best_order = new int[graph.nverts];
		for (int i = 0; i < order.length; i++) {
			order[i] = i;
		}
		System.arraycopy(order, 0, best_order, 0, order.length);
	}
	
	public int[] anneal() {
		double cost = Double.MAX_VALUE;
		for (int i = 0; i < NUM_COOLING; i++) {
			double prev_cost = cost;
			temp *= COOL_RATIO;
			for (int j = 0; j < ITERATIONS; j++) {
				int[] choice = {0,0};
				getChoice(choice);
				swap(choice);
				double temp_cost = calcCost(order);
				double delta = temp_cost - prev_cost;
				double cutoff = Math.exp(-delta/(temp*BOLTZMANN));
				if (delta < 0) {
					cost = temp_cost;
					if (standardCost(order) < best_cost) {
						best_cost = standardCost(order);
						System.arraycopy(order, 0, best_order, 0, order.length);
					}
				}else if (cutoff > Math.random()) {
					cost = temp_cost;
				}
				else {swap(choice);}
			}
			if (cost < prev_cost) {
				temp /= COOL_RATIO;
			}
		}
		return best_order;
	}
	
	private void getChoice(int[] c) {
		if (c.length <= 1) {System.out.println("Warning nothing to choose from!"); return;}
		while (c[0] == c[1]){
			c[0] = (int) (Math.random()*(order.length));
			c[1] = (int) (Math.random()*(order.length));
		}
	}
	private void swap(int[] c) {
		int _temp = order[c[0]];
		order[c[0]] = order[c[1]];
		order[c[1]] = _temp;
	}
	
	public double calcCost(int[] _order) {
		double cost = 0;
		for (int i = 0; i < graph.nverts; i++) {
			EdgeNode p = graph.edges[i];
			while (p != null) {
				cost += COST_CONST*Math.abs(_order[p.parent] - _order[p.child])*(Math.exp(-temp));
				p = p.next;
			}
		}
		return cost;
	}
	
	public double standardCost(int[] _order) {
		double cost = 0;
		for (int i = 0; i < graph.nverts; i++) {
			EdgeNode p = graph.edges[i];
			while (p != null) {
				cost += COST_CONST*Math.abs(_order[p.parent] - _order[p.child]);
				p = p.next;
			}
		}
		return cost;
	}
	
	private static class Builder{
		private double initial_temp;
		private double cool_ratio;
		private int num_cooling;
		private int iterations;
		private int boltzmann;
		private int cost_const;
		private Graph _graph;
		
		public Builder setInitialTemp(double initialTemp) {
			initial_temp = initialTemp;
			return this;
		}
		public Builder setCoolRatio(double coolRatio) {
			cool_ratio = coolRatio;
			return this;
		}
		public Builder setNumCooling(int numCooling) {
			num_cooling = numCooling;
			return this;
		}
		public Builder setIterations(int iterations) {
			this.iterations = iterations;
			return this;
		}
		public Builder setBoltzmann(int boltzmann) {
			this.boltzmann = boltzmann;
			return this;
		}
		public Builder setCostConst(int costConst) {
			cost_const = costConst;
			return this;
		}
		public Builder setGraph(Graph _graph) {
			this._graph = _graph;
			return this;
		}
		
		public BandwidthAnneal build() {
			return new BandwidthAnneal(this);
		}
	}
	
	public static void main(String[] args) {
		BandwidthAnneal.Builder myBuilder = BandwidthAnneal.getBuilder();
		myBuilder.setBoltzmann(1000).setCoolRatio(0.9).setNumCooling(2000).setCostConst(5);
		myBuilder.setInitialTemp(1).setIterations(500);
		File graphFile = new File(System.getProperty("user.dir")+"\\resources\\graph_in.txt");
		myBuilder.setGraph(new Graph(graphFile));
		
		BandwidthAnneal annealer = myBuilder.build();
		int[] _order = annealer.anneal();
		double _cost = annealer.standardCost(_order);
		for (int i = 0; i < _order.length; i++) {
			System.out.print(_order[i]+" ");
		}
		System.out.println();
		System.out.println("cost "+_cost);
	}
}
