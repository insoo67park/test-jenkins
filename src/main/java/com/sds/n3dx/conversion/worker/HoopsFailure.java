package com.sds.n3dx.conversion.worker;

import com.sds.n3dx.conversion.worker.ConversionResponse.FailCause;

public class HoopsFailure {

	private final int value;
	
	private final String name;
	
	public static HoopsFailure GENERIC_FAILURE            = new HoopsFailure(1<<0,  "GENERIC_FAILURE");
	public static HoopsFailure MODEL_LOAD_FAILURE         = new HoopsFailure(1<<1,  "MODEL_LOAD_FAILURE");
	public static HoopsFailure INVALID_LICENSE            = new HoopsFailure(1<<2,  "INVALID_LICENSE");
	public static HoopsFailure INITIALIZATION_FAILURE     = new HoopsFailure(1<<3,  "INITIALIZATION_FAILURE");
	public static HoopsFailure NO_INPUT                   = new HoopsFailure(1<<4,  "NO_INPUT");
	public static HoopsFailure SC_MASTER_OUTPUT_FAILURE   = new HoopsFailure(1<<5,  "SC_MASTER_OUTPUT_FAILURE");
	public static HoopsFailure PNG_OUTPUT_FAILURE         = new HoopsFailure(1<<6,  "PNG_OUTPUT_FAILURE");
	public static HoopsFailure HSF_OUTPUT_FAILURE         = new HoopsFailure(1<<7,  "HSF_OUTPUT_FAILURE");
	public static HoopsFailure PRC_OUTPUT_FAILURE         = new HoopsFailure(1<<8,  "PRC_OUTPUT_FAILURE");
	public static HoopsFailure STEP_OUTPUT_FAILURE        = new HoopsFailure(1<<9,  "STEP_OUTPUT_FAILURE");
	public static HoopsFailure STL_OUTPUT_FAILURE         = new HoopsFailure(1<<10, "STL_OUTPUT_FAILURE");
	public static HoopsFailure XT_OUTPUT_FAILURE          = new HoopsFailure(1<<11, "XT_OUTPUT_FAILURE");
	public static HoopsFailure THREE_MF_OUTPUT_FAILURE    = new HoopsFailure(1<<12, "THREE_MF_OUTPUT_FAILURE");
	public static HoopsFailure JT_OUTPUT_FAILURE          = new HoopsFailure(1<<13, "JT_OUTPUT_FAILURE");
	public static HoopsFailure PDF_OUTPUT_FAILURE         = new HoopsFailure(1<<14, "PDF_OUTPUT_FAILURE");
	public static HoopsFailure SC_OUTPUT_FAILURE          = new HoopsFailure(1<<15, "SC_OUTPUT_FAILURE");
	public static HoopsFailure XML_OUTPUT_FAILURE         = new HoopsFailure(1<<16, "XML_OUTPUT_FAILURE");
	public static HoopsFailure HTML_OUTPUT_FAILURE        = new HoopsFailure(1<<17, "STL_OUTPUT_FAILURE");
	public static HoopsFailure DEPENDENDIES_OUTPUT_FAILURE= new HoopsFailure(1<<18, "DEPENDENDIES_OUTPUT_FAILURE");
	public static HoopsFailure XML_BOM_OUTPUT_FAILURE     = new HoopsFailure(1<<19, "XML_BOM_OUTPUT_FAILURE");
	public static HoopsFailure CONFIG_FILE_OUTPUT_FAILURE = new HoopsFailure(1<<20, "CONFIG_FILE_OUTPUT_FAILURE");
	public static HoopsFailure SHATTERED_OUTPUT_FAILURE   = new HoopsFailure(1<<21, "SHATTERED_OUTPUT_FAILURE");
	public static HoopsFailure UNKNOWN                    = new HoopsFailure(1<<30, "UNKNOWNN");

	
	private HoopsFailure(int value, String name) {
		this.value=value;
		this.name=name;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static HoopsFailure toHooopsFailuer(int value) {
		if(value==1<<0) return GENERIC_FAILURE;
		else if(value==1<<1)  return MODEL_LOAD_FAILURE;
		else if(value==1<<2)  return INVALID_LICENSE;
		else if(value==1<<3)  return INITIALIZATION_FAILURE;
		else if(value==1<<4)  return NO_INPUT;
		else if(value==1<<5)  return SC_MASTER_OUTPUT_FAILURE;
		else if(value==1<<6)  return PNG_OUTPUT_FAILURE;
		else if(value==1<<7)  return HSF_OUTPUT_FAILURE;
		else if(value==1<<8)  return PRC_OUTPUT_FAILURE;
		else if(value==1<<9)  return STEP_OUTPUT_FAILURE;
		else if(value==1<<10) return STL_OUTPUT_FAILURE;
		else if(value==1<<11) return XT_OUTPUT_FAILURE;
		else if(value==1<<12) return THREE_MF_OUTPUT_FAILURE;
		else if(value==1<<13) return JT_OUTPUT_FAILURE;
		else if(value==1<<14) return PDF_OUTPUT_FAILURE;
		else if(value==1<<15) return SC_OUTPUT_FAILURE;
		else if(value==1<<16) return XML_OUTPUT_FAILURE;
		else if(value==1<<17) return HTML_OUTPUT_FAILURE;
		else if(value==1<<18) return DEPENDENDIES_OUTPUT_FAILURE;
		else if(value==1<<19) return XML_BOM_OUTPUT_FAILURE;
		else if(value==1<<20) return CONFIG_FILE_OUTPUT_FAILURE;
		else if(value==1<<21) return SHATTERED_OUTPUT_FAILURE;
		else return UNKNOWN;
	}
	
	public static FailCause toGeneralFailCause(int value) {
		if(value==1<<0) return FailCause.GENERIC_FAILURE;
		else if(value==1<<1)  return FailCause.MODEL_LOAD_FAILURE;
		else if(value==1<<2)  return FailCause.INVALID_LICENSE;
		else if(value==1<<3)  return FailCause.INITIALIZATION_FAILURE;
		else if(value==1<<4)  return FailCause.NO_INPUT;
		else if(value==1<<5)  return FailCause.SC_MASTER_OUTPUT_FAILURE;
		else if(value==1<<6)  return FailCause.PNG_OUTPUT_FAILURE;
		else if(value==1<<7)  return FailCause.HSF_OUTPUT_FAILURE;
		else if(value==1<<8)  return FailCause.PRC_OUTPUT_FAILURE;
		else if(value==1<<9)  return FailCause.STEP_OUTPUT_FAILURE;
		else if(value==1<<10) return FailCause.STL_OUTPUT_FAILURE;
		else if(value==1<<11) return FailCause.XT_OUTPUT_FAILURE;
		else if(value==1<<12) return FailCause.THREE_MF_OUTPUT_FAILURE;
		else if(value==1<<13) return FailCause.JT_OUTPUT_FAILURE;
		else if(value==1<<14) return FailCause.PDF_OUTPUT_FAILURE;
		else if(value==1<<15) return FailCause.SC_OUTPUT_FAILURE;
		else if(value==1<<16) return FailCause.XML_OUTPUT_FAILURE;
		else if(value==1<<17) return FailCause.HTML_OUTPUT_FAILURE;
		else if(value==1<<18) return FailCause.DEPENDENDIES_OUTPUT_FAILURE;
		else if(value==1<<19) return FailCause.XML_BOM_OUTPUT_FAILURE;
		else if(value==1<<20) return FailCause.CONFIG_FILE_OUTPUT_FAILURE;
		else if(value==1<<21) return FailCause.SHATTERED_OUTPUT_FAILURE;
		else return FailCause.UNKNOWN;
	}
	
	public static HoopsFailure toHoopsFailuer(String name) {
		switch(name) {
		case "GENERIC_FAILURE":
			return GENERIC_FAILURE;
		case "MODEL_LOAD_FAILURE":
			return MODEL_LOAD_FAILURE;
		case "INVALID_LICENSE":
			return INVALID_LICENSE;
		case "INITIALIZATION_FAILURE":
			return INITIALIZATION_FAILURE;
		case "NO_INPUT":
			return NO_INPUT;
		case "SC_MASTER_OUTPUT_FAILURE":
			return SC_MASTER_OUTPUT_FAILURE;
		case "PNG_OUTPUT_FAILURE":
			return PNG_OUTPUT_FAILURE;
		case "HSF_OUTPUT_FAILURE":
			return HSF_OUTPUT_FAILURE;
		case "PRC_OUTPUT_FAILURE":
			return PRC_OUTPUT_FAILURE;
		case "STEP_OUTPUT_FAILURE":
			return STEP_OUTPUT_FAILURE;
		case "STL_OUTPUT_FAILURE":
			return STL_OUTPUT_FAILURE;
		case "XT_OUTPUT_FAILURE":
			return XT_OUTPUT_FAILURE;
		case "THREE_MF_OUTPUT_FAILURE":
			return THREE_MF_OUTPUT_FAILURE;
		case "JT_OUTPUT_FAILURE":
			return JT_OUTPUT_FAILURE;
		case "PDF_OUTPUT_FAILURE":
			return PDF_OUTPUT_FAILURE;
		case "SC_OUTPUT_FAILURE":
			return SC_OUTPUT_FAILURE;
		case "XML_OUTPUT_FAILURE":
			return XML_OUTPUT_FAILURE;
		case "HTML_OUTPUT_FAILURE":
			return HTML_OUTPUT_FAILURE;
		case "DEPENDENDIES_OUTPUT_FAILURE":
			return DEPENDENDIES_OUTPUT_FAILURE;
		case "XML_BOM_OUTPUT_FAILURE":
			return XML_BOM_OUTPUT_FAILURE;
		case "CONFIG_FILE_OUTPUT_FAILURE":
			return CONFIG_FILE_OUTPUT_FAILURE;
		case "SHATTERED_OUTPUT_FAILURE":
			return SHATTERED_OUTPUT_FAILURE;
		default:
			return UNKNOWN;
		}
	}
	
	public static FailCause toGeneralFailCause(String name) {
		switch(name) {
		case "GENERIC_FAILURE":
			return FailCause.GENERIC_FAILURE;
		case "MODEL_LOAD_FAILURE":
			return FailCause.MODEL_LOAD_FAILURE;
		case "INVALID_LICENSE":
			return FailCause.INVALID_LICENSE;
		case "INITIALIZATION_FAILURE":
			return FailCause.INITIALIZATION_FAILURE;
		case "NO_INPUT":
			return FailCause.NO_INPUT;
		case "SC_MASTER_OUTPUT_FAILURE":
			return FailCause.SC_MASTER_OUTPUT_FAILURE;
		case "PNG_OUTPUT_FAILURE":
			return FailCause.PNG_OUTPUT_FAILURE;
		case "HSF_OUTPUT_FAILURE":
			return FailCause.HSF_OUTPUT_FAILURE;
		case "PRC_OUTPUT_FAILURE":
			return FailCause.PRC_OUTPUT_FAILURE;
		case "STEP_OUTPUT_FAILURE":
			return FailCause.STEP_OUTPUT_FAILURE;
		case "STL_OUTPUT_FAILURE":
			return FailCause.STL_OUTPUT_FAILURE;
		case "XT_OUTPUT_FAILURE":
			return FailCause.XT_OUTPUT_FAILURE;
		case "THREE_MF_OUTPUT_FAILURE":
			return FailCause.THREE_MF_OUTPUT_FAILURE;
		case "JT_OUTPUT_FAILURE":
			return FailCause.JT_OUTPUT_FAILURE;
		case "PDF_OUTPUT_FAILURE":
			return FailCause.PDF_OUTPUT_FAILURE;
		case "SC_OUTPUT_FAILURE":
			return FailCause.SC_OUTPUT_FAILURE;
		case "XML_OUTPUT_FAILURE":
			return FailCause.XML_OUTPUT_FAILURE;
		case "HTML_OUTPUT_FAILURE":
			return FailCause.HTML_OUTPUT_FAILURE;
		case "DEPENDENDIES_OUTPUT_FAILURE":
			return FailCause.DEPENDENDIES_OUTPUT_FAILURE;
		case "XML_BOM_OUTPUT_FAILURE":
			return FailCause.XML_BOM_OUTPUT_FAILURE;
		case "CONFIG_FILE_OUTPUT_FAILURE":
			return FailCause.CONFIG_FILE_OUTPUT_FAILURE;
		case "SHATTERED_OUTPUT_FAILURE":
			return FailCause.SHATTERED_OUTPUT_FAILURE;
		default:
			return FailCause.UNKNOWN;
		}
	}
}
