package com.xinyan.sell.controller;

import com.xinyan.sell.converter.OrderFormToOrderDTOConverter;
import com.xinyan.sell.dto.OrderDto;
import com.xinyan.sell.enums.ResultStatus;
import com.xinyan.sell.exception.SellException;
import com.xinyan.sell.form.OrderForm;
import com.xinyan.sell.po.OrderDetail;
import com.xinyan.sell.service.OrderService;
import com.xinyan.sell.utils.ResultVOUtil;
import com.xinyan.sell.vo.OrderDetailVO;
import com.xinyan.sell.vo.OrderDtoVO;
import com.xinyan.sell.vo.OrderMasterVO;
import com.xinyan.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.ResolveResult;
import javax.validation.Valid;
import java.util.*;

/**
 * Nico
 * 2018/11/17
 *
 * 买家订单管理的Controller
 */
@Slf4j
@RequestMapping("/buyer/order")
@RestController
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

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
     * 根据openid和orderId查询订单详情
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVo findOne(@RequestParam("orderId") String orderId,
                            @RequestParam("openid") String openid) {
        OrderDto orderDto = orderService.findOne(orderId);
        String dbOpenid = orderDto.getBuyerOpenid();

        // 返回结果的VO
        ResultVo resultVo = new ResultVo();
        if (openid.equals(dbOpenid)) {
            if (orderDto == null) {
                log.error("[查询订单详情]: orderId 有误 ", orderId);
                throw new SellException(ResultStatus.ORDER_NOT_EXIST);
            }

            // 返回结果VO中的Object对象
            OrderDtoVO orderDtoVO = new OrderDtoVO();
            List<OrderDetail> orderDetailList = orderDto.getOrderDetailList();
            List<OrderDetailVO> orderDetailVOList = new ArrayList<>();

            // 将查询出来的订单详情遍历传递给视图层的orderDetailVOList
            for (OrderDetail orderDetail : orderDetailList) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();

                // 将orderDetail的值copy给orderDetailVO展示层
                BeanUtils.copyProperties(orderDetail, orderDetailVO);
                orderDetailVOList.add(orderDetailVO);

                orderDtoVO.setOrderDetailVOList(orderDetailVOList);
                // 将orderDto的值copy给orderDtoVO展示层
                BeanUtils.copyProperties(orderDto, orderDtoVO);

            }

            resultVo.setCode(0);
            resultVo.setMsg("成功");
            resultVo.setData(orderDtoVO);

        }
        // 返回寄送形式的结果给前端
        return resultVo;
    }

    /**
     * 根据openid查询订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVo findAllByOpenid(@RequestParam("openid") String openid,
                                    @RequestParam("page") Integer page,
                                    @RequestParam("size") Integer size){
        PageRequest pageRequest = new PageRequest(page, size);
        ResultVo resultVo = new ResultVo();

        Page<OrderDto> orderMasterPage = orderService.findList(openid, pageRequest);
        List<OrderMasterVO> orderMasterVOList = new ArrayList<>();
        for (OrderDto orderDto : orderMasterPage){
            OrderMasterVO orderMasterOV = new OrderMasterVO();
            BeanUtils.copyProperties(orderDto, orderMasterOV);
            orderMasterVOList.add(orderMasterOV);
        }

        resultVo.setCode(0);
        resultVo.setMsg("成功");
        resultVo.setData(orderMasterVOList);
        return  resultVo;
    }
}
