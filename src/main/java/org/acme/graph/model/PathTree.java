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
	public PathTree(Vertex source) {
		PathNode node = new PathNode(0.0, null, false);
		nodes.put(source, node);
	}

	private PathNode getNode(Vertex vertex) {
		return nodes.get(vertex);
	}

	/**
	 * Construit le chemin en remontant les relations incoming edge
	 * 
	 * @param target
	 * @return
	 */
	public Path getPath(Vertex destination) {
		assert (isReached(destination));

		Path path = new Path();

		Edge current = getNode(destination).getReachingEdge();
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

	public PathNode getOrCreateVertex(Vertex vertex) {
		if (!nodes.containsKey(vertex)) {
			PathNode node = new PathNode(Double.POSITIVE_INFINITY, null, false);
			nodes.put(vertex, node);
		}
		return getNode(vertex);
	}

}
