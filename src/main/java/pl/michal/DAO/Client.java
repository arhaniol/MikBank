package pl.michal.DAO;

import javax.xml.crypto.Data;
import java.util.List;

public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String city;
    private String mail;
    private Data creationDate;
    private double balance;
    private int limit;
    private List<BankTransaction> bankTransactionList;
}
