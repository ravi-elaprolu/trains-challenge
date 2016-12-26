package com.trains;

import java.util.Map;
import java.util.TreeMap;

public class Condition {
	private interface Operation {
		public boolean evaluate(int x, int y);
	}

	private int value;
	private String entity;
	private Operation operation;
	
	static Map<String, Operation> operatorsMap;
	
	static {
		operatorsMap = new TreeMap<String, Operation>();
		operatorsMap.put(">", new Operation() {
			public boolean evaluate(int x, int y) {
				return x > y;
			}
		});
		operatorsMap.put("<", new Operation() {
			public boolean evaluate(int x, int y) {
				return x < y;
			}
		});
		operatorsMap.put("=", new Operation() {
			public boolean evaluate(int x, int y) {
				return x == y;
			}
		});
	}
	
	public boolean evaluate(Path path) {
		boolean rc;
		if (entity.equals("stops")) {
			rc = operation.evaluate(path.getHops(), value);
		} else if (entity.equals("distance")) {
			rc = operation.evaluate(path.getDistance(), value);
		} else {
			throw new IllegalArgumentException(App.getProperty("unknown_entity") + ": " + entity);
		}
		return rc;
	}

	/**
	 * Constructs a Condition object that can be evaluated against a given Path
	 * later. 
	 * @param operator one of"<", ">" or "="
	 * @param value to be used in the comparison
	 * @param entity either "stops" or "distance"
	 */
	public Condition(String operator, int value, String entity) {
		this.operation = operatorsMap.get(operator);
		
		if (this.operation == null) {
			throw new IllegalArgumentException(App.getProperty("unknown_operator") +": " + operator);
		}
		this.value  = value;
		this.entity = entity;
	}
}
