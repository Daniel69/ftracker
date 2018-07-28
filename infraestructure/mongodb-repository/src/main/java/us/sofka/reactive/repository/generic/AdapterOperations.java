package us.sofka.reactive.repository.generic;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import us.sofka.reactive.mapper.ObjectMapper;

public abstract class AdapterOperations<Entity, Data, ID, Repo extends ReactiveCrudRepository<Data, ID>> {

    protected Repo repository;
    protected ObjectMapper mapper;
    private Class<Data> dataClass;

    public AdapterOperations(Repo repository, ObjectMapper mapper, Class<Data> dataClass) {
        this.repository = repository;
        this.mapper = mapper;
        this.dataClass = dataClass;
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

    protected Flux<Entity> doQueryMany(Flux<Data> query) {
        return query.map(this::toEntity);
    }


    protected Data toData(Entity entity) {
        return mapper.map(entity, dataClass);
    }

    protected abstract Entity toEntity(Data data);

    protected Mono<Data> saveData(Data data) {
        return repository.save(data);
    }
}
