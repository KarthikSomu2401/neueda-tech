package interview.model;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import com.google.common.hash.Hashing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class URLDtoObject {

	private final String id;
	private final String url;
	private final LocalDateTime created;

	public static URLDtoObject create(final String url) {
		final String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
		return new URLDtoObject(id, url, LocalDateTime.now());
	}

}
