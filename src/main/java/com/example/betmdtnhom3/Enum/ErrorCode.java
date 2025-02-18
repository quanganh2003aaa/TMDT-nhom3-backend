package com.example.betmdtnhom3.Enum;

public enum ErrorCode {
    AUTHENTICATED(555, "Lỗi authenticated"),
    AUTHORIZATION(444, "Bạn không có quyền truy cập"),
    USER_NOT_FOUND(5404, "Tài khoản không tồn tại"),
    USER_EXISTED(5111, "Tài khoản đã tồn tại"),
    INVALID_QUANTITY_ORDER(9001, "Số lượng đặt hàng lỗi"),
    INVALID_TEL(9002, "Số điện thoại không đúng định dạng"),
    INVALID_NAME_USER(9003, "Độ dài của tên không phù hợp"),
    INVALID_NOT_EMPTY(9004, "Nội dung là bắt buộc"),
    INVALID_GMAIL(9005, "Lỗi gmail"),
    ERROR_OTHER(9999, "Lỗi mới"),
    PRODUCT_EXITED(1111, "Sản phẩm đã tồn tại"),
    PRODUCT_CREATE_ERROR(1112, "Tạo sản phẩm thất bại"),
    PRODUCT_UPDATE_ERROR(1113, "Sửa sản phẩm thất bại"),
    PRODUCT_DELETE_ERROR(1114, "Xóa sản phẩm thất bại"),
    PRODUCT_NOT_FOUND(1404, "Sản phẩm không tồn tại"),
    ORDER_CREATE_ERROR(2111, "Đặt hàng thất bại"),
    ORDER_ERROR(2112, "Thao tác với đơn hàng bị lỗi"),
    CREATE_RATE_ERROR(3001, "Lỗi khi đánh giá sản phẩm"),
    NUMBER_RATE_ERROR(3002, "Lỗi điểm đánh giá sản phẩm"),
    CONTENT_RATE_ERROR(3003, "Nội dung đánh giá quá dài"),
    USER_RATE_ERROR(3004, "Bạn chưa được đánh giá sản phẩm"),
    USER_RATE_EXISTED(3005, "Bạn đã đánh giá sản phẩm 1 lần"),
    FILE_UPLOAD_ERROR(8001, "Lỗi thêm ảnh"),
    FILE_DELETE_ERROR(8002, "Lỗi xóa ảnh"),
    UPDATE_STORE_ERROR(7001, "Lỗi sửa thông tin cửa hàng"),

    CATEGORY_EXITED(6661, "Danh mục này đã tồn tại"),
    CATEGORY_CREATE_ERROR(6662, "Tạo danh mục thất bại"),
    CATEGORY_UPDATE_ERROR(6663, "Sửa danh mục thất bại"),
    CATEGORY_DELETE_ERROR(6664, "Xóa danh mục thất bại"),
    CATEGORY_NOT_FOUND(6665, "Danh mục không tồn tại"),
    BLOG_CREATE_ERROR(6666, "Tạo bài viết thất bại"),
    BLOG_UPDATE_ERROR(6667, "Sửa bài viết thất bại"),
    BLOG_NOT_FOUND(6668, "Bài viết không tồn tại"),
    BLOG_DELETE_ERROR(6669, "Xóa bài viết thất bại"),
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
