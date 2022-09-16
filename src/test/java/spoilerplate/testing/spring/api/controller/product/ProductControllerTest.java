package spoilerplate.testing.spring.api.controller.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import spoilerplate.testing.spring.api.service.product.ProductService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @DisplayName("검색어와 상품 타입으로 검색한다.")
    @Test
    void search() throws Exception {
        mockMvc.perform(
                get("/api/v1/products/search")
                    .param("keyword", "카")
                    .param("productType", "ALL")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value("OK"))
            .andExpect(jsonPath("$.message").value("요청이 성공하였습니다."))
            .andExpect(jsonPath("$.data").isArray());
    }

    @DisplayName("검색어 파라미터가 없는 경우 예외가 발생한다.")
    @Test
    void searchWithEmptyKeywordParam() throws Exception {
        mockMvc.perform(
                get("/api/v1/products/search")
                    .param("productType", "ALL")
            )
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("검색어는 null이 될 수 없습니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("상품 타입 파라미터가 없는 경우 예외가 발생한다.")
    @Test
    void searchWithEmptyProductTypeParam() throws Exception {
        mockMvc.perform(
                get("/api/v1/products/search")
                    .param("keyword", "카")
                    .param("productType", "")
            )
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.code").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 타입은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

}