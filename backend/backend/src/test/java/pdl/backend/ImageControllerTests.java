package pdl.backend;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class ImageControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@BeforeAll
	public static void reset() {
  	// reset Image class static counter
  	ReflectionTestUtils.setField(Image.class, "count", Long.valueOf(0));
	}

	@Test
	@Order(1)
	public void getImageListShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void getImageShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(get("/images/1")).andDo(print()).andExpect(status().isNotFound());
		// TODO
	}

	@Test
	@Order(3)
	public void getImageShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(get("/images/0")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void deleteImagesShouldReturnMethodNotAllowed() throws Exception {
		this.mockMvc.perform(delete("/images")).andDo(print()).andExpect(status().isMethodNotAllowed());

	}

	@Test
	@Order(5)
	public void deleteImageShouldReturnNotFound() throws Exception {
		this.mockMvc.perform(delete("/images/10")).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	public void deleteImageShouldReturnSuccess() throws Exception {
		this.mockMvc.perform(delete("/images/0")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	@Order(7)
	public void createImageShouldReturnSuccess() throws Exception {
		final ClassPathResource imageResource = new ClassPathResource("test.jpg");
		MockMultipartFile file = new MockMultipartFile("file", "test.jpg",  "image/jpeg" , imageResource.getInputStream() );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("http://localhost:8080/images")
					.file(file))
					.andExpect(status().isCreated());
	}

	@Test
	@Order(8)
	public void createImageShouldReturnUnsupportedMediaType() throws Exception {
		final ClassPathResource imageResource = new ClassPathResource("test.jpg");
		MockMultipartFile file = new MockMultipartFile("file", "test.png",  "image/png" , imageResource.getInputStream() );
		this.mockMvc.perform(MockMvcRequestBuilders.multipart("http://localhost:8080/images")
					.file(file))
					.andExpect(status().isUnsupportedMediaType());
	}
}
