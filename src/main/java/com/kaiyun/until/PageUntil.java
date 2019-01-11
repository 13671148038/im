package com.kaiyun.until;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Think
 */
public class PageUntil<T> implements Serializable{
	
	//查询条件
	Map<String, Object> selectConditionMap = null;

    //总条数
    private Long total;
    //总页数
    private Long totalPage;
    //当前页
    private Integer currentPage;
    //每页显示条数
    private Integer pageCount;
    //条数集合
    private List<T> rows = new ArrayList<T>();

    
    public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	

    public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalPage() {
    	return (total + pageCount - 1) / pageCount;
	}

	

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

	public Map<String, Object> getSelectConditionMap() {
		return selectConditionMap;
	}

	public void setSelectConditionMap(Map<String, Object> selectConditionMap) {
		this.selectConditionMap = selectConditionMap;
	}
    

}
