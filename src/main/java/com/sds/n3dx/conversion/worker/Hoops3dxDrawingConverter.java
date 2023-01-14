package com.sds.n3dx.conversion.worker;

import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sds.n3dx.conversion.worker.ConversionResponse.FailCause;
import com.sds.n3dx.conversion.worker.ConversionResponse.Result;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Hoops3dxDrawingConverter implements DrawingConverter {
	
	/**
	 * example: /bin/<platform>/converter
	 */
	@Value("app.3dx-converter.executablePath")
	private String executable;

	public ConversionResponse execute(DrawingConverterOptions options) {
		log.info("");
		HoopsConverterOptions opts = (HoopsConverterOptions)options;
		CommandLine commandLine=new CommandLine(this.executable);
		for (final Map.Entry<String, String> option : opts.toMap().entrySet()) {
	        commandLine.addArgument("--" + option.getKey(), false);
	        commandLine.addArgument(option.getValue(), false);
		}
		
		ConversionResponse resp=new ConversionResponse();
		DefaultExecutor executor=new DefaultExecutor();
		int exitValue=0;
		
		try {
			exitValue=executor.execute(commandLine);
			if(exitValue==0) resp.setResult(Result.SUCCESS);
			else {
				resp.setResult(Result.FAIL);
				resp.setCause(HoopsFailure.toGeneralFailCause(exitValue));
			}
		} catch (Exception e) {
			log.error("");
			resp.setResult(Result.FAIL);
			resp.setCause(FailCause.GENERIC_FAILURE);
		}
		return resp;
	}
}
