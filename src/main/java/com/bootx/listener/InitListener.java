
package com.bootx.listener;

import com.bootx.service.CategoryService;
import com.bootx.service.HomeService;
import jakarta.annotation.Resource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener - 初始化
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Component
public class InitListener {

	@Resource
	private CategoryService categoryService;

	@Resource
	private HomeService homeService;

	/**
	 * 事件处理
	 * 
	 * @param contextRefreshedEvent
	 *            ContextRefreshedEvent
	 */
	@EventListener
	public void handle(ContextRefreshedEvent contextRefreshedEvent) {
		categoryService.clearCache(null);
		categoryService.list();
		homeService.load(false);
		System.out.println("aaaaaaaaaaaaaaaaa");
	}

}