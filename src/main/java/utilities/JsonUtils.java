package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

import constants.FrameworkConstants;
import enums.ConfigProperties;
import lombok.experimental.UtilityClass;

import com.fasterxml.jackson.core.type.TypeReference;

public class JsonUtils {

	private static Map<String, String> CONFIGMAP = new HashMap<>();

	private JsonUtils() {
	}
	

	static {
		try {
			ObjectMapper mapper = new ObjectMapper();
			CONFIGMAP = mapper.readValue(new File(FrameworkConstants.getJsonconfigfilepath()),
					new TypeReference<Map<String, String>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String get(ConfigProperties key) throws Exception {
		if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.name().toLowerCase()))) {
			throw new Exception("Property name " + key + " is not found. Please check config.json");
		}
		return CONFIGMAP.get(key.name().toLowerCase());
	}
}
