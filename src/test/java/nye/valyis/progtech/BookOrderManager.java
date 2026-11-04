package nye.valyis.progtech;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// ==========================================
// 3. JUNIT 5 TEST CLASS: BookOrderManagerTest
// ==========================================
class BookOrderManagerTest {

    private BookOrderManager manager;
    private Book sampleBook;

    // Fixture setup before each test case
    @BeforeEach
    void setUp() {
        manager = new BookOrderManager();
        sampleBook = new Book("Java Programming", 100.0, 10);
        manager.addBookToInventory(sampleBook);
    }

    // Fixture teardown after each test case
    @AfterEach
    void tearDown() {
        manager = null;
        sampleBook = null;
    }

    @Test
    void testCalculateOrderTotalStandard() {
        // State testing: verifying correct mathematical calculation
        double total = manager.calculateOrderTotal("Java Programming", 2);
        assertEquals(200.0, total, 0.001, "Total should be 200.0 without discount.");
    }

    @Test
    void testCalculateOrderTotalWithDiscount() {
        manager.setDiscountRate(0.1); // 10% discount
        double total = manager.calculateOrderTotal("Java Programming", 2);
        assertEquals(180.0, total, 0.001, "Total should be 180.0 with 10% discount.");
    }

    @Test
    void testCalculateOrderTotalNullTitleThrowsException() {
        // Exception testing using assertThrows
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            manager.calculateOrderTotal(null, 1);
        });
        assertEquals("Book title cannot be null or empty.", exception.getMessage());
    }

    // ==========================================
    // HOMEWORK SECTION: BRANCH COVERAGE EXERCISE
    // ==========================================

    @Test
    void testAddNullBookThrowsException() {
        // TODO (Homework): Implement test to verify that adding a null book throws IllegalArgumentException.
        // Ensure branch coverage covers null validation in addBookToInventory.
        fail("Homework pending: implement testAddNullBookThrowsException");
    }

    @Test
    void testSetInvalidDiscountRateThrowsException() {
        // TODO (Homework): Test boundary values for discount rate (< 0.0 and > 1.0)
        // to reach 99% branch coverage on setDiscountRate.
        fail("Homework pending: implement testSetInvalidDiscountRateThrowsException");
    }

    @Test
    void testCalculateOrderTotalInsufficientStock() {
        // TODO (Homework): Test when requested quantity exceeds available stock,
        // ensuring IllegalStateException is thrown.
        fail("Homework pending: implement testCalculateOrderTotalInsufficientStock");
    }

    @Test
    void testProcessCheckoutSuccessAndStockReduction() {
        // TODO (Homework): Test processCheckout with valid inputs and verify
        // that the inventory stock is correctly reduced and boolean true is returned.
        fail("Homework pending: implement testProcessCheckoutSuccessAndStockReduction");
    }
}