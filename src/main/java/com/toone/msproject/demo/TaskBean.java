package com.toone.msproject.demo;
import java.util.Date;

public class TaskBean {
	/**
	 * uuid
	 */
	private String id;
	/**
	 * 编号id
	 */
	private String recordId;
	/**
	 * 唯一id
	 */
	private String uniqueId;
	/**
	 * 父节点编号
	 **/
	private String parentId;
	/**
	 * 任务名称
	 **/
	private String name;
	/**
	 * 大纲水平
	 */
	private Integer level;
	/**
	 * 工期
	 **/
	private Number duration;
	/**
	 * 工期单位
	 */
	private String durationTimeUnit;
	/**
	 * 开始时间
	 **/
	private Date startTime;
	/**
	 * 结束时间
	 **/
	private Date finishTime;
	/**
	 * 完成百分比
	 **/
	private Number percentageComplete;
	/**
	 * 前置任务
	 **/
	private String predecessors;
	/**
	 * 所属项目文件id
	 */
	private String ProId;
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

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

}
