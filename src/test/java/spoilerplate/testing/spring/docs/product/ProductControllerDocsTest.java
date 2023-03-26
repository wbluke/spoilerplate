package spoilerplate.testing.spring.docs.product;

import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import spoilerplate.testing.spring.api.controller.product.ProductController;
import spoilerplate.testing.spring.api.service.product.ProductService;
import spoilerplate.testing.spring.api.service.product.response.ProductResponse;
import spoilerplate.testing.spring.docs.RestDocsSupport;
import spoilerplate.testing.spring.domain.product.ProductSellingStatus;
import spoilerplate.testing.spring.domain.product.ProductType;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerDocsTest extends RestDocsSupport {

    private final ProductService productService = mock(ProductService.class);

    @Override
    protected Object initController() {
        return new ProductController(productService);
    }

    @Test
    void getSellingProducts() throws Exception {
        List<ProductResponse> response = List.of(
            ProductResponse.builder()
                .id(1L)
                .productNumber("001")
                .sellingStatus(ProductSellingStatus.SELLING)
                .type(ProductType.HANDMADE)
                .name("아메리카노")
                .price(4000)
                .build()
        );
        given(productService.getSellingProducts())
            .willReturn(response);

        mockMvc.perform(
                get("/api/v1/products/selling")
            )
            .andExpect(status().isOk())
            .andDo(document("product-selling",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.STRING)
                        .description("코드"),
//                    fieldWithPath("code").type(JsonFieldType.NUMBER)
//                        .description("코드"),
//                    fieldWithPath("status").type(JsonFieldType.STRING)
//                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].id").type(JsonFieldType.NUMBER)
                        .description("상품 ID"),
                    fieldWithPath("data[].productNumber").type(JsonFieldType.STRING)
                        .description("상품 번호"),
                    fieldWithPath("data[].type").type(JsonFieldType.STRING)
                        .optional()
                        .description("상품 타입"),
                    fieldWithPath("data[].sellingStatus").type(JsonFieldType.STRING)
                        .description("판매 상태"),
                    fieldWithPath("data[].name").type(JsonFieldType.STRING)
                        .description("상품 이름"),
                    fieldWithPath("data[].price").type(JsonFieldType.NUMBER)
                        .description("상품 가격")
                )
            ));
    }

}
