package interview.controller;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import interview.model.URLDtoObject;
import interview.model.URLError;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/rest/url")
@SuppressWarnings("rawtypes")
public class URLAppController {

	@Autowired
	private RedisTemplate<String, URLDtoObject> redisTemplate;

	@Value("${redis.ttl}")
	private long ttl;

	@PostMapping
	public ResponseEntity create(@RequestBody final Map<String, String> url) {
		final UrlValidator urlValidator = new UrlValidator(new String[] { "http", "https" });
		if (!urlValidator.isValid(url.get("url"))) {
			return ResponseEntity.badRequest().body(new URLError("Invalid URL."));
		}

		final URLDtoObject urlDtoObject = URLDtoObject.create(url.get("url"));
		log.info("URL id generated = {}", urlDtoObject.getId());
		redisTemplate.opsForValue().set(urlDtoObject.getId(), urlDtoObject, ttl, TimeUnit.SECONDS);
		return ResponseEntity.ok(urlDtoObject.getId());
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity getUrl(@PathVariable final String id) {
		final URLDtoObject urlDtoObject = redisTemplate.opsForValue().get(id);
		if (Objects.isNull(urlDtoObject)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new URLError("No such key exists."));
		} else {
			log.info("URL retrieved = {}", urlDtoObject.getUrl());
		}

		return ResponseEntity.ok(urlDtoObject);
	}
}
