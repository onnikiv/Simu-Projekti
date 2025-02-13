package simu.model;

import simu.framework.IEventType;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum EventType implements IEventType {
	ARRIVAL, TABLEASSIGNMENT, ORDER, SERVE, PAYMENT, EXIT, APPETIZER, DESSERT, MAINCOURSE;

}