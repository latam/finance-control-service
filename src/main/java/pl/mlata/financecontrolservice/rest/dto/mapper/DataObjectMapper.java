package pl.mlata.financecontrolservice.rest.dto.mapper;

public interface DataObjectMapper<T, K> {
	T mapTo(K object) throws Exception;
	K mapFrom(T object) throws Exception;
}
