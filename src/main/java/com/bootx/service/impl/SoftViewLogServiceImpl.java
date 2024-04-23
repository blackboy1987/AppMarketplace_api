package com.bootx.service.impl;

import com.bootx.dao.SoftViewLogDao;
import com.bootx.entity.SoftViewLog;
import com.bootx.service.SoftViewLogService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SoftViewLogServiceImpl extends BaseServiceImpl<SoftViewLog, Long> implements SoftViewLogService {

	@Resource
	private SoftViewLogDao softViewLogDao;

	@Override
	@Async
	public void create(SoftViewLog softViewLog) {
		softViewLogDao.persist(softViewLog);
	}

	@Override
	public void clear() {
		softViewLogDao.removeAll();
	}

}