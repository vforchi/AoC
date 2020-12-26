package aoc.y2020.day16;

import aoc.Day;
import aoc.y2020.Utils;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day16 extends Day {

	List<TicketRule> rules;
	Ticket myTicket;
	List<Ticket> nearbyTickets;

	@Override
	public Object partOne() {
		return this.nearbyTickets.stream()
				.map(t -> t.invalidFields(rules))
				.reduce(Integer::sum)
				.orElseThrow();
	}

	@Override
	public Object partTwo() {
		var validTickets = this.nearbyTickets.stream()
				.filter(t -> t.hasNoInvalidFields(rules))
				.collect(Collectors.toList());

		// key: rule, values: fields that match the rule
		var candidates = Stream.ofAll(this.rules)
				.map(r -> new Tuple2<>(r, r.findValidFields(validTickets)))
				.collect(Collectors.toMap(Tuple2::_1, Tuple2::_2));

		List<Integer> uniqueFields = Collections.emptyList();
		while (candidates.size() != uniqueFields.size()) {
			// fields that have been already assigned to the rule
			uniqueFields = candidates.entrySet().stream()
					.filter(f -> f.getValue().size() == 1)
					.flatMap(e -> e.getValue().stream())
					.collect(Collectors.toList());

			// remove the fields already identified from the candidates
			List<Integer> finalUniqueFields = uniqueFields;
			candidates.entrySet().stream()
					.filter(e -> e.getValue().size() > 1)
					.forEach(e -> e.getValue().removeAll(finalUniqueFields));
		}

		return candidates.entrySet().stream()
				.filter(e -> e.getKey().name.startsWith("departure"))
				.map(e -> e.getValue().get(0))
				.mapToLong(field -> myTicket.getField(field))
				.reduce((a, b) -> a * b)
				.orElseThrow();
	}

	@Override
	public void setup(String path) {
		super.setup(path);

		var sections = Utils.partitionByEmptyLine(this.input).collect(Collectors.toList());

		this.rules = sections.get(0).stream()
				.map(TicketRule::fromString)
				.collect(Collectors.toList());

		this.myTicket = Ticket.fromString(sections.get(1).get(1));

		this.nearbyTickets = sections.get(2).stream()
				.skip(1)
				.map(Ticket::fromString)
				.collect(Collectors.toList());
	}

}
