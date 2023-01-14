package com.sds.n3dx.conversion.worker;

import java.util.List;

import lombok.Data;

@Data
public class ConversionRequest {
	
	private long reqId;
	
	private long projectId;
	
	private String resQueueName;
	
	/**
	 * monolithic, shattered, default
	 */
	private String conversionMode;
	
	private String workspace;
	
	private boolean independent;
	
	private String status;

	private List<ConversionRequestItem> conversionItemList;
}
