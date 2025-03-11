package simu.model;

import simu.framework.IEventType;

/**
 * The EventType enum represents the event types in the simulation model.
 * The event types are used to determine the order of events in the simulation.
 */

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum EventType implements IEventType {
	SAAPUMINEN, PÖYTIINOHJAUS, TILAAMINEN, TARJOILU, SAFKAAMINEN, POISTUMINEN;

}