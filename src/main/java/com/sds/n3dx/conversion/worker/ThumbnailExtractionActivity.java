package com.sds.n3dx.conversion.worker;

public class ThumbnailExtractionActivity extends ConversionActivity {
	
	public ThumbnailExtractionActivity(String activityName) {
		super(activityName);
	}

	@Override
	public void doActivity(ConversionRequest req, ConversionResponse res) {
		
	}

	@Override
	public ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res) {
		return null;
	}
}
