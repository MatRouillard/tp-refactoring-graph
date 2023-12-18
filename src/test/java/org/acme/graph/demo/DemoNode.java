package org.acme.graph.demo;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

/**
 * /!\ NE PAS LIRE EN DEBUT DE TP /!\
 *
 * 0.11 - classe de test fonctionnellement équivalente à PathNode pour illustrer
 * le fonctionnement de cqengine avant son utilisation pour indexer les PathNode
 * dans les dernières questions du TP.
 */
public class DemoNode {
	public String id;
	public boolean visited;
	public double cost;

	public static final SimpleAttribute<DemoNode, String> ID = new SimpleAttribute<DemoNode, String>("nodeId") {
		public String getValue(DemoNode node, QueryOptions queryOptions) {
			return node.id;
		}
	};
	public static final SimpleAttribute<DemoNode, Boolean> VISITED = new SimpleAttribute<DemoNode, Boolean>(
			"nodeVisited") {
		public Boolean getValue(DemoNode node, QueryOptions queryOptions) {
			return node.visited;
		}
	};
	public static final SimpleAttribute<DemoNode, Double> COST = new SimpleAttribute<DemoNode, Double>("nodeCost") {
		public Double getValue(DemoNode node, QueryOptions queryOptions) {
			return node.cost;
		}
	};

}
