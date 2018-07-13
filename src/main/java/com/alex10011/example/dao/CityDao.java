package com.alex10011.example.dao;

import java.util.List;

import com.alex10011.example.domain.City;
import org.apache.ibatis.annotations.Param;
import com.alex10011.example.domain.City;

/**
 * 城市 DAO 接口类
 *
 * Created by alex10011 on 07/02/2017.
 */
public interface CityDao {

	/**
	 * 获取城市信息列表
	 *
	 * @return
	 */
	List<City> findAllCity();

	/**
	 * 根据城市 ID，获取城市信息
	 *
	 * @param id
	 * @return
	 */
	City findById(@Param("id") Long id);

	Long saveCity(City city);

	Long updateCity(City city);

	Long deleteCity(Long id);
}
