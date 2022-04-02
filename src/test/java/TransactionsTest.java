import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class TransactionsTest extends TestCase {
    private Bank bank;
    private List<Client> clients;

    @Override
    protected void setUp() {
        bank = new Bank();
        clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clients.add(new Client(bank));
        }
    }

    public void testTransfer() {
        long beforeTransfer = bank.getSumAllAccounts();
        List<Thread> threads = new ArrayList<>();
        for (Client client : clients) {
            threads.add(new Thread(client));
        }
        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        long afterTransfer = bank.getSumAllAccounts();
        assertEquals(beforeTransfer, afterTransfer);
    }
}
