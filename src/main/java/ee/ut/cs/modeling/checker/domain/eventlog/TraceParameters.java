package ee.ut.cs.modeling.checker.domain.eventlog;

import java.util.ArrayList;
import java.util.List;

public class TraceParameters {

	private int count;
	private int missing;
	private int remaining;
	private int consumed;
	private int produced;
	private List<Integer> enabledTransitions = new ArrayList<>();

	public int getCount() {
		return count;
	}

	public int getMissing() {
		return missing;
	}

	public int getRemaining() {
		return remaining;
	}

	public int getConsumed() {
		return consumed;
	}

	public int getProduced() {
		return produced;
	}

	public void incrementCount() {
		count++;
	}

	public void incrementMissing() {
		missing++;
	}

	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}

	public void incrementConsumed() {
		consumed++;
	}

	public void incrementProduced() {
		produced++;
	}

	public void addEnabledTransition(Integer count) {
		enabledTransitions.add(count);
	}

	public double getMeanEnabledTransitions() {
		double sum = 0d;
		for (Integer c : enabledTransitions) {
			sum += c;
		}
		return sum / enabledTransitions.size();
	}

	@Override
	public String toString() {
		return "{" +
				"n=" + count +
				", m=" + missing +
				", r=" + remaining +
				", c=" + consumed +
				", p=" + produced +
				", mean=" + getMeanEnabledTransitions() +
				'}';
	}
}
