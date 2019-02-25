package org.kobic.omics.main.vo;

import java.io.Serializable;

public class MainVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String moduleID;
	private String ontologyID;
	private String appFormat;
	private String language;
	private String scriptPath;
	private String cmd;
	private String moduleName;
	private String moduleDesc;
	private String moduleAuthor;
	private String registerDate;
	private String updateDate;
	private String parameter;
	private String linkedKey;
	private String version;
	private String icon;
	private String type;
	private String ontologyName;

	public String getOntologyName() {
		return ontologyName;
	}
	public void setOntologyName(String ontologyName) {
		this.ontologyName = ontologyName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public String getOntologyID() {
		return ontologyID;
	}
	public void setOntologyID(String ontologyID) {
		this.ontologyID = ontologyID;
	}
	public String getAppFormat() {
		return appFormat;
	}
	public void setAppFormat(String appFormat) {
		this.appFormat = appFormat;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getScriptPath() {
		return scriptPath;
	}
	public void setScriptPath(String scriptPath) {
		this.scriptPath = scriptPath;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	public String getModuleAuthor() {
		return moduleAuthor;
	}
	public void setModuleAuthor(String moduleAuthor) {
		this.moduleAuthor = moduleAuthor;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public String getLinkedKey() {
		return linkedKey;
	}
	public void setLinkedKey(String linkedKey) {
		this.linkedKey = linkedKey;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}