
package com.bootx.controller.page;

import com.bootx.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author black
 */
@Controller("PageIndexController")
@RequestMapping("/page")
public class IndexController extends BaseController {

	@GetMapping("/register")
	public String register(ModelMap modal) {
		return "/register";
	}
	@GetMapping("/ys")
	public String ys(ModelMap modal) {
		return "/ys";
	}
}