package org.acme.graph.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PathTree {

	private Map<Vertex, PathNode> nodes = new HashMap<Vertex, PathNode>();

	/**
	 * Prépare le graphe pour le calcul du plus court chemin
	 * 
	 * @param graph
	 * @param source
	 */
	public PathTree(Graph graph, Vertex source) {
		for (Vertex vertex : graph.getVertices()) {
			PathNode node = new PathNode(source == vertex ? 0.0 : Double.POSITIVE_INFINITY, null, false);
			nodes.put(vertex, node);
		}
	}

	public PathNode getNode(Vertex vertex) {
		return nodes.get(vertex);
	}

	/**
	 * Construit le chemin en remontant les relations incoming edge
	 * 
	 * @param target
	 * @return
	 */
	public Path buildPath(Vertex target) {
		Path path = new Path();

		Edge current = getNode(target).getReachingEdge();
		do {
			path.getEdges().add(current);
			current = getNode(current.getSource()).getReachingEdge();
		} while (current != null);

		Collections.reverse(path.getEdges());
		return path;
	}
	
	public boolean isReached(Vertex destination) {
		return getNode(destination).getReachingEdge() != null;
	}

}
