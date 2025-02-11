package simu.model;

import simu.framework.IEventType;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum EventType implements IEventType {
	arrival, tableAssignment, ordering, serving, payment, leaving, appetizer, dessert, mainCourse;

}