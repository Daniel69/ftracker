package bid.dbo.ftracker.categories;

import lombok.Data;

@Data
public class CategoryCommand {
    private String id;
    private String name;
    private String description;
    private String parentId;
    private String account;
}
