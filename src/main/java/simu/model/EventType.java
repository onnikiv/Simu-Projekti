package simu.model;

import simu.framework.IEventType;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum EventType implements IEventType {
	SAAPUMINEN, PÖYTIINOHJAUS, TILAAMINEN, TARJOILU, POISTUMINEN;

}