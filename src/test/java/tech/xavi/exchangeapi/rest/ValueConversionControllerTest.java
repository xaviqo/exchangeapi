package tech.xavi.exchangeapi.rest;

import jakarta.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tech.xavi.exchangeapi.service.CurrencySymbolService;
import tech.xavi.exchangeapi.service.ValueConversionService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
class ValueConversionControllerTest {

    @MockBean
    private ValueConversionService conversionService;

    @MockBean
    private CurrencySymbolService currencySymbolService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach

    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("valueConversionController"));
    }

    //TODO: Finish test, mocking service layer
    @Disabled
    @Test
    void testGetMultipleValueConversion_shouldReturnException_withNoCurrencyFoundError() throws Exception {

        // GIVEN
        String payload = "{\n" +
                "  \"from\":\"EUR\",\n" +
                "  \"amountTo\":10,\n" +
                "  \"targetCurrencies\":[\"GBP\",\"HRK\",\"MXN\"]\n" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post("/v1/multiple-values-conversion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isOk())
                .andReturn();

        log.info(mvcResult.toString());
    }
}