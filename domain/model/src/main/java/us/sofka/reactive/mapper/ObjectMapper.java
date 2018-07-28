package us.sofka.reactive.mapper;

public interface ObjectMapper {
    <T> T map(Object var1, Class<T> var2);
    <T> T mapBuilder(Object var1, Class<T> var2);
}
