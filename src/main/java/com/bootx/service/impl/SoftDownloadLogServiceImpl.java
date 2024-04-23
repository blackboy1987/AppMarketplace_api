package com.bootx.service.impl;

import com.bootx.dao.SoftDownloadLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftDownloadLog;
import com.bootx.entity.SoftViewLog;
import com.bootx.service.SoftDownloadLogService;
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
public class SoftDownloadLogServiceImpl extends BaseServiceImpl<SoftDownloadLog, Long> implements SoftDownloadLogService {

	@Resource
	private SoftDownloadLogDao softDownloadLogDao;


	@Override
	public void clear() {
		softDownloadLogDao.removeAll();
	}

	@Override
	@Async
	public void add(Member member, Soft soft) {
		SoftDownloadLog softDownloadLog = new SoftDownloadLog();
		softDownloadLog.setSoftId(soft.getId());
		if(member!=null){
			softDownloadLog.setMemberId(member.getId());
		}
		softDownloadLog.setIp(IPUtils.getIpAddr(Objects.requireNonNull(WebUtils.getRequest())));
		Map<String,Object> softInfo = new HashMap<>();
		softInfo.put("id",soft.getId());
		softInfo.put("name",soft.getName());
		softInfo.put("downloadUrl",soft.getDownloadUrl());
		softInfo.put("logo",soft.getLogo());
		softDownloadLog.setSoftInfo(softInfo);
		super.save(softDownloadLog);
	}

}