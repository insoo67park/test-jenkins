package com.sds.n3dx.conversion.worker;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public abstract class ConversionActivity {
	
	private List<PropertyChangeListener> propertyChangeListenerList;
	
	protected DrawingConverter converter;
	
	private String activityName;

	private ConversionActivity next;
	
	public ConversionActivity() {
		this("", null);
	}
	
	public ConversionActivity(String activityName) {
		this(activityName, null);
	}
	
	public ConversionActivity(DrawingConverter converter) {
		this(converter, null);
	}
	
	public ConversionActivity(String activityName, DrawingConverter converter) {
		this(activityName, converter, null);
	}
	
	public ConversionActivity(DrawingConverter converter, ConversionActivity next) {
		this("", converter, next);
	}
	
	public ConversionActivity(String activityName, DrawingConverter converter, ConversionActivity next) {
		this.propertyChangeListenerList=new ArrayList<>();
		this.activityName=activityName;
		this.converter=converter;
		this.next=next;
	}
	
	public void setConverter(DrawingConverter converter) {
		this.converter=converter;
	}
	
	public void setNext(ConversionActivity next) {
		this.next=next;
	}
	
	public void addPropertyChangeListener(String eventName, PropertyChangeListener listener) {
		propertyChangeListenerList.add(listener);
	}
	
	public void execute(ConversionRequest req, ConversionResponse res) {
		this.doActivity(req, res);
		
		ConversionStatusChangeEvent event=createStatusChangeEvent(res);
		for(PropertyChangeListener pcl:propertyChangeListenerList) pcl.propertyChange(event);
		
		if(ConversionResponse.Result.SUCCESS==res.getResult() && next != null) {
			next.execute(req, res);
		}
	}
	
	public void setActivityName(String activityName) {
		this.activityName=activityName;
	}
	
	public String getActivityName() {
		return this.activityName;
	}
	
	public abstract void doActivity(ConversionRequest req, ConversionResponse res);
	
	public abstract ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res);	
}
