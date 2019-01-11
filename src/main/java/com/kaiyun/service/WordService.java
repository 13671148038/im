package com.kaiyun.service;

import java.util.Map;

import com.kaiyun.pojo.Word;
import com.kaiyun.until.PageUntil;

public interface WordService {

	PageUntil<Word> getPageDate(Map<String, Object> condition);

	void deleteWord(String id);

}
