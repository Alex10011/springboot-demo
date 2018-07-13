package com.alex10011.example.bo;

import javax.validation.constraints.NotNull;

import com.alex10011.example.annonation.ValidCron;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "cron表达式")
public class Bo_Cron implements Bo_Interface {
	@ApiModelProperty(value = "cron表达式")
	@ValidCron
	@NotNull()
	private String cron;

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

}
