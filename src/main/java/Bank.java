import java.util.HashMap;
import java.util.Random;

public class Bank {

    private final HashMap<String, Account> accounts;
    private final Random random = new Random();

    public Bank() {
        accounts = new HashMap<>();
    }

    public synchronized boolean isFraud()
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, long amount) {
        if (getBalance(fromAccountNum) > amount && amount != 0
                && accounts.get(fromAccountNum).isBlock() && accounts.get(toAccountNum).isBlock()) {
            synchronized (accounts) {
                accounts.get(fromAccountNum).setMoney(-amount);
                accounts.get(toAccountNum).setMoney(amount);
            }
            System.out.println("Перевод выполнен со счета №" + fromAccountNum + ", всего средств " + accounts.get(fromAccountNum).getMoney() +
                    ", на счет №" + toAccountNum + ", всего средств " + accounts.get(toAccountNum).getMoney() + ". Сумма операции " + amount);
            if (amount > 50000) {
                try {
                    if (isFraud()) {
                        accounts.get(fromAccountNum).setBlock(true);
                        accounts.get(toAccountNum).setBlock(true);
                        System.out.println("Счета №" + accounts.get(fromAccountNum).getAccNumber() + ", №"
                                + accounts.get(toAccountNum).getAccNumber() + " заблокированы");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public long getBalance(String accountNum) {
        return accounts.get(accountNum).getMoney();
    }

    public long getSumAllAccounts() {
        long sum = 0;
        for (Account account : accounts.values()) {
            sum += account.getMoney();
        }
        return sum;
    }

    public Account createAccount(long money) {
        String accNumber = Integer.toString(accounts.size() + 1);
        Account account = new Account(money, accNumber);
        accounts.put(accNumber, account);
        return account;
    }

    public String getRandomNumberAcc(String accountNumber) {
        String newNumber = String.valueOf((int) (Math.random() * (accounts.size() - 1) + 1));
        if (newNumber.equals(accountNumber)) {
            newNumber = null;
        }
        return newNumber;
    }
}
