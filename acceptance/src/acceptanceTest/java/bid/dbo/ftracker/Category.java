package bid.dbo.ftracker;

import lombok.Data;

import java.util.List;

@Data
public class Category {
    private String id;
    private String name;
    private String description;
    private String account;
    private String parentId;
    private List<Category> children;

}
