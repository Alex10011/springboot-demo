package com.alex10011.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alex10011.example.dao.CityDao;
import com.alex10011.example.domain.City;
import com.alex10011.example.service.CityService;

/**
 * 城市业务逻辑实现类
 * <p>
 * Created by alex10011 on 07/02/2017.
 */
@Service
public class CityServiceImpl implements CityService {

	private final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
	private CityDao cityDao;

	/**
	 * 获取城市逻辑： 如果缓存存在，从缓存中获取城市信息 如果缓存不存在，从 DB 中获取城市信息，然后插入缓存
	 */
	@Cacheable(value = "city", key = "T(String).valueOf(#id).concat('_city')")
	public City findCityById(Long id) {
		// 从 DB 中获取城市信息
		City city = cityDao.findById(id);
		System.out.println("取数据库");

		return city;
	}

	/**
	 * 更新城市逻辑： 如果缓存存在，删除 如果缓存不存在，不操作
	 */
	@Override
	@CacheEvict(value = "city", key = "T(String).valueOf(#id).concat('_city')") // 移除指定key的数据
	public Long updateCity(City city) {
		Long ret = cityDao.updateCity(city);

		return ret;
	}

	@Override
	@CacheEvict(value = "city", key = "T(String).valueOf(#id).concat('_city')") // 移除指定key的数据
	public Long deleteCity(Long id) {
		Long ret = cityDao.deleteCity(id);

		return ret;
	}

	@Override
	public Long saveCity(City city) {
		return cityDao.saveCity(city);
	}
}
