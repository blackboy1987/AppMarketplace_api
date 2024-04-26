package com.bootx.repository;

import com.bootx.entity.SoftDownloadLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author black
 */
@Repository
public interface SoftDownloadLogRepository extends PagingAndSortingRepository<SoftDownloadLog,Long>, JpaSpecificationExecutor<SoftDownloadLog> {

}
