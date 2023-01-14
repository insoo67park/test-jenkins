package com.sds.n3dx.conversion.worker;

import java.nio.file.Path;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TempSymbolicLinkActivity extends ConversionActivity {
	
	public TempSymbolicLinkActivity(String activityName) {
		super(activityName);
	}

	@Override
	public void doActivity(ConversionRequest req, ConversionResponse res) {
		req.getConversionItemList().forEach(item->{
			Path path=FileUtils.createSymbolicLink(
					null, 
					null, 
					e -> FileUtils.supportsSymbolicLinkFallback(null, null, e));
			log.debug(""+path.getFileName());
		});
		res.setResult(ConversionResponse.Result.SUCCESS);
	}

	@Override
	public ConversionStatusChangeEvent createStatusChangeEvent(ConversionResponse res) {
		return null;
	}
}
