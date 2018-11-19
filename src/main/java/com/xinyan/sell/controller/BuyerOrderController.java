package com.xinyan.sell.controller;

import com.xinyan.sell.converter.OrderFormToOrderDTOConverter;
import com.xinyan.sell.converter.OrderMasterToOrderDTOConverter;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.form.OrderForm;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.po.OrderMaster;
import com.xinyan.sell.repository.OrderDetailRepository;
import com.xinyan.sell.repository.OrderMasterRepository;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.utils.ResultVOUtil;
import com.xinyan.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nico
 * 2018/11/17
 * 买家订单管理的Controller
 */
@Slf4j
@RequestMapping("/buyer/order")
@RestController
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVo create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //校验参数
        if (bindingResult.hasErrors()){
            throw new SellException(ResultStatus.ORDER_PARAM_ERROR.getCode()
                    ,bindingResult.getFieldError().getDefaultMessage());
        }
        //将OrderForm对象转化为DTO对象
        OrderDto orderDto = OrderFormToOrderDTOConverter.converter(orderForm);
        //判断订单详情列表是否为空，为空则抛异常
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("创建订单，购物车不能为空");
            throw new SellException(ResultStatus.CART_EMPTY);
        }
        //创建订单
        OrderDto orderDtoResult = orderService.createOrder(orderDto);

        //设置订单id并返回ResultVo对象
        Map<String,String> map = new HashMap();
        map.put("orderId",orderDtoResult.getOrderId());
        return ResultVOUtil.success(map);
    }

    /**
     * 查询订单列表
     * @param openId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVo list(@Param("openId") String openId, Integer page,Integer size){
        PageRequest pageRequest = new PageRequest(page,size);
        //获取分页订单列表
        Page<OrderDto> orderDtoPage = orderService.findList(openId,pageRequest);
        //获取resultVo对象并返回
        ResultVo resultVo = ResultVOUtil.success(orderDtoPage.getContent());
        return  resultVo;
    }

    /**
     * 查询订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVo getDetail(@Param("orderId") String orderId){
        //获取orderDto对象
        OrderDto orderDto = orderService.findOne(orderId);
        //获取resultVO对象并返回
        ResultVo resultVo = ResultVOUtil.success(orderDto);
        return  resultVo;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVo cancel(String orderId){
        //查询出orderMaster对象并转换为orderDto对象
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        OrderDto orderDto = OrderMasterToOrderDTOConverter.converter(orderMaster);
        //获取orderDetailList并设置进orderDto对象中
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        orderDto.setOrderDetailList(orderDetailList);
        orderService.cancelOrder(orderDto);
        //获取resultVo对象并返回
        ResultVo resultVo = ResultVOUtil.success();
        return  resultVo;
    }
}
