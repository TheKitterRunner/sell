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
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.ResolveResult;
import javax.validation.Valid;
import java.util.*;

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
     * @param bingingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVo createOrder(@Valid OrderForm orderForm, @Valid BindingResult bingingResult){
        if (bingingResult.hasErrors()){
            log.error("[创建订单] : 参数有误, orderForm : {}" , orderForm);
            throw new SellException(ResultStatus.ORDER_PARAM_ERROR.getCode(),
                    bingingResult.getFieldError().getDefaultMessage());
        }

        // 将OrderForm 转换为orderDto
        OrderDto orderDto = OrderFormToOrderDTOConverter.converter(orderForm);

        // 判断购物车是否为空
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("[创建订单] : 购物车不能为空");
            throw new SellException(ResultStatus.CART_EMPTY);
        }
        // 创建订单
        OrderDto result = orderService.createOrder(orderDto);

        // 将订单的id存入到一个map中
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDto.getOrderId());

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
    public ResultVo list(@RequestParam("openId") String openId,
                         @RequestParam("page") Integer page,
                         @RequestParam("size") Integer size){

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
    public ResultVo getDetail(@RequestParam("orderId") String orderId){
        try {
            //获取orderDto对象
            OrderDto orderDto = orderService.findOne(orderId);
            //获取resultVO对象并返回
            ResultVo resultVo = ResultVOUtil.success(orderDto);
            return  resultVo;
        } catch (SellException s){
            log.error("[订单不存在], orderId :" + orderId);
        }
        return null;
    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("orderId") String orderId){
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

    /**
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVo finish(@RequestParam("orderId") String orderId){
        // 根据orderID查询出orderMaster
        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        // 将ordermaster转换为orderDto
        OrderDto orderDto =OrderMasterToOrderDTOConverter.converter(orderMaster);
        // 调用完结订单的方法,返回更改状态后的orderDto
        OrderDto finishOrderDto = orderService.finishOrder(orderDto);
        return ResultVOUtil.success(finishOrderDto);

    }
}
