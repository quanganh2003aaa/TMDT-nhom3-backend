package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.Enum.PaymentStatus;
import com.example.betmdtnhom3.Enum.StatusProduct;
import com.example.betmdtnhom3.dto.*;
import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.dto.request.DetailOrderRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.DetailOrderMapper;
import com.example.betmdtnhom3.mapper.OrderMapper;
import com.example.betmdtnhom3.responsitory.*;
import com.example.betmdtnhom3.service.impl.GmailServiceImpl;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import com.example.betmdtnhom3.utils.FileImgUtilsHelper;
import com.example.betmdtnhom3.utils.SizeUtilsHelper;
import com.example.betmdtnhom3.utils.VoucherUtilsHelper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements OrderServiceImpl {
    @Autowired
    OrderReponsitory orderReponsitory;
    @Autowired
    DetailOrderReponsitory detailOrderReponsitory;
    @Autowired
    SizeReponsitory sizeReponsitory;
    @Autowired
    RevenueReponsitory revenueReponsitory;
    @Autowired
    UserReponsitory userReponsitory;
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    SizeUtilsHelper sizeUtilsHelper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    DetailOrderMapper detailOrderMapper;
    @Autowired
    VoucherReponsitory voucherReponsitory;
    @Autowired
    VoucherUtilsHelper voucherUtilsHelper;
    @Autowired
    DeliveryMethodReponsitory deliveryMethodReponsitory;
    @Autowired
    CartReponsitory cartReponsitory;
    @Autowired
    GmailServiceImpl gmailService;
    @Autowired
    FileImgUtilsHelper fileImgUtilsHelper;

    @Override
    public PagenationDTO getAllOrder(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "date"));
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<Order> ordersPage = orderReponsitory.findAllExcludeStatuses(pageable);
        List<OrderListDTO> orderListDTOList = new ArrayList<>();
        for (Order order: ordersPage) {
            OrderListDTO orderListDTO = orderMapper.toOderList(order);
            orderListDTOList.add(orderListDTO);
        }
        pagenationDTO.setTotalPages(ordersPage.getTotalPages());
        pagenationDTO.setObjectList(orderListDTOList);
        return pagenationDTO;
    }

    @Override
    public PagenationDTO getOrderSearch(int page, String query, int select) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "date"));
        PagenationDTO pagenationDTO = new PagenationDTO();
        List<Integer> excludedStatuses = Arrays.asList(6, 7, 8, 9, 10);
        Page<Order> ordersPage;
        if (select == 0){
            ordersPage = orderReponsitory.findByPartialIdOrderAndExcludeStatuses(query, excludedStatuses, pageable);
        } else {
            ordersPage = orderReponsitory.findByPartialIdOrderAndStatusOrders(query, select, pageable);
        }

        List<OrderListDTO> orderDTOList = new ArrayList<>();
        for (Order order: ordersPage) {
            OrderListDTO orderListDTO = orderMapper.toOderList(order);
            orderDTOList.add(orderListDTO);
        }

        pagenationDTO.setTotalPages(ordersPage.getTotalPages());
        pagenationDTO.setObjectList(orderDTOList);

        return pagenationDTO;
    }

    @Override
    public List<OrderListDTO> getOrderByUser(String idUser) {
        User users = userReponsitory.findById(idUser).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));
        List<Order> ordersPage = orderReponsitory.findAllByUserOrderByDateDesc(users);

        List<OrderListDTO> orderDTOList = new ArrayList<>();
        for (Order order: ordersPage) {
            OrderListDTO orderListDTO = orderMapper.toOderList(order);
            orderDTOList.add(orderListDTO);
        }

        return orderDTOList;
    }

    @Override
    public OrderDTO getById(int id) {
        Order order = orderReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrderDTO orderDTO = orderMapper.toOrder(order);

        List<DetailOrderDTO> detailOrderDTOList = order.getDetails().stream()
                .map(detail -> {
                    DetailOrderDTO dto = detailOrderMapper.toDTO(detail);
                    dto.setImg(fileImgUtilsHelper.getFirstImage(detail.getProduct()));
                    return dto;
                })
                .collect(Collectors.toList());

        orderDTO.setDetailOrderDTOList(detailOrderDTOList);

        return orderDTO;

    }
    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public CreateOrderDTO createOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        Optional<User> user = userReponsitory.findById(createOrderRequest.getUser());
        if (user.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        Order order = new Order();
        int totalPrice = 0;
        List<DetailOrder> detailOrdersList = new ArrayList<>();

        for (DetailOrderRequest detailRequest : createOrderRequest.getDetailOrderRequestList()) {
            Product product = productReponsitory.findByIdAndStatusProduct(detailRequest.getId(), StatusProduct.ACTIVE)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_INACTIVE));

            List<Size> sizes = sizeReponsitory.findAllByProduct(product);
            if (!sizeUtilsHelper.isSizeAvailable(sizes, detailRequest.getSize())) {
                throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
            }

            int priceProduct = detailRequest.getQuantity() * product.getPrice();
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setOrder(order);
            detailOrder.setQuantity(detailRequest.getQuantity());
            detailOrder.setProduct(product);
            detailOrder.setIdProductHistory(detailRequest.getId());
            detailOrder.setSize(detailRequest.getSize());
            detailOrdersList.add(detailOrder);

            productReponsitory.save(product);
            totalPrice += priceProduct;
        }

        DeliveryMethod deliveryMethod = deliveryMethodReponsitory.findById(createOrderRequest.getDeliveryMethod())
                .orElseThrow(() -> new AppException(ErrorCode.METHOD_DELIVERY_NOT_FOUND));

        totalPrice += deliveryMethod.getPrice();
        order.setDeliveryMethod(deliveryMethod);
        order.setDate(LocalDateTime.now());
        order.setTel(createOrderRequest.getTel());
        order.setAddress(createOrderRequest.getAddress());
        order.setNote(createOrderRequest.getNote());
        order.setUser(user.get());
        order.setStatusOrder(new StatusOrder(1));
        order.setPaymentStatus(PaymentStatus.UNPAID);
        order.setTotalPrice(totalPrice);
        Revenue revenue = revenueReponsitory.findRevenuesByMonthAndYear(LocalDateTime.now().getMonthValue(), LocalDateTime.now().getYear());
        if (revenue == null) {
            revenue = new Revenue();
            revenue.setRevenue(0);
            revenue.setMonth(LocalDateTime.now().getMonthValue());
            revenue.setYear(LocalDateTime.now().getYear());
            revenueReponsitory.save(revenue);
        }
        order.setRevenue(revenue);

        if (createOrderRequest.getVoucherCode().isEmpty()){
            order.setDiscountAmount(0);
            order.setFinalAmount(totalPrice);
        } else {
            Voucher voucher = voucherReponsitory.findById(createOrderRequest.getVoucherCode())
                    .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
            order.setDiscountAmount(voucher.getDiscountValue());
            order.setFinalAmount(totalPrice - voucher.getDiscountValue());
            order.setVoucher(voucher);
        }


        List<CartUser> cartUserList = cartReponsitory.findAllByUser(user.get());
        orderReponsitory.save(order);
        detailOrderReponsitory.saveAll(detailOrdersList);
        cartReponsitory.deleteAll(cartUserList);

        createOrderDTO.setIdOrder(order.getId());
        createOrderDTO.setSuccess(true);
        return createOrderDTO;
    }

    @Override
    public Boolean confirmOrder(int id) {
        boolean isSuccess = false;
        Optional<Order> orders = orderReponsitory.findById(id);
        try {
            if (orders.isPresent() && orders.get().getStatusOrder().getId() == 1){
                orders.get().setStatusOrder(new StatusOrder(2));
            } else {
                throw new AppException(ErrorCode.ORDER_ERROR);
            }
            orderReponsitory.save(orders.get());

            OrderGmailDTO orderGmailDTO = orderMapper.toOrderGmail(orders.get());

            List<DetailOrderDTO> detailOrderDTOList = orders.get().getDetails().stream()
                    .map(detailOrderMapper::toDTO)
                    .collect(Collectors.toList());
            orderGmailDTO.setDetailOrderDTOList(detailOrderDTOList);
            gmailService.sendOrderGmail(orders.get().getUser().getGmail(), orderGmailDTO);
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.ORDER_ERROR);
        }
        return isSuccess;
    }

    @Override
    public Boolean deliverOrder(int id) {
        boolean isSuccess = false;
        try {
            Optional<Order> orders = orderReponsitory.findById(id);
            if (orders.isPresent() && orders.get().getStatusOrder().getId() == 2){
                orders.get().setStatusOrder(new StatusOrder(3));
            } else {
                throw new AppException(ErrorCode.ORDER_ERROR);
            }

            orderReponsitory.save(orders.get());
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.ORDER_ERROR);
        }
        return isSuccess;
    }

    @Override
    public Boolean cancelOrder(int id) {
        boolean isSuccess = false;
        Optional<Order> orders = orderReponsitory.findById(id);
        if (orders.isPresent() ){
            if (orders.get().getStatusOrder().getId() == 2 || orders.get().getStatusOrder().getId() == 1){
                orders.get().setStatusOrder(new StatusOrder(5));
            }
        } else {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }
        try {
            orderReponsitory.save(orders.get());
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.ORDER_ERROR);
        }
        return isSuccess;
    }

    @Transactional(rollbackFor = {AppException.class, IOException.class, SQLException.class})
    @Override
    public Boolean successOrder(int id) {
        Optional<Order> optionalOrder = orderReponsitory.findById(id);

        if (optionalOrder.isEmpty()) {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }

        Order order = optionalOrder.get();

        if (order.getStatusOrder().getId() != 3) {
            throw new AppException(ErrorCode.ORDER_ERROR);
        }

        order.setStatusOrder(new StatusOrder(4));
        order.setPaymentStatus(PaymentStatus.PAID);
        orderReponsitory.save(order);

        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        Revenue revenue = revenueReponsitory.findRevenuesByMonthAndYear(month, year);

        if (revenue == null) {
            revenue = new Revenue();
            revenue.setMonth(month);
            revenue.setYear(year);
            revenue.setRevenue(order.getTotalPrice());
        } else {
            revenue.setRevenue(revenue.getRevenue() + order.getTotalPrice());
        }

        revenueReponsitory.save(revenue);

        return true;
    }

    @Override
    public Boolean deleteOrder(int id) {
        boolean isSuccess = false;
        Order orders = orderReponsitory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        try {
            orderReponsitory.delete(orders);
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.DELETE_ORDER_ERROR);
        }
        return isSuccess;
    }

    @Override
    public Boolean paySuccess(int idOrder) {
        boolean isSuccess = false;
        Order orders = orderReponsitory.findById(idOrder).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        try {
            orders.setPaymentStatus(PaymentStatus.PAID);
            orderReponsitory.save(orders);
            isSuccess = true;
        }catch (Exception e){
            throw new AppException(ErrorCode.DELETE_ORDER_ERROR);
        }
        return isSuccess;
    }

    @Override
    public long count() {
        return orderReponsitory.count();
    }
}
