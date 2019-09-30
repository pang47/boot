package com.taoboot.mini.service.impl;


import com.taoboot.mini.core.dao.impl.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务基类
 */
public class BaseServiceImpl {
	@Autowired
	private BaseDao baseDao;

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

}
