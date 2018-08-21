package bid.dbo.ftracker.categories;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class Category {

    private final String id;
    private final String name;
    private final String description;
    private final String account;
    private final String parentId;
    private final List<Category> children;

}

