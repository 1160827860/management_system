package xatu.csce.fzs.config;

import org.junit.Test;

/**
 * DataConfig Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 5, 2018</pre>
 */
public class DataConfigTest {


    /**
     * Method: dataSource()
     */
    @Test
    public void testDataSource() throws Exception {
        System.out.println(System.getenv("MYSQL_PASSWORD"));
    }
} 
