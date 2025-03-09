package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.DetailOrderDTO;
import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderRefundDTO;
import com.example.betmdtnhom3.dto.request.ChangeStatusRefundRequest;
import com.example.betmdtnhom3.dto.request.CreateOrderRefundRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.OrderRefundMapper;
import com.example.betmdtnhom3.responsitory.*;
import com.example.betmdtnhom3.service.impl.OrderRefundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderRefundService implements OrderRefundServiceImpl {
    @Autowired
    OrderRefundReponsitory orderRefundReponsitory;
    @Autowired
    OrderReponsitory orderReponsitory;
    @Autowired
    DetailOrderReponsitory detailOrderReponsitory;
    @Autowired
    RevenueReponsitory revenueReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    OrderRefundMapper orderRefundMapper;

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public boolean create(CreateOrderRefundRequest createOrderRefundRequest) {
        User user = userReponsitory.findById(createOrderRefundRequest.getIdUser())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Order order = orderReponsitory.findByIdAndUser(createOrderRefundRequest.getIdOrder(), user);
        if (order == null) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        if (order.getStatusOrder().getId() != 4) {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }

        order.setStatusOrder(new StatusOrder(6));

        OrderRefund orderRefund = new OrderRefund();
        orderRefund.setOrder(order);
        orderRefund.setCreatedAt(LocalDateTime.now());
        orderRefund.setRevenue(order.getRevenue());
        orderRefund.setReason(new Reason(createOrderRefundRequest.getReason()));

        orderReponsitory.save(order);
        orderRefundReponsitory.save(orderRefund);

        return true;
    }

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public Boolean confirmOrderRefund(int id) {
        OrderRefund orderRefund = orderRefundReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (orderRefund.getOrder().getStatusOrder().getId() != 6) {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }

        orderRefund.getOrder().setStatusOrder(new StatusOrder(7));

        orderRefundReponsitory.save(orderRefund);

        return true;
    }

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public Boolean deliverOrder(ChangeStatusRefundRequest changeStatusRefundRequest) {
        User user = userReponsitory.findById(changeStatusRefundRequest.getIdUser())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        OrderRefund orderRefund = orderRefundReponsitory.findByIdAndOrderUser(changeStatusRefundRequest.getIdRefund(), user)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (orderRefund.getOrder().getStatusOrder().getId() != 7) {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }

        orderRefund.getOrder().setStatusOrder(new StatusOrder(8));

        orderRefundReponsitory.save(orderRefund);

        return true;
    }

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public Boolean successOrderRefund(int id) {
        OrderRefund orderRefund = orderRefundReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ERROR));

        if (orderRefund.getOrder().getStatusOrder().getId() != 8) {
            throw new AppException(ErrorCode.ORDER_REFUND_ERROR);
        }

        orderRefund.getOrder().setStatusOrder(new StatusOrder(9));
        orderRefundReponsitory.save(orderRefund);

        Revenue revenue = revenueReponsitory.findById(orderRefund.getRevenue().getId())
                .orElseThrow(() -> new AppException(ErrorCode.REVENUE_NOT_FOUND));

        revenue.setRevenue(revenue.getRevenue() - orderRefund.getOrder().getFinalAmount());
        revenueReponsitory.save(revenue);

        return true;
    }

    @Override
    public Boolean rejectOrderRefund(int id) {
        OrderRefund orderRefund = orderRefundReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ERROR));

        if (orderRefund.getOrder().getStatusOrder().getId() != 6) {
            throw new AppException(ErrorCode.ORDER_REFUND_ERROR);
        }

        orderRefund.getOrder().setStatusOrder(new StatusOrder(10));
        orderRefundReponsitory.save(orderRefund);

        return true;
    }

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public Boolean cancelOrderRefund(ChangeStatusRefundRequest changeStatusRefundRequest) {
        User user = userReponsitory.findById(changeStatusRefundRequest.getIdUser())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        OrderRefund orderRefund = orderRefundReponsitory.findByIdAndOrderUser(changeStatusRefundRequest.getIdRefund(), user)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_ERROR));

        if (orderRefund.getOrder().getStatusOrder().getId() != 6) {
            throw new AppException(ErrorCode.ORDER_REFUND_ERROR);
        }

        orderRefund.getOrder().setStatusOrder(new StatusOrder(4));
        orderRefundReponsitory.save(orderRefund);

        return true;
    }

    @Override
    public Boolean deleteOrderRefund(int id) {
        boolean isSuccess = false;
        OrderRefund orders = orderRefundReponsitory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        try {
            orderRefundReponsitory.delete(orders);
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.DELETE_ORDER_ERROR);
        }
        return isSuccess;
    }

    @Override
    public PagenationDTO getOrderRefund(int page, String query, int select) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<OrderRefund> ordersPage = (select == 0)
                ? orderRefundReponsitory.findByPartialIdOrderRefund(query, pageable)
                : orderRefundReponsitory.findByPartialIdOrderAndStatusOrders(query, select, pageable);

        List<OrderRefundDTO> orderDTOList = ordersPage.getContent().stream()
                .map(orderRefundMapper::toOderRefundDTO)
                .collect(Collectors.toList());

        pagenationDTO.setTotalPages(ordersPage.getTotalPages());
        pagenationDTO.setObjectList(orderDTOList);

        return pagenationDTO;
    }

}
