package pl.michal;

import pl.michal.interfejs.BankAccount;
import pl.michal.interfejs.BankTransfer;

public class Bank implements BankTransfer {
    private int account;
    private final int FEE;

    public Bank() {
        account = 0;
        FEE = 1;
    }

    @Override
    public void transfer(BankAccount from, BankAccount to, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer must be greater then 0 zl!!!");
        }
        from.withdraw(amount + FEE);
        to.deposit(amount);
        account += FEE;
    }

    public void printBankBalance() {
        System.out.println(String.format("Bank account: %d zl", account));
    }
}
