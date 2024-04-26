package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.SoftDownloadLogDao;
import com.bootx.entity.Member;
import com.bootx.entity.Soft;
import com.bootx.entity.SoftDownloadLog;
import com.bootx.entity.SoftViewLog;
import com.bootx.repository.SoftDownloadLogRepository;
import com.bootx.service.SoftDownloadLogService;
import com.bootx.util.IPUtils;
import com.bootx.util.WebUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	@Resource
	private SoftDownloadLogRepository softDownloadLogRepository;


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

	@Override
	public Page<SoftDownloadLog> findPage(Pageable pageable, Long softId) {
		softDownloadLogRepository.findAll((Specification<SoftDownloadLog>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate restrictions = criteriaBuilder.conjunction();
            if (softId != null) {
                restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("softId"), softId));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createdDate")));
            return restrictions;
        }, org.springframework.data.domain.Pageable.ofSize(pageable.getPageSize()).withPage(pageable.getPageNumber() - 1));
		return null;
	}

}