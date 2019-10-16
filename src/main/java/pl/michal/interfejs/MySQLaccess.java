package pl.michal.interfejs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class MySQLaccess {
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static final Logger logger = LoggerFactory.getLogger(MySQLaccess.class);

    /**
     * default constructor
     */
    public MySQLaccess() {
        try {
            createConnection("src\\main\\resources\\db.properties");
        } catch (Exception e) {
            logger.info("MySQL connecting problem!");
//            System.out.println("MySQL connecting problem!\n");
            e.printStackTrace();
        }
    }

    /**
     * @param dbProperties patch to the properties file
     * @throws Exception
     */
    private void createConnection(String dbProperties) throws Exception {
        try (FileInputStream f = new FileInputStream(dbProperties)) {
            //load properties from file
            Properties properties = new Properties();
            properties.load(f);

            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");

            connection = DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(false);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * this method insert all date of client account
     *
     * @param accoutNO  account number
     * @param firstName first name of account owner
     * @param lastName  last name of account owner
     * @param city      city of account owner
     * @param eMail     eMail of account owner
     * @throws Exception
     */
    public void insertClientData(int accoutNO, String firstName, String lastName, String city, String eMail) throws Exception {
        if (accoutNO <= 0 || firstName == null || lastName == null || city == null) {
            throw new IllegalArgumentException("No data passed!");
        }
        try {
            //idclientID int(11) PK
            //accountNumber int(11)
            //firstName varchar(45)
            //sirName varchar(45)
            //city varchar(45)
            //eMail varchar(45)
            //creationDate timestamp
            preparedStatement = connection.prepareStatement("INSERT INTO clientData VALUES (default,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, accoutNO);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, eMail);
            preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (Exception ex) {
                throw ex;
            }
            throw e;
        }
    }

    /**
     * this method set balance and limit of given account
     *
     * @param accountNO      account number
     * @param accountBalance account balance
     * @param accountLimit   account limit
     * @throws Exception
     */
    public void insertAccountData(int accountNO, double accountBalance, int accountLimit) throws Exception {
        //Columns:
        //accountNumber int(11) UN zerofill PK
        //accountBalance double
        //creditLimit int(4)
        //banktransaction_transactionID int
        if (accountNO <= 0) {
            throw new IllegalArgumentException("No passed Account Number");
        }
        if (Math.abs(0 - accountBalance) < 0.0001)
            accountBalance = 0.0;
        if (accountLimit < 0) {
            accountLimit = 0;
        }
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO accountData VALUES (?,?,?,null)");
            preparedStatement.setInt(1, accountNO);
            preparedStatement.setDouble(2, accountBalance);
            preparedStatement.setInt(3, accountLimit);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            try {
                if (connection != null)
                    connection.rollback();
            } catch (Exception ex) {
                throw ex;
            }
            throw e;
        }
    }

    /**
     * this method commit transaction
     *
     * @throws Exception
     */
    public void finishTransaction() throws Exception {
        try {
            if (connection != null)
                connection.commit();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * this method close connection
     *
     * @throws Exception
     */
    public void closeDB() throws Exception {
        try {
            if (resultSet != null)
                resultSet.close();
            if (preparedStatement != null)
                preparedStatement.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * this method return balance of given account
     *
     * @param accountNO account number
     * @return balance of account
     * @throws Exception
     */
    public double getAccountInfo(int accountNO) throws Exception {
        if (accountNO <= 0) {
            throw new IllegalArgumentException("No passed Account number");
        }

        try {
            statement = connection.createStatement();
//            resultSet=statement.executeQuery(String.format("SELECT accountBalance FROM accountData WHERE accountNO=%d",accountNO));
            resultSet = statement.executeQuery("SELECT accountBalance FROM accountData WHERE accountNO=" + accountNO);
            if (resultSet.next()) {
                return resultSet.getDouble(1);
            }
            return -1;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * this method update balance of given account
     *
     * @param accountNO  number of account
     * @param val        new value balance of account
     * @param transferID ID of transaction
     * @throws Exception
     */
    public void updateAccountData(int accountNO, double val, int transferID) throws Exception {
        if (accountNO <= 0 || val < 0 || transferID <= 0) {
            throw new IllegalArgumentException("Wrong argument data\n");
        }
        String sqlUpdate = "UPDATE accountData "
                + "SET accountBalance = ?,"
                + "bankTransaction_transactionID = ? "
                + "WHERE accountNO = ?";
        try {
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setDouble(1, val);
            preparedStatement.setInt(2, transferID);
            preparedStatement.setInt(3, accountNO);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * this method insert info about transaction to the database
     *
     * @param from   number of account from money are being transfer
     * @param to     number of account to money are being transfer
     * @param amount amount of money to transfer
     * @param fee    fee of transaction
     * @throws Exception
     */
    public void insertBankTransaction(int from, int to, double amount, int fee) throws Exception {
        if (from <= 0 || to <= 0 || amount <= 0) {
            throw new IllegalArgumentException("Wrong argument data\n");
        }
        //Columns:
        //transactionID int(10) UN AI PK
        //accountFrom int(11)
        //accountTo int(11)
        //amount double
        //transactionDate timestamp
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO bankTransaction VALUES(default,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, to);
            preparedStatement.setDouble(3, amount);
            preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
            int nRow = preparedStatement.executeUpdate();
            if (nRow == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int transactionID = resultSet.getInt(1);
                    double val = 0;
                    if (from != to && fee != 0) {
                        val = getAccountInfo(from);
                        updateAccountData(from, val - (amount + fee), transactionID);
                    }
                    if (from == to && fee == 0)
                        val = 0.0;
                    else
                        val = getAccountInfo(to);
                    updateAccountData(to, val + amount, transactionID);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
