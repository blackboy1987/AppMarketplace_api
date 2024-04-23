package com.bootx.service.impl;

import com.bootx.dao.SoftViewLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftViewLog;
import com.bootx.service.SoftViewLogService;
import com.bootx.util.IPUtils;
import com.bootx.util.WebUtils;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author black
 */
@Service
public class SoftViewLogServiceImpl extends BaseServiceImpl<SoftViewLog, Long> implements SoftViewLogService {

	@Resource
	private SoftViewLogDao softViewLogDao;


	@Override
	public void clear() {
		softViewLogDao.removeAll();
	}

	@Override
	@Async
	public void add(Member currentUser, Soft soft) {
		SoftViewLog softViewLog = new SoftViewLog();
		softViewLog.setSoftId(soft.getId());
		if(currentUser!=null){
			softViewLog.setMemberId(currentUser.getId());
		}
		softViewLog.setIp(IPUtils.getIpAddr(Objects.requireNonNull(WebUtils.getRequest())));
		Map<String,Object> softInfo = new HashMap<>();
		softInfo.put("id",soft.getId());
		softInfo.put("name",soft.getName());
		softInfo.put("logo",soft.getLogo());
		softInfo.put("downloadUrl",soft.getDownloadUrl());
		softViewLog.setSoftInfo(softInfo);
		super.save(softViewLog);
	}

}