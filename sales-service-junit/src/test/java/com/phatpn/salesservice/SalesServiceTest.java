package com.phatpn.salesservice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SalesServiceTest {

    private SalesService service;

    @BeforeEach
    void setUp() {
        service = new SalesService();
    }

    // ==========================================
    // 1. calculateSubtotal() (Tối thiểu 3 test)
    // ==========================================
    @Test
    void testCalculateSubtotal_NormalCases() {
        // Giá 500, số lượng 2 -> kỳ vọng 1000
        Product p1 = new Product("P01", "Bút", 500, 2);
        assertEquals(1000.0, service.calculateSubtotal(p1), 0.001);

        // Giá 1500, số lượng 3 -> kỳ vọng 4500
        Product p2 = new Product("P02", "Vở", 1500, 3);
        assertEquals(4500.0, service.calculateSubtotal(p2), 0.001);

        // Giá 100, số lượng 1 -> kỳ vọng 100
        Product p3 = new Product("P03", "Thước", 100, 1);
        assertEquals(100.0, service.calculateSubtotal(p3), 0.001);
    }

    // ==========================================
    // 2. calculateDiscount() (Tối thiểu 6 test với Boundary Values) - Áp dụng Parameterized Test
    // ==========================================
    @ParameterizedTest
    @CsvSource({
        "999.99, 0.0",       // < 1000 -> 0%
        "1000.0, 50.0",      // 1000.. < 5000 -> 5% (1000 * 0.05 = 50)
        "4999.99, 249.9995", // cận dưới 5000 -> 5%
        "5000.0, 500.0",     // 5000.. < 10000 -> 10% (5000 * 0.10 = 500)
        "9999.99, 999.999",  // cận dưới 10000 -> 10%
        "10000.0, 1500.0"    // >= 10000 -> 15% (10000 * 0.15 = 1500)
    })
    void testCalculateDiscount_BoundaryValues(double subtotal, double expectedDiscount) {
        assertEquals(expectedDiscount, service.calculateDiscount(subtotal), 0.001);
    }

    // ==========================================
    // 3. calculateShippingFee() (Tối thiểu 3 test)
    // ==========================================
    @Test
    void testCalculateShippingFee() {
        // subtotal < 2000 -> ship = 50
        assertEquals(50.0, service.calculateShippingFee(1999.99), 0.001);

        // subtotal == 2000 -> ship = 0 (theo đề: >= 2000 là 0)
        assertEquals(0.0, service.calculateShippingFee(2000.0), 0.001);

        // subtotal > 2000 -> ship = 0
        assertEquals(0.0, service.calculateShippingFee(5000.0), 0.001);
    }

    // ==========================================
    // 4. calculateTotal() (Tối thiểu 2 test)
    // Total = Subtotal - Discount + Shipping
    // ==========================================
    // ==========================================
    // 4. calculateTotal() (Tối thiểu 2 test)
    // Total = Subtotal - Discount + Shipping
    // ==========================================
    @Test
    void testCalculateTotal() {
        // Case 1: Subtotal = 1000 -> Discount = 50 -> Shipping = 50 -> Total = 1000 - 50 + 50 = 1000
        Product p1 = new Product("P01", "Bút", 500, 2);
        assertEquals(1000.0, service.calculateTotal(p1), 0.001);

        // Case 2: Subtotal = 500 -> Discount = 0 -> Shipping = 50 -> Total = 500 - 0 + 50 = 550
        Product p2 = new Product("P02", "Thước", 500, 1);
        assertEquals(550.0, service.calculateTotal(p2), 0.001);
    }

    // ==========================================
    // 5. classifyCustomer() (Tối thiểu 4 test)
    // ==========================================
    @Test
    void testClassifyCustomer() {
        assertEquals("REGULAR", service.classifyCustomer(999.99));
        assertEquals("SILVER", service.classifyCustomer(1000.0));
        assertEquals("GOLD", service.calculateDiscount(5000.0) >= 0 ? service.classifyCustomer(5000.0) : "");
        // Mốc quan trọng: đúng 10000 theo đề là VIP (>= 10000)
        assertEquals("VIP", service.classifyCustomer(10000.0));
    }

    // ==========================================
    // 6. Exceptions (Tối thiểu 2 test với assertThrows)
    // ==========================================
    @Test
    void testExceptions() {
        // Exception 1: Product null ném lỗi
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateSubtotal(null);
        });

        // Exception 2: Subtotal âm ném lỗi
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateDiscount(-10);
        });
    }
    @Test
    void testAdditionalCoverage() {
        // Phủ nhánh subtotal âm cho calculateShippingFee
        assertThrows(IllegalArgumentException.class, () -> {
            service.calculateShippingFee(-1);
        });

        // Phủ các nhánh ngoại lệ validation của Product
        assertThrows(IllegalArgumentException.class, () -> new Product(null, "A", 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new Product("", "A", 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new Product("P", null, 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new Product("P", "", 10, 1));
        assertThrows(IllegalArgumentException.class, () -> new Product("P", "A", 0, 1));
        assertThrows(IllegalArgumentException.class, () -> new Product("P", "A", 10, 0));

        // Phủ nốt các hàm getter của Product
        Product p = new Product("P99", "Test", 100, 2);
        assertEquals("P99", p.getProductId());
        assertEquals("Test", p.getProductName());
    }
}