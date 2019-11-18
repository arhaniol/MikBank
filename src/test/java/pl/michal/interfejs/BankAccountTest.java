package pl.michal.interfejs;

import org.junit.Test;

import static org.junit.Assert.*;

public class BankAccountTest {
    public BankAccount bankAccount = new BankAccount(20);

    @org.junit.Test
    public void getAccountNOTest() {
        getExecutionPath();
        assertEquals(1, bankAccount.getAccountNO());
        assertNotEquals(30, bankAccount.getAccountNO());
    }

    @org.junit.Test//(expected = IllegalArgumentException.class)
    public void depositTest() {
//        bankAccount.deposit(-2);
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(- 1));
    }

    @org.junit.Test
    public void withdraw() {
    }

    @org.junit.Test
    public void testWithdraw() {
    }

    @org.junit.Test
    public void depositInfo() {
    }

    public static void getExecutionPath() {
        try {
            String executionPath = System.getProperty("user.dir");
            System.out.println("Executing at =>" + executionPath.replace("\\", "/"));
        } catch (Exception e) {
            System.out.println("Exception caught =" + e.getMessage());
        }
    }
}