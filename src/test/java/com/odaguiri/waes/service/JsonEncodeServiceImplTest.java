package com.odaguiri.waes.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.odaguiri.waes.domain.JsonEncode;
import com.odaguiri.waes.dto.JsonEncodeResponse;
import com.odaguiri.waes.repository.JsonEncodeRepository;

@RunWith(SpringRunner.class)
public class JsonEncodeServiceImplTest {

	@InjectMocks
	private JsonEncodeService jsonEncodeService = new JsonEncodeServiceImpl();
	
	@Mock
	private JsonEncodeRepository jsonEncodeRepository;
	
	private JsonEncode jsonEncode;
	
	@Before
	public void setUp() throws Exception {
		jsonEncode = new JsonEncode();
		jsonEncode.setId("1");		
	}

	@Test
	public void testSaveLeft() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.ofNullable(null));
		when(jsonEncodeRepository.save(any(JsonEncode.class))).thenReturn(jsonEncode);
		JsonEncodeResponse response = jsonEncodeService.save("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "left");
		assertNull("Json right data must be null", response.getRight());
	}
	
	@Test
	public void testSaveRight() {
		jsonEncode.setRight("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.ofNullable(null));
		when(jsonEncodeRepository.save(any(JsonEncode.class))).thenReturn(jsonEncode);
		JsonEncodeResponse response = jsonEncodeService.save("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "right");
		assertNull("Json left data must be null", response.getLeft());
	}
	
	@Test
	public void testSaveRightWhenLeftExists() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		when(jsonEncodeRepository.save(any(JsonEncode.class))).thenReturn(jsonEncode);
		JsonEncodeResponse response = jsonEncodeService.save("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "right");
		assertNotNull("Json left data must be exists", response.getLeft());
		assertNotNull("Json right data must be exists", response.getRight());
	}
	
	@Test
	public void testSaveLeftWhenRightExists() {
		jsonEncode.setRight("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		when(jsonEncodeRepository.save(any(JsonEncode.class))).thenReturn(jsonEncode);
		JsonEncodeResponse response = jsonEncodeService.save("1", "ewoJImNvdW50cnkiOiAiQnJhemlsIgp9", "left");
		assertNotNull("Json left data must be exists", response.getLeft());
		assertNotNull("Json right data must be exists", response.getRight());
	}

	@Test
	public void testDiffLeftAndRightEqual() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		jsonEncode.setRight("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		JsonEncodeResponse response = jsonEncodeService.diff("1");
		assertThat(response.getMessage().get(0), Matchers.is("Left and Right data are equal"));
	}
	
	@Test
	public void testDiffLeftAndRightNotSameSize() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		jsonEncode.setRight("ewoJImNvdW50cnkiOiAiQnJhemlsIgp91");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		JsonEncodeResponse response = jsonEncodeService.diff("1");
		assertThat(response.getMessage().get(0), Matchers.is("Left and Right data don't have same size"));
	}
	
	@Test
	public void testDiffMissingLeft() {
		jsonEncode.setRight("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		JsonEncodeResponse response = jsonEncodeService.diff("1");
		assertThat(response.getMessage().get(0), Matchers.is("Missing left data for Json with id 1"));
	}
	
	@Test
	public void testDiffMissingRight() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		JsonEncodeResponse response = jsonEncodeService.diff("1");
		assertThat(response.getMessage().get(0), Matchers.is("Missing right data for Json with id 1"));
	}
	
	@Test
	public void testDiffSameSizeDifferentChar() {
		jsonEncode.setLeft("ewoJImNvdW50cnkiOiAiQnJhemlsIgp9");
		jsonEncode.setRight("1woJImNvdW50cnkiOiAiQnJhemlsIgp9");
		when(jsonEncodeRepository.findById("1")).thenReturn(Optional.of(jsonEncode));
		JsonEncodeResponse response = jsonEncodeService.diff("1");
		assertThat(response.getMessage().get(0), Matchers.is("Char at index 0 is different"));
	}

}
