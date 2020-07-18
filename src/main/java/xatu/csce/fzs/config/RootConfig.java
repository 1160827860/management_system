package xatu.csce.fzs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xatu.csce.fzs.commonservice.TokenPool;

/**
 * Spring 配置
 *
 * @author mars
 */
@Configuration
@Import(DataConfig.class)
public class RootConfig {

    @Bean
    public TokenPool getTokenPool() {
        return TokenPool.getInstance();
    }
}
