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
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manager.addBookToInventory(null));
        assertEquals("Book cannot be null.", exception.getMessage());
    }

    @Test
    void testSetInvalidDiscountRateThrowsException() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> manager.setDiscountRate(-0.01)),
                () -> assertThrows(IllegalArgumentException.class, () -> manager.setDiscountRate(1.01)));
    }

    @Test
    void testSetDiscountRateBoundaries() {
        assertDoesNotThrow(() -> manager.setDiscountRate(0.0));
        assertDoesNotThrow(() -> manager.setDiscountRate(1.0));
    }

    @Test
    void testCalculateOrderTotalInsufficientStock() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> manager.calculateOrderTotal("Java Programming", 11));
        assertEquals("Insufficient stock available.", exception.getMessage());
    }

    @Test
    void testCalculateOrderTotalRejectsBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> manager.calculateOrderTotal("   ", 1));
    }

    @Test
    void testCalculateOrderTotalRejectsNonPositiveQuantity() {
        assertThrows(IllegalArgumentException.class, () -> manager.calculateOrderTotal("Java Programming", 0));
    }

    @Test
    void testCalculateOrderTotalBookNotFound() {
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> manager.calculateOrderTotal("Unknown Book", 1));
        assertEquals("Book not found in inventory.", exception.getMessage());
    }

    @Test
    void testCalculateOrderTotalFindsBookAfterNonMatchingEntry() {
        manager.addBookToInventory(new Book("Algorithms", 50.0, 3));
        assertEquals(100.0, manager.calculateOrderTotal("Algorithms", 2), 0.001);
    }

    @Test
    void testProcessCheckoutSuccessAndStockReduction() {
        Book secondBook = new Book("Algorithms", 50.0, 3);
        manager.addBookToInventory(secondBook);

        assertTrue(manager.processCheckout("Algorithms", 2, false));
        assertEquals(10, sampleBook.getStock());
        assertEquals(1, secondBook.getStock());
    }

    @Test
    void testProcessCheckoutReturnsFalseForInvalidInputs() {
        assertAll(
                () -> assertFalse(manager.processCheckout(null, 1, false)),
                () -> assertFalse(manager.processCheckout("Java Programming", 0, false)));
        assertEquals(10, sampleBook.getStock());
    }

    @Test
    void testProcessCheckoutPropagatesOrderValidationFailure() {
        assertThrows(
                IllegalStateException.class,
                () -> manager.processCheckout("Java Programming", 11, true));
        assertEquals(10, sampleBook.getStock());
    }
}