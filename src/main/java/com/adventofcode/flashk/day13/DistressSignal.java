package com.adventofcode.flashk.day13;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

public class DistressSignal {

	private List<Packet> packets;
	
	public DistressSignal(List<String> inputs) {
		
		List<List<String>> pairs = Lists.partition(inputs, 3);
		packets = pairs.stream().map(Packet::new).collect(Collectors.toList());
		
	}
	
	public long solveA() {
		
		long result = 0;
		int packetIndex = 0;
		System.out.println();
		for(Packet packet : packets) {
			packetIndex++;
			if(packet.compare()) {
				result += packetIndex;
				System.out.println(String.format("Pair %s: ok", packetIndex));
			} else {
				System.out.println(String.format("Pair %s: not ok", packetIndex));
			}
		}
		
		return result;
	}
}
