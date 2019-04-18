package com.seb.pelicanapp;

import org.json.JSONObject;
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
@SpringBootTest(classes = PelicanappApplication.class)
@WebAppConfiguration
@DirtiesContext
public class IntegrationTests extends AbstractTest {
    private final String TRANSLATION_ENDPOINT = "/country/";
    private final String BELGIUM_ENDPOINT = "Belgium/";
    private final String ESTONIA_ENDPOINT = "Estonia/";
    private final String LITHUANIA_ENDPOINT = "Lithuania/";
    private final String RANDOM_WORD = "Hello";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void checkPelicanCallsEstoniaService_ThenTestResponse() throws Exception {
        MvcResult mvcResult = mvc.perform(get(TRANSLATION_ENDPOINT + ESTONIA_ENDPOINT + RANDOM_WORD))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("estonia", response.getHeaderValue("translation-service"));
        assertThat(jsonObject.get("data")).isEqualTo("Tere");
    }

    @Test
    public void checkPelicanCallsBelgiumService_ThenTestResponse() throws Exception {
        MvcResult mvcResult = mvc.perform(get(TRANSLATION_ENDPOINT + BELGIUM_ENDPOINT + RANDOM_WORD))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("belgium", response.getHeaderValue("translation-service"));
        assertThat(jsonObject.get("data")).isEqualTo("Bonjour");
    }

    @Test
    public void checkPelicanCallsLithuaniaService_ThenTestResponse() throws Exception {
        MvcResult mvcResult = mvc.perform(get(TRANSLATION_ENDPOINT + LITHUANIA_ENDPOINT + RANDOM_WORD))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());

        Assert.assertEquals(200, response.getStatus());
        Assert.assertEquals("lithuania", response.getHeaderValue("translation-service"));
        assertThat(jsonObject.get("data")).isEqualTo("Labas");
    }

}
