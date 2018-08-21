package bid.dbo.ftracker.repository.adapters;

import bid.dbo.ftracker.categories.Category;
import bid.dbo.ftracker.categories.gateways.CategoryRepository;
import bid.dbo.ftracker.repository.data.CategoryData;
import bid.dbo.ftracker.repository.data.TransactionData;
import bid.dbo.ftracker.repository.data.interfaces.CategoryDataRepository;
import bid.dbo.ftracker.repository.data.interfaces.TransactionDataRepository;
import bid.dbo.ftracker.repository.generic.AdapterOperations;
import bid.dbo.ftracker.transactions.Transaction;
import bid.dbo.ftracker.transactions.gateways.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.sofka.reactive.mapper.ObjectMapper;

@Repository
public class CategoryRepositoryAdapter extends AdapterOperations<Category, CategoryData, String, CategoryDataRepository> implements CategoryRepository {

    @Autowired
    public CategoryRepositoryAdapter(CategoryDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.mapBuilder(d, Category.CategoryBuilder.class).build());
    }

}
