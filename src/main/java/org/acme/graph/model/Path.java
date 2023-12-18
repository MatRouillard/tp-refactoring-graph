package org.acme.graph.model;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Edge> edges = new ArrayList<Edge>();

	private double length;

	public Path() {

	}

	public Path(List<Edge> edges) {
		this.edges = edges;
	}

	public double getLength() {
		this.length = 0;
		for (Edge edge : edges) {
			this.length += edge.getCost();
		}
		return this.length;
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}

}
