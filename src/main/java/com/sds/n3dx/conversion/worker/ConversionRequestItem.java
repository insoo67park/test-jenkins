package com.sds.n3dx.conversion.worker;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ConversionRequestItem {

	private long itemId;
	
	private String reason;
	
	private String inputPath;
	
	private String sorucePath;
	
	private List<ConversionOutput> outputs;
	
	public ConversionRequestItem() {
		outputs=new ArrayList<>();
	}
	
	public void addOutput(ConversionOutput output) {
		outputs.add(output);
	}
}
