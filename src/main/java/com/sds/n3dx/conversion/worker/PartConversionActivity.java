package com.sds.n3dx.conversion.worker;

public class PartConversionActivity extends ConversionActivity {
	
	public PartConversionActivity(String activityName) {
		super(activityName);
	}

	@Override
	public void doActivity(ConversionRequest req, ConversionResponse res) {
		HoopsConverterOptions options = new HoopsConverterOptions();
		options.setDrawingsMode(null);
		options.setInput(null);
		options.setOutputSc(null);
		options.setOutputScMaster(null);

		ConversionResponse resp=super.converter.execute(options);
		
	}

	@Override
	public ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res) {
		return null;
	}
}
