package bid.dbo.ftracker.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CategoryData {
    @Id
    private String id;
    private String name;
    private String description;
    private String parentId;
}
