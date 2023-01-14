package com.sds.n3dx.conversion.worker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversionStartActivity extends ConversionActivity {
	
	public ConversionStartActivity(String activityName) {
		super(activityName);
	}

	@Override
	public void doActivity(ConversionRequest req, ConversionResponse res) {
		log.info("ConversionStartActivity.doActivity(ConversionRequest req, ConversionResponse res) is called");
		log.info("Nothing to do at this activity.");
	}

	@Override
	public ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res) {
		ConversionStatusChangeEvent event=new ConversionStatusChangeEvent(res, "status", null, "STARTED");
		return event;
	}
}
