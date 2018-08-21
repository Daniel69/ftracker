package bid.dbo.ftracker.repository.data.interfaces;

import bid.dbo.ftracker.repository.data.CategoryData;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CategoryDataRepository extends ReactiveCrudRepository<CategoryData, String>, ReactiveQueryByExampleExecutor<CategoryData> {
}
