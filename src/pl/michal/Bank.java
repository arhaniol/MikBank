package pl.michal;

import pl.michal.interfejs.BankAccount;
import pl.michal.interfejs.BankTransfer;
import pl.michal.interfejs.MySQLaccess;

public class Bank implements BankTransfer {
    private int account;
    private final int FEE;
    private MySQLaccess mySQLaccess;

    public Bank() {
        account = 0;
        FEE = 1;
        mySQLaccess = new MySQLaccess();
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

    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (amount <= 0) {
            this.transfer(from, to, 0);
        } else {
            int fromNO = from.getAccountNO(),
                    toNO = to.getAccountNO();

            System.out.println(String.format("Transfer %.3f zl from %s account to %s account", amount, fromNO, toNO));

            try {
                from.withdraw(amount + FEE);

                mySQLaccess.InsertBankTransaction(fromNO, toNO, amount, FEE);
                mySQLaccess.FinishTransaction();
            } catch (Exception e) {
                throw new IllegalStateException("Transfer Error: " + e.toString());
            }
            account += FEE;
        }
    }

    public void printBankBalance() {
        System.out.println(String.format("Bank account: %d zl", account));
    }

    public void Finish() throws Exception {
        mySQLaccess.CloseDB();
    }
}
