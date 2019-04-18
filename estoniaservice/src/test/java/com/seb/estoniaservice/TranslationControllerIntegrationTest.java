package com.seb.estoniaservice;

import com.seb.translationcore.pojo.TranslationPojo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EstoniaserviceApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
public class TranslationControllerIntegrationTest extends AbstractTest {
    private final String TRANSLATION_ENDPOINT = "/translation/";
    private final String RANDOM_WORD = "dsadsa";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void checkApiReturnsHelloTranslationAndSuccessAndServiceHeader_WhenPathIsValid() throws Exception {
        MvcResult mvcResult = mvc.perform(get(TRANSLATION_ENDPOINT + RANDOM_WORD))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        TranslationPojo estonianTranslation = objectMapper.readValue(response.getContentAsString(), TranslationPojo.class);

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("estonia", response.getHeaderValue("translation-service"));
        assertThat(estonianTranslation.getTranslation()).isEqualTo("tere");
    }
}
