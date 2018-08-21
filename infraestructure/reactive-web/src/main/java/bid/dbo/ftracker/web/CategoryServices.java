package bid.dbo.ftracker.web;

import bid.dbo.ftracker.categories.CategoryCommand;
import bid.dbo.ftracker.categories.CategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import static bid.dbo.ftracker.web.generic.ReactiveIdentityContext.identity;

@RestController
@AllArgsConstructor
@RequestMapping("categories")
public class CategoryServices {

    private final CategoryUseCase categoryUseCase;

    public Mono<Void> createCategory(CategoryCommand command){
        return categoryUseCase.createCategory(command, identity()).then();
    }

}
