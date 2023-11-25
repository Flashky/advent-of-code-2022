package com.adventofcode.flashk.day19;

import java.util.List;
import java.util.stream.Collectors;

public class Minerals {

	private List<Blueprint> blueprints;
	private RobotFactory robotFactory = new RobotFactory();
	
	public Minerals(List<String> inputs) {
		blueprints = inputs.stream().map(Blueprint::new).collect(Collectors.toList());
	}
	
	public long solveA() {
		return blueprints.stream().map(robotFactory::buildWithQuality).reduce(0L, Long::sum);
	}
	
	public long solveB() {
		return blueprints.stream().limit(3L).map(robotFactory::build).reduce(1L, Math::multiplyExact);
	}
}
