package com.sds.n3dx.conversion.worker;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class HoopsConverterOptions implements DrawingConverterOptions {

	private final Map<String, String> options;

	/**
	 * 옵션 입력되는 순서대로 생성되도록 LinkedHashMap 으로 처리.
	 */
	public HoopsConverterOptions() {
		this(new LinkedHashMap<>());
	}

	public HoopsConverterOptions(final Map<String, String> options) {
		this.options = options;
	}

	public Path getInput() {
		return this.getPathOption("input");
	}

	public Path getOutputSc() {
		return this.getPathOption("output_sc");
	}

	public Path getOutputScMaster() {
		return this.getPathOption("output_sc_master");
	}

	public Path getOutputXmlAssemblyTree() {
		return this.getPathOption("output_xml_assemblytree");
	}

	public Path getPrepareShatteredParts() {
		return this.getPathOption("prepare_shattered_parts");
	}

	public boolean isScCreateScz() {
		return Boolean.parseBoolean(this.getOption("sc_create_scz"));
	}

	/**
	 * 0: only 3D, 1: only drawings, 2: Both 3D and drawing. Default: 1
	 * @param drawingsMode
	 */
	public void setDrawingsMode(final String drawingsMode) {
		this.setOption("drawings_mode", drawingsMode);
	}

	public void setInput(final Path input) {
		this.setOption("input", input);
	}

	public void setInputXmlShattered(final Path inputXmlShattered) {
		this.setOption("input_xml_shattered", inputXmlShattered);
	}

	public void setLicense(final String license) {
		this.setOption("license", license);
	}

	public void setOutputDependencies(final Path outputDependencies) {
		this.setOption("output_dependencies", outputDependencies);
	}

	public void setOutputPng(final Path outputPng) {
		this.setOption("output_png", outputPng);
	}
	
	public void setOutputPngResolution(String resolution) {
		this.setOption("output_png_resolution", resolution);
	}

	public void setOutputPrc(final Path outputPrc) {
	    this.setOption("output_prc", outputPrc);
	}

	public void setOutputSc(final Path outputSc) {
		this.setOption("output_sc", outputSc);
	}

	public void setOutputScMaster(final Path outputScMaster) {
		this.setOption("output_sc_master", outputScMaster);
	}

	public void setOutputXmlAssemblyTree(final Path outputXmlAssemblyTree) {
	    this.setOption("output_xml_assemblytree", outputXmlAssemblyTree);
	}

	public void setPngTransparentBackground(final Boolean pngTransparentBackground) {
		this.setOption("png_transparent_background", pngTransparentBackground);
	}

	public void setPrepareShatteredParts(final Path prepareShatteredParts) {
		this.setOption("prepare_shattered_parts", prepareShatteredParts);
	}

	public void setPrepareShatteredXml(final Path prepareShatteredXml) {
		this.setOption("prepare_shattered_xml", prepareShatteredXml);
	}

	public void setScExportAttributes(final Boolean scExportAttributes) {
		this.setOption("sc_export_attributes", scExportAttributes);
	}

	public void setScExportMaterials(final Boolean scExportMaterials) {
		this.setOption("sc_export_materials", scExportMaterials);
	}

	public void setScShatteredPartsDirectory(final Path scShatteredPartsDirectory) {
	    this.setOption("sc_shattered_parts_directory", scShatteredPartsDirectory);
	}
	
	public void setOutputLogFile(final Path outputLogfile) {
		this.setOption("output_logfile", outputLogfile);
	}

	public Map<String, String> toMap() {
		return Collections.unmodifiableMap(this.options);
	}

	/**
	 * name의 option을 리턴한다.
	 *
	 * @param name 이름.
	 * @return 값.
	 */
	protected String getOption(final String name) {
	    return this.options.get(name);
	}

	/**
	 * options를 일괄적으로 반영한다.
	 *
	 * @param options 반영할 options.
	 */
	protected void putOptions(final Map<String, String> options) {
		for (final Entry<String, String> option : options.entrySet()) {
			this.setOption(option.getKey(), option.getValue());
		}
	}

	protected void setOption(final String name, final Object value) {
	    if (value == null) this.options.remove(name);
	    else this.options.put(name, value.toString());
	}

	private Path getPathOption(final String name) {
		final String option = this.getOption(name);

	    return option == null ? null : Paths.get(option); // NOSONAR
	}
}
