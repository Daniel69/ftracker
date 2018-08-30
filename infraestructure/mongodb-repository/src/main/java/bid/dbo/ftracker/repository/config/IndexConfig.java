package bid.dbo.ftracker.repository.config;

import bid.dbo.ftracker.repository.data.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@RequiredArgsConstructor
public class IndexConfig {

    private final MongoOperations mongoOperations;

    @Bean
    public CommandLineRunner createIndexes(){
        return args -> {
//            mongoOperations.indexOps(UserData.class).ensureIndex(new Index()
//                .on("authorization", Sort.Direction.ASC)
//                .unique()
//            );
            System.out.println("Indexes created!");
        };
    }

}
