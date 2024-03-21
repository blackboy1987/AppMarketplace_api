
package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.service.SoftService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiSoftController")
@RequestMapping("/api/soft")
public class SoftController {

	@Resource
	private SoftService softService;

	@PostMapping("/list")
	public Result list(Pageable pageable,Long categoryId) {
		return Result.success(softService.list(pageable,categoryId));
	}

	@PostMapping("/detail")
	public Result detail(Long id) {

		return Result.success(softService.detail(id));
	}
}