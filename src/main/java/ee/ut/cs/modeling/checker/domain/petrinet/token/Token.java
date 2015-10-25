package ee.ut.cs.modeling.checker.domain.petrinet.token;

import ee.ut.cs.modeling.checker.domain.petrinet.node.Place;

public class Token {

	private Place place;

	public Place getPlace() {
		return place;
	}

	@Override
	public String toString() {
		return "*";
	}
}
