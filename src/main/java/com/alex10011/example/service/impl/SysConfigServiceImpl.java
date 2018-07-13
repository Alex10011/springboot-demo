package com.alex10011.example.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import com.alex10011.example.constant.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alex10011.example.constant.GlobalConstant;
import com.alex10011.example.dao.SysConfigDao;
import com.alex10011.example.domain.SysConfig;
import com.alex10011.example.service.SysConfigService;
import com.alex10011.example.util.SvcUtil;

@Service
public class SysConfigServiceImpl implements SysConfigService {

	private static final Logger log = LoggerFactory.getLogger(SysConfigServiceImpl.class);

	@Autowired
	SysConfigDao payGateConfigDao;

	// 给支付网关PayGateConfig配置对象中的静态属性赋值
	@Override
	public void initConfig() {
		// 获取所有支付网关表中所有数据
		List<SysConfig> list = payGateConfigDao.queryAll();
		if (list != null && list.size() > 0) {
			// 配置数据放入对象
			for (SysConfig payGateConfig : list) {
				// 反射注入
				try {
					// 给支付网关配置对象的静态属性赋值
					SvcUtil.setStaticValue(GlobalConstant.class, payGateConfig.getKey(), payGateConfig.getValue());
				} catch (Exception e) {
					log.error("给支付网关配置对象GlobalConstant的静态属性赋值失败" + payGateConfig.getKey() + "="
							+ payGateConfig.getValue(), e);
				}
			}
		}

		// 赋值完成后，检测下是否有空参数，如果有给出警告提示开发人员配置config
		String alarm = "";
		try {
			Field[] fields = GlobalConstant.class.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				if (Modifier.isStatic(field.getModifiers())) {
					Object obj = field.get(null);

					// System.out.println(field.getName() + "-> " + obj);
					if (field.getType() == String.class) {
						String str = (String) obj;
						if (StringUtils.isBlank(str) || str.equals("null")) {
							if (alarm.length() > 1) {
								alarm += "，";
							}
							alarm += field.getName();
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		if (StringUtils.isNotBlank(alarm)) {
			log.error("***************************************");
			log.error(">>警告：有配置参数没有从数据库中得到：" + alarm);
			log.error("***************************************");
		}
	}

}
