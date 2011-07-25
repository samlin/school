package net.shopxx.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.shopxx.bean.TenpayConfig;
import net.shopxx.bean.TenpayConfig.TenpayType;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.entity.PaymentConfig.PaymentConfigType;
import net.shopxx.entity.PaymentConfig.PaymentFeeType;
import net.shopxx.service.PaymentConfigService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 支付方式
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXX026BAB39F30F9C838DE0050825B212E1
 * ============================================================================
 */

@ParentPackage("admin")
public class PaymentConfigAction extends BaseAdminAction {

	private static final long serialVersionUID = 3562311377613294892L;
	
	private PaymentConfig paymentConfig;
	private TenpayConfig tenpayConfig;
	
	@Resource
	private PaymentConfigService paymentConfigService;
	
	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = paymentConfig.getName();
		if (paymentConfigService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}
	
	// 列表
	public String list() {
		pager = paymentConfigService.findByPager(pager);
		return LIST;
	}
	
	// 删除
	public String delete() {
		long totalCount = paymentConfigService.getTotalCount();
		if (ids.length >= totalCount) {
			return ajaxJsonErrorMessage("删除失败，必须保留至少一个支付方式！");
		}
		paymentConfigService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		paymentConfig = paymentConfigService.load(id);
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.tenpay) {
			tenpayConfig = (TenpayConfig) paymentConfig.getConfigObject();
		}
		return INPUT;
	}
	
	// 保存
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空！")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.orderList", message = "排序不允许为空！")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数！")
		}
	)
	@InputConfig(resultName = "error")
	public String save() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("支付手续费金额不允许小于0！");
			return ERROR;
		}
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.tenpay) {
			if (tenpayConfig == null) {
				addActionError("财付通配置不允许为空！");
				return ERROR;
			}
			if (tenpayConfig.getTenpayType() == null) {
				addActionError("财付通交易类型不允许为空！");
				return ERROR;
			}
			if (StringUtils.isEmpty(tenpayConfig.getBargainorId())) {
				addActionError("财付通商户号不允许为空！");
				return ERROR;
			}
			if (StringUtils.isEmpty(tenpayConfig.getKey())) {
				addActionError("财付通密钥不允许为空！");
				return ERROR;
			}
			paymentConfig.setConfigObject(tenpayConfig);
		}
		paymentConfigService.save(paymentConfig);
		redirectionUrl = "payment_config!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "paymentConfig.name", message = "支付方式名称不允许为空！")
		}, 
		requiredFields = {
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentConfigType", message = "支付方式类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFeeType", message = "支付手续费设置不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.paymentFee", message = "支付手续费不允许为空！"),
			@RequiredFieldValidator(fieldName = "paymentConfig.orderList", message = "排序不允许为空！")
		},
		intRangeFields = {
			@IntRangeFieldValidator(fieldName = "paymentConfig.orderList", min = "0", message = "排序必须为零或正整数！")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		if (paymentConfig.getPaymentFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("支付手续费金额不允许小于0！");
			return ERROR;
		}
		if (paymentConfig.getPaymentConfigType() == PaymentConfigType.tenpay) {
			if (tenpayConfig == null) {
				addActionError("财付通配置不允许为空！");
				return ERROR;
			}
			if (tenpayConfig.getTenpayType() == null) {
				addActionError("财付通交易类型不允许为空！");
				return ERROR;
			}
			if (StringUtils.isEmpty(tenpayConfig.getBargainorId())) {
				addActionError("财付通商户号不允许为空！");
				return ERROR;
			}
			if (StringUtils.isEmpty(tenpayConfig.getKey())) {
				addActionError("财付通密钥不允许为空！");
				return ERROR;
			}
			paymentConfig.setConfigObject(tenpayConfig);
		}
		PaymentConfig persistent = paymentConfigService.load(id);
		BeanUtils.copyProperties(paymentConfig, persistent, new String[] {"id", "createDate", "modifyDate"});
		paymentConfigService.update(persistent);
		redirectionUrl = "payment_config!list.action";
		return SUCCESS;
	}

	// 获取所有支付配置类型
	public List<PaymentConfigType> getAllPaymentConfigType() {
		List<PaymentConfigType> allPaymentConfigType = new ArrayList<PaymentConfigType>();
		for (PaymentConfigType paymentConfigType : PaymentConfigType.values()) {
			allPaymentConfigType.add(paymentConfigType);
		}
		return allPaymentConfigType;
	}
	
	// 获取所有支付手续费类型
	public List<PaymentFeeType> getAllPaymentFeeType() {
		List<PaymentFeeType> allPaymentFeeType = new ArrayList<PaymentFeeType>();
		for (PaymentFeeType paymentFeeType : PaymentFeeType.values()) {
			allPaymentFeeType.add(paymentFeeType);
		}
		return allPaymentFeeType;
	}
	
	// 获取所有财付通交易类型
	public List<TenpayType> getAllTenpayType() {
		List<TenpayType> allTenpayType = new ArrayList<TenpayType>();
		for (TenpayType tenpayType : TenpayType.values()) {
			allTenpayType.add(tenpayType);
		}
		return allTenpayType;
	}

	public PaymentConfig getPaymentConfig() {
		return paymentConfig;
	}

	public void setPaymentConfig(PaymentConfig paymentConfig) {
		this.paymentConfig = paymentConfig;
	}

	public TenpayConfig getTenpayConfig() {
		return tenpayConfig;
	}

	public void setTenpayConfig(TenpayConfig tenpayConfig) {
		this.tenpayConfig = tenpayConfig;
	}
	
}