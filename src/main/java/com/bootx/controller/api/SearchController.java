
package com.bootx.controller.api;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.service.SoftService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiSearchController")
@RequestMapping("/api/search")
public class SearchController extends BaseController {

	@Resource
	private SoftService softService;

	@PostMapping
	@JsonView(BaseEntity.PageView.class)
	public Result list(Pageable pageable,String keywords) {
		return Result.success(softService.search(keywords,pageable));
	}
}