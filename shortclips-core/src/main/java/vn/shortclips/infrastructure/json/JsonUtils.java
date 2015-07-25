package vn.shortclips.infrastructure.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	private JsonUtils() {

	}

	public static String json(Object value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new JsonSerializationException(e);
		}
	}
}
