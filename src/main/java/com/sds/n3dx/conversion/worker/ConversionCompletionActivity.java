package com.sds.n3dx.conversion.worker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConversionCompletionActivity extends ConversionActivity {
	
	public ConversionCompletionActivity(String activityName) {
		super(activityName);
	}

	@Override
	public void doActivity(ConversionRequest req, ConversionResponse res) {
		log.info("ConversionCompletionActivity.doActivity(ConversionRequest req, ConversionResponse res) is called");
		log.info("Nothing to do at this activity.");
	}

	@Override
	public ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res) {
		return null;
	}
}
