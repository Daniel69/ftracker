package us.sofka.reactive.common;

public interface GenericCommand {

    <E extends Enum<E>> E getAction();
}
