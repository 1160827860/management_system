package xatu.csce.fzs.commonservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xatu.csce.fzs.config.RootConfig;
import xatu.csce.fzs.entity.Token;
import xatu.csce.fzs.util.TokenUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class TokenPoolTest {

    @Autowired
    private TokenPool tokenPool;

    @Test
    public void getTokenTest() {
        String s = TokenUtils.getInstance().makeToken();
        tokenPool.addToken(new Token(10));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(tokenPool.getToken(s));
    }
}
