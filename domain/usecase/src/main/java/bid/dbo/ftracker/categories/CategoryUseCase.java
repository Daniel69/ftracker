package bid.dbo.ftracker.categories;

import bid.dbo.ftracker.categories.gateways.CategoryRepository;
import bid.dbo.ftracker.events.UserAccountCreated;
import bid.dbo.ftracker.users.User;
import bid.dbo.ftracker.users.UserAccount;
import bid.dbo.ftracker.users.UserAccountOperations;
import bid.dbo.ftracker.users.gateways.UserAccountRepository;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import us.sofka.reactive.mapper.ObjectMapper;


import java.util.List;

import static reactor.function.TupleUtils.function;
import static bid.dbo.ftracker.categories.CategoryFactory.createInitialCategory;
import static bid.dbo.ftracker.categories.CategoryFactory.createNewCategory;
import static bid.dbo.ftracker.common.UniqueIDGenerator.uuid;

@AllArgsConstructor
public class CategoryUseCase implements UserAccountOperations, CategoryOperations {

    private final CategoryRepository categories;
    private final UserAccountRepository userAccounts;
    private final ObjectMapper mapper;

    //TODO: asociar categoria a la cuenta
    public Mono<Category> createCategory(CategoryCommand command, Mono<User> user) {
        return validateUserAccount(command.getAccount(), user).then(uuid())
            .flatMap(uuid -> createNewCategory(() -> mapper.mapBuilder(command, Category.CategoryBuilder.class).build(), uuid))
            .flatMap(categories::save);
    }

    public Mono<Void> createGeneralCategory(UserAccountCreated createdAccount){
        return uuid()
            .flatMap(uuid -> createInitialCategory(createdAccount.getAccountId(), uuid))
            .flatMap(categories::save)
            .then();
    }

    public Mono<Category> updateCategory(CategoryCommand command, Mono<User> user) {
        return categories.findById(command.getId())
            .flatMap(category -> validateUserAccount(category.getAccount(), user).thenReturn(category))
            .flatMap(update(command.getName(), command.getDescription()))
            .flatMap(categories::save);
    }

    public Mono<Category> findCategory(String id, Mono<User> user) {
        return categories.findById(id)
            .flatMap(category -> validateUserAccount(category.getAccount(), user).thenReturn(category));
    }

    public Mono<List<Category>> getCategoryTree(String userAccountId, Mono<User> user) {
        return validateUserAccount(userAccountId, user)
            .thenMany(categories.findByExample(Category.builder().account(userAccountId).build()))
            .collectList().map(categories -> createCategoryTree(categories));
    }

    private Mono<UserAccount> validateUserAccount(String userAccountId, Mono<User> identity) {
        return userAccounts.findById(userAccountId)
            .zipWith(identity)
            .flatMap(function(this::validateOwnership));
    }

}
