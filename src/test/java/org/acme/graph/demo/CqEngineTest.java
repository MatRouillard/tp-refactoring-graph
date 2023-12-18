package org.acme.graph.demo;

import static com.googlecode.cqengine.query.QueryFactory.ascending;
import static com.googlecode.cqengine.query.QueryFactory.equal;
import static com.googlecode.cqengine.query.QueryFactory.orderBy;
import static com.googlecode.cqengine.query.QueryFactory.queryOptions;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.index.hash.HashIndex;
import com.googlecode.cqengine.index.navigable.NavigableIndex;
import com.googlecode.cqengine.query.Query;

/**
 * /!\ NE PAS LIRE EN DEBUT DE TP /!\
 * 
 * 0.11 - Cette classe de test illustre l'utilisation de la bibliothèque
 * https://github.com/npgall/cqengine qui sera utile dans les dernières
 * questions du TP pour créer une collection avec plusieurs indexes afin de
 * trouver efficacement le sommet non visité le plus proche du point de départ.
 */
public class CqEngineTest {

	private IndexedCollection<DemoNode> nodes;

	@Before
	public void setUp() throws Exception {
		// création de la collection
		this.nodes = new ConcurrentIndexedCollection<DemoNode>();
		// création des indexes
		this.nodes.addIndex(NavigableIndex.onAttribute(DemoNode.ID));
		this.nodes.addIndex(HashIndex.onAttribute(DemoNode.VISITED));
		this.nodes.addIndex(NavigableIndex.onAttribute(DemoNode.COST));

		for (int i = 0; i < 100000; i++) {
			DemoNode node = new DemoNode();
			node.id = "node-" + i;
			node.visited = (i % 2 == 0);
			node.cost = 10.0 * i;
			this.nodes.add(node);
		}
	}

	/**
	 * Exemple de récupération d'un DemoNode par son identifiant.
	 * 
	 * NB : Fonctionnera de la même manière pour récupérer un PathNode associé à un
	 * vertex de type Vertex.
	 */
	@Test
	public void testFindVertexById() {
		Optional<DemoNode> result = this.nodes.retrieve(equal(DemoNode.ID, "node-20")).stream().findFirst();
		assertFalse(result.isEmpty());
		DemoNode node = result.get();
		assertEquals("node-20", node.id);
	}

	/**
	 * Exemple de récupération des DemoNode non visités.
	 */
	@Test
	public void testFindNotVisited() {
		int countNotVisited = this.nodes.retrieve(equal(DemoNode.VISITED, false)).size();
		assertEquals(50000, countNotVisited);
	}

	/**
	 * Exemple de récupération des DemoNode non visités avec un tri sur le coût.
	 */
	@Test
	public void testFindNotVisitedSortByCost() {
		Query<DemoNode> query = equal(DemoNode.VISITED, false);

		/*
		 * Recherche du sommet non visité le plus proche du point de départ.
		 */
		Optional<DemoNode> result1 = this.nodes.retrieve(query, queryOptions(orderBy(ascending(DemoNode.COST))))
				.stream().findFirst();
		assertFalse(result1.isEmpty());
		DemoNode node1 = result1.get();
		assertEquals("node-1", node1.id);

		/*
		 * ATTENTION : On doit supprimer l'élément de la collection avant de le modifier
		 * sans quoi l'indexe n'est pas valable.
		 */
		this.nodes.remove(node1);
		node1.visited = true;
		this.nodes.add(node1);

		/*
		 * Recherche du sommet non visité le plus proche du point de départ.
		 */
		Optional<DemoNode> result2 = this.nodes.retrieve(query, queryOptions(orderBy(ascending(DemoNode.COST))))
				.stream().findFirst();
		assertFalse(result2.isEmpty());

		DemoNode node2 = result2.get();
		assertEquals("node-3", node2.id);
	}

}
