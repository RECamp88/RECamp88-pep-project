package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    
    private AccountDAO accountDAO;

    // no args constructor for creating a new AccountService with a new AccountDAO
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    // constructor when accountDAO is provided
    public AccountService (AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    // using the AccountDAO to persist an account.  Account ID will not be provided. 
    public Account addAccount(Account account){
        if(account.username!="" && account.password.length() > 3){
            return accountDAO.addAccount(account);
        }
        
        return null;        
    }

    public Account loginAccount(Account account){
        return accountDAO.loginAccount(account);
    }
}
