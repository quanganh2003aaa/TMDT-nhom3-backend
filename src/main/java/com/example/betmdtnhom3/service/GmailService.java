package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.DetailOrderDTO;
import com.example.betmdtnhom3.dto.OrderDTO;
import com.example.betmdtnhom3.dto.OrderGmailDTO;
import com.example.betmdtnhom3.entity.User;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.responsitory.UserReponsitory;
import com.example.betmdtnhom3.service.impl.GmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class GmailService implements GmailServiceImpl {
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserReponsitory userReponsitory;
    private static final SecureRandom random = new SecureRandom();

    @Override
    public boolean sendOrderGmail(String toEmail, OrderGmailDTO orderGmailDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("hopeso2k3@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Xác nhận đơn hàng");

            String htmlContent = Files.readString(Paths.get("src/main/resources/templates/order.html"), StandardCharsets.UTF_8);

            htmlContent = htmlContent.replace("{{TOTAL_PRICE}}", formatCurrency(orderGmailDTO.getTotalPrice()-orderGmailDTO.getShippingFee()))
                    .replace("{{DISCOUNT_AMOUNT}}", formatCurrency(orderGmailDTO.getDiscountAmount()))
                    .replace("{{SHIPPING_FEE}}", formatCurrency(orderGmailDTO.getShippingFee()))
                    .replace("{{FINAL_AMOUNT}}", formatCurrency(orderGmailDTO.getFinalAmount()))
                    .replace("{{NAME_CLIENT}}", orderGmailDTO.getNameUser())
                    .replace("{{TEL}}", orderGmailDTO.getTel())
                    .replace("{{GMAIL}}", toEmail)
                    .replace("{{ADDRESS}}", orderGmailDTO.getAddress())
                    .replace("{{DELIVERY_METHOD}}", orderGmailDTO.getDeliveryMethod());

            String orderDetailsHtml = generateOrderDetailsHtml(orderGmailDTO.getDetailOrderDTOList());
            htmlContent = htmlContent.replace("{{ORDER_DETAILS}}", orderDetailsHtml);

            helper.setText(htmlContent, true);

            ClassPathResource imageResource = new ClassPathResource("static/images/logo.jpg");
            helper.addInline("logo.png", imageResource);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sendOTPGmail(String toEmail) throws MessagingException, IOException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String otp = generateOtp(6);
            User user = userReponsitory.findByGmail(toEmail).orElseThrow(
                    () -> new AppException(ErrorCode.USER_NOT_FOUND)
            );
            user.setOtp(otp);
            user.setOtpExpiration(LocalDateTime.now().plusSeconds(60));
            userReponsitory.save(user);

            helper.setFrom("hopeso2k3@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Xác thực OTP");
            String htmlContent = Files.readString(Paths.get("src/main/resources/templates/otp.html"), StandardCharsets.UTF_8);
            htmlContent = htmlContent.replace("{{OTP_CODE}}", otp);
            helper.setText(htmlContent, true);
            ClassPathResource imageResource = new ClassPathResource("static/images/logo.jpg");
            helper.addInline("logo.png", imageResource);

            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String generateOtp(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    @Scheduled(fixedRate = 5000)
    public void cleanExpiredOtps() {
        userReponsitory.findAll().forEach(user -> {
            if (user.getOtpExpiration() != null && LocalDateTime.now().isAfter(user.getOtpExpiration())) {
                user.setOtp(null);
                user.setOtpExpiration(null);
                userReponsitory.save(user);
            }
        });
    }

    public String generateOrderDetailsHtml(List<DetailOrderDTO> products) {
        StringBuilder html = new StringBuilder();

        html.append("<div class='order-details'>");
        for (DetailOrderDTO detailOrderDTO : products) {
            html.append("<div class='product'>")
                    .append("<div class='product-info'>")
                    .append("<p><strong>Sản phẩm:</strong>").append(detailOrderDTO.getNameProduct()).append("</p>")
                    .append("<p><strong>Size:</strong> ").append(detailOrderDTO.getSize()).append("</p>")
                    .append("<p><strong>Số lượng:</strong> ").append(detailOrderDTO.getQuantity()).append("</p>")
                    .append("</div>")
                    .append("<div class='price'>").append(formatCurrency(detailOrderDTO.getPrice())).append("</div>")
                    .append("</div>");
        }
        html.append("</div>");

        return html.toString();
    }

    public static String formatCurrency(long amount) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VND";
    }
}
