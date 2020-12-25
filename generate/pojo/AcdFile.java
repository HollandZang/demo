package com.stardon.stardon_main.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
/**
 * comment: 事故信息主表
 */
@Data
@Accessors(chain = true)
public class AcdFile {
    /**
     * 事故编号
     */
	@ApiModelProperty(value="事故编号")
	@NotNull
	@Size(max = 18, message = "SGBH 长度不能大于18")
	private String sgbh;

    /**
     * 行政区划
     */
	@ApiModelProperty(value="行政区划")
	@NotNull
	@Size(max = 10, message = "XZQH 长度不能大于10")
	private String xzqh;

    /**
     * 登记编号
     */
	@ApiModelProperty(value="登记编号")
	@NotNull
	@Size(max = 18, message = "DJBH 长度不能大于18")
	private String djbh;

    /**
     * 开始勘查时间
     */
	@ApiModelProperty(value="开始勘查时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "KSKCSJ 长度不能大于0")
	private Date kskcsj;

    /**
     * 结束勘查时间
     */
	@ApiModelProperty(value="结束勘查时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "JSKCSJ 长度不能大于0")
	private Date jskcsj;

    /**
     * 星期
     */
	@ApiModelProperty(value="星期")
	@NotNull
	@Size(max = 0, message = "XQ 长度不能大于0")
	private BigDecimal xq;

    /**
     * 事故发生时间
     */
	@ApiModelProperty(value="事故发生时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "SGFSSJ 长度不能大于0")
	private Date sgfssj;

    /**
     * 路号
     */
	@ApiModelProperty(value="路号")
	@NotNull
	@Size(max = 7, message = "LH 长度不能大于7")
	private String lh;

    /**
     * 路名
     */
	@ApiModelProperty(value="路名")
	@NotNull
	@Size(max = 64, message = "LM 长度不能大于64")
	private String lm;

    /**
     * 公里数
     */
	@ApiModelProperty(value="公里数")
	@NotNull
	@Size(max = 0, message = "GLS 长度不能大于0")
	private BigDecimal gls;

    /**
     * 米数
     */
	@ApiModelProperty(value="米数")
	@NotNull
	@Size(max = 0, message = "MS 长度不能大于0")
	private BigDecimal ms;

    /**
     * 起点米数
     */
	@ApiModelProperty(value="起点米数")
	@NotNull
	@Size(max = 0, message = "QDMS 长度不能大于0")
	private BigDecimal qdms;

    /**
     * 绝对位置
     */
	@ApiModelProperty(value="绝对位置")
	@NotNull
	@Size(max = 0, message = "JDWZ 长度不能大于0")
	private BigDecimal jdwz;

    /**
     * 事故地点
     */
	@ApiModelProperty(value="事故地点")
	@NotNull
	@Size(max = 128, message = "SGDD 长度不能大于128")
	private String sgdd;

    /**
     * 在道路横断面位置  select * from frm_code where xtlb=''03'' and dmlb=''0122'' '
     */
	@ApiModelProperty(value="在道路横断面位置  select * from frm_code where xtlb=''03'' and dmlb=''0122'' '")
	@NotNull
	@Size(max = 1, message = "ZHDMWZ 长度不能大于1")
	private String zhdmwz;

    /**
     * 中央隔离设施  select * from frm_code where xtlb=''00'' and dmlb=''3134'''
     */
	@ApiModelProperty(value="中央隔离设施  select * from frm_code where xtlb=''00'' and dmlb=''3134'''")
	@NotNull
	@Size(max = 1, message = "ZYGLSS 长度不能大于1")
	private String zyglss;

    /**
     * 道路安全属性  select * from frm_code where xtlb=''03'' and dmlb=''0141'''
     */
	@ApiModelProperty(value="道路安全属性  select * from frm_code where xtlb=''03'' and dmlb=''0141'''")
	@NotNull
	@Size(max = 1, message = "DLAQSX 长度不能大于1")
	private String dlaqsx;

    /**
     * 交通信号方式 （控制）  select * from frm_code where xtlb=''03'' and dmlb=''0125'''
     */
	@ApiModelProperty(value="交通信号方式 （控制）  select * from frm_code where xtlb=''03'' and dmlb=''0125'''")
	@NotNull
	@Size(max = 5, message = "JTXHFS 长度不能大于5")
	private String jtxhfs;

    /**
     * 路侧防护设施类型 select * from frm_code where xtlb=''00'' and dmlb=''3127'''
     */
	@ApiModelProperty(value="路侧防护设施类型 select * from frm_code where xtlb=''00'' and dmlb=''3127'''")
	@NotNull
	@Size(max = 2, message = "FHSSLX 长度不能大于2")
	private String fhsslx;

    /**
     * 道路物理隔离   select * from frm_code where xtlb=''00'' and dmlb=''3121'''
     */
	@ApiModelProperty(value="道路物理隔离   select * from frm_code where xtlb=''00'' and dmlb=''3121'''")
	@NotNull
	@Size(max = 1, message = "DLWLGL 长度不能大于1")
	private String dlwlgl;

    /**
     * 路面状况  select * from frm_code where xtlb=''03'' and dmlb=''0117'''
     */
	@ApiModelProperty(value="路面状况  select * from frm_code where xtlb=''03'' and dmlb=''0117'''")
	@NotNull
	@Size(max = 1, message = "LMZK 长度不能大于1")
	private String lmzk;

    /**
     * 路表情况   select * from frm_code where xtlb=''03'' and dmlb=''0118'''
     */
	@ApiModelProperty(value="路表情况   select * from frm_code where xtlb=''03'' and dmlb=''0118'''")
	@NotNull
	@Size(max = 2, message = "LBQK 长度不能大于2")
	private String lbqk;

    /**
     * 路面结构   select * from frm_code where xtlb=''00'' and dmlb=''3119'' '
     */
	@ApiModelProperty(value="路面结构   select * from frm_code where xtlb=''00'' and dmlb=''3119'' '")
	@NotNull
	@Size(max = 1, message = "LMJG 长度不能大于1")
	private String lmjg;

    /**
     * 路口路段类型    select * from frm_code where xtlb=''00'' and dmlb=''3120'''
     */
	@ApiModelProperty(value="路口路段类型    select * from frm_code where xtlb=''00'' and dmlb=''3120'''")
	@NotNull
	@Size(max = 2, message = "LKLDLX 长度不能大于2")
	private String lkldlx;

    /**
     * 道路线型   select * from frm_code where xtlb=''00'' and dmlb=''3123'' '
     */
	@ApiModelProperty(value="道路线型   select * from frm_code where xtlb=''00'' and dmlb=''3123'' '")
	@NotNull
	@Size(max = 2, message = "DLXX 长度不能大于2")
	private String dlxx;

    /**
     * 道路类型   select * from frm_code where xtlb=''00'' and dmlb=''3124'''
     */
	@ApiModelProperty(value="道路类型   select * from frm_code where xtlb=''00'' and dmlb=''3124'''")
	@NotNull
	@Size(max = 2, message = "DLLX 长度不能大于2")
	private String dllx;

    /**
     * 勘查人1
     */
	@ApiModelProperty(value="勘查人1")
	@NotNull
	@Size(max = 30, message = "KCR1 长度不能大于30")
	private String kcr1;

    /**
     * 勘查人2
     */
	@ApiModelProperty(value="勘查人2")
	@NotNull
	@Size(max = 30, message = "KCR2 长度不能大于30")
	private String kcr2;

    /**
     * 办案人1
     */
	@ApiModelProperty(value="办案人1")
	@NotNull
	@Size(max = 30, message = "BAR1 长度不能大于30")
	private String bar1;

    /**
     * 办案人2
     */
	@ApiModelProperty(value="办案人2")
	@NotNull
	@Size(max = 3, message = "BAR2 长度不能大于3")
	private String bar2;

    /**
     * 当场死亡人数
     */
	@ApiModelProperty(value="当场死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRS 长度不能大于0")
	private BigDecimal swrs;

    /**
     * 抢救无效死亡人数
     */
	@ApiModelProperty(value="抢救无效死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRSQ 长度不能大于0")
	private BigDecimal swrsq;

    /**
     * 24小时死亡人数
     */
	@ApiModelProperty(value="24小时死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRS24 长度不能大于0")
	private BigDecimal swrs24;

    /**
     * 24小时受伤人数
     */
	@ApiModelProperty(value="24小时受伤人数")
	@NotNull
	@Size(max = 0, message = "SSRS24 长度不能大于0")
	private BigDecimal ssrs24;

    /**
     * 3日内死亡人数
     */
	@ApiModelProperty(value="3日内死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRS3 长度不能大于0")
	private BigDecimal swrs3;

    /**
     * 3日内受伤人数
     */
	@ApiModelProperty(value="3日内受伤人数")
	@NotNull
	@Size(max = 0, message = "SSRS3 长度不能大于0")
	private BigDecimal ssrs3;

    /**
     * 7日内死亡人数
     */
	@ApiModelProperty(value="7日内死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRS7 长度不能大于0")
	private BigDecimal swrs7;

    /**
     * 7日内受伤人数
     */
	@ApiModelProperty(value="7日内受伤人数")
	@NotNull
	@Size(max = 0, message = "SSRS7 长度不能大于0")
	private BigDecimal ssrs7;

    /**
     * 30日内死亡人数
     */
	@ApiModelProperty(value="30日内死亡人数")
	@NotNull
	@Size(max = 0, message = "SWRS30 长度不能大于0")
	private BigDecimal swrs30;

    /**
     * 30日内受伤人数
     */
	@ApiModelProperty(value="30日内受伤人数")
	@NotNull
	@Size(max = 0, message = "SSRS30 长度不能大于0")
	private BigDecimal ssrs30;

    /**
     * 失踪人数
     */
	@ApiModelProperty(value="失踪人数")
	@NotNull
	@Size(max = 0, message = "SZRS 长度不能大于0")
	private BigDecimal szrs;

    /**
     * 重伤人数
     */
	@ApiModelProperty(value="重伤人数")
	@NotNull
	@Size(max = 0, message = "ZSRS 长度不能大于0")
	private BigDecimal zsrs;

    /**
     * 轻伤人数
     */
	@ApiModelProperty(value="轻伤人数")
	@NotNull
	@Size(max = 0, message = "QSRS 长度不能大于0")
	private BigDecimal qsrs;

    /**
     * 受伤人数
     */
	@ApiModelProperty(value="受伤人数")
	@NotNull
	@Size(max = 0, message = "SSRS 长度不能大于0")
	private BigDecimal ssrs;

    /**
     * 机动车数量
     */
	@ApiModelProperty(value="机动车数量")
	@NotNull
	@Size(max = 0, message = "JDCSL 长度不能大于0")
	private BigDecimal jdcsl;

    /**
     * 非机动车数量
     */
	@ApiModelProperty(value="非机动车数量")
	@NotNull
	@Size(max = 0, message = "FJDCSL 长度不能大于0")
	private BigDecimal fjdcsl;

    /**
     * 行人数量
     */
	@ApiModelProperty(value="行人数量")
	@NotNull
	@Size(max = 0, message = "XRSL 长度不能大于0")
	private BigDecimal xrsl;

    /**
     * 刑事管理部门
     */
	@ApiModelProperty(value="刑事管理部门")
	@NotNull
	@Size(max = 12, message = "XSGLBM 长度不能大于12")
	private String xsglbm;

    /**
     * 刑事办案单位
     */
	@ApiModelProperty(value="刑事办案单位")
	@NotNull
	@Size(max = 64, message = "XSBADW 长度不能大于64")
	private String xsbadw;

    /**
     * 刑事办案人
     */
	@ApiModelProperty(value="刑事办案人")
	@NotNull
	@Size(max = 32, message = "XSBAR 长度不能大于32")
	private String xsbar;

    /**
     * 图片张数
     */
	@ApiModelProperty(value="图片张数")
	@NotNull
	@Size(max = 0, message = "TPZS 长度不能大于0")
	private BigDecimal tpzs;

    /**
     * 现场图张数
     */
	@ApiModelProperty(value="现场图张数")
	@NotNull
	@Size(max = 0, message = "XCTZS 长度不能大于0")
	private BigDecimal xctzs;

    /**
     * 现场照片张数
     */
	@ApiModelProperty(value="现场照片张数")
	@NotNull
	@Size(max = 0, message = "XCZPZS 长度不能大于0")
	private BigDecimal xczpzs;

    /**
     * 直接财产损失
     */
	@ApiModelProperty(value="直接财产损失")
	@NotNull
	@Size(max = 0, message = "ZJCCSS 长度不能大于0")
	private BigDecimal zjccss;

    /**
     * 事故类型select * from frm_code where xtlb=''00'' and dmlb=''3003'' ';
     */
	@ApiModelProperty(value="事故类型select * from frm_code where xtlb=''00'' and dmlb=''3003'' ';")
	@NotNull
	@Size(max = 1, message = "SGLX 长度不能大于1")
	private String sglx;

    /**
     * 路外事故类型
     */
	@ApiModelProperty(value="路外事故类型")
	@NotNull
	@Size(max = 1, message = "LWSGLX 长度不能大于1")
	private String lwsglx;

    /**
     * 事故初查原因分类 select * from frm_code where xtlb=''03'' and dmlb=''0115'''
     */
	@ApiModelProperty(value="事故初查原因分类 select * from frm_code where xtlb=''03'' and dmlb=''0115'''")
	@NotNull
	@Size(max = 2, message = "CCYYFL 长度不能大于2")
	private String ccyyfl;

    /**
     * 事故认定原因分类
     */
	@ApiModelProperty(value="事故认定原因分类")
	@NotNull
	@Size(max = 2, message = "RDYYFL 长度不能大于2")
	private String rdyyfl;

    /**
     * 事故初查原因  select * from frm_code where xtlb=''03'' and dmlb=''0160'''
     */
	@ApiModelProperty(value="事故初查原因  select * from frm_code where xtlb=''03'' and dmlb=''0160'''")
	@NotNull
	@Size(max = 4, message = "SGCCYY 长度不能大于4")
	private String sgccyy;

    /**
     * 事故认定原因
     */
	@ApiModelProperty(value="事故认定原因")
	@NotNull
	@Size(max = 4, message = "SGRDYY 长度不能大于4")
	private String sgrdyy;

    /**
     * 简要案情
     */
	@ApiModelProperty(value="简要案情")
	@NotNull
	@Size(max = 2000, message = "JYAQ 长度不能大于2000")
	private String jyaq;

    /**
     * 天气
     */
	@ApiModelProperty(value="天气")
	@NotNull
	@Size(max = 4, message = "TQ 长度不能大于4")
	private String tq;

    /**
     * 能见度
     */
	@ApiModelProperty(value="能见度")
	@NotNull
	@Size(max = 1, message = "NJD 长度不能大于1")
	private String njd;

    /**
     * 现场
     */
	@ApiModelProperty(value="现场")
	@NotNull
	@Size(max = 1, message = "XC 长度不能大于1")
	private String xc;

    /**
     * 涉外事故1是2否
     */
	@ApiModelProperty(value="涉外事故1是2否")
	@NotNull
	@Size(max = 1, message = "SWSG 长度不能大于1")
	private String swsg;

    /**
     * 事故形态    select * from frm_code where xtlb=''03'' and dmlb=''0168'''
     */
	@ApiModelProperty(value="事故形态    select * from frm_code where xtlb=''03'' and dmlb=''0168'''")
	@NotNull
	@Size(max = 2, message = "SGXT 长度不能大于2")
	private String sgxt;

    /**
     * 是否逃逸 1-否 2-驾车逃逸 3-弃车逃逸 X
     */
	@ApiModelProperty(value="是否逃逸 1-否 2-驾车逃逸 3-弃车逃逸 X")
	@NotNull
	@Size(max = 1, message = "SFTY 长度不能大于1")
	private String sfty;

    /**
     * 车辆间事故 select * from frm_code where xtlb=''03'' and dmlb=''0116'''
     */
	@ApiModelProperty(value="车辆间事故 select * from frm_code where xtlb=''03'' and dmlb=''0116'''")
	@NotNull
	@Size(max = 1, message = "CLJSG 长度不能大于1")
	private String cljsg;

    /**
     * 单车事故  select * from frm_code where xtlb=''03'' and dmlb=''0138'''
     */
	@ApiModelProperty(value="单车事故  select * from frm_code where xtlb=''03'' and dmlb=''0138'''")
	@NotNull
	@Size(max = 2, message = "DCSG 长度不能大于2")
	private String dcsg;

    /**
     * 碰撞方式
     */
	@ApiModelProperty(value="碰撞方式")
	@NotNull
	@Size(max = 1, message = "PZFS 长度不能大于1")
	private String pzfs;

    /**
     * 逃逸事故侦破1-是2-否
     */
	@ApiModelProperty(value="逃逸事故侦破1-是2-否")
	@NotNull
	@Size(max = 1, message = "TYSGZP 长度不能大于1")
	private String tysgzp;

    /**
     * 逃逸事故侦破时间
     */
	@ApiModelProperty(value="逃逸事故侦破时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "TYZPSJ 长度不能大于0")
	private Date tyzpsj;

    /**
     * 地形  select * from frm_code where xtlb=''00'' and dmlb=''3110'''
     */
	@ApiModelProperty(value="地形  select * from frm_code where xtlb=''00'' and dmlb=''3110'''")
	@NotNull
	@Size(max = 1, message = "DX 长度不能大于1")
	private String dx;

    /**
     * 照明条件
     */
	@ApiModelProperty(value="照明条件")
	@NotNull
	@Size(max = 1, message = "ZMTJ 长度不能大于1")
	private String zmtj;

    /**
     * 调解人1
     */
	@ApiModelProperty(value="调解人1")
	@NotNull
	@Size(max = 30, message = "TJR1 长度不能大于30")
	private String tjr1;

    /**
     * 调解人2
     */
	@ApiModelProperty(value="调解人2")
	@NotNull
	@Size(max = 30, message = "TJR2 长度不能大于30")
	private String tjr2;

    /**
     * 是否运载危险物品1-是2否
     */
	@ApiModelProperty(value="是否运载危险物品1-是2否")
	@NotNull
	@Size(max = 1, message = "YZWXP 长度不能大于1")
	private String yzwxp;

    /**
     * 运载危险品事故后果
     */
	@ApiModelProperty(value="运载危险品事故后果")
	@NotNull
	@Size(max = 2, message = "YZWXPHG 长度不能大于2")
	private String yzwxphg;

    /**
     * 初次录入时间
     */
	@ApiModelProperty(value="初次录入时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "CCLRSJ 长度不能大于0")
	private Date cclrsj;

    /**
     * 1-快报记录 2-快报/全项记录 3全项记录 9 不立案
     */
	@ApiModelProperty(value="1-快报记录 2-快报/全项记录 3全项记录 9 不立案")
	@NotNull
	@Size(max = 1, message = "JLLX 长度不能大于1")
	private String jllx;

    /**
     * 上传时间段(天数)
     */
	@ApiModelProperty(value="上传时间段(天数)")
	@NotNull
	@Size(max = 0, message = "SCSJD 长度不能大于0")
	private BigDecimal scsjd;

    /**
     * 经办人
     */
	@ApiModelProperty(value="经办人")
	@NotNull
	@Size(max = 30, message = "JBR 长度不能大于30")
	private String jbr;

    /**
     * 提交时间
     */
	@ApiModelProperty(value="提交时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "TJSJ 长度不能大于0")
	private Date tjsj;

    /**
     * 更新时间
     */
	@ApiModelProperty(value="更新时间")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Size(max = 0, message = "GXSJ 长度不能大于0")
	private Date gxsj;

    /**
     * 所属中队
     */
	@ApiModelProperty(value="所属中队")
	@NotNull
	@Size(max = 12, message = "SSZD 长度不能大于12")
	private String sszd;

    /**
     * 公路行政等级
     */
	@ApiModelProperty(value="公路行政等级")
	@NotNull
	@Size(max = 1, message = "GLXZDJ 长度不能大于1")
	private String glxzdj;

    /**
     * 档案号
     */
	@ApiModelProperty(value="档案号")
	@NotNull
	@Size(max = 50, message = "DAH 长度不能大于50")
	private String dah;

    /**
     * 卷内号
     */
	@ApiModelProperty(value="卷内号")
	@NotNull
	@Size(max = 0, message = "JNH 长度不能大于0")
	private BigDecimal jnh;

    /**
     * 所辖乡镇
     */
	@ApiModelProperty(value="所辖乡镇")
	@NotNull
	@Size(max = 10, message = "SXXZ 长度不能大于10")
	private String sxxz;

    /**
     * 上报 1-是 2-否  为空3-上报但更新不成功
     */
	@ApiModelProperty(value="上报 1-是 2-否  为空3-上报但更新不成功")
	@NotNull
	@Size(max = 1, message = "SB 长度不能大于1")
	private String sb;

    /**
     * 统计事故编号
     */
	@ApiModelProperty(value="统计事故编号")
	@NotNull
	@Size(max = 64, message = "TJSGBH 长度不能大于64")
	private String tjsgbh;

    /**
     * 管理部门
     */
	@ApiModelProperty(value="管理部门")
	@NotNull
	@Size(max = 12, message = "GLBM 长度不能大于12")
	private String glbm;

    /**
     * 预留字段1
     */
	@ApiModelProperty(value="预留字段1")
	@NotNull
	@Size(max = 100, message = "YLZD1 长度不能大于100")
	private String ylzd1;

    /**
     * 预留字段2
     */
	@ApiModelProperty(value="预留字段2")
	@NotNull
	@Size(max = 100, message = "YLZD2 长度不能大于100")
	private String ylzd2;

    /**
     * 预留字段3
     */
	@ApiModelProperty(value="预留字段3")
	@NotNull
	@Size(max = 100, message = "YLZD3 长度不能大于100")
	private String ylzd3;

    /**
     * 预留字段4
     */
	@ApiModelProperty(value="预留字段4")
	@NotNull
	@Size(max = 100, message = "YLZD4 长度不能大于100")
	private String ylzd4;

    /**
     * 预留字段5
     */
	@ApiModelProperty(value="预留字段5")
	@NotNull
	@Size(max = 100, message = "YLZD5 长度不能大于100")
	private String ylzd5;

    /**
     * 电子坐标 格式为地址坐标X,地址坐标Y,地址经度,地址纬度
     */
	@ApiModelProperty(value="电子坐标 格式为地址坐标X,地址坐标Y,地址经度,地址纬度")
	@NotNull
	@Size(max = 1024, message = "DZZB 长度不能大于1024")
	private String dzzb;

    /**
     * 特大预留1
     */
	@ApiModelProperty(value="特大预留1")
	@NotNull
	@Size(max = 100, message = "TDYL1 长度不能大于100")
	private String tdyl1;

    /**
     * 特大预留2
     */
	@ApiModelProperty(value="特大预留2")
	@NotNull
	@Size(max = 100, message = "TDYL2 长度不能大于100")
	private String tdyl2;

    /**
     * 特大预留3
     */
	@ApiModelProperty(value="特大预留3")
	@NotNull
	@Size(max = 100, message = "TDYL3 长度不能大于100")
	private String tdyl3;

    /**
     * 特大预留4
     */
	@ApiModelProperty(value="特大预留4")
	@NotNull
	@Size(max = 100, message = "TDYL4 长度不能大于100")
	private String tdyl4;

    /**
     * 特大预留5
     */
	@ApiModelProperty(value="特大预留5")
	@NotNull
	@Size(max = 100, message = "TDYL5 长度不能大于100")
	private String tdyl5;

    /**
     * 特大预留6
     */
	@ApiModelProperty(value="特大预留6")
	@NotNull
	@Size(max = 100, message = "TDYL6 长度不能大于100")
	private String tdyl6;

    /**
     * 特大预留7
     */
	@ApiModelProperty(value="特大预留7")
	@NotNull
	@Size(max = 100, message = "TDYL7 长度不能大于100")
	private String tdyl7;

    /**
     * 特大预留8
     */
	@ApiModelProperty(value="特大预留8")
	@NotNull
	@Size(max = 100, message = "TDYL8 长度不能大于100")
	private String tdyl8;

    /**
     * 特大预留9
     */
	@ApiModelProperty(value="特大预留9")
	@NotNull
	@Size(max = 100, message = "TDYL9 长度不能大于100")
	private String tdyl9;

    /**
     * 特大预留10
     */
	@ApiModelProperty(value="特大预留10")
	@NotNull
	@Size(max = 100, message = "TDYL10 长度不能大于100")
	private String tdyl10;

    /**
     * 办案联系方式
     */
	@ApiModelProperty(value="办案联系方式")
	@NotNull
	@Size(max = 100, message = "BALXFS 长度不能大于100")
	private String balxfs;

    /**
     * 办案单位
     */
	@ApiModelProperty(value="办案单位")
	@NotNull
	@Size(max = 100, message = "BADW 长度不能大于100")
	private String badw;

    /**
     * 县以下行政区划
     */
	@ApiModelProperty(value="县以下行政区划")
	@NotNull
	@Size(max = 12, message = "XYXDM 长度不能大于12")
	private String xyxdm;

    /**
     * 校验位
     */
	@ApiModelProperty(value="校验位")
	@NotNull
	@Size(max = 64, message = "JYW 长度不能大于64")
	private String jyw;

    /**
     * 是否二次事故 1-是 2-否 * X
     */
	@ApiModelProperty(value="是否二次事故 1-是 2-否 * X")
	@NotNull
	@Size(max = 1, message = "SFECSG 长度不能大于1")
	private String sfecsg;

    /**
     * 是否典型事故 1-是 2-否
     */
	@ApiModelProperty(value="是否典型事故 1-是 2-否")
	@NotNull
	@Size(max = 1, message = "SFDXSG 长度不能大于1")
	private String sfdxsg;

    /**
     * 道路安全隐患等级
     */
	@ApiModelProperty(value="道路安全隐患等级")
	@NotNull
	@Size(max = 1, message = "DLAQYHDJ 长度不能大于1")
	private String dlaqyhdj;

    /**
     * 当事人总数
     */
	@ApiModelProperty(value="当事人总数")
	@NotNull
	@Size(max = 0, message = "DSRZS 长度不能大于0")
	private BigDecimal dsrzs;

    /**
     * 发送机关
     */
	@ApiModelProperty(value="发送机关")
	@NotNull
	@Size(max = 10, message = "FSJG 长度不能大于10")
	private String fsjg;

    /**
     * 分发机关
     */
	@ApiModelProperty(value="分发机关")
	@NotNull
	@Size(max = 128, message = "FFJG 长度不能大于128")
	private String ffjg;

    /**
     * 
     */
	@ApiModelProperty(value="")
	@NotNull
	@Size(max = 1, message = "SDSGDSCWZ 长度不能大于1")
	private String sdsgdscwz;

    /**
     * 
     */
	@ApiModelProperty(value="")
	@NotNull
	@Size(max = 2, message = "HLZT 长度不能大于2")
	private String hlzt;

    /**
     * 
     */
	@ApiModelProperty(value="")
	@NotNull
	@Size(max = 1, message = "ZBLX 长度不能大于1")
	private String zblx;

    /**
     * 
     */
	@ApiModelProperty(value="")
	@NotNull
	@Size(max = 128, message = "AJMC 长度不能大于128")
	private String ajmc;

    /**
     * 经度
     */
	@ApiModelProperty(value="经度")
	@NotNull
	@Size(max = 0, message = "lag 长度不能大于0")
	private BigDecimal lag;

    /**
     * 维度
     */
	@ApiModelProperty(value="维度")
	@NotNull
	@Size(max = 0, message = "lat 长度不能大于0")
	private BigDecimal lat;
}