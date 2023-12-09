package com.adventofcode.flashk.day16;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.ManyToManyShortestPathsAlgorithm;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraManyToManyShortestPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProboscideaVolcaniumJGraphT {

    private static final String VALVE_REGEX = "Valve ([A-Z]*) has flow rate=(\\d*)";
    private static final Pattern VALVE_PATTERN = Pattern.compile(VALVE_REGEX);
    private static final String TUNNELS_REGEX = "lead to valves ([,A-Z ]*)|leads to valve ([A-Z]*)";
    private static final Pattern TUNNELS_PATTERN = Pattern.compile(TUNNELS_REGEX);
    private static final String SEPARATOR = ", ";
    public static final int MAX_TIME_PART_1 = 30;
    public static final int MAX_TIME_PART_2 = 26;

    private Graph<SimpleValve, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
    private ManyToManyShortestPathsAlgorithm.ManyToManyShortestPaths<SimpleValve,DefaultEdge> paths;
    private long maxReleasedPressure = 0;
    private int maxTime = MAX_TIME_PART_1;


    // TODO DijkstraManyToManyShortestPaths
    // Método:getManyToManyPaths
    // Permite obtenre todos los caminos que hay desde un Set de vértices de origen hasta un set de vértices objetivo

    public ProboscideaVolcaniumJGraphT(List<String> inputs) {

        for(String input : inputs) {

            // Create valves (graph vertex)
            SimpleValve currentValve = createValve(input);

            // Create tunnels (graph edges)
            createTunnel(input, currentValve);
        }

        // Calculate all shortest paths from all openable valves and starting valve to the all the openable valves.
        DijkstraManyToManyShortestPaths<SimpleValve, DefaultEdge> dijkstra = new DijkstraManyToManyShortestPaths<>(graph);
        paths = dijkstra.getManyToManyPaths(graph.vertexSet(), getOpenableValves());

        System.out.println("test");
    }

    private SimpleValve createValve(String input) {

        Matcher valveMatcher = VALVE_PATTERN.matcher(input);
        valveMatcher.find();

        String name = valveMatcher.group(1);
        int flow = Integer.parseInt(valveMatcher.group(2));

        SimpleValve currentValve = getValveOrDefault(name);
        currentValve.setFlow(flow);

        return currentValve;
    }

    private void createTunnel(String input, SimpleValve currentValve) {

        String[] neighbourNames = getNeighbourNames(input);

        graph.addVertex(currentValve);

        for(String neighbourName : neighbourNames) {

            // Search or create neighbour valve
            //SimpleValve neighbourValve = valves.getOrDefault(neighbourName, new SimpleValve(neighbourName));
            SimpleValve neighbourValve = getValveOrDefault(neighbourName);
            graph.addVertex(neighbourValve);

            // Add edge between both
            graph.addEdge(currentValve, neighbourValve);

        }
    }

    private static String[] getNeighbourNames(String input) {
        Matcher tunnelMatcher = TUNNELS_PATTERN.matcher(input);
        tunnelMatcher.find();

        String tunnels = tunnelMatcher.group(1);
        String tunnel = tunnelMatcher.group(2);

        String[] neighbourNames = null;

        if(!StringUtils.isBlank(tunnels)) {
            neighbourNames = tunnelMatcher.group(1).split(SEPARATOR);
        } else if(!StringUtils.isBlank(tunnel)){
            neighbourNames = new String[1];
            neighbourNames[0] = tunnel;
        }
        return neighbourNames;
    }

    public long solveA() {

        maxReleasedPressure = 0;

        // Always start from "AA" valve
        SimpleValve startingValve = getValveOrDefault("AA");
        startingValve.setOpen(true); // Consider origin valve as open as its flow is 0
        for(SimpleValve nextValve : getOpenableValves()) {
            double time = paths.getPath(startingValve, nextValve).getWeight();
            releasePressure(nextValve, maxTime, (int) time,0);
        }

        return maxReleasedPressure;
    }

    private void releasePressure(SimpleValve currentValve, int remainingTime, int timeToReach, long totalPressure) {
        // PRE 1: currentValve no está abierta (hemos prefiltrado)
        // PRE 2: La válvula actual da tiempo a abrirla y a producir presión (hemos prefiltrado)

        currentValve.setOpen(true);
        int remainingTimeAfterOpen = remainingTime - (timeToReach + SimpleValve.OPEN_TIME);
        long updatedPressure = totalPressure + currentValve.getFlow() * remainingTimeAfterOpen;

        //Set<Valve> candidates = getNextCandidates(remainingTimeAfterOpen);

        Set<SimpleValve> candidates = getOpenableValves(currentValve, remainingTimeAfterOpen);
        if(candidates.isEmpty()) {
            maxReleasedPressure = Math.max(updatedPressure, maxReleasedPressure);
        } else {
            for(SimpleValve nextValve : candidates) {
                double timeToReachNext = paths.getPath(currentValve, nextValve).getWeight();
                releasePressure(nextValve, remainingTimeAfterOpen, (int) timeToReachNext, updatedPressure);
            }
        }

        // Backtrack
        currentValve.setOpen(false);
    }

    private SimpleValve getValveOrDefault(String name) {
        return graph.vertexSet().stream()
                .filter(v -> name.equals(v.getName()))
                .findFirst()
                .orElse(new SimpleValve(name));
    }

    private Set<SimpleValve> getOpenableValves(SimpleValve currentValve, int remainingTime) {
        return graph.vertexSet().stream()
                .filter(v -> !v.isOpen())
                .filter(v -> v.getFlow() > 0)
                .filter(v -> paths.getPath(currentValve, v).getWeight() + SimpleValve.OPEN_TIME < remainingTime)
                .collect(Collectors.toSet());
    }

    private Set<SimpleValve> getOpenableValves() {
        return graph.vertexSet().stream()
                .filter(v -> !v.isOpen())
                .filter(v -> v.getFlow() > 0)
                .collect(Collectors.toSet());
    }
}
