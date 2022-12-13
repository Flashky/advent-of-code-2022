package com.adventofcode.flashk.day13;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class DistressSignal {

	private List<Packet> packets;
	
	public DistressSignal(List<String> inputs) {
		
		packets = inputs.stream().filter(StringUtils::isNotBlank).map(Packet::new).collect(Collectors.toList());
		
	}
	
	public long solveA() {
		
		long result = 0;
		int packetIndex = 0;

		for(int i = 0; i < packets.size(); i++) {
			packetIndex++;
			
			Packet a = packets.get(i);
			Packet b = packets.get(i+1);
			
			i++;
			if(a.compareTo(b) > 0) {
				result += packetIndex;
			}
		}
		
		return result;
	}
	
	public long solveB() {
		
		// Add divider packets - Keep a pointer of them to use them later
		Packet dividerPacket1 = new Packet("[[2]]");
		Packet dividerPacket2 = new Packet("[[6]]");
		
		packets.add(dividerPacket1);
		packets.add(dividerPacket2);
		
		// Sort packages using comparator from part 1 and update indexes
		AtomicInteger packetIndex = new AtomicInteger(1);
		packets.stream()
				.sorted(Comparator.reverseOrder())
				.forEach(packet -> packet.setPacketIndex(packetIndex.getAndIncrement()));

		return dividerPacket1.getPacketIndex() * dividerPacket2.getPacketIndex();

				
	}
}
