package com.kaiyun.dao;

import java.util.List;
import java.util.Map;

import com.kaiyun.pojo.Word;

public interface WordDao {

	void add(Word word);

	String getTopNew();

	List<Word> getPageDate(Map<String, Object> condition);

	void deleteById(String id);

}
