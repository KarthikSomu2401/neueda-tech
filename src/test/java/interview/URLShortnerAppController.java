package interview;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import interview.controller.URLAppController;

@RunWith(SpringRunner.class)
@WebMvcTest(URLAppController.class)
public class URLShortnerAppController {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private URLAppController uRLAppController;

	@BeforeAll
	public void setConfigurations() {
		ResponseEntity<String> resp = ResponseEntity.status(HttpStatus.OK).body("1");
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("url", "https://www.programiz.com/java-programming/hello-world");
		given(uRLAppController.create(createMap)).willReturn(resp);
	}

	@Test
	public void testCreate() throws Exception {
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("url", "https://www.programiz.com/java-programming/hello-world");
		mvc.perform(post("http://localhost:10095/rest/url").content(new JSONObject(createMap).toString())
				.contentType(APPLICATION_JSON)).andExpect(status().isOk());
	}

}
