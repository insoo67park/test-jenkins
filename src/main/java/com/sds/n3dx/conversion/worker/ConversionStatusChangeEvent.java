package com.sds.n3dx.conversion.worker;

import java.beans.PropertyChangeEvent;

public class ConversionStatusChangeEvent extends PropertyChangeEvent {

	private static final long serialVersionUID = 1L;
	
	public ConversionStatusChangeEvent(Object source, String propertyName, Object oldValue, Object newValue) {
		super(source, propertyName, oldValue, newValue);
	}
}