package bid.dbo.ftracker.repository.adapters;

import bid.dbo.ftracker.transactions.Transaction;
import bid.dbo.ftracker.transactions.gateways.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.sofka.reactive.mapper.ObjectMapper;
import bid.dbo.ftracker.repository.data.TransactionData;
import bid.dbo.ftracker.repository.data.interfaces.TransactionDataRepository;
import bid.dbo.ftracker.repository.generic.AdapterOperations;

@Repository
public class TransactionRepositoryAdapter extends AdapterOperations<Transaction, TransactionData, String, TransactionDataRepository> implements TransactionRepository {

    @Autowired
    public TransactionRepositoryAdapter(TransactionDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Transaction.TransactionBuilder.class).build());
    }

}
