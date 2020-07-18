package xatu.csce.fzs.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import xatu.csce.fzs.config.filter.AccessControllerFilterConfig;

import javax.servlet.Filter;

/**
 * 初始化应用程序，导入配置信息
 *
 * @author mars
 */
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Spring 基本配置
     * @return 配置信息对象
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    /**
     * Spring MVC 基本配置
     * @return 配置信息对象
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }


    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[] {new AccessControllerFilterConfig()};
    }
}