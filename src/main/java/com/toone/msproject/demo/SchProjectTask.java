package com.toone.msproject.demo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 14258 on 2017/3/24. ����Task
 */
public class SchProjectTask implements Serializable {
	private static final long serialVersionUID = -3323529750871923312L;
	/**
	 * uuid
	 */
	private String id;
	/**
	 * ���id
	 */
	private String recordId;
	/**
	 * Ψһid
	 */
	private String uniqueId;
	/**
	 * ���ڵ���
	 **/
	private String parentId;
	/**
	 * ��������
	 **/
	private String name;
	/**
	 * ���ˮƽ
	 */
	private Integer level;
	/**
	 * ����
	 **/
	private Number duration;
	/**
	 * ���ڵ�λ
	 */
	private String durationTimeUnit;
	/**
	 * ��ʼʱ��
	 **/
	private Date startTime;
	/**
	 * ����ʱ��
	 **/
	private Date finishTime;
	/**
	 * ��ɰٷֱ�
	 **/
	private Number percentageComplete;
	/**
	 * ǰ������/������
	 **/
	private String predecessors;
	/**
	 * ������Ŀ�ļ�id
	 */
	private String ProId;
	/**
	 * �������ͣ�FS,SS,FF,SF��
	 */
	private String type;
	/**
	 * ��Դ
	 */
	private String resource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Number getDuration() {
		return duration;
	}

	public void setDuration(Number duration) {
		this.duration = duration;
	}

	public String getDurationTimeUnit() {
		return durationTimeUnit;
	}

	public void setDurationTimeUnit(String durationTimeUnit) {
		this.durationTimeUnit = durationTimeUnit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Number getPercentageComplete() {
		return percentageComplete;
	}

	public void setPercentageComplete(Number percentageComplete) {
		this.percentageComplete = percentageComplete;
	}

	public String getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(String predecessors) {
		this.predecessors = predecessors;
	}

	public String getProId() {
		return ProId;
	}

	public void setProId(String proId) {
		ProId = proId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	// ����дset��get
	// ����дtoStirng����
	public String toString() {
		return "id=" + id + ",���id=" + recordId + ",Ψһid=" + uniqueId + ",���ڵ���=" + parentId + ",��������=" + name
				+ ",���ˮƽ=" + level + ",����=" + duration + ",���ڵ�λ=" + durationTimeUnit + ",��ʼʱ��=" + startTime + ",����ʱ��="
				+ finishTime + ",��ɰٷֱ�=" + percentageComplete + ",ǰ������=" + predecessors + ",������Ŀ�ļ�id=" + ProId
				+ ",��������=" + type + ",��Դ=" + resource;
	}
}
