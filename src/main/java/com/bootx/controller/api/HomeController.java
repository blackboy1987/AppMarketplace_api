
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.controller.BaseController;
import com.bootx.service.CategoryService;
import com.bootx.service.HomeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author black
 */
@RestController("ApiHomeController")
@RequestMapping("/api/home")
public class HomeController extends BaseController {

    @Resource
    private HomeService homeService;

    @PostMapping("/load")
    public Result index(){
        return Result.success(homeService.load(true));
    }

}