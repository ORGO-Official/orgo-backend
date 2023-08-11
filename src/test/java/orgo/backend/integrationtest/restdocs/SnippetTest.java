package orgo.backend.integrationtest.restdocs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import orgo.backend.domain._2user.dao.UserRepository;
import orgo.backend.domain._etc.test.TestController;
import orgo.backend.setting.IntegrationTest;

import static hansol.restdocsdsl.docs.RestDocsAdapter.docs;
import static hansol.restdocsdsl.docs.RestDocsRequest.requestFields;
import static hansol.restdocsdsl.element.FieldElement.field;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class SnippetTest extends IntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("스니펫 생성 테스트")
    void test() throws Exception {
        // given
        TestController.TestRequest requestDto = new TestController.TestRequest("test");
        //when
        ResultActions actions = mvc.perform(post("/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));

        //then
        actions.andExpect(status().isOk())
                .andDo(docs("test",
                        requestFields(
                                field("field").description("테스트 요청 필드")
                        ),
                        requestFields(
                                field("field").description("테스트 응답 필드")
                        )));
    }
}
