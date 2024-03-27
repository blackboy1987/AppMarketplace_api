
package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.service.CategoryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author black
 */
@RestController("ApiCategoryController")
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @PostMapping("/list")
    public Result category(){
        return Result.success(categoryService.list());
    }

    @PostMapping("/tree")
    public Result tree(){
        return Result.success(categoryService.tree());
    }
}