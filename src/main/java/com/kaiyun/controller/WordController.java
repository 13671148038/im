package com.kaiyun.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kaiyun.pojo.User;
import com.kaiyun.pojo.Word;
import com.kaiyun.service.WordService;
import com.kaiyun.until.PageUntil;

@Controller
@RequestMapping("word")
public class WordController {
	
	@Autowired
	private WordService wordService;

	/**
	 * word界面跳转
	 * @param mv
	 * @return
	 */
	@RequestMapping("toWordList")
	public ModelAndView toWordList(ModelAndView mv){
		mv.setViewName("task/wordList");
		return mv;
	}
	
	/**
	 * 分页查询
	 */
	@RequestMapping("ajax/getPageDate")
	@ResponseBody
	public PageUntil<Word> getPageDate(Integer page,Integer rows){
		PageUntil<Word> dataPage = new PageUntil<>();
		try {
			Map<String, Object> condition = new HashMap<>();
			condition.put("currentPage", page);
			condition.put("pageSize", rows);
			dataPage = wordService.getPageDate(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataPage;
	}
	
	@RequestMapping("ajax/deleteWord")
	@ResponseBody
	public String deleteWord(String id){
		String result = "SUCCESS";
		try {
			wordService.deleteWord(id);
		} catch (Exception e) {
			e.printStackTrace();
			result="ERROR";
		}
		return result;
	}
	
}
