package com.kaiyun.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.kaiyun.dao.TaskDao;
import com.kaiyun.dao.TaskResultDao;
import com.kaiyun.dao.TaskSubmitRecordDao;
import com.kaiyun.dao.TimeSectionDao;
import com.kaiyun.dao.WordDao;
import com.kaiyun.pojo.TaskResult;
import com.kaiyun.pojo.User;
import com.kaiyun.pojo.Word;
import com.kaiyun.service.WordService;
import com.kaiyun.until.PageUntil;

@Service
public class WordServiceImpl implements WordService {
	
	@Autowired
	private WordDao wordDao;
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskResultDao taskResultDao;
	@Autowired
	private TaskSubmitRecordDao taskSubmitRecordDao;
	@Autowired
	private TimeSectionDao timeSectionDao;

	/**
	 * 分页查询
	 */
	@Override
	public PageUntil<Word> getPageDate(Map<String, Object> condition) {
		Integer currentPage = (Integer)condition.get("currentPage");
		Integer pageSize = (Integer)condition.get("pageSize");
		Page<Word> page = PageHelper.startPage(currentPage,pageSize);
		List<Word> list = wordDao.getPageDate(condition);
		PageUntil<Word> pu = new PageUntil<>();
		pu.setCurrentPage(currentPage);
		pu.setPageCount(pageSize);
		pu.setRows(list);
		pu.setTotal(page.getTotal());
		return pu;
	}

	/**
	 * 删除word
	 */
	@Override
	public void deleteWord(String wordId) {
		//word 删除
		wordDao.deleteById(wordId);
		//任务 删除
		List<String> list = taskDao.getIdByWordId(wordId);
		taskDao.deleteByWordId(wordId);
		//删除提交记录
		for (String taskId : list) {
			taskSubmitRecordDao.deleteByTaskId(taskId);
		}
		//任务结果  删除
		taskResultDao.deleteByWordId(wordId);
		//时间段删除
		timeSectionDao.deleteByWordId(wordId);
	}

}
