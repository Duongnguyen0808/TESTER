package com.testing;

/**
 * PaymentCalculator - Tính tiền khám bệnh dựa trên độ tuổi và giới tính
 * 
 * Bảng giá:
 * - Nam (Male): 18-35 (100€), 36-50 (120€), 51-145 (140€)
 * - Nữ (Female): 18-35 (80€), 36-50 (110€), 51-145 (140€)
 * - Trẻ em (Child): 0-17 (50€)
 */
public class PaymentCalculator {
    
    public enum Gender {
        MALE, FEMALE, CHILD
    }
    
    /**
     * Tính tiền khám bệnh
     * 
     * @param age Tuổi của bệnh nhân
     * @param gender Giới tính (MALE, FEMALE, CHILD)
     * @return Số tiền phải trả (euro)
     * @throws IllegalArgumentException nếu tuổi không hợp lệ
     */
    public double calculatePayment(int age, Gender gender) {
        // Kiểm tra tuổi hợp lệ
        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Tuổi không hợp lệ. Phải từ 0 đến 145.");
        }
        
        // Trẻ em (0-17 tuổi)
        if (gender == Gender.CHILD || age <= 17) {
            return 50.0;
        }
        
        // Nam giới
        if (gender == Gender.MALE) {
            if (age >= 18 && age <= 35) {
                return 100.0;
            } else if (age >= 36 && age <= 50) {
                return 120.0;
            } else if (age >= 51 && age <= 145) {
                return 140.0;
            }
        }
        
        // Nữ giới
        if (gender == Gender.FEMALE) {
            if (age >= 18 && age <= 35) {
                return 80.0;
            } else if (age >= 36 && age <= 50) {
                return 110.0;
            } else if (age >= 51 && age <= 145) {
                return 140.0;
            }
        }
        
        throw new IllegalArgumentException("Không thể tính toán được số tiền");
    }
    
    /**
     * Kiểm tra tính hợp lệ của tuổi
     */
    public boolean isValidAge(int age) {
        return age >= 0 && age <= 145;
    }
}
