package bid.dbo.ftracker.categories;

import bid.dbo.ftracker.common.ex.BusinessValidationException;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

import static bid.dbo.ftracker.common.StringUtil.isEmpty;

public interface CategoryFactory {

    static Mono<Category> createNewCategory(Supplier<Category> supplier, String id){
        final Category category = supplier.get();
        if(isEmpty(category.getName()) || isEmpty(id)){
            return Mono.error(BusinessValidationException.Type.INVALID_CATEGORY_INITIAL_DATA.build());
        }else {
            return Mono.just(category.toBuilder().id(id).build());
        }
    }

    static Mono<Category> createInitialCategory(String accountId, String id){
        if(isEmpty(accountId) || isEmpty(id)) {
            return Mono.error(BusinessValidationException.Type.INVALID_CATEGORY_INITIAL_DATA.build());
        }else {
            return Mono.just(Category.builder().id(id).account(accountId).name("General").description("General Category").build());
        }
    }


}

