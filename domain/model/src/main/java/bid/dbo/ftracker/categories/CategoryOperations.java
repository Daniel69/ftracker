package bid.dbo.ftracker.categories;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static bid.dbo.ftracker.common.StringUtil.isEmpty;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

public interface CategoryOperations {

    default Function<Category, Mono<Category>> update(String name, String description){
        return category -> !isEmpty(name) ?
            just(Category.builder().name(name).description(description).build()) :
            error(BusinessValidationException.Type.CATEGORY_NAME_REQUIRED.build());
    }

    default List<Category> createCategoryTree(List<Category> categories){
        Map<String, Category> map = new HashMap<>();
        categories.forEach(category -> map.put(category.getId(), category.toBuilder().children(new LinkedList<>()).build()));
        final List<Category> rootElements = new LinkedList<>();
        map.values().forEach(category -> {
            if(category.getParentId() != null){
                final Category parent = map.get(category.getParentId());
                if(parent != null)
                    parent.getChildren().add(category);
            }else{
                rootElements.add(category);
            }
        });
        return rootElements;
    }

}

