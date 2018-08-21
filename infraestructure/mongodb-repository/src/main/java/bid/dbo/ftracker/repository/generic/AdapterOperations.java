package bid.dbo.ftracker.repository.generic;

import static org.springframework.data.domain.Example.of;
import org.springframework.data.mongodb.core.ReactiveAggregationOperation;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.sofka.reactive.mapper.ObjectMapper;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class AdapterOperations<Entity, Data, ID, Repo extends ReactiveCrudRepository<Data, ID> & ReactiveQueryByExampleExecutor<Data>> {

    protected Repo repository;
    protected ObjectMapper mapper;
    private Class<Data> dataClass;
    private Function<Data, Entity> toEntityFn;

    public AdapterOperations(Repo repository, ObjectMapper mapper, Function<Data, Entity> toEntityFn) {
        this.repository = repository;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<Data>) genericSuperclass.getActualTypeArguments()[1];
        this.toEntityFn = toEntityFn;
    }

    public Mono<Entity> save(Entity entity) {
        return Mono.just(entity)
            .map(this::toData)
            .flatMap(this::saveData)
            .thenReturn(entity);
    }

    protected Mono<Entity> doQuery(Mono<Data> query) {
        return query.map(this::toEntity);
    }

    public Mono<Entity> findById(ID id) {
        return doQuery(repository.findById(id));
    }

    public Flux<Entity> findByExample(Entity entity){
        return doQueryMany(repository.findAll(of(toData(entity))));
    }

    protected Flux<Entity> doQueryMany(Flux<Data> query) {
        return query.map(this::toEntity);
    }


    protected Data toData(Entity entity) {
        return mapper.map(entity, dataClass);
    }

    protected Entity toEntity(Data data){
        return toEntityFn.apply(data);
    }

    protected Mono<Data> saveData(Data data) {
        return repository.save(data);
    }

}
