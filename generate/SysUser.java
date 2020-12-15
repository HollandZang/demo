package com.stardon.stardon_main.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.util.Date;
/*
 * comment: 用户信息表
 */
@Data
@Accessors(chain = true)
public class SysUser {
    /**
     * 用户编号  16位GUID的唯一字符串 
     */
	@NotNull
	@Size(max = 16, message = "USERID 长度不能大于16")
	private String userid;

    /**
     * 用户名
     */
	@NotNull
	@Size(max = 50, message = "UNAME 长度不能大于50")
	private String uname;

    /**
     * 密码 MD5加密后的字符串
     */
	@NotNull
	@Size(max = 100, message = "PWD 长度不能大于100")
	private String pwd;

    /**
     * 身份证号码
     */
	@Size(max = 18, message = "IDCARD 长度不能大于18")
	private String idcard;

    /**
     * 真实姓名
     */
	@Size(max = 50, message = "REALNAME 长度不能大于50")
	private String realname;

    /**
     * 用户有效期
     */
	@Size(max = 0, message = "UEXPIRED 长度不能大于0")
	private Date uexpired;

    /**
     * 密码有效期
     */
	@Size(max = 0, message = "PWDEXPIRED 长度不能大于0")
	private Date pwdexpired;

    /**
     * 所属部门
     */
	@Size(max = 20, message = "ORGID 长度不能大于20")
	private String orgid;

    /**
     * IP开始
     */
	@Size(max = 15, message = "IPSTART 长度不能大于15")
	private String ipstart;

    /**
     * IP结束
     */
	@Size(max = 15, message = "IPEND 长度不能大于15")
	private String ipend;

    /**
     * 备注
     */
	@Size(max = 200, message = "REMARKS 长度不能大于200")
	private String remarks;

    /**
     * 联系电话
     */
	@Size(max = 11, message = "TEL 长度不能大于11")
	private String tel;

    /**
     * 状态 1 启用  0 停用
     */
	@NotNull
	@Size(max = 1, message = "STATE 长度不能大于1")
	private String state;

    /**
     * 联系地址
     */
	@Size(max = 100, message = "ADDRESS 长度不能大于100")
	private String address;

    /**
     * 创建时间
     */
	@NotNull
	@Size(max = 0, message = "CTIME 长度不能大于0")
	private Date ctime;

    /**
     * 修改时间
     */
	@NotNull
	@Size(max = 0, message = "UTIME 长度不能大于0")
	private Date utime;

    /**
     * 是否首次登录   0 否 1是
     */
	@NotNull
	@Size(max = 1, message = "LOGIN_FIRST 长度不能大于1")
	private String loginFirst;

    /**
     * 登录失败次数
     */
	@NotNull
	@Size(max = 0, message = "LOGIN_FAIL_NUM 长度不能大于0")
	private Long loginFailNum;

    /**
     * 登录时间
     */
	@Size(max = 0, message = "LOGIN_TIME 长度不能大于0")
	private Date loginTime;

    /**
     * 逗号分隔的单个IP地址
     */
	@Size(max = 200, message = "IPS 长度不能大于200")
	private String ips;

    /**
     * 校验位
     */
	@Size(max = 32, message = "CHECK_DIGIT 长度不能大于32")
	private String checkDigit;

    /**
     * 是否警员 0 不是 1 是
     */
	@Size(max = 1, message = "POLICE 长度不能大于1")
	private String police;

    /**
     * 警员/用户编号
     */
	@Size(max = 15, message = "USER_NUM 长度不能大于15")
	private String userNum;

    /**
     * 访问时段开始
     */
	@Size(max = 10, message = "ACCESS_STIME 长度不能大于10")
	private String accessStime;

    /**
     * 访问时段结束
     */
	@Size(max = 10, message = "ACCESS_ETIME 长度不能大于10")
	private String accessEtime;
}