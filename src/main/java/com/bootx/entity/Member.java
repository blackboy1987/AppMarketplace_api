package com.bootx.entity;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author black
 */
@Entity
public class Member extends User {

    /**
     * "登录失败尝试次数"缓存名称
     */
    public static final String FAILED_LOGIN_ATTEMPTS_CACHE_NAME = "memberFailedLoginAttempts";


    /**
     * 用户名
     */
    @NotEmpty(groups = Save.class)
    @Column(nullable = false, updatable = false,unique = true)
    @JsonView({PageView.class})
    private String username;

    /**
     * 密码
     */
    @NotEmpty(groups = Save.class)
    @Length(min = 4, max = 20)
    @Transient
    private String password;

    /**
     * E-mail
     */
    @NotEmpty
    //@Email
    @Length(max = 200)
    @Column(nullable = false)
    private String email;

    /**
     * 加密密码
     */
    @Column(nullable = false)
    private String encodedPassword;

    /**
     * 总积分
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Long point;

    /**
     * 当前剩余积分
     */
    @NotNull
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Long remainPoint;


    /**
     * 累计签到的天数
     */
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer signInDays;


    /**
     * 连续签到的天数
     */
    @NotNull
    @Min(0)
    @Column(nullable = false)
    @JsonView({PageView.class})
    private Integer continuousSignInDays;

    /**
     * 最近一次签到的时间
     */
    @JsonView({PageView.class})
    private Date signInDate;
    @JsonView({PageView.class})
    private String avatar;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberRank memberRank;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY)
    private Set<Member> children = new HashSet<>();

    /**
     * 获取用户名
     *
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username
     *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password
     *            密码
     */
    public void setPassword(String password) {
        this.password = password;
        if (password != null) {
            setEncodedPassword(DigestUtils.md5Hex(password));
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取加密密码
     *
     * @return 加密密码
     */
    public String getEncodedPassword() {
        return encodedPassword;
    }

    /**
     * 设置加密密码
     *
     * @param encodedPassword
     *            加密密码
     */
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getRemainPoint() {
        return remainPoint;
    }

    public void setRemainPoint(Long remainPoint) {
        this.remainPoint = remainPoint;
    }

    public Integer getSignInDays() {
        return signInDays;
    }

    public void setSignInDays(Integer signInDays) {
        this.signInDays = signInDays;
    }

    public Integer getContinuousSignInDays() {
        return continuousSignInDays;
    }

    public void setContinuousSignInDays(Integer continuousSignInDays) {
        this.continuousSignInDays = continuousSignInDays;
    }

    public Date getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(Date signInDate) {
        this.signInDate = signInDate;
    }

    public String getAvatar() {
        if(StringUtils.isBlank(avatar)){
            avatar = "https://bootx-tuchuang.oss-cn-hangzhou.aliyuncs.com/avatar/" + ((getId()==null?0:getId()) % 50) + ".png";
        }
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public MemberRank getMemberRank() {
        return memberRank;
    }

    public void setMemberRank(MemberRank memberRank) {
        this.memberRank = memberRank;
    }

    @Transient
    @Override
    public boolean isValidCredentials(Object credentials) {
        return credentials != null && StringUtils.equals(DigestUtils.md5Hex(credentials instanceof char[] ? String.valueOf((char[]) credentials) : String.valueOf(credentials)), getEncodedPassword());
    }

    public Member getParent() {
        return parent;
    }

    public void setParent(Member parent) {
        this.parent = parent;
    }

    public Set<Member> getChildren() {
        return children;
    }

    public void setChildren(Set<Member> children) {
        this.children = children;
    }

    @Transient
    public String getMemberRankName() {
        if(memberRank!=null){
            return memberRank.getName();
        }
        return null;
    }
}
