package com.trains;

import java.util.Map;
import java.util.TreeMap;

/**
 * A small Factory Pattern to instantiate Actions based on a command
 * string
 */
public class ActionFactory {
	private Map<String, Class<?> > actionsMap;
	
	public Action createAction(String s) {
		Class<?> c = actionsMap.get(s);
		Action action = null;

		if (c != null) {
			try {
				action = (Action) c.newInstance();
			} catch (Exception e) {
				System.err.println(App.getProperty("internal_error") + " " + s + " action: " + e.getMessage());
			}
		}		

		return action;
	}
	
	public ActionFactory() {
		actionsMap = new TreeMap<String, Class<?> >();
		
		actionsMap.put("distance?", DistanceAction.class);
		actionsMap.put("trips?", TripsAction.class);
		actionsMap.put("shortest?", ShortestPathAction.class);
	}
}
