package ee.ut.cs.modeling.checker.domain.eventlog;

public class TraceParameters {

	private int count;
	private int missing;
	private int remaining;
	private int consumed;
	private int produced;

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

	public void incrementRemaining() {
		remaining++;
	}

	public void incrementConsumed() {
		consumed++;
	}

	public void incrementProduced() {
		produced++;
	}

	@Override
	public String toString() {
		return "{" +
				"n=" + count +
				", m=" + missing +
				", r=" + remaining +
				", c=" + consumed +
				", p=" + produced +
				'}';
	}
}
