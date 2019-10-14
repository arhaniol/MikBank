package pl.michal.interfejs;

import java.util.Random;

public class BankAccount implements Account {
    private double accountBalance;

    /**
     *
     * @return number of account
     */
    public int getAccountNO() {
        return accountNO;
    }

    private int accountNO;
    private static int orderNO = 1;
    private static MySQLaccess mySQLaccess = new MySQLaccess();
    private static Random rand = new Random();

    /**
     * Function add amount of money to current account
     * @param amount of money (int)
     */
    @Override
    public void deposit(int amount) {
        if (amount > 0)
            accountBalance += amount;
        else
            throw new IllegalArgumentException("The amount must be greater than 0!!!\n");
    }

    /**
     * Function subtract amount of money from current account
     * @param amount of money
     */
    @Override
    public void withdraw(int amount) {
        if (amount > 0) {
            if (amount <= accountBalance)
                accountBalance -= amount;
            else
                throw new IllegalStateException("Lack of account funds\n");
        } else
            throw new IllegalArgumentException("The amount must be greater than 0!!!\n");
    }

    /**
     * Function subtract amount of money from current account
     * @param amount money
     * @throws Exception
     */
    public void withdraw(double amount) throws Exception {
        if (amount > 0) {
            try {
                double accB = mySQLaccess.getAccountInfo(accountNO);
                if ((accB - amount) < 0) {
                    throw new IllegalStateException("Lack of account founds!\n");
                }
            } catch (Exception e) {
                throw new Exception(e);
            }
        }
    }

    /**
     * Creating new bank account with full of random personal data
     */
    public BankAccount() {
        generateAccountNO();

        accountBalance = rand.nextDouble() * 100;
        String name = String.format("%c", rand.nextInt(24) + 65);
        String lastName = String.format("%c", rand.nextInt(24) + 65);
        String eMail = String.format("%s.%s@com.pl", name, lastName);
        String city = String.format("%S%s%S%s%s%s", name, name, name, lastName, lastName, lastName);
        try {
            mySQLaccess.insertClientData(accountNO, name, lastName, city, eMail);
            mySQLaccess.insertAccountData(accountNO, accountBalance, 10);
            mySQLaccess.insertBankTransaction(accountNO, accountNO, accountBalance, 0);
            mySQLaccess.finishTransaction();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     *  Creating new bank account with initial deposit value
     * @param amount
     */
    public BankAccount(int amount) {
        this.accountBalance = amount;
        generateAccountNO();
    }

    /**
     *  Generating account number
     */
    private void generateAccountNO() {
//        accountNO = String.format("0000 %d", orderNO++);
        accountNO = orderNO++;
    }

    /**
     * Printing account info
     */
    @Override
    public void depositInfo() {
        try {
            System.out.println(String.format("There is %.2f zł on account %s", mySQLaccess.getAccountInfo(accountNO), accountNO));
        } catch (Exception e) {
            System.out.println("Problem to get Deposit Info\n");
        }
    }

    /**
     * Closing data base connections
     * @throws Exception
     */
    public void Finish() throws Exception {
        mySQLaccess.closeDB();
    }
}
