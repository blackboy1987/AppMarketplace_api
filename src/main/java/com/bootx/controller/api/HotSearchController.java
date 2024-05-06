
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author black
 */
@RestController("ApiHotSearchController")
@RequestMapping("/api/hotSearch")
public class HotSearchController extends BaseController {

	@PostMapping
	public Result list() {
		List<Map<String, Object>> maps = jdbcTemplate.queryForList("select name from hotsearch order by orders asc ;");
		return Result.success(maps.stream().map(item->item.get("name")).collect(Collectors.toList()));
	}
}