package com.odaguiri.waes.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.odaguiri.waes.domain.JsonEncode;
import com.odaguiri.waes.dto.JsonEncodeRequest;
import com.odaguiri.waes.repository.JsonEncodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JsonEncodeControllerIntTest {
	
	@Autowired
	private JsonEncodeRepository jsonEncodeRepository; 
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext; 
	
	private JsonEncodeRequest request;
	private JsonEncode jsonEncode;
	private JsonEncode jsonEncodeDiffSize;
	private JsonEncode jsonEncodeSameSizeDiffChar;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.jsonEncodeRepository.deleteAll();
		request = new JsonEncodeRequest("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		jsonEncode = new JsonEncode("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		jsonEncodeDiffSize = new JsonEncode("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9=");
		jsonEncodeSameSizeDiffChar = new JsonEncode("1", "XwoJImNvdW50cnkiOiAiQnJhemlsIgp9", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
	}

	@Test
	public void testLeft() throws Exception {
		int databaseSizeBeforeCreate = jsonEncodeRepository.findAll().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/left").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(request)))
				.andExpect(status().isCreated());
		
		List<JsonEncode> jsons = jsonEncodeRepository.findAll();
        assertThat(jsons).hasSize(databaseSizeBeforeCreate + 1);
        
        JsonEncode testJsonEncode = jsons.get(jsons.size() - 1);
        assertThat(testJsonEncode.getId()).isEqualTo("1");
        assertThat(testJsonEncode.getLeft()).isEqualTo("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
        assertThat(testJsonEncode.getRight()).isNull();
	}

	@Test
	public void testRight() throws Exception {
		int databaseSizeBeforeCreate = jsonEncodeRepository.findAll().size();
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/diff/1/right").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(request)))
				.andExpect(status().isCreated());
		
		List<JsonEncode> jsons = jsonEncodeRepository.findAll();
        assertThat(jsons).hasSize(databaseSizeBeforeCreate + 1);
        
        JsonEncode testJsonEncode = jsons.get(jsons.size() - 1);
        assertThat(testJsonEncode.getId()).isEqualTo("1");
        assertThat(testJsonEncode.getLeft()).isNull();
        assertThat(testJsonEncode.getRight()).isEqualTo("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
	}

	@Test
	public void testDiffWithEqual() throws Exception {
		jsonEncodeRepository.save(jsonEncode);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message[0]", is("Left and Right data are equal")));		
	}
	
	@Test
	public void testDiffWithDiffSize() throws Exception {
		jsonEncodeRepository.save(jsonEncodeDiffSize);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message[0]", is("Left and Right data don't have same size")));		
	}
	
	@Test
	public void testDiffWithEqualAndDiffChar() throws Exception {
		jsonEncodeRepository.save(jsonEncodeSameSizeDiffChar);
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/diff/1").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message[0]", is("Char at index 0 is different")));		
	}

}
