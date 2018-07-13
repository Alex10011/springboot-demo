package com.alex10011.example.service;

import com.alex10011.example.domain.City;
import com.alex10011.example.domain.City;

//采用redis+mybatis演示带缓存的业务流程
public interface CityService {
	/**
	 * 根据城市 ID,查询城市信息
	 *
	 * @param id
	 * @return
	 */
	City findCityById(Long id);

	/**
	 * 新增城市信息
	 *
	 * @param city
	 * @return
	 */
	Long saveCity(City city);

	/**
	 * 更新城市信息
	 *
	 * @param city
	 * @return
	 */
	Long updateCity(City city);

	/**
	 * 根据城市 ID,删除城市信息
	 *
	 * @param id
	 * @return
	 */
	Long deleteCity(Long id);
}
