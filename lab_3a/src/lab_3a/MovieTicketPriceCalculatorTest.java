package lab_3a;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovieTicketPriceCalculatorTest {

    private MovieTicketPriceCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new MovieTicketPriceCalculator(
                LocalTime.of(12, 0),
                LocalTime.of(17, 10),
                12,
                65
        );
    }

    @Test
    void testStandardAdultTicket() {
        int price = calculator.computePrice(LocalTime.of(19, 0), 30);
        assertEquals(2700, price, "Standard adult ticket failed.");
    }

    @Test
    void testMatineeAdultTicket() {
        int price = calculator.computePrice(LocalTime.of(14, 0), 30);
        assertEquals(2400, price, "Matinee adult ticket failed.");
    }

    @Test
    void testMatineeChildTicket() {
        int price = calculator.computePrice(LocalTime.of(14, 0), 10);
        assertEquals(2400 - 300, price, "Matinee child ticket failed.");
    }

    @Test
    void testMatineeSeniorTicket() {
        int price = calculator.computePrice(LocalTime.of(14, 0), 70);
        assertEquals(2400 - 400, price, "Matinee senior ticket failed.");
    }

    @Test
    void testStandardChildTicket() {
        int price = calculator.computePrice(LocalTime.of(19, 0), 10);
        assertEquals(2700 - 300, price, "Standard child ticket failed.");
    }

    @Test
    void testStandardSeniorTicket() {
        int price = calculator.computePrice(LocalTime.of(19, 0), 70);
        assertEquals(2700 - 400, price, "Standard senior ticket failed.");
    }

    @Test
    void testMatineeEdgeCaseStartTime() {
        int price = calculator.computePrice(LocalTime.of(12, 0), 30);
        assertEquals(2400, price, "Matinee edge case start time failed.");
    }

    @Test
    void testMatineeEdgeCaseEndTime() {
        int price = calculator.computePrice(LocalTime.of(17, 10), 30);
        assertEquals(2700, price, "Matinee edge case end time failed.");
    }

    @Test
    void testBeforeMatinee() {
        int price = calculator.computePrice(LocalTime.of(11, 0), 30);
        assertEquals(2700, price, "Ticket before matinee time failed.");
    }

    @Test
    void testAfterMatinee() {
        int price = calculator.computePrice(LocalTime.of(18, 0), 30);
        assertEquals(2700, price, "Ticket after matinee time failed.");
    }

    @Test
    void testJustBeforeEndMatineeTime() {
        int price = calculator.computePrice(LocalTime.of(17, 9), 30);
        assertEquals(2400, price, "Matinee price just before end time failed.");
    }

    @Test
    void testConstructorWithNullStartMatineeTime() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new MovieTicketPriceCalculator(null, LocalTime.of(17, 0), 12, 65);
        });
        assertEquals("matinee start time cannot be null", exception.getMessage());
    }

    @Test
    void testConstructorWithNullEndMatineeTime() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            new MovieTicketPriceCalculator(LocalTime.of(12, 0), null, 12, 65);
        });
        assertEquals("matinee end time cannot be null", exception.getMessage());
    }

    @Test
    void testConstructorWithStartTimeAfterEndTime() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new MovieTicketPriceCalculator(LocalTime.of(18, 0), LocalTime.of(17, 0), 12, 65);
        });
        assertEquals("matinee start time cannot come after end time", exception.getMessage());
    }
}
