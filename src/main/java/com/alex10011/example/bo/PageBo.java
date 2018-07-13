package com.alex10011.example.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 分页参数
 */
@ApiModel(value="用户参数")
public class PageBo implements Serializable  {
	@ApiModelProperty(value = "分页，开始", example = "1")
	private Integer start = 1;
	@ApiModelProperty(value = "分页，行数", example = "10")
	private Integer limit = 10;
	@ApiModelProperty(value = "当前登录用户ID", hidden = true)
	private Integer userId;

	// 取原始值，getStart会做运算
	public Integer getOldStart() {
		return start;
	}

	/**
	 * POST会调用两次，为什么呢 不能对 start 和 limit进行变更
	 * 
	 * @return
	 */
	public Integer getStart() {
		int tmp = this.start;
		if (tmp <= 1) {
			tmp = 0;
		} else {
			tmp = (tmp - 1) * this.limit;
		}
		return tmp;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
