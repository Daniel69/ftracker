package bid.dbo.ftracker.web;

import bid.dbo.ftracker.categories.Category;
import bid.dbo.ftracker.categories.CategoryCommand;
import bid.dbo.ftracker.categories.CategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

import static bid.dbo.ftracker.web.generic.ReactiveIdentityContext.identity;

@RestController
@AllArgsConstructor
@RequestMapping("categories")
public class CategoryServices {

    private final CategoryUseCase categoryUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Void> createCategory(@RequestBody CategoryCommand command){
        return categoryUseCase.createCategory(command, identity()).then();
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/account/{accountId}")
    public Mono<List<Category>> getCatagoryTree(@PathVariable("accountId") String accountId){
        return categoryUseCase.getCategoryTree(accountId, identity());
    }

}
