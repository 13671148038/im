package com.kaiyun.service.impl;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.STAlignV;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHpsMeasure;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHint;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaiyun.dao.ShareTimeDao;
import com.kaiyun.dao.TaskDao;
import com.kaiyun.dao.TaskResultDao;
import com.kaiyun.dao.TaskSubmitRecordDao;
import com.kaiyun.dao.TimeSectionDao;
import com.kaiyun.dao.WordDao;
import com.kaiyun.pojo.Task;
import com.kaiyun.pojo.TaskResult;
import com.kaiyun.pojo.TaskSubmitRecord;
import com.kaiyun.pojo.Word;
import com.kaiyun.service.PhoneUserService;
import com.kaiyun.service.TaskService;
import com.kaiyun.until.DateUtil;
import com.kaiyun.until.RandomUUID;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	public TaskDao taskDao;
	@Autowired
	public TimeSectionDao timeSectionDao;
	@Autowired
	public PhoneUserService phoneUserService;
	@Autowired
	private TaskResultDao taskResultDao;

	@Autowired
	private ShareTimeDao shareTimeDao;

	@Autowired
	private WordDao wordDao;

	@Autowired
	private TaskSubmitRecordDao taskSubmitRecordDao;

	@Value("${taskVersion}")
	private String taskVersion;

	@Override
	public void analysis(InputStream inputStream, String biaoshi) throws Exception {
		// 添加word表中的信息
		String wordId = RandomUUID.random();
		String time = DateUtil.getTime();
		wordDao.add(new Word(wordId, time));

		List<String> column = new ArrayList<>();
		column.add("columnOne");
		column.add("columnTwo");
		column.add("columnThree");
		column.add("columnFour");
		int orderColumn = 1;
		if ("1".equals(biaoshi)) {
			XWPFDocument xwpf = new XWPFDocument(inputStream);// 得到word文档的信息
			Iterator<XWPFTable> tablesIterator = xwpf.getTablesIterator();
			while (tablesIterator.hasNext()) {
				XWPFTable table = tablesIterator.next();
				List<XWPFTableRow> rows = table.getRows();
				// 获取任务类型
				XWPFTableRow typeXwpfTableRow = rows.get(0);
				List<XWPFTableCell> typeCell = typeXwpfTableRow.getTableCells();
				XWPFTableCell typeXwpfTableCell = typeCell.get(1);
				String typeText = typeXwpfTableCell.getText();
				int indexOf = typeText.indexOf(")");
				if (indexOf == -1) {
					indexOf = typeText.indexOf("）");
				}
				typeText = typeText.substring(1, indexOf); // 任务类型名称

				// 获取有多少个空格
				XWPFTableRow xwpfTableRow2 = rows.get(2);
				List<XWPFTableCell> tableCells2 = xwpfTableRow2.getTableCells();
				int numTaskCells = tableCells2.size();
				numTaskCells = numTaskCells - 4;

				Map<String, Object> taskMap = new HashMap<>(); // 每一行的数据
				taskMap.put("wordId", wordId);
				for (int i = 2; i < rows.size(); i++) {
					XWPFTableRow xwpfTableRow = rows.get(i);
					List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();

					if (i == (rows.size() - 1)) { // 最后一行就是备注那一行
						XWPFTableCell xwpfTableCell = tableCells.get(1);
						String text = xwpfTableCell.getText().replaceAll("\r\n|\r|\n", "");
						taskMap.put("id", RandomUUID.random());
						taskMap.put(column.get(0), "备注");
						taskMap.put("remark", text);
						taskMap.put("type", typeText);
						taskMap.put("createTime", time);
						taskMap.put("orderColumn", orderColumn);
						saveTask(taskMap);
						orderColumn++;
					} else {
						for (int j = 0; j < tableCells.size() - numTaskCells; j++) {
							XWPFTableCell xwpfTableCell = tableCells.get(j);
							String text = xwpfTableCell.getText().replaceAll("\r\n|\r|\n", "");
							if (StringUtils.isNotBlank(text)) {
								taskMap.put(column.get(j), text);
							}
							int columnNum = tableCells.size() - numTaskCells;
							int cha = 4 - columnNum;
							if (cha > 0) {
								for (int a = 4; a > columnNum; a--) {
									taskMap.remove(column.get(a - 1));
								}
							}
						}
						taskMap.put("id", RandomUUID.random());
						taskMap.put("type", typeText);
						taskMap.put("createTime", time);
						taskMap.put("orderColumn", orderColumn);
						orderColumn++;
						saveTask(taskMap);
					}
				}
			}
		} else {
			HWPFDocument hwpf = new HWPFDocument(inputStream);// 得到word文档的信息
			Range range = hwpf.getRange();
			TableIterator tt = new TableIterator(range);
			while (tt.hasNext()) {
				Table table = tt.next();
				int numRows = table.numRows();
				// 获取任务类型
				TableRow typeRow = table.getRow(0);
				TableCell typeCell = typeRow.getCell(1);
				String typeText = typeCell.text();
				int indexOf = typeText.indexOf(")");
				if (indexOf == -1) {
					indexOf = typeText.indexOf("）");
				}
				typeText = typeText.substring(1, indexOf); // 任务类型名称
				// 获取后面的空格有多少个
				TableRow firstRow = table.getRow(2);
				int numTaskCells = firstRow.numCells();
				numTaskCells = numTaskCells - 4;

				Map<String, Object> taskMap = new HashMap<>(); // 每一行的数据
				taskMap.put("wordId", wordId);
				// 遍历任务
				for (int i = 2; i < numRows; i++) {
					TableRow row = table.getRow(i);
					int numCells = row.numCells();
					if (i == (numRows - 1)) { // 最后一行就是备注那一行
						TableCell cell = row.getCell(1);
						taskMap.put("id", RandomUUID.random());
						taskMap.put(column.get(0), "备注");
						taskMap.put("remark", cell.text().replace("", "").replaceAll("\r\n|\r|\n", ""));
						taskMap.put("type", typeText);
						taskMap.put("createTime", time);
						taskMap.put("orderColumn", orderColumn);
						saveTask(taskMap);
						orderColumn++;
					} else {

						for (int j = 0; j < numCells - numTaskCells; j++) {
							TableCell cell = row.getCell(j);
							String text = cell.text().replace("", "").replaceAll("\r\n|\r|\n", "");
							if (StringUtils.isNotBlank(text)) {
								taskMap.put(column.get(j), text);
							}
							int columnNum = numCells - numTaskCells;
							int cha = 4 - columnNum;
							if (cha > 0) {
								for (int a = 4; a > columnNum; a--) {
									taskMap.remove(column.get(a - 1));
								}
							}
						}
						taskMap.put("id", RandomUUID.random());
						taskMap.put("type", typeText);
						taskMap.put("createTime", time);
						taskMap.put("orderColumn", orderColumn);
						orderColumn++;
						saveTask(taskMap);
					}
				}
			}
		}
		// 更新版本信息
		phoneUserService.updateVersion(taskVersion);
	}

	/**
	 * 保存任务
	 * 
	 * @param task
	 */
	private void saveTask(Map<String, Object> task) {
		taskDao.add(task);
	}

	/**
	 * 获取任务的类型
	 */
	@Override
	public List<String> getTaskType() {
		List<String> list = taskDao.getTaskType();
		return list;
	}

	/**
	 * 根据任务类型查询任务
	 */
	@Override
	public List<List<String>> getTaskByType(String type, String wordId) {
		// 如果wordId是空,默认查询最新的任务
		if (StringUtils.isBlank(wordId)) {
			wordId = wordDao.getTopNew();
		}
		// 定义返回的list
		List<List<String>> result = new ArrayList<>();
		List<Task> taskList = taskDao.getTaskByType(type, wordId);
		for (Task task : taskList) {
			List<String> list = new ArrayList<>();
			String columnOne = task.getColumnOne();
			String columnTwo = task.getColumnTwo();
			String columnThree = task.getColumnThree();
			String columnFour = task.getColumnFour();
			String id = task.getId();
			if (null != columnOne) {
				list.add(columnOne);
			}
			if (null != columnTwo) {
				list.add(columnTwo);
			}
			if (null != columnThree) {
				list.add(columnThree);
			}
			if (null != columnFour) {
				list.add(columnFour);
			}
			if (null != id) {
				list.add(id);
			}
			result.add(list);
		}
		return result;
	}

	/**
	 * 根据id查询任务的备注信息
	 */
	@Override
	public String getTaskRemarkById(String id) {
		String remark = taskDao.getTaskRemarkById(id);
		return remark;
	}

	/**
	 * 新增选择时间
	 * 
	 * @throws ParseException
	 */
	@Override
	public void updateStartTime(String startTime, String taskType) throws ParseException {
		Calendar instance = Calendar.getInstance();
		Map<String, String> timeSection = new HashMap<>();
		// 查询最新的wordid
		String wordId = wordDao.getTopNew();
		if (StringUtils.isNotBlank(wordId)) {
			timeSection.put("wordId", wordId);
			Date date = DateUtil.getDate2(startTime);
			instance.setTime(date);
			int startYear = instance.get(Calendar.YEAR);
			if ("周".equals(taskType)) {
				// 获取开始时间
				// 获取毫秒值
				long startTimeMS = date.getTime();
				// 定义一周的毫秒值
				Long dayMS = 1000 * 60 * 60 * 24 * 7l;
				timeSection.put("type", taskType);
				for (int i = 0; i < 60; i++) {
					Date date2 = new Date(startTimeMS + i * dayMS);
					instance.setTime(date2);
					int currentYear = instance.get(Calendar.YEAR);
					if (startYear != currentYear) {
						break;
					}
					timeSection.put("id", RandomUUID.random());
					timeSection.put("time", DateUtil.getDay(date2));
					timeSectionDao.add(timeSection);
				}
			} else if ("月".equals(taskType)) {
				instance.setTime(date);
				int day_of_week = instance.get(Calendar.DAY_OF_WEEK);
				if (day_of_week == 1) {
					day_of_week = 7;
				} else {
					day_of_week = day_of_week - 1;
				}
				int month = instance.get(Calendar.MONTH) + 1;
				int year = instance.get(Calendar.YEAR);
				timeSection.put("type", taskType);
				for (int i = 0; i < 24; i++) {
					if (month > 12) {
						year++;
						month = 1;
						break;
					}
					String m = month + "";
					m = m.length() < 2 ? ('0' + m) : m;
					Date date2 = DateUtil.getDate2(year + "-" + m + "-03");
					String lastWeekDate = DateUtil.getLastWeekDate(date2, day_of_week);
					timeSection.put("time", lastWeekDate);
					timeSection.put("id", RandomUUID.random());
					timeSectionDao.add(timeSection);
					month++;
				}
			} else {
				timeSection.put("type", taskType);
				timeSection.put("time", startTime);
				timeSection.put("id", RandomUUID.random());
				timeSectionDao.add(timeSection);
			}

		}

	}

	/**
	 * 手机端获取所有的任务
	 */
	@Override
	public List<Task> getAllTask() {
		List<Task> allTask = taskDao.getAllTask();
		return allTask;
	}

	/**
	 * 手机端获取所有的任务
	 */
	@Override
	public Map<String, Object> getAllTaskByType() {
		Map<String, Object> result = new HashMap<>();
		List<String> taskType = taskDao.getTaskType();
		for (String type : taskType) {
			List<Task> list = taskDao.getTaskByType(type, wordDao.getTopNew());
			result.put(type, list);
		}
		return result;
	}

	/**
	 * 检查结果分页查询
	 */
	@Override
	public Map<String, Object> getCheckResult(Map<String, Object> condition) {

		Map<String, Object> result = new HashMap<>();

		String taskIds = (String) condition.get("taskIds");
		Gson gson = new Gson();
		List<String> taskIdList = gson.fromJson(taskIds, List.class); // 将任务的ids
																		// 变为集合
		String wordId = (String) condition.get("wordId");
		if (StringUtils.isBlank(wordId)) {
			wordId = wordDao.getTopNew();
		}
		String type = (String) condition.get("type");
		Integer pageNumber = (Integer) condition.get("pageNumber");
		Integer pageSize = (Integer) condition.get("pageSize");
		int parseInt2 = Integer.parseInt(DateUtil.getDay().replaceAll("-", ""));
		// 计算当前的提交的任务在第几页
		if (pageNumber == null) {
			// 记录最后一个小于当前时间的索引
			int bs = 0;
			List<String> list = timeSectionDao.getAll(type, wordId);
			for (int i = 0; i < list.size(); i++) {
				int parseInt = Integer.parseInt(list.get(i).replaceAll("-", ""));
				if (parseInt <= parseInt2) {
					bs++;
				}
			}
			if (bs == 0) {
				pageNumber = 1;
			} else {
				pageNumber = (bs + 6) / 7;
			}

		}
		// 查询当前任务日期
		Date nowDate = new Date();
		Long h = nowDate.getTime() + (24 * 60 * 60 * 1000);
		nowDate = new Date(h);
		String day2 = DateUtil.getDay(nowDate);
		// 获取当前时间
		String maxTimeAndXiaoYuNow = timeSectionDao.getMaxTimeAndXiaoYuNow(day2, type, wordId);

		Page<Object> startPage = PageHelper.startPage(pageNumber, pageSize);
		List<String> timeList = timeSectionDao.getPageDate(type, null, wordId);
		result.put("pageNumber", pageNumber);
		result.put("total", startPage.getTotal());
		result.put("pages",startPage.getPages());
        result.put("timeList", timeList); // 检查结果的日期集合
		// 定义没有提交的个数
		double notSubmit = 0;
		// 全部任务
		double size = taskIdList.size();
		// 定义检查结果map
		Map<String, List<String>> m = new HashMap<>();
		// 遍历所有的任务的id
		for (String taskId : taskIdList) {
			List<String> taskCheckResult = new ArrayList<String>(); // 定义每个任务的不同日期的检查结果存储的map
			for (String checkTime : timeList) { // 遍历当前日期集合
				condition.put("taskId", taskId);
				condition.put("checkTime", checkTime);
				// 根据任务id和时间查询检查结果
				String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
				if (StringUtils.isBlank(checkResult)) {
					// 计算未提交的个数
					if (checkTime.equals(maxTimeAndXiaoYuNow)) {
						notSubmit++;
					}
					checkResult = "";
				}
				taskCheckResult.add(checkResult);
			}
			m.put(taskId, taskCheckResult);
		}
		result.put("checkResult", m);

		int index = 0;
		boolean contains = false;
		for (int i = 0; i < timeList.size(); i++) {
			if (timeList.get(i).equals(maxTimeAndXiaoYuNow)) {
				contains = true;
				index = i;
			}
		}
		if (contains) {
			// 计算百分比
			DecimalFormat df = new DecimalFormat("#.00");
			if (size == 0.0) {
				result.put("bfb", "0%"); // 完成百分比
			} else {
				double bfb = (size - notSubmit) / size;
				String format = df.format(bfb);
				StringBuffer sb = new StringBuffer(format);
				sb.deleteCharAt(sb.indexOf("."));
				double parseDouble = Double.parseDouble(sb.toString());
				sb.setLength(0);
				sb.append(parseDouble + "");
				sb.delete(sb.indexOf("."), sb.length());
				sb.append("%");
				timeList.set(index, timeList.get(index) + "完成比(" + sb.toString() + ")");
			}

		}
		return result;
	}

	/**
	 * 手机端任务提交
	 */
	@Override
	public void submitTask(Map<String, String> contentMap) {
		String taskId = contentMap.get("taskId");
		String checkResult = contentMap.get("checkResult");
		if (checkResult == null) {
			checkResult = "";
		}
		String userName = contentMap.get("userName");
		Date nowDate = new Date();
		Long h = nowDate.getTime() + (24 * 60 * 60 * 1000);
		nowDate = new Date(h);
		String day2 = DateUtil.getDay(nowDate);

		// 获取任务类型
		Task task = taskDao.getTaskById(taskId);
		String type = task.getType();
		String wordId = task.getWordId();
		// 获取当前时间
		String maxTimeAndXiaoYuNow = timeSectionDao.getMaxTimeAndXiaoYuNow(day2, type, wordId);
		// 如果时间不是空
		if (StringUtils.isNotBlank(maxTimeAndXiaoYuNow)) {
			Map<String, Object> condition = new HashMap<>();
			condition.put("checkTime", maxTimeAndXiaoYuNow);
			condition.put("taskId", taskId);
			TaskResult result = taskResultDao.getByTaskIdAndCheckTime2(condition);
			TaskResult tr = null;
			String submitTime = DateUtil.getTime();
			if (result != null) {
				result.setSubmitTime(submitTime);
				result.setCheckResult(checkResult);
				result.setUserName(userName);
				taskResultDao.update(result);
			} else {
				tr = new TaskResult();
				tr.setId(RandomUUID.random());
				tr.setTaskId(taskId);
				tr.setSubmitTime(submitTime);
				tr.setCheckTime(maxTimeAndXiaoYuNow);
				tr.setCheckResult(checkResult);
				tr.setUserName(userName);
				tr.setType(task.getType());
				tr.setWordId(task.getWordId());
				taskResultDao.add(tr);
			}
			// 添加提交记录
			taskSubmitRecordDao
					.add(new TaskSubmitRecord(userName, taskId, submitTime, maxTimeAndXiaoYuNow, type, checkResult));
		}
	}

	/**
	 * 批量提交结果
	 */
	@Override
	public void submitTasks(String tasks) {
		Gson g = new Gson();
		Type listType = new TypeToken<List<TaskResult>>() {
		}.getType();
		List<TaskResult> taskList = (List<TaskResult>) g.fromJson(tasks, listType);
		Map<String, String> contentMap = new HashMap<String, String>();
		for (TaskResult taskResult : taskList) {
			contentMap.put("taskId", taskResult.getTaskId());
			contentMap.put("checkResult", taskResult.getCheckResult());
			contentMap.put("userName", taskResult.getUserName());
			submitTask(contentMap);
		}
	}

	/**
	 * 获取最新的检查结果
	 */
	@Override
	public List<TaskResult> getNewResult() {
		List<TaskResult> result = new ArrayList<>();
		Date nowDate = new Date();
		Long h = nowDate.getTime() + (24 * 60 * 60 * 1000);
		nowDate = new Date(h);
		String day2 = DateUtil.getDay(nowDate);
		List<String> taskType = taskDao.getTaskType();
		List<TaskResult> list = null;
		String workId = wordDao.getTopNew();
		for (String type : taskType) {
			String maxTimeAndXiaoYuNow = timeSectionDao.getMaxTimeAndXiaoYuNow(day2, type, workId);
			list = taskResultDao.getNewResult(maxTimeAndXiaoYuNow, type, workId);
			result.addAll(list);
		}
		return result;
	}

	/**
	 * 添加同步时间
	 */
	@Value("${checkResultVersion}")
	private String checkResultVersion;

	@Override
	public String updateShareTime() {
		String time = DateUtil.getTime();
		Map<String, Object> map = new HashMap<>();
		map.put("id", RandomUUID.random());
		map.put("shareTime", time);
		shareTimeDao.addShareTime(map);
		// 更新版本信息
		phoneUserService.updateVersion(checkResultVersion);
		return time;
	}

	/**
	 * 获取最新的同步时间
	 */
	@Override
	public String getMaxShareTime() {
		String maxShareTime = shareTimeDao.getMaxShareTime();
		return maxShareTime;
	}

	/**
	 * 重置时间
	 * 
	 * @param content
	 * @throws ParseException
	 */
	@Override
	public void resetTime(Map<String, String> content) throws ParseException {
		String type = content.get("type");
		String resetTime = content.get("resetTime");
		String taskType = content.get("taskType");
		// 获取当前任务
		String wordId = wordDao.getTopNew();
		content.put("wordId", wordId);
		if ("1".equals(type)) { // 只重置一个日期
			timeSectionDao.resetTime(content);
		} else if ("2".equals(type)) { // 批量重置
			// 查询选择的时间后面一共有多少个数据
			List<String> list = timeSectionDao.selectResetTimeAfterNum(content);
			Date date2 = DateUtil.getDate2(resetTime);
			if ("周".equals(taskType)) {
				long time2 = date2.getTime();
				for (int i = 0; i < list.size(); i++) {
					String id = list.get(i);
					String day = DateUtil.getDay(new Date(time2 + i * 1000 * 60 * 60 * 24 * 7l));
					content.put("id", id);
					content.put("resetTime", day);
					timeSectionDao.resetTimeById(content);
				}
			} else if ("月".equals(taskType)) {
				Calendar instance = Calendar.getInstance();
				instance.setTime(date2);
				int day_of_week = instance.get(Calendar.DAY_OF_WEEK);
				if (day_of_week == 1) {
					day_of_week = 7;
				} else {
					day_of_week = day_of_week - 1;
				}
				int month = instance.get(Calendar.MONTH) + 1;
				int year = instance.get(Calendar.YEAR);
				for (int i = 0; i < list.size(); i++) {
					String id = list.get(i);
					if (month > 12) {
						year++;
						month = 1;
					}
					String m = month + "";
					m = m.length() < 2 ? ('0' + m) : m;
					Date date3 = DateUtil.getDate2(year + "-" + m + "-03");
					String lastWeekDate = DateUtil.getLastWeekDate(date3, day_of_week);
					month++;
					content.put("id", id);
					content.put("resetTime", lastWeekDate);
					timeSectionDao.resetTimeById(content);
				}
			} else {
				for (int i = 0; i < list.size(); i++) {
					content.put("id", list.get(i));
					timeSectionDao.resetTimeById(content);
				}
			}
		}
	}

	/**
	 * 查询导出的数据
	 */
	@Override
	public XWPFDocument getImportResultData(String wordId) {
		XWPFDocument document = new XWPFDocument();
		if (StringUtils.isBlank(wordId)) {
			wordId = wordDao.getTopNew();
		}
		List<String> taskType = taskDao.getTaskTypeByWordId(wordId);
		Map<String, Object> condition = new HashMap<>();
		condition.put("wordId", wordId);
		for (String type : taskType) {
			condition.put("type", type);
			condition.put("time", DateUtil.getDay());
			// 标题段落
			String titleParagraphStr = type + " 维  护  检  查  登  记  表";
			XWPFParagraph titleParagraph = document.createParagraph();
			// 设置居中
			titleParagraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun titleParagraphStrRun = titleParagraph.createRun();
			titleParagraphStrRun.setText(titleParagraphStr);
			titleParagraphStrRun.setFontSize(20);

			// 查询到到当前日期之前的全部时间段
			List<String> timeList = timeSectionDao.getAlltimeXiaoYuNowByWordIdAndType(condition);
			// 一页显示七条检查结果, 计算一共几页
			int size = timeList.size();
			int pageNum = (size + 6) / 7;
			// 定义集合用来存储每页时间的list集合
			List<List<String>> allPageList = new ArrayList<>();
			for (int i = 0; i < pageNum; i++) {
				// 每页显示的时间集合
				List<String> pageList = new ArrayList<>();
				for (int j = 0; j < 7; j++) {
					int index = i * 7 + j;
					if (index < size) {
						pageList.add(timeList.get(index));
					}
				}
				allPageList.add(pageList);
			}
			
			//查询任务
			List<List<String>> taskList = getTaskByType(type, wordId);
			
			// 遍历allPageList,每一个元素代表一页数据
			for (int i = 0; i < allPageList.size(); i++) {
				List<String> list = allPageList.get(i);
				// 创建表格 创建的table会自带一行一列忽视不要理它就行
				XWPFTable table = document.createTable();
				CTTcPr newTcPr = null;
				XWPFTableRow row = null;
				XWPFTableCell cell = null;
				CTP p = null;
				CTPPr newPPr = null;

				// 第一行
				row = table.createRow();
				// 第一列 创建行的时候会自带一列,多以调用get方法获取第一列 后面的列使用create方法创建列
				cell = row.getCell(0);
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewVMerge().setVal(STMerge.RESTART); // 向下合并
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				newTcPr.addNewTcW().setW(new BigInteger("621")); // 宽度
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("序号");

				// 第二列
				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewGridSpan().setVal(new BigInteger("2")); // 左右合并
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("(" + type + ")检查内容");

				// 第三列
				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				newTcPr.addNewTcW().setW(new BigInteger("1044")); // 宽度
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("技术要求");
				// 第四列
				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				// TODO 这里是查询的日期集合中的个数就是检查结果中合并的个数
				newTcPr.addNewGridSpan().setVal(new BigInteger(list.size()+"")); // 左右合并
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				newTcPr.addNewTcW().setW(new BigInteger("4844")); // 宽度
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("检查结果");

				// 第二行
				row = table.createRow();
				// 第一列
				cell = row.getCell(0);
				newTcPr = cell.getCTTc().addNewTcPr();
				// 这个方法的意思:如果有行合并的话就加这个
				newTcPr.addNewVMerge();
				// 第二列
				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewGridSpan().setVal(new BigInteger("3")); // 列合并
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("日期");

				for (int j = 0; j < list.size(); j++) {
					cell = row.createCell();
					newTcPr = cell.getCTTc().addNewTcPr();
					newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
					p = cell.getCTTc().getPArray(0);
					newPPr = p.addNewPPr();
					newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
					cell.setText(list.get(j));
				}
				
				
				String firstColumnOne="";
				for (int j = 0; j < taskList.size(); j++) {
					List<String> task = taskList.get(j);
					//任务id
					String taskId = task.remove(task.size()-1);
					condition.put("taskId", taskId);
					int size2 = task.size();
					String string = task.get(0);
					//创建行
					row = table.createRow();
					if(!string.equals(firstColumnOne)){
						firstColumnOne=string;
						cell = row.getCell(0);
						newTcPr = cell.getCTTc().addNewTcPr();
						if(size2==1){
							newTcPr.addNewGridSpan().setVal(new BigInteger("4")); // 左右合并
						}
						newTcPr.addNewVMerge().setVal(STMerge.RESTART); // 向下合并
						newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
						p = cell.getCTTc().getPArray(0);
						newPPr = p.addNewPPr();
						newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
						cell.setText(task.get(0));
						if(size2==4){
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVMerge().setVal(STMerge.RESTART); // 向下合并
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(1));
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(2));
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(3));
							
							for (int k = 0; k < list.size(); k++) {
								condition.put("checkTime", list.get(k));
								String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
								//这是检查结果
								cell = row.createCell();
								newTcPr = cell.getCTTc().addNewTcPr();
								newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
								p = cell.getCTTc().getPArray(0);
								newPPr = p.addNewPPr();
								newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
								cell.setText(checkResult);
							}
						}else if(size2==3){
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewGridSpan().setVal(new BigInteger("2")); // 左右合并
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(1));
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(2));
							
							for (int k = 0; k < list.size(); k++) {
								condition.put("checkTime", list.get(k));
								String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
								//这是检查结果
								cell = row.createCell();
								newTcPr = cell.getCTTc().addNewTcPr();
								newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
								p = cell.getCTTc().getPArray(0);
								newPPr = p.addNewPPr();
								newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
								cell.setText(checkResult);
							}
						}else if(size2==1){
							for (int k = 0; k < list.size(); k++) {
								condition.put("checkTime", list.get(k));
								String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
								//这是检查结果
								cell = row.createCell();
								newTcPr = cell.getCTTc().addNewTcPr();
								newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
								p = cell.getCTTc().getPArray(0);
								newPPr = p.addNewPPr();
								newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
								cell.setText(checkResult);
							}
						}
					}else{
						
						cell = row.getCell(0);
						if(size2==4){
							cell = row.getCell(0);
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVMerge();
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVMerge();
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(2));
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(3));
							
							for (int k = 0; k < list.size(); k++) {
								condition.put("checkTime", list.get(k));
								String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
								//这是检查结果
								cell = row.createCell();
								newTcPr = cell.getCTTc().addNewTcPr();
								newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
								p = cell.getCTTc().getPArray(0);
								newPPr = p.addNewPPr();
								newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
								cell.setText(checkResult);
							}
						}else if(size2==3){
							
							cell = row.getCell(0);
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVMerge();
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewGridSpan().setVal(new BigInteger("2")); // 左右合并
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(1));
							
							cell = row.createCell();
							newTcPr = cell.getCTTc().addNewTcPr();
							newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
							p = cell.getCTTc().getPArray(0);
							newPPr = p.addNewPPr();
							newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
							cell.setText(task.get(2));
							
							for (int k = 0; k < list.size(); k++) {
								condition.put("checkTime", list.get(k));
								String checkResult = taskResultDao.getByTaskIdAndCheckTime(condition);
								//这是检查结果
								cell = row.createCell();
								newTcPr = cell.getCTTc().addNewTcPr();
								newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
								p = cell.getCTTc().getPArray(0);
								newPPr = p.addNewPPr();
								newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
								cell.setText(checkResult);
							}
						}
						
					}
				}
				
				// 第三列
			/*	row = table.createRow();
				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("sdsdsd");

				cell = row.createCell();
				newTcPr = cell.getCTTc().addNewTcPr();
				newTcPr.addNewVAlign().setVal(STVerticalJc.CENTER); // 上下居中
				newTcPr.addNewTcW().setW(new BigInteger("1089")); // 宽度
				p = cell.getCTTc().getPArray(0);
				newPPr = p.addNewPPr();
				newPPr.addNewJc().setVal(STJc.CENTER); // 左右居中
				cell.setText("sdsdsd收到");

				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();
				cell = row.createCell();*/

			}

		}
		return document;
	}
}
