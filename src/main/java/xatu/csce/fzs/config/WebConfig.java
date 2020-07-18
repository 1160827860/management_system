package xatu.csce.fzs.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.List;

/**
 * WEB 配置
 *
 * @author mars
 */
@Configuration
@ComponentScan(basePackages = {"xatu.csce.fzs.service", "xatu.csce.fzs.controller"})
@EnableWebMvc
@EnableScheduling
@EnableTransactionManagement
public class WebConfig implements WebMvcConfigurer {
    /**
     * 配置路由匹配规则
     *
     * @param configurer 配置变量
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper pathHelper = new UrlPathHelper();
        // For @MatrixVariable's
        pathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(pathHelper);
    }

    /**
     * 配置异步支持
     *
     * @param configurer 配置变量
     */
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(3000);
        configurer.registerCallableInterceptors(new TimeoutCallableProcessingInterceptor());
    }

    /**
     * 配置消息转换器
     * @param converters 转换器集合
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());

        ObjectMapper objectMapper = new ObjectMapper();
        // 转换为 JSON 之后按照字母排序
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true);

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

        converters.add(converter);
    }
}
