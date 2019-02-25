package org.kobic.closha.xml.model;

import java.io.Serializable;
import java.util.List;

public class XmlConnectLinkedModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String key;
	private String sourceId;
	private String targetId;
	private String source;
	private String target;
	private List<PointsModel> points;

	public XmlConnectLinkedModel() {
	}

	public XmlConnectLinkedModel(String id, String key, String sourceId, String targetId, String source, String target,
			List<PointsModel> points) {
		this.id = id;
		this.key = key;
		this.sourceId = sourceId;
		this.targetId = targetId;
		this.source = source;
		this.target = target;
		this.points = points;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<PointsModel> getPoints() {
		return points;
	}

	public void setPoints(List<PointsModel> points) {
		this.points = points;
	}

}