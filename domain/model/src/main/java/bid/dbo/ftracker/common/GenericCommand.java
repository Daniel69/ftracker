package bid.dbo.ftracker.common;

public interface GenericCommand<E extends Enum<E>>  {

    E getAction();
}
