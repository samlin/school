package net.shopxx.action.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;

import net.shopxx.bean.SystemConfig.PointType;
import net.shopxx.bean.SystemConfig.StoreFreezeTime;
import net.shopxx.entity.DeliveryCorp;
import net.shopxx.entity.DeliveryItem;
import net.shopxx.entity.DeliveryType;
import net.shopxx.entity.Deposit;
import net.shopxx.entity.Member;
import net.shopxx.entity.MemberRank;
import net.shopxx.entity.Order;
import net.shopxx.entity.OrderItem;
import net.shopxx.entity.OrderLog;
import net.shopxx.entity.Payment;
import net.shopxx.entity.PaymentConfig;
import net.shopxx.entity.Product;
import net.shopxx.entity.Refund;
import net.shopxx.entity.Reship;
import net.shopxx.entity.Shipping;
import net.shopxx.entity.Deposit.DepositType;
import net.shopxx.entity.Order.OrderStatus;
import net.shopxx.entity.Order.ShippingStatus;
import net.shopxx.entity.OrderLog.OrderLogType;
import net.shopxx.entity.Payment.PaymentStatus;
import net.shopxx.entity.Payment.PaymentType;
import net.shopxx.entity.Product.WeightUnit;
import net.shopxx.entity.Refund.RefundType;
import net.shopxx.service.AdminService;
import net.shopxx.service.AreaService;
import net.shopxx.service.DeliveryCorpService;
import net.shopxx.service.DeliveryItemService;
import net.shopxx.service.DeliveryTypeService;
import net.shopxx.service.DepositService;
import net.shopxx.service.HtmlService;
import net.shopxx.service.MemberRankService;
import net.shopxx.service.MemberService;
import net.shopxx.service.OrderItemService;
import net.shopxx.service.OrderLogService;
import net.shopxx.service.OrderService;
import net.shopxx.service.PaymentConfigService;
import net.shopxx.service.PaymentService;
import net.shopxx.service.ProductService;
import net.shopxx.service.RefundService;
import net.shopxx.service.ReshipService;
import net.shopxx.service.ShippingService;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;

import com.opensymphony.oscache.base.Cache;
import com.opensymphony.oscache.web.ServletCacheAdministrator;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 订单
 * ============================================================================
 * 版权所有 2008-2010 长沙鼎诚软件有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得SHOP++商业授权之前，您不能将本软件应用于商业用途，否则SHOP++将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.shopxx.net
 * ----------------------------------------------------------------------------
 * KEY: SHOPXXCC7D55FE70812A2D98AAB420D628038C
 * ============================================================================
 */

@ParentPackage("admin")
@Results({ 
	@Result(name = "processAction", location = "order!process.action", params = {"order.id", "${order.id}"}, type = "redirect")
})
public class OrderAction extends BaseAdminAction {

	private static final long serialVersionUID = -2080980180511054311L;

	private Order order;
	private Payment payment;
	private Shipping shipping;
	private Refund refund;
	private Reship reship;
	
	private List<OrderItem> orderItemList;
	private List<DeliveryItem> deliveryItemList;

	@Resource
	private OrderService orderService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private OrderLogService orderLogService;
	@Resource
	private DeliveryTypeService deliveryTypeService;
	@Resource
	private DeliveryCorpService deliveryCorpService;
	@Resource
	private PaymentConfigService paymentConfigService;
	@Resource
	private PaymentService paymentService;
	@Resource
	private DepositService depositService;
	@Resource
	private AreaService areaService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberRankService memberRankService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProductService productService;
	@Resource
	private DeliveryItemService deliveryItemService;
	@Resource
	private ShippingService shippingService;
	@Resource
	private RefundService refundService;
	@Resource
	private ReshipService reshipService;
	@Resource
	private HtmlService htmlService;

	// 列表
	public String list() {
		pager = orderService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() {
		try {
			orderService.delete(ids);
		} catch (Exception e) {
			return ajaxJsonErrorMessage("删除失败，订单数据被引用！");
		}
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 编辑
	public String edit() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑！");
			return ERROR;
		}
		if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑！");
			return ERROR;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单发货状态无法编辑！");
			return ERROR;
		}
		setResponseNoCache();
		return INPUT;
	}
	
	// 更新
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "order.deliveryType.id", message = "配送方式不允许为空！"),
			@RequiredStringValidator(fieldName = "order.shipName", message = "收货人姓名不允许为空！"),
			@RequiredStringValidator(fieldName = "order.shipAreaPath", message = "收货人地区不允许为空！"),
			@RequiredStringValidator(fieldName = "order.shipAddress", message = "收货人地址不允许为空！"),
			@RequiredStringValidator(fieldName = "order.shipZipCode", message = "邮编不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "order.deliveryFee", message = "配送费用不允许为空！"),
			@RequiredFieldValidator(fieldName = "order.paymentFee", message = "支付费用不允许为空！"),
			@RequiredFieldValidator(fieldName = "order.productWeight", message = "商品重量不允许为空！"),
			@RequiredFieldValidator(fieldName = "order.productWeightUnit", message = "商品重量单位不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String update() {
		Order persistent = orderService.load(order.getId());
		if (persistent.getOrderStatus() == OrderStatus.completed || persistent.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑！");
			return ERROR;
		}
		if (persistent.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单付款状态无法编辑！");
			return ERROR;
		}
		if (persistent.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单配送状态无法编辑！");
			return ERROR;
		}
		if (order.getDeliveryFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("配送费用不允许小于0！");
			return ERROR;
		}
		if (order.getPaymentFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("支付费用不允许小于0！");
			return ERROR;
		}
		if (order.getProductWeight() < 0) {
			addActionError("商品重量不允许小于0！");
			return ERROR;
		}
		if (StringUtils.isEmpty(order.getShipPhone()) && StringUtils.isEmpty(order.getShipMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项！");
			return ERROR;
		}
		if (orderItemList == null || orderItemList.size() == 0) {
			addActionError("请保留至少一个商品！");
			return ERROR;
		}
		if (!areaService.isAreaPath(order.getShipAreaPath())) {
			addActionError("地区错误！");
			return ERROR;
		}
		for (OrderItem orderItem : orderItemList) {
			if (orderItem.getProductQuantity() <= 0) {
				addActionError("商品数量错误！");
				return ERROR;
			}
			if (orderItem.getProductPrice().compareTo(new BigDecimal("0")) < 0) {
				addActionError("商品价格错误！");
				return ERROR;
			}
			Product product = productService.load(orderItemService.load(orderItem.getId()).getProduct().getId());
			if (product.getStore() != null) {
				OrderItem orderItemPersistent = orderItemService.load(orderItem.getId());
				Integer availableStore = 0;
				if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid) || 
					(getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
					availableStore = product.getStore() - product.getFreezeStore();
				} else {
					availableStore = product.getStore() - product.getFreezeStore() + orderItemPersistent.getProductQuantity();
				}
				if (orderItem.getProductQuantity() > availableStore) {
					addActionError("商品[" + product.getName() + "]库存不足！");
					return ERROR;
				}
				if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) || 
					(getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() != ShippingStatus.unshipped)) {
					product.setFreezeStore(product.getFreezeStore() - orderItemPersistent.getProductQuantity() + orderItem.getProductQuantity());
					productService.update(product);
				}
			}
		}
		DeliveryType deliveryType = deliveryTypeService.load(order.getDeliveryType().getId());
		PaymentConfig paymentConfig = order.getPaymentConfig();
		String paymentConfigName = null;
		if (paymentConfig != null && StringUtils.isNotEmpty(paymentConfig.getId())) {
			paymentConfig = paymentConfigService.load(paymentConfig.getId());
			paymentConfigName = paymentConfig.getName();
		} else {
			paymentConfig = null;
			paymentConfigName = "货到付款";
		}
		
		Integer productTotalQuantity = 0;// 商品总数
		BigDecimal productTotalPrice = new BigDecimal("0");// 商品总价格
		BigDecimal totalAmount = new BigDecimal("0");// 订单总金额
		for (OrderItem orderItem : orderItemList) {
			OrderItem orderItemPersistent = orderItemService.load(orderItem.getId());
			productTotalQuantity += orderItem.getProductQuantity();
			productTotalPrice = productTotalPrice.add(orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity().toString())));
			BeanUtils.copyProperties(orderItem, orderItemPersistent, new String[] {"id", "createDate", "modifyDate", "productSn", "productName", "productHtmlFilePath", "deliveryQuantity", "totalDeliveryQuantity", "order", "product"});
			orderItemService.update(orderItemPersistent);
		}
		for (OrderItem orderItem : persistent.getOrderItemSet()) {
			if (!orderItemList.contains(orderItem)) {
				orderItemService.delete(orderItem);
			}
		}
		totalAmount = productTotalPrice.add(order.getDeliveryFee()).add(order.getPaymentFee());
		order.setTotalAmount(totalAmount);
		order.setOrderStatus(OrderStatus.processed);
		order.setDeliveryTypeName(deliveryType.getName());
		order.setPaymentConfigName(paymentConfigName);
		order.setProductTotalPrice(productTotalPrice);
		order.setProductTotalQuantity(productTotalQuantity);
		order.setPaymentConfig(paymentConfig);
		order.setShipArea(areaService.getAreaString(order.getShipAreaPath()));
		BeanUtils.copyProperties(order, persistent, new String[] {"id", "createDate", "modifyDate", "orderSn", "orderStatus", "paymentStatus", "shippingStatus", "paidAmount", "memo", "member", "orderItemSet", "orderLogSet", "paymentSet", "refundSet", "shippingSet", "reshipSet"});
		orderService.update(persistent);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.modify);
		orderLog.setOrderSn(persistent.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(persistent);
		orderLogService.save(orderLog);
		
		redirectionUrl = "order!list.action";
		return SUCCESS;
	}
	
	// 处理
	public String process() {
		order = orderService.load(order.getId());
		setResponseNoCache();
		return "process";
	}
	
	// 支付
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "payment.paymentConfig.id", message = "支付方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "payment.paymentType", message = "支付类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "payment.totalAmount", message = "支付金额不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String payment() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法支付！");
			return ERROR;
		}
		if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.paid || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.partRefund || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法支付！");
			return ERROR;
		}
		if (payment.getPaymentType() == PaymentType.recharge) {
			addActionError("支付类型错误！");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(new BigDecimal("0")) < 0) {
			addActionError("支付金额不允许小于0！");
			return ERROR;
		}
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) > 0) {
			addActionError("支付金额超出订单需付金额！");
			return ERROR;
		}
		Deposit deposit = null;
		if (payment.getPaymentType() == PaymentType.deposit) {
			Member member = order.getMember();
			if (payment.getTotalAmount().compareTo(member.getDeposit()) > 0) {
				addActionError("会员余存款余额不足！");
				return ERROR;
			}
			member.setDeposit(member.getDeposit().subtract(payment.getTotalAmount()));
			memberService.update(member);
			deposit = new Deposit();
			deposit.setDepositType(DepositType.adminPayment);
			deposit.setCredit(new BigDecimal("0"));
			deposit.setDebit(payment.getTotalAmount());
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			depositService.save(deposit);
		}
		PaymentConfig paymentConfig = paymentConfigService.load(payment.getPaymentConfig().getId());
		payment.setPaymentConfigName(paymentConfig.getName());
		payment.setPaymentFee(new BigDecimal("0"));
		payment.setOperator(adminService.getLoginAdmin().getUsername());
		payment.setPaymentStatus(PaymentStatus.success);
		payment.setDeposit(deposit);
		payment.setOrder(order);
		paymentService.save(payment);
		
		// 库存处理
		if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) {
			for (OrderItem orderItem : order.getOrderItemSet()) {
				Product product = orderItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
					if (product.getIsOutOfStock()) {
						Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
					}
					productService.update(product);
					if (product.getIsOutOfStock()) {
						flushCache();
						htmlService.productContentBuildHtml(product);
					}
				}
			}
		}
		
		order.setOrderStatus(OrderStatus.processed);
		if (payment.getTotalAmount().compareTo(order.getTotalAmount().subtract(order.getPaidAmount())) == 0) {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.paid);
		} else {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.partPayment);
		}
		order.setPaidAmount(order.getPaidAmount().add(payment.getTotalAmount()));
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.payment);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("支付金额：" + payment.getTotalAmount());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		
		return "processAction";
	}
	
	// 发货
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "shipping.deliveryCorpName", message = "物流公司不允许为空！"),
			@RequiredStringValidator(fieldName = "shipping.shipName", message = "收货人姓名不允许为空！"),
			@RequiredStringValidator(fieldName = "shipping.shipAreaPath", message = "收货人地区不允许为空！"),
			@RequiredStringValidator(fieldName = "shipping.shipAddress", message = "收货人地址不允许为空！"),
			@RequiredStringValidator(fieldName = "shipping.shipZipCode", message = "邮编不允许为空！"),
			@RequiredStringValidator(fieldName = "shipping.deliveryType.id", message = "配送方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "shipping.deliveryFee", message = "物流费用不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String shipping() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法发货！");
			return ERROR;
		}
		if (order.getShippingStatus() == ShippingStatus.shipped) {
			addActionError("此订单配送状态无法发货！");
			return ERROR;
		}
		if (shipping.getDeliveryFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("物流费用不允许小于0！");
			return ERROR;
		}
		if (!areaService.isAreaPath(shipping.getShipAreaPath())) {
			addActionError("地区错误！");
			return ERROR;
		}
		if (StringUtils.isEmpty(order.getShipPhone()) && StringUtils.isEmpty(order.getShipMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项！");
			return ERROR;
		}
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		int totalDeliveryQuantity = 0;// 总发货数
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("发货数不允许小于0！");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			boolean isExist = false;
			for (OrderItem orderItem : orderItemSet) {
				if (product == orderItem.getProduct()) {
					if (deliveryQuantity > (orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
						addActionError("发货数超出订单购买数！");
						return ERROR;
					}
					if (product.getStore() != null) {
						if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
							if (deliveryQuantity > (product.getStore() - product.getFreezeStore())) {
								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
								return ERROR;
							}
						} else {
							if (orderItem.getTotalDeliveryQuantity() < orderItem.getProductQuantity() && deliveryQuantity > (product.getStore() - product.getFreezeStore() + orderItem.getProductQuantity() - orderItem.getDeliveryQuantity())) {
								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
								return ERROR;
							} else if (orderItem.getTotalDeliveryQuantity() >= orderItem.getProductQuantity() && deliveryQuantity > (product.getStore() - product.getFreezeStore())) {
								addActionError("商品[" + orderItem.getProduct().getName() + "]库存不足！");
								return ERROR;
							}
						}
					}
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				addActionError("发货商品未在订单中！");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("发货总数必须大于0！");
			return ERROR;
		}
		DeliveryType deliveryType = deliveryTypeService.load(shipping.getDeliveryType().getId());
		shipping.setShipArea(areaService.getAreaString(shipping.getShipAreaPath()));
		shipping.setOrder(order);
		shipping.setDeliveryTypeName(deliveryType.getName());
		shippingService.save(shipping);
		
		// 库存处理
		if ((getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid && order.getShippingStatus() == ShippingStatus.unshipped) || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.ship && order.getShippingStatus() == ShippingStatus.unshipped)) {
			for (OrderItem orderItem : order.getOrderItemSet()) {
				Product product = orderItem.getProduct();
				if (product.getStore() != null) {
					product.setFreezeStore(product.getFreezeStore() + orderItem.getProductQuantity());
					if (product.getIsOutOfStock()) {
						Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
					}
					productService.update(product);
					if (product.getIsOutOfStock()) {
						flushCache();
						htmlService.productContentBuildHtml(product);
					}
				}
			}
		}
		
		ShippingStatus shippingStatus = ShippingStatus.shipped;// 发货状态
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			for (OrderItem orderItem : orderItemSet) {
				if (orderItem.getProduct().getId() == product.getId()) {
					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() + deliveryItem.getDeliveryQuantity());
					orderItem.setTotalDeliveryQuantity(orderItem.getTotalDeliveryQuantity() + deliveryItem.getDeliveryQuantity());
					orderItemService.update(orderItem);
					if (orderItem.getProductQuantity() > orderItem.getDeliveryQuantity()) {
						shippingStatus = ShippingStatus.partShipped;
					}
					// 库存处理
					if (product.getStore() != null) {
						if (orderItem.getTotalDeliveryQuantity() <= orderItem.getProductQuantity()) {
							product.setFreezeStore(product.getFreezeStore() - deliveryItem.getDeliveryQuantity());
						}
						product.setStore(product.getStore() - deliveryItem.getDeliveryQuantity());
						if (product.getIsOutOfStock()) {
							Hibernate.initialize(product.getProductAttributeMapStore());
						}
						productService.update(product);
						if (product.getIsOutOfStock()) {
							flushCache();
							htmlService.productContentBuildHtml(product);
						}
					}
					break;
				}
			}
			deliveryItem.setProductSn(product.getProductSn());
			deliveryItem.setProductName(product.getName());
			deliveryItem.setProductHtmlFilePath(product.getHtmlFilePath());
			deliveryItem.setShipping(shipping);
			deliveryItemService.save(deliveryItem);
		}
		order.setShippingStatus(shippingStatus);
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.shipping);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 退款
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "refund.paymentConfig.id", message = "退款方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "refund.refundType", message = "退款类型不允许为空！"),
			@RequiredFieldValidator(fieldName = "refund.totalAmount", message = "退款金额不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String refund() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法退款！");
			return ERROR;
		}
		if (order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.unpaid || order.getPaymentStatus() == net.shopxx.entity.Order.PaymentStatus.refunded) {
			addActionError("此订单付款状态无法支付！");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(new BigDecimal("0")) < 0) {
			addActionError("退款金额不允许小于0！");
			return ERROR;
		}
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) > 0) {
			addActionError("退款金额超出订单已付金额！");
			return ERROR;
		}
		Deposit deposit = null;
		if (refund.getRefundType() == RefundType.deposit) {
			Member member = order.getMember();
			member.setDeposit(member.getDeposit().add(refund.getTotalAmount()));
			memberService.update(member);
			deposit = new Deposit();
			deposit.setDepositType(DepositType.adminRecharge);
			deposit.setCredit(refund.getTotalAmount());
			deposit.setDebit(new BigDecimal("0"));
			deposit.setBalance(member.getDeposit());
			deposit.setMember(member);
			depositService.save(deposit);
		}
		PaymentConfig paymentConfig = paymentConfigService.load(refund.getPaymentConfig().getId());
		refund.setPaymentConfigName(paymentConfig.getName());
		refund.setOperator(adminService.getLoginAdmin().getUsername());
		refund.setDeposit(deposit);
		refund.setOrder(order);
		refundService.save(refund);
		
		order.setOrderStatus(OrderStatus.processed);
		if (refund.getTotalAmount().compareTo(order.getPaidAmount()) < 0) {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.partRefund);
		} else {
			order.setPaymentStatus(net.shopxx.entity.Order.PaymentStatus.refunded);
		}
		order.setPaidAmount(order.getPaidAmount().subtract(refund.getTotalAmount()));
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.refund);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo("退款金额：" + refund.getTotalAmount());
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 退货
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "reship.deliveryCorpName", message = "物流公司不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipName", message = "收货人姓名不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipAreaPath", message = "收货人地区不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipAddress", message = "收货人地址不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.shipZipCode", message = "邮编不允许为空！"),
			@RequiredStringValidator(fieldName = "reship.deliveryType.id", message = "配送方式不允许为空！")
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "reship.deliveryFee", message = "物流费用不允许为空！")
		}
	)
	@InputConfig(resultName = "error")
	public String reship() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法退货！");
			return ERROR;
		}
		if (order.getShippingStatus() == ShippingStatus.unshipped || order.getShippingStatus() == ShippingStatus.reshiped) {
			addActionError("此订单配送状态无法退货！");
			return ERROR;
		}
		if (reship.getDeliveryFee().compareTo(new BigDecimal("0")) < 0) {
			addActionError("物流费用不允许小于0！");
			return ERROR;
		}
		if (!areaService.isAreaPath(reship.getShipAreaPath())) {
			addActionError("地区错误！");
			return ERROR;
		}
		if (StringUtils.isEmpty(order.getShipPhone()) && StringUtils.isEmpty(order.getShipMobile())) {
			addActionError("联系电话、联系手机必须填写其中一项！");
			return ERROR;
		}
		order = orderService.load(order.getId());
		Set<OrderItem> orderItemSet = order.getOrderItemSet();
		int totalDeliveryQuantity = 0;// 总退货数
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			Integer deliveryQuantity = deliveryItem.getDeliveryQuantity();
			if (deliveryQuantity < 0) {
				addActionError("退货数不允许小于0！");
				return ERROR;
			}
			totalDeliveryQuantity += deliveryQuantity;
			boolean isExist = false;
			for (OrderItem orderItem : orderItemSet) {
				if (product == orderItem.getProduct()) {
					if (deliveryQuantity > orderItem.getDeliveryQuantity()) {
						addActionError("退货数超出已发货数！");
						return ERROR;
					}
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				addActionError("退货商品未在订单中！");
				return ERROR;
			}
		}
		if (totalDeliveryQuantity < 1) {
			addActionError("退货总数必须大于0！");
			return ERROR;
		}
		
		DeliveryType deliveryType = deliveryTypeService.load(reship.getDeliveryType().getId());
		reship.setShipArea(areaService.getAreaString(reship.getShipAreaPath()));
		reship.setOrder(order);
		reship.setDeliveryTypeName(deliveryType.getName());
		reshipService.save(reship);
		
		ShippingStatus shippingStatus = ShippingStatus.reshiped;// 配送状态
		for (DeliveryItem deliveryItem : deliveryItemList) {
			Product product = productService.load(deliveryItem.getProduct().getId());
			for (OrderItem orderItem : orderItemSet) {
				if (orderItem.getProduct() == product) {
					orderItem.setDeliveryQuantity(orderItem.getDeliveryQuantity() - deliveryItem.getDeliveryQuantity());
					orderItemService.update(orderItem);
					if (orderItem.getDeliveryQuantity() > deliveryItem.getDeliveryQuantity()) {
						shippingStatus = ShippingStatus.partReshiped;
					}
				}
			}
			deliveryItem.setProductSn(product.getProductSn());
			deliveryItem.setProductName(product.getName());
			deliveryItem.setProductHtmlFilePath(product.getHtmlFilePath());
			deliveryItem.setReship(reship);
			deliveryItemService.save(deliveryItem);
		}
		order.setShippingStatus(shippingStatus);
		orderService.update(order);
		
		// 订单日志
		OrderLog orderLog = new OrderLog();
		orderLog.setOrderLogType(OrderLogType.reship);
		orderLog.setOrderSn(order.getOrderSn());
		orderLog.setOperator(adminService.getLoginAdmin().getUsername());
		orderLog.setInfo(null);
		orderLog.setOrder(order);
		orderLogService.save(orderLog);
		return "processAction";
	}
	
	// 完成
	public String completed() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed) {
			return ajaxJsonWarnMessage("此订单已经完成！");
		} else if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajaxJsonErrorMessage("此订单已经作废！");
		} else {
			order.setOrderStatus(OrderStatus.completed);
			orderService.update(order);
			
			// 积分处理
			Integer totalPoint = 0;
			if (getSystemConfig().getPointType() == PointType.orderAmount) {
				totalPoint = order.getProductTotalPrice().multiply(new BigDecimal(getSystemConfig().getPointScale().toString())).setScale(0, RoundingMode.DOWN).intValue();
			} else if (getSystemConfig().getPointType() == PointType.productSet) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					totalPoint = orderItem.getProduct().getPoint() * orderItem.getProductQuantity() + totalPoint;
				}
			}
			if (totalPoint > 0) {
				Member member = order.getMember();
				member.setPoint(member.getPoint() + totalPoint);
				MemberRank upMemberRank = memberRankService.getUpMemberRankByPoint(member.getPoint());
				if (upMemberRank != null && member.getPoint() >= upMemberRank.getPoint()) {
					member.setMemberRank(upMemberRank);
				}
				memberService.update(member);
			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.completed);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(null);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajaxJsonSuccessMessage("您的操作已成功！");
		}
	}
	
	// 作废
	public String invalid() {
		order = orderService.load(order.getId());
		if (order.getOrderStatus() == OrderStatus.completed || order.getOrderStatus() == OrderStatus.invalid) {
			addActionError("此订单状态无法编辑！");
			return ERROR;
		}
		if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			addActionError("此订单支付状态无法编辑！");
			return ERROR;
		}
		if (order.getShippingStatus() != ShippingStatus.unshipped) {
			addActionError("此订单发货状态无法编辑！");
			return ERROR;
		}
		
		if (order.getOrderStatus() == OrderStatus.completed) {
			return ajaxJsonWarnMessage("此订单已经完成！");
		} else if (order.getOrderStatus() == OrderStatus.invalid) {
			return ajaxJsonErrorMessage("此订单已经作废！");
		} else if (order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) {
			return ajaxJsonErrorMessage("此订单付款状态无法作废！");
		} else if (order.getShippingStatus() != ShippingStatus.unshipped) {
			return ajaxJsonErrorMessage("此订单配送状态无法作废！");
		} else {
			order.setOrderStatus(OrderStatus.invalid);
			orderService.update(order);
			
			// 库存处理
			if (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.order || (getSystemConfig().getStoreFreezeTime() == StoreFreezeTime.payment && order.getPaymentStatus() != net.shopxx.entity.Order.PaymentStatus.unpaid) || order.getShippingStatus() != ShippingStatus.unshipped) {
				for (OrderItem orderItem : order.getOrderItemSet()) {
					Product product = orderItem.getProduct();
					product.setFreezeStore(product.getFreezeStore() - orderItem.getProductQuantity());
					if (!product.getIsOutOfStock()) {
						Hibernate.initialize(orderItem.getProduct().getProductAttributeMapStore());
					}
					productService.update(product);
					if (!product.getIsOutOfStock()) {
						flushCache();
						htmlService.productContentBuildHtml(product);
					}
				}
			}
			
			// 订单日志
			OrderLog orderLog = new OrderLog();
			orderLog.setOrderLogType(OrderLogType.invalid);
			orderLog.setOrderSn(order.getOrderSn());
			orderLog.setOperator(adminService.getLoginAdmin().getUsername());
			orderLog.setInfo(null);
			orderLog.setOrder(order);
			orderLogService.save(orderLog);
			
			return ajaxJsonSuccessMessage("您的操作已成功！");
		}
	}
	
	// 查看
	public String view() {
		order = orderService.load(order.getId());
		setResponseNoCache();
		return "view";
	}
	
	// 更新页面缓存
	private void flushCache() {
		Cache cache = ServletCacheAdministrator.getInstance(getRequest().getSession().getServletContext()).getCache(getRequest(), PageContext.APPLICATION_SCOPE); 
		cache.flushAll(new Date());
	}
	
	// 获取所有重量单位
	public List<WeightUnit> getAllWeightUnit() {
		List<WeightUnit> allWeightUnit = new ArrayList<WeightUnit>();
		for (WeightUnit weightUnit : WeightUnit.values()) {
			allWeightUnit.add(weightUnit);
		}
		return allWeightUnit;
	}
	
	// 获取所有配送方式
	public List<DeliveryType> getAllDeliveryType() {
		return deliveryTypeService.getAll();
	}
	
	// 获取所有支付方式
	public List<PaymentConfig> getAllPaymentConfig() {
		return paymentConfigService.getAll();
	}
	
	// 获取支付类型（不包含在线充值）
	public List<PaymentType> getNonRechargePaymentTypeList() {
		List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
		for (PaymentType paymentType : PaymentType.values()) {
			if (paymentType != PaymentType.recharge) {
				paymentTypeList.add(paymentType);
			}
		}
		return paymentTypeList;
	}
	
	// 获取退款类型
	public List<RefundType> getRefundTypeList() {
		List<RefundType> refundTypeList = new ArrayList<RefundType>();
		for (RefundType refundType : RefundType.values()) {
			refundTypeList.add(refundType);
		}
		return refundTypeList;
	}
	
	// 获取所有物流公司
	public List<DeliveryCorp> getAllDeliveryCorp() {
		return deliveryCorpService.getAll();
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public Refund getRefund() {
		return refund;
	}

	public void setRefund(Refund refund) {
		this.refund = refund;
	}

	public Reship getReship() {
		return reship;
	}

	public void setReship(Reship reship) {
		this.reship = reship;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public List<DeliveryItem> getDeliveryItemList() {
		return deliveryItemList;
	}

	public void setDeliveryItemList(List<DeliveryItem> deliveryItemList) {
		this.deliveryItemList = deliveryItemList;
	}

}