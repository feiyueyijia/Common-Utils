package ${BasePackageName}${APIBaseTestPackageName};

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试类通用实现
 * Author ${Author}
 * Date  ${Date}
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class APIBaseTest extends Thread {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected void postRequest(String url) throws Exception {

        this.mockMvc
                .perform(post(url)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void postRequest(String url, Map<String, String> paramsMap) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        for (String param : paramsMap.keySet()) {
            params.add(param, paramsMap.get(param));
        }

        this.mockMvc
                .perform(post(url)
                        .params(params)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void postRequest(String url, String content) throws Exception {

        this.mockMvc
                .perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void getRequest(String url) throws Exception {

        this.mockMvc
                .perform(get(url)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void getRequest(String url, Map<String, String> paramsMap) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();

        for (String param : paramsMap.keySet()) {
            params.add(param, paramsMap.get(param));
        }

        this.mockMvc
                .perform(get(url)
                        .params(params)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    protected void getRequest(String url, String content) throws Exception {

        this.mockMvc
                .perform(get(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(content))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

}
