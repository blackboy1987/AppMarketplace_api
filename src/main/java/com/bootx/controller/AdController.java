
package com.bootx.controller;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.Map;

/**
 * @author black
 */
@RestController("MemberAdController")
@RequestMapping("/api/ad")
public class AdController extends BaseController {

    @Resource
    private MemberService memberService;

    /**
     * 广告回调
     */
    @GetMapping("/collection")
    public Result collection(Ad ad, HttpServletRequest request) {
        Member current = memberService.getCurrent(ad.getToken());

        return Result.success();
    }


    static class Ad {

        /**
         * 广告类型
         * 0：开屏广告
         */
        private Integer adType;

        /**
         * 0：成功
         */
        private Integer status;

        private String token;

        public Integer getAdType() {
            return adType;
        }

        public void setAdType(Integer adType) {
            this.adType = adType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}