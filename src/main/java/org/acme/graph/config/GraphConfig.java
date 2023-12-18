package org.acme.graph.config;

import java.io.File;
import java.io.IOException;

import org.acme.graph.io.GraphReader;
import org.acme.graph.model.Graph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

/**
 * [spring] Cette classe permet d'initialiser des objects pour l'application et
 * de gérer des paramètres
 * 
 * @see https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
 * 
 * @author mborne
 */
@Configuration
public class GraphConfig {

	private static final Logger log = LogManager.getLogger(GraphConfig.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${graph.path}")
	private String path;

	/**
	 * Chargement du graphe configuré via la variable "graph.path" dans le contexte
	 * spring
	 */
	@Bean
	public Graph graph() throws Exception {
		File file = getGraphFile();
		log.info("Loading graph from {}...", file.getAbsolutePath());
		long startTime = System.currentTimeMillis();
		GraphReader reader = new GraphReader();
		Graph graph = reader.read(file);
		long endTime = System.currentTimeMillis();
		log.info("Graph loaded in {} milliseconds (num_vertices={}, num_edges={})", (endTime - startTime),
				graph.getVertices().size(), graph.getEdges().size());
		return graph;
	}

	/**
	 * 
	 * Récupération du fichier configuré via la variable "graph.path" avec
	 * traitement du cas où c'est une resource ou un fichier externe au jar
	 * 
	 * @return
	 * @throws IOException
	 */
	File getGraphFile() throws IOException {
		if (path.startsWith("classpath:")) {
			return resourceLoader.getResource(path).getFile();
		} else {
			return new File(path);
		}
	}

}
