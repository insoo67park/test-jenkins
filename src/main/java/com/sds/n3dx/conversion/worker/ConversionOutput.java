package com.sds.n3dx.conversion.worker;

import lombok.Data;

@Data
public class ConversionOutput {

	private long outputId;
	
	private long itemId;
	
	private String outputKey; 
	
	private String outputPath;
}