package mytest;

import com.chenyilei.demo1.DemoApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.regex.Pattern;

/**
 * --添加相关注释--
 *
 * @author chenyilei
 * @email 705029004@qq.com
 * @date 2019/05/27- 15:44
 */
//@SpringBootTest(classes = DemoApplication.class)
//@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    WebApplicationContext context;
    MockMvc mockMvc;


    @Before
    public void init() throws IOException, InterruptedException {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenQuery() throws IOException, InterruptedException {
        System.out.println(RestTestUtil.get("http://localhost:8080/hello?xxx=15"));
    }

    @Test
    public void testt(){
        String s = "^\\d{4}-\\d{1,2}-\\d{1,2}\\s{1}\\d{1,2}:\\d{1,2}:\\d{1,2}$";
        Pattern compile = Pattern.compile(s);
        System.out.println(compile.matcher("1888-11-11 12:12:12").find());

        File file = new File(System.getProperty("user.dir")+"/src/main/resources/error/503.html");
        System.out.println(file.exists());
    }
}
