package com.toone.msproject.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

//import com.xyy.TaskBean;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Relation;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;
import net.sf.mpxj.mspdi.MSPDIReader;

/**
 * @Author xuyangyang
 * @Describe ���� project
 * @Date 2017/3/24
 * @Params
 * @Return
 */
public class MppUtil {

	/**
	 * �����ĸ���Id
	 **/
	private static final int TOP_PARENTID = 0;
	/**
	 * �����Ĳ��
	 **/
	private static final int TOP_LEVEL = 1;

	/**
	 * ��������mpp�ļ���ŵ�·��
	 **/
	private static final String FILE_PATH = "D:/work/workspace/msproject/���봴��.mpp";

	/**
	 * ��ȡ��Ŀ�ļ�
	 *
	 * @param fileName
	 *            �ļ��� ����·��
	 * @return
	 * @throws FileNotFoundException
	 */
	private static ProjectFile readProject(String fileName) throws FileNotFoundException {
		InputStream is = new BufferedInputStream(new FileInputStream(fileName));
		is.mark(0);// ������Ҫ�ظ�ʹ�����������������°�װ���������ñ��

		ProjectFile mpx = null;
		try {
			mpx = new MPXReader().read(is);
		} catch (Exception ex) {
			try {
				is.reset();// ����
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (mpx == null) {
			try {
				mpx = new MPPReader().read(is);
			} catch (Exception ex) {
				try {
					is.reset();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (mpx == null) {
			try {
				mpx = new MSPDIReader().read(is);
			} catch (Exception ex) {
			}
		}
		return mpx;
	}

	/**
	 * ��Ŀ�ļ�--��ȡ�����б�
	 *
	 * @throws FileNotFoundException
	 * @Author xuyangyang
	 * @Describe
	 * @Date 2017/3/24
	 * @Params fileName ��ȡ.mpp�ļ�·��
	 * @Return
	 */
	public static List<SchProjectTask> getTaskList(String fileName) throws FileNotFoundException {
		ProjectFile file = readProject(fileName);

		// SchProject schProject = new SchProject();
		// schProject.setName(file.getProjectHeader().getProjectTitle());
		// schProject.setAuthor(file.getProjectHeader().getAuthor());
		// schProject.setVersion(0);

		List<Task> tasks = file.getChildTasks();
		List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
		if (!tasks.isEmpty()) {
			Task msTask = tasks.get(TOP_PARENTID);
			schProjectTaskList = listHierarchy(msTask, TOP_PARENTID);
		}
		return schProjectTaskList;
	}

	/**
	 * ��ȡ�����������
	 *
	 * @param parentId
	 *            ������Id
	 * @Author xuyangyang
	 * @Describe
	 * @Date 2017/3/24
	 * @Params task ����
	 * @Return
	 */

	private static List<SchProjectTask> listHierarchy(Task msTask, int parentId) {
		List<Task> childTasks = msTask.getChildTasks();
		List<SchProjectTask> schProjectTaskList = new ArrayList<SchProjectTask>();
		SchProjectTask schProjectTask = null;
		if (!childTasks.isEmpty()) {
			for (Task task : childTasks) {
				schProjectTaskList.add(getTaskBean(schProjectTask, task, parentId));
				schProjectTaskList.addAll(listHierarchy(task, task.getID()));
			}
		}
		return schProjectTaskList;
	}

	/**
	 * ��ȡ����ľ����ֶ�
	 *
	 * @Author xuyangyang
	 * @Describe
	 * @Date 2017/3/24
	 * @Params schProjectTask ����Bean
	 * @Params task ����
	 * @Params parentId ����Id
	 * @Return schProjectTask bean;
	 */
	private static SchProjectTask getTaskBean(SchProjectTask schProjectTask, Task task, Integer parentId) {
		schProjectTask = new SchProjectTask();
		schProjectTask.setRecordId(task.getID().toString());
		schProjectTask.setUniqueId(task.getUniqueID().toString());
		schProjectTask.setParentId(parentId.toString());
		schProjectTask.setLevel(task.getOutlineLevel());
		schProjectTask.setName(task.getName());
		schProjectTask.setDuration(task.getDuration().getDuration());
		schProjectTask.setDurationTimeUnit(task.getDuration().getUnits().getName());
		schProjectTask.setStartTime(task.getStart());
		schProjectTask.setFinishTime(task.getFinish());
		schProjectTask.setPercentageComplete(task.getPercentageComplete());
		schProjectTask.setLevel(task.getOutlineLevel());
		schProjectTask.setId(task.getGUID()+"");
//		List<Relation> task_predecessors = task.getPredecessors();

		// 4. ��ȡǰ��������������
//		StringBuilder beforeTaskId = new StringBuilder();
//		StringBuilder beforeTaskType = new StringBuilder();
//		if (task_predecessors != null) {
//			if (task_predecessors.size() > 0) {
//				for (Relation relation : task_predecessors) {
//					Integer targetTaskId = relation.getTargetTask().getID();
//					if (beforeTaskId.length() == 0) {
//						beforeTaskId.append(targetTaskId);
//						beforeTaskType.append(relation.getType());
//					} else {
//						beforeTaskId.append("," + targetTaskId);
//						beforeTaskType.append("," + relation.getType());
//					}
//				}
//			}
//		}
//		String task_predecessors_str = beforeTaskId.toString();
//		String task_predecessors_str_type = beforeTaskType.toString();
//		schProjectTask.setPredecessors(task_predecessors_str);
//		schProjectTask.setType(task_predecessors_str_type);

		schProjectTask.setResource(getResources(task));
		// 6.�Զ����ж�ȡ
		//String task_operator = task.getText(2);

		List<String> preLists = new ArrayList<String>();

		List<Relation> predecessors = task.getPredecessors();
		if (predecessors != null && predecessors.isEmpty() == false) {
			for (Relation relation : predecessors) {
				Task tragetTask = relation.getTargetTask();
				Integer targetTaskRecordId = tragetTask.getID();
				Integer targetTaskUniqueId = tragetTask.getUniqueID();
				String m_type = relation.getType().toString();// SS///FS
				String m_lag = relation.getLag().toString();// 10.0d//0.0d//-35.0d

				// if (m_type.equals("FS")){m_type = "";}
				// if (m_lag.equals("0.0d")){m_lag="";}else if(m_lag.equals("")){
				// }else if (!m_lag.startsWith("-") && !m_lag.equals("0.0d")){m_lag="+"+m_lag;
				// }
				// String s = " ������";
				// String predecessor;
				// if (m_type.equals("") || m_lag.equals("")){predecessor =
				// targetRaskId.toString();
				// } else {predecessor = targetRaskId+m_type+m_lag+s;
				// }

				String predecessor = targetTaskRecordId + ":" + targetTaskUniqueId + ":" + m_type + ":" + m_lag;
				preLists.add(predecessor);
			}
		}
		schProjectTask.setPredecessors(listToString(preLists));
		return schProjectTask;
	}

	/**
	 * List תΪ String
	 * 
	 * @Author xuyangyang
	 * @Describe
	 * @Date 2017/3/24
	 * @Params List ���󼯺�
	 * @Return String
	 */
	public static String listToString(List list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}

	/**
	 * ����mpp�ļ�
	 *
	 * @param taskBeanList
	 *            �����б�
	 * @throws Exception
	 */

	public static void createMppFile(List<TaskBean> taskBeanList) throws Exception {
		File file = new File(FILE_PATH);
		if (file.exists()) {
			file.delete();
		}
		if (taskBeanList != null && taskBeanList.size() > 0) {
			ActiveXComponent app = null;
			try {
				app = new ActiveXComponent("MSProject.Application");
				app.setProperty("Visible", new Variant(false));
				Dispatch projects = app.getProperty("Projects").toDispatch();
				Dispatch project = Dispatch.call(projects, "Add").toDispatch();// ���һ����Ŀ
				Dispatch tasks = Dispatch.get(project, "Tasks").toDispatch();// ����һ��task����
				// ����Task
				TaskBean topTaskBean = getTopTaskBean(taskBeanList);
				createTreeTable(tasks, topTaskBean, TOP_LEVEL, taskBeanList);
				// ���Ϊ
				Dispatch.invoke(project, "SaveAs", Dispatch.Method, new Object[] { FILE_PATH, new Variant(0) },
						new int[1]);
				System.out.println("�����ɹ�");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("����ʧ��");
			} finally {
				if (app != null)
					app.invoke("Quit", new Variant[] {});
			}
		}
	}

	/**
	 * �������νṹ
	 *
	 * @param tasks
	 *            ���񼯺�
	 * @param taskBean
	 *            ����Bean
	 * @param level
	 *            ���
	 * @param taskBeanList
	 *            �����б�
	 */
	private static void createTreeTable(Dispatch tasks, TaskBean taskBean, int level, List<TaskBean> taskBeanList) {
		Dispatch task = Dispatch.call(tasks, "Add").toDispatch();
		setTaskValue(task, taskBean, level);
		List<TaskBean> sonTaskBeanList = getSonTaskBean(taskBeanList, taskBean);
		if (!sonTaskBeanList.isEmpty()) {
			for (TaskBean sonTaskBean : sonTaskBeanList) {
				createTreeTable(tasks, sonTaskBean, level + 1, taskBeanList);
			}
		}
	}

	/**
	 * ��ȡ���е�������
	 *
	 * @param taskBeanList
	 *            �����б�
	 * @param parentTaskBean
	 *            ��������Bean
	 * @return
	 */
	private static List<TaskBean> getSonTaskBean(List<TaskBean> taskBeanList, TaskBean parentTaskBean) {
		List<TaskBean> sonTaskBeanList = new ArrayList<TaskBean>();
		for (TaskBean taskBean : taskBeanList) {
			if (taskBean.getParentId().equals(parentTaskBean.getRecordId())) {
				sonTaskBeanList.add(taskBean);
			}
		}
		return sonTaskBeanList;
	}

	/**
	 * ��ȡ��������
	 *
	 * @param taskBeanList
	 *            �����б�
	 * @return
	 */
	private static TaskBean getTopTaskBean(List<TaskBean> taskBeanList) {
		for (TaskBean taskBean : taskBeanList) {
			if (Integer.parseInt(taskBean.getParentId()) == TOP_PARENTID)
				return taskBean;
		}
		return null;
	}

	/**
	 * ��������������
	 *
	 * @param task
	 *            ����ָ��
	 * @param taskBean
	 *            ����Bean
	 * @param level
	 *            ���
	 */
	private static void setTaskValue(Dispatch task, TaskBean taskBean, int level) {
		Dispatch.put(task, "Name", taskBean.getName());
		// Dispatch.put(task, "Duration",Duration.getInstance(5, TimeUnit.DAYS));
		Dispatch.put(task, "Start", taskBean.getStartTime());
		Dispatch.put(task, "Finish", taskBean.getFinishTime());
		// Dispatch.put(task, "Duration",taskBean.getDuration());//�Զ�����
		// Dispatch.put(task,
		// "PercentageComplete",NumberUtility.getDouble(taskBean.getPercentageComplete()));
		Dispatch.put(task, "OutlineLevel", level);
		Dispatch.put(task, "ResourceNames", taskBean.getResource());
	}

	/**
	 * ��ȡ�����е���Դ
	 *
	 * @param task
	 *            ����
	 * @return
	 */
	private static String listTaskRes(Task task) {
		StringBuffer buf = new StringBuffer();
		List<ResourceAssignment> assignments = task.getResourceAssignments();// ��ȡ������Դ�б�
		if (assignments != null && !assignments.isEmpty()) {
			ResourceAssignment assignment = (ResourceAssignment) assignments.get(0);// ֻ��ȡ��һ����Դ
			Resource resource = assignment.getResource();
			if (resource != null)
				buf.append(resource.getName());
		}
		return buf.toString();
	}
	 private static String getResources(Task task){
	        if(task == null){
	            return "";
	        }
	        StringBuffer sb = new StringBuffer();
	        List<ResourceAssignment> assignments = task.getResourceAssignments();
	        for(ResourceAssignment ra : assignments){
	            Resource resource = ra.getResource();
	            if(resource != null){
	                sb = sb.append(resource.getName());
	            }
	        }
	        return sb.toString();
	    }
	public static void main(String[] args) throws Exception {
		// Project project=MppUtil.getTaskList("D:/������ѧ¥������.mpp");
		// List<TaskBean>taskBeanList=project.getTaskBeanList();
		// System.out.println(taskBeanList.size());
		// for(TaskBean task:taskBeanList){
		// System.out.println(task);
		// }
		// createMppFile(taskBeanList);
		List<TaskBean> beanList = new ArrayList<TaskBean>();
		// ����mpp
		List<SchProjectTask> taskBeanList = MppUtil.getTaskList("D:/work/workspace/msproject/ԭʼ.mpp");
		for (SchProjectTask task : taskBeanList) {
			System.out.println(task);

			TaskBean bean = new TaskBean();
			// bean.setId(task.getId());
			// bean.setName(task.getName());
			// bean.setParentId(task.getParentId());
			// bean.setResource("");
			// bean.setStartTime(task.getStartTime());
			// bean.setFinishTime(task.getFinishTime());
			if (task.getId() == null || task.getId().equals("null") || task.getId().equals(""))
				task.setId(task.getRecordId());
			BeanUtils.copyProperties(bean, task);
			beanList.add(bean);
		}
		System.out.println("beanList size=" + beanList.size());
		createMppFile(beanList);
	}
}
