import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import simu.framework.Clock;
import simu.framework.Trace;
import simu.model.Customer;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        System.out.println("**--- ALOITETAAN UUSI TESTI ---**");
        Trace.setTraceLevel(Trace.Level.INFO);
        Clock.getInstance();
        customer = new Customer();
    }

    @Test
    void testCustomerInitialization() {
        assertNotNull(customer.getId());
        assertEquals(Clock.getInstance().getTime(), customer.getArrivalTime());
    }

    @Test
    void testSetAndGetDepartTime() {
        customer.setDepartTime(10.0);
        assertEquals(10.0, customer.getDepartTime());
    }

    @Test
    void testSetAndGetArrivalTime() {
        customer.setArrivalTime(5.0);
        assertEquals(5.0, customer.getArrivalTime());
    }

    @Test
    void testSetAndGetHasOrdered() {
        customer.setHasOrdered(true);
        assertTrue(customer.hasOrdered());
    }

    @Test
    void testSetAndGetIsLeaving() {
        customer.setLeaving(true);
        assertTrue(customer.isLeaving());
    }

    @Test
    void testSetAndGetIsSeated() {
        customer.setSeated(true);
        assertTrue(customer.isSeated());
    }

    @Test
    void testSetAndGetGroupId() {
        customer.setGroupId(1);
        assertEquals(1, customer.getGroupId());
    }
}