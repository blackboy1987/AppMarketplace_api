package com.bootx.service.impl;

import com.bootx.dao.SoftDownloadLogDao;
import com.bootx.entity.SoftDownloadLog;
import com.bootx.service.SoftDownloadLogService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author black
 */
@Service
public class SoftDownloadLogServiceImpl extends BaseServiceImpl<SoftDownloadLog, Long> implements SoftDownloadLogService {

	@Resource
	private SoftDownloadLogDao softDownloadLogDao;

	@Override
	@Async
	public void create(SoftDownloadLog softDownloadLog) {
		softDownloadLogDao.persist(softDownloadLog);
	}

	@Override
	public void clear() {
		softDownloadLogDao.removeAll();
	}

}