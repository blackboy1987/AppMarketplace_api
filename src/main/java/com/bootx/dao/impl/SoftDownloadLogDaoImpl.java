
package com.bootx.dao.impl;

import com.bootx.dao.SoftDownloadLogDao;
import com.bootx.entity.SoftDownloadLog;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public class SoftDownloadLogDaoImpl extends BaseDaoImpl<SoftDownloadLog, Long> implements SoftDownloadLogDao {

	@Override
	public void removeAll() {
		String jpql = "delete from SoftDownloadLog softDownloadLog";
		entityManager.createQuery(jpql).executeUpdate();
	}

}