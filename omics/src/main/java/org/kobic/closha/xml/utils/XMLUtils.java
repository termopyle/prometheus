package org.kobic.closha.xml.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.kobic.closha.xml.model.PointsModel;
import org.kobic.closha.xml.model.XmlConnectLinkedModel;
import org.kobic.closha.xml.model.XmlDispatchModel;
import org.kobic.closha.xml.model.XmlModuleModel;
import org.kobic.closha.xml.model.XmlParameterModel;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class XMLUtils {

	public static XmlDispatchModel parserXML(String instance_pipeline_xml) {

		Document doc = null;

		SAXBuilder builder = new SAXBuilder();
		XmlDispatchModel xmlDispatchBean = new XmlDispatchModel();

		try {
			doc = builder.build(new StringReader(instance_pipeline_xml));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(doc);
		
		Element getRootElement = doc.getRootElement();
		xmlDispatchBean.setId(getRootElement.getAttributeValue("id"));
		xmlDispatchBean.setName(getRootElement.getAttributeValue("name"));
		xmlDispatchBean.setAuthor(getRootElement.getAttributeValue("author"));
		xmlDispatchBean.setVersion(getRootElement.getAttributeValue("version"));

		Element descElement = getRootElement.getChild("description");
		xmlDispatchBean.setDescription(descElement.getText());

		// module value setting
		Element getModulesElement = getRootElement.getChild("modules");
		List<Element> getModuleElement = getModulesElement.getChildren("module");

		List<XmlModuleModel> xmlModulesBeanList = new ArrayList<XmlModuleModel>(getModuleElement.size());

		for (Element moduleElement : getModuleElement) {
			XmlModuleModel xmlModuleModel = new XmlModuleModel();
			xmlModuleModel.setType(moduleElement.getAttributeValue("type"));
			xmlModuleModel.setModuleID(moduleElement.getAttributeValue("id"));
			xmlModuleModel.setKey(moduleElement.getAttributeValue("key"));
			xmlModuleModel.setModuleName(moduleElement.getAttributeValue("name"));
			xmlModuleModel.setImgSrc(moduleElement.getAttributeValue("img_src"));
			xmlModuleModel.setImgWidth(Integer.parseInt(moduleElement.getAttributeValue("img_width")));
			xmlModuleModel.setImgHeight(Integer.parseInt(moduleElement.getAttributeValue("img_height")));
			xmlModuleModel.setX(Integer.parseInt(moduleElement.getAttributeValue("x")));
			xmlModuleModel.setY(Integer.parseInt(moduleElement.getAttributeValue("y")));
			xmlModuleModel.setParameterNumber(moduleElement.getAttributeValue("parameter_number"));
			xmlModuleModel.setStatus(moduleElement.getAttributeValue("status"));
			xmlModuleModel.setModuleDesc(moduleElement.getText());
			xmlModuleModel.setUrl(moduleElement.getAttributeValue("url"));

			switch (Integer.parseInt(moduleElement.getAttributeValue("isTerminate"))) {
			case 1:
				xmlModuleModel.setTerminate(false);
			case 0:
				xmlModuleModel.setTerminate(true);
			}
			xmlModuleModel.setModuleAuthor(moduleElement.getAttributeValue("author"));
			xmlModuleModel.setVersion(moduleElement.getAttributeValue("version"));
			xmlModuleModel.setAppFormat(moduleElement.getAttributeValue("app_format"));

			if (xmlModuleModel.getAppFormat().equals("script")) {
				xmlModuleModel.setScriptPath(moduleElement.getAttributeValue("script"));
				xmlModuleModel.setLanguage(moduleElement.getAttributeValue("language"));
			} else {
				xmlModuleModel.setCmd(moduleElement.getAttributeValue("cmd"));
			}

			xmlModulesBeanList.add(xmlModuleModel);
		}

		// connection value setting
		Element getConnectionsElement = getRootElement.getChild("connections");
		List<Element> getConnectionElement = getConnectionsElement.getChildren("connection");

		List<XmlConnectLinkedModel> connectionsBeanList = new ArrayList<XmlConnectLinkedModel>(
				getConnectionElement.size());
		List<PointsModel> pList;
		for (Element connectionElement : getConnectionElement) {
			XmlConnectLinkedModel xmlConnectionModel = new XmlConnectLinkedModel();
			xmlConnectionModel.setId(connectionElement.getAttributeValue("id"));
			xmlConnectionModel.setKey(connectionElement.getAttributeValue("key"));
			xmlConnectionModel.setSourceId(connectionElement.getAttributeValue("source_id"));
			xmlConnectionModel.setSource(connectionElement.getAttributeValue("source"));
			xmlConnectionModel.setTargetId(connectionElement.getAttributeValue("target_id"));
			xmlConnectionModel.setTarget(connectionElement.getAttributeValue("target"));

			List<Element> getPointElement = connectionElement.getChildren("point");
			pList = new ArrayList<PointsModel>();

			for (Element pointElement : getPointElement) {
				PointsModel pm = new PointsModel();
				pm.setLeft(Integer.parseInt(pointElement.getAttributeValue("left")));
				pm.setTop(Integer.parseInt(pointElement.getAttributeValue("top")));
				pList.add(pm);
			}

			xmlConnectionModel.setPoints(pList);

			connectionsBeanList.add(xmlConnectionModel);
		}

		// parameter value setting
		Element getParametersElement = getRootElement.getChild("parameters");
		List<Element> getParameterElement = getParametersElement.getChildren("parameter");

		List<XmlParameterModel> parametersBeanList = new ArrayList<XmlParameterModel>(getParameterElement.size());

		for (Element parameterElement : getParameterElement) {
			XmlParameterModel xmlParameterModel = new XmlParameterModel();
			xmlParameterModel.setId(parameterElement.getAttributeValue("id"));
			xmlParameterModel.setKey(parameterElement.getAttributeValue("key"));
			xmlParameterModel.setName(parameterElement.getAttributeValue("name"));
			xmlParameterModel.setValue(parameterElement.getAttributeValue("value"));
			xmlParameterModel.setSetupValue(parameterElement.getAttributeValue("setup_value"));
			xmlParameterModel.setDescription(parameterElement.getAttributeValue("description"));
			xmlParameterModel.setType(parameterElement.getAttributeValue("type"));
			xmlParameterModel.setModule(parameterElement.getAttributeValue("module"));
			xmlParameterModel.setParameterType(parameterElement.getAttributeValue("parameter_type"));

			parametersBeanList.add(xmlParameterModel);
		}

		xmlDispatchBean.setModulesBeanList(xmlModulesBeanList);
		xmlDispatchBean.setConnectionsBeanList(connectionsBeanList);
		xmlDispatchBean.setParametersBeanList(parametersBeanList);

		return xmlDispatchBean;
	}

	public static String updateXML(XmlDispatchModel xmlDispatchModel) {

		Element rootElement;
		Element desciptionElement;
		Element modulesElement;
		Element moduleElement;
		Element connectionsElement;
		Element parametersElement;

		String xml = "";

		rootElement = new Element("closha");
		rootElement.setAttribute("id", xmlDispatchModel.getId());
		rootElement.setAttribute("name", xmlDispatchModel.getName());
		rootElement.setAttribute("author", xmlDispatchModel.getAuthor());
		rootElement.setAttribute("version", xmlDispatchModel.getVersion());

		desciptionElement = new Element("description");
		desciptionElement.addContent(xmlDispatchModel.getDescription());

		modulesElement = new Element("modules");
		connectionsElement = new Element("connections");
		parametersElement = new Element("parameters");

		rootElement.addContent(desciptionElement);
		rootElement.addContent(modulesElement);
		rootElement.addContent(connectionsElement);
		rootElement.addContent(parametersElement);

		for (XmlModuleModel xm : xmlDispatchModel.getModulesBeanList()) {

			moduleElement = new Element("module");
			moduleElement.setAttribute("type", xm.getType());
			moduleElement.setAttribute("id", xm.getModuleID());
			moduleElement.setAttribute("key", xm.getKey());
			moduleElement.setAttribute("name", xm.getModuleName());
			moduleElement.setAttribute("img_src", xm.getImgSrc());
			moduleElement.setAttribute("img_width", String.valueOf(xm.getImgWidth()));
			moduleElement.setAttribute("img_height", String.valueOf(xm.getImgHeight()));
			moduleElement.setAttribute("x", String.valueOf(xm.getX()));
			moduleElement.setAttribute("y", String.valueOf(xm.getY()));
			moduleElement.setAttribute("parameter_number", xm.getParameterNumber());
			moduleElement.setAttribute("status", xm.getStatus());
			if (xm.isTerminate()) {
				moduleElement.setAttribute("isTerminate", "0");
			} else {
				moduleElement.setAttribute("isTerminate", "1");
			}
			moduleElement.setAttribute("author", xm.getModuleAuthor());
			moduleElement.setAttribute("version", xm.getVersion());
			moduleElement.setAttribute("app_format", xm.getAppFormat());

			if (xm.getAppFormat().equals("script")) {
				moduleElement.setAttribute("script", xm.getScriptPath());
				moduleElement.setAttribute("language", xm.getLanguage());
			} else {
				moduleElement.setAttribute("cmd", xm.getCmd());
			}

			moduleElement.setAttribute("url", xm.getUrl());

			moduleElement.setText(xm.getModuleDesc());
			modulesElement.addContent(moduleElement);
		}

		for (XmlConnectLinkedModel xc : xmlDispatchModel.getConnectionsBeanList()) {
			Element connectionElement = new Element("connection");
			connectionElement.setAttribute("id", xc.getId());
			connectionElement.setAttribute("key", xc.getKey());
			connectionElement.setAttribute("source", xc.getSource());
			connectionElement.setAttribute("target", xc.getTarget());
			connectionElement.setAttribute("source_id", xc.getSourceId());
			connectionElement.setAttribute("target_id", xc.getTargetId());

			for (PointsModel pm : xc.getPoints()) {
				Element pointElement = new Element("point");
				pointElement.setAttribute("left", String.valueOf(pm.getLeft()));
				pointElement.setAttribute("top", String.valueOf(pm.getTop()));
				connectionElement.addContent(pointElement);
			}

			connectionsElement.addContent(connectionElement);
		}

		for (XmlParameterModel xp : xmlDispatchModel.getParametersBeanList()) {

			Element parameterElement = new Element("parameter");
			parameterElement.setAttribute("id", xp.getId());
			parameterElement.setAttribute("module", xp.getModule());
			parameterElement.setAttribute("key", xp.getKey());
			parameterElement.setAttribute("name", xp.getName());
			parameterElement.setAttribute("value", xp.getValue());
			parameterElement.setAttribute("setup_value", xp.getSetupValue());
			parameterElement.setAttribute("description", xp.getDescription());
			parameterElement.setAttribute("type", xp.getType());
			parameterElement.setAttribute("parameter_type", xp.getParameterType());
			parametersElement.addContent(parameterElement);
		}

		// write XML format
		Document doc = new Document(rootElement);

		XMLOutputter out = new XMLOutputter();
		Format f = out.getFormat();
		f.setEncoding("euc-kr");
		f.setLineSeparator("\n");
		f.setIndent("  ");
		f.setTextMode(Format.TextMode.TRIM);
		out.setFormat(f);

		try {
			xml = out.outputString(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xml;
	}
	
	public static Map<Integer, XmlModuleModel> convertModulesListToMap(List<XmlModuleModel> modules){
		Map<Integer, XmlModuleModel> moduleMap = new HashMap<Integer, XmlModuleModel>();
		for(XmlModuleModel xm : modules){
			System.out.println(Integer.parseInt(xm.getKey()) + ":" + xm.getModuleName());
			moduleMap.put(Integer.parseInt(xm.getKey()), xm);
		}
		return moduleMap;
	}
	
	public static Map<String, XmlModuleModel> convertModulesList(List<XmlModuleModel> modules){
		Map<String, XmlModuleModel> moduleMap = new HashMap<String, XmlModuleModel>();
		for(XmlModuleModel xm : modules){
			moduleMap.put(xm.getKey(), xm);
		}
		return moduleMap;
	}
	
	public static ListMultimap<Integer, Integer> convertConnectionListToListMultiMap(List<XmlConnectLinkedModel> connections){
		
		ListMultimap<Integer, Integer> multiMap = ArrayListMultimap.create();
		
		for(XmlConnectLinkedModel connection : connections){
			System.out.println(Integer.parseInt(connection.getSource()) + "->" + Integer.parseInt(connection.getTarget()));
			multiMap.put(Integer.parseInt(connection.getSource()), 
					Integer.parseInt(connection.getTarget()));
		}
		return multiMap;
	}
	
	public static ListMultimap<String, XmlParameterModel> convertParameterListToListMultiMap(List<XmlParameterModel> parameters){
		
		ListMultimap<String, XmlParameterModel> multiMap = ArrayListMultimap.create();
		
		for(XmlParameterModel parameter : parameters){
			multiMap.put(parameter.getKey(), parameter);
		}
		
		return multiMap;
	}
	
	public static List<XmlConnectLinkedModel> getConnectionModelData(String source, List<XmlConnectLinkedModel> connectionModelList){
		
		List<XmlConnectLinkedModel> collectList = new ArrayList<XmlConnectLinkedModel>();
		
		for(XmlConnectLinkedModel xc : connectionModelList){
			if(xc.getSource().equals(source)){
				collectList.add(xc);
			}
		}
		return collectList;
	}
	
	public static List<XmlParameterModel> getParametersModelData(Integer key, List<XmlParameterModel> parameters){
		
		List<XmlParameterModel> collectList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xp : parameters){
			if(xp.getKey().equals(String.valueOf(key))){
				collectList.add(xp);
			}
		}
		
		return collectList;
	}
	
	public static List<XmlParameterModel> getParametersModelData(String key, List<XmlParameterModel> parameters){
		
		List<XmlParameterModel> collectList = new ArrayList<XmlParameterModel>();
		
		for(XmlParameterModel xp : parameters){
			if(xp.getKey().equals(key)){
				collectList.add(xp);
			}
		}
		
		return collectList;
	}
}