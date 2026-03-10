package com.example;

/**
 * Kiểm tra số điện thoại Việt Nam hợp lệ.
 * Quy tắc:
 * - Bắt đầu bằng 0 hoặc +84
 * - Sau chuẩn hóa: 03x, 05x, 07x, 08x, 09x
 * - Tổng 10 chữ số (sau chuẩn hóa)
 * - Chỉ chứa 0-9, +, space
 */
public class PhoneValidator {

    public static boolean isValid(String phone) {
        // D1: null hoặc rỗng
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        // Xóa khoảng trắng
        String cleaned = phone.replaceAll("\\s+", "");

        // D2: chỉ cho phép chữ số và dấu +
        if (!cleaned.matches("[0-9+]+")) {
            return false;
        }

        // D3: chuẩn hóa +84 → 0
        if (cleaned.startsWith("+84")) {
            cleaned = "0" + cleaned.substring(3);
        }

        // D4: phải bắt đầu bằng 0
        if (!cleaned.startsWith("0")) {
            return false;
        }

        // D5: phải đúng 10 chữ số
        if (cleaned.length() != 10) {
            return false;
        }

        // D6: đầu số hợp lệ
        String prefix = cleaned.substring(0, 2);
        if (!prefix.matches("03|05|07|08|09")) {
            return false;
        }

        return true;
    }
}
