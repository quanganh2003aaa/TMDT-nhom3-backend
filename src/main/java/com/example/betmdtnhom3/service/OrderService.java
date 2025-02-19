package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.ApplyVoucherDTO;
import com.example.betmdtnhom3.dto.DetailOrderDTO;
import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderListDTO;
import com.example.betmdtnhom3.dto.request.CreateOrderRequest;
import com.example.betmdtnhom3.dto.request.DetailOrderRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.DetailOrderMapper;
import com.example.betmdtnhom3.mapper.OrderMapper;
import com.example.betmdtnhom3.responsitory.*;
import com.example.betmdtnhom3.service.impl.OrderServiceImpl;
import com.example.betmdtnhom3.utils.SizeUtilsHelper;
import com.example.betmdtnhom3.utils.VoucherUtilsHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Override
    public PagenationDTO getAllOrder(int page) {
        Pageable pageable = PageRequest.of(page - 1, 5, Sort.by(Sort.Direction.DESC, "date"));
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<Order> ordersPage = orderReponsitory.findAll(pageable);
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
        return null;
    }

    @Override
    public List<OrderListDTO> getOrderByUser(String idUser) {
        return null;
    }

    @Override
    public OrderDTO getById(int id) {
        Order order = orderReponsitory.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.ORDER_NOT_FOUND)
        );
        OrderDTO orderDTO = orderMapper.toOrder(order);
        List<DetailOrderDTO> detailOrderDTOList = new ArrayList<>();
        for (DetailOrder detail:order.getDetails()) {
            DetailOrderDTO detailOrderDTO = detailOrderMapper.toDTO(detail);
            detailOrderDTOList.add(detailOrderDTO);
        }
        orderDTO.setDetailOrderDTOList(detailOrderDTOList);
        return orderDTO;
    }
    @Transactional
    @Override
    public Boolean createOrder(CreateOrderRequest createOrderRequest) {
        boolean isSuccess = false;
        Optional<User> users = userReponsitory.findById(createOrderRequest.getUser());

        if (users.isPresent()){
            Order orders = new Order();
            int totalPrice = 0;

            List<DetailOrder> detailOrdersList = new ArrayList<>();
            for (DetailOrderRequest detailOrderRequest: createOrderRequest.getDetailOrderRequestList()) {
                Optional<Product> products = productReponsitory.findById(detailOrderRequest.getId());
                if (products.isPresent()){
                    List<Size> sizes = sizeReponsitory.findAllByProduct(products.get());
                    if (sizeUtilsHelper.isSizeAvailable(sizes, detailOrderRequest.getSize())){
                        int priceProduct = detailOrderRequest.getQuantity() * products.get().getPrice();

                        DetailOrder detailOrders = new DetailOrder();
                        detailOrders.setOrder(orders);
                        detailOrders.setQuantity(detailOrderRequest.getQuantity());
                        detailOrders.setProduct(products.get());
                        detailOrders.setIdProductHistory(detailOrderRequest.getId());
                        detailOrders.setSize(detailOrderRequest.getSize());
                        detailOrdersList.add(detailOrders);

                        products.get().setQuantity(products.get().getQuantity() - detailOrderRequest.getQuantity());
                        totalPrice+=priceProduct;
                        try {
                            productReponsitory.save(products.get());
                        } catch (Exception e){
                            throw new AppException(ErrorCode.ORDER_CREATE_ERROR);
                        }
                    } else{
                        throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
                    }
                }else {
                    throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
                }
            }

            DeliveryMethod deliveryMethod = deliveryMethodReponsitory.findById(createOrderRequest.getDeliveryMethod()).orElseThrow(
                    () -> new AppException(ErrorCode.METHOD_DELIVERY_NOT_FOUND)
            );
            totalPrice += deliveryMethod.getPrice();
            orders.setDeliveryMethod(deliveryMethod);
            orders.setDate(LocalDateTime.now());
            orders.setTel(createOrderRequest.getTel());
            orders.setAddress(createOrderRequest.getAddress());
            orders.setNote(createOrderRequest.getNote());
            orders.setUser(users.get());

            StatusOrder statusOrders = new StatusOrder();
            statusOrders.setId(1);
            orders.setStatusOrder(statusOrders);

            LocalDateTime now = LocalDateTime.now();
            int month = now.getMonthValue();
            int year = now.getYear();
            Revenue revenues = revenueReponsitory.findRevenuesByMonthAndYear(month, year);
            if (revenues==null){
                Revenue newRevenue = new Revenue();
                newRevenue.setRevenue(0);
                newRevenue.setMonth(month);
                newRevenue.setYear(year);
                revenueReponsitory.save(newRevenue);
                orders.setRevenue(newRevenue);
            }else {
                orders.setRevenue(revenues);
            }
            Optional<Voucher> voucher = voucherReponsitory.findById(createOrderRequest.getVoucherCode());
            if (voucher.isPresent()){
                orders.setDiscountAmount(voucher.get().getDiscountValue());
                orders.setFinalAmount(totalPrice - voucher.get().getDiscountValue());
                orders.setVoucher(voucher.get());
            }

            try {
                orders.setTotalPrice(totalPrice);
                orderReponsitory.save(orders);
                detailOrderReponsitory.saveAll(detailOrdersList);
                isSuccess = true;
            } catch (Exception e){
                throw new AppException(ErrorCode.ORDER_CREATE_ERROR);
            }
        }else {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }

        return isSuccess;
    }

    @Override
    public Boolean confirmOrder(int id) {
        return null;
    }

    @Override
    public Boolean deliverOrder(int id) {
        return null;
    }

    @Override
    public Boolean cancelOrder(int id) {
        return null;
    }

    @Override
    public Boolean successOrder(int id) {
        return null;
    }

    @Override
    public Boolean deleteOrder(int id) {
        return null;
    }
}
