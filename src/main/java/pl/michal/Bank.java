package pl.michal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.michal.interfejs.BankAccount;
import pl.michal.interfejs.BankTransfer;
import pl.michal.interfejs.MySQLaccess;


public class Bank implements BankTransfer {
    private int account;
    private final int FEE;
    private MySQLaccess mySQLaccess;
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);

    /**
     * Creating the BANK
     */
    public Bank() {
        account = 0;
        FEE = 1;
        mySQLaccess = new MySQLaccess();
    }

    /**
     * Transfer of money from one account to another
     *
     * @param from   BankAccount
     * @param to     BankAccount
     * @param amount fo money (int) to transfer
     */
    @Override
    public void transfer(BankAccount from, BankAccount to, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer must be greater then 0 zl!!!");
        }
        from.withdraw(amount + FEE);
        to.deposit(amount);
        account += FEE;
    }

    /**
     * Transfer of money from one account to another
     *
     * @param from   BankAccount
     * @param to     BankAccount
     * @param amount of money (double) to transfer
     */
    public void transfer(BankAccount from, BankAccount to, double amount) {
        if (amount <= 0) {
            this.transfer(from, to, 0);
        } else {
            int fromNO = from.getAccountNO(),
                    toNO = to.getAccountNO();

//            System.out.println(String.format("Transfer %.3f zl from %s account to %s account", amount, fromNO, toNO));
            logger.info(String.format("Transfer %.3f zl from %s account to %s account", amount, fromNO, toNO));

            try {
                from.withdraw(amount + FEE);

                mySQLaccess.insertBankTransaction(fromNO, toNO, amount, FEE);
                mySQLaccess.finishTransaction();
            } catch (Exception e) {
                throw new IllegalStateException("Transfer Error: " + e.toString());
            }
            account += FEE;
        }
    }

    /**
     * Printing of current account balance
     */
    public void printBankBalance() {
//        System.out.println(String.format("Bank account: %d zl", account));
        logger.info(String.format("Bank account: %d zl", account));
    }

    /**
     * Closing all connections
     *
     * @throws Exception
     */
    public void finish() throws Exception {
        mySQLaccess.closeDB();
    }
}
