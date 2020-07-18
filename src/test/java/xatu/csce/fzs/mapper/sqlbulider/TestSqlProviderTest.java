package xatu.csce.fzs.mapper.sqlbulider;

import org.junit.Test;
import xatu.csce.fzs.entity.User;
import xatu.csce.fzs.mapper.UserMapper;

/**
 * TestSqlProvider Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 5, 2018</pre>
 */
public class TestSqlProviderTest {


    /**
     * Method: buildInsertUser(User user)
     */
    @Test
    public void testBuildInsertUser() {
        User user = new User();
        user.setEducation(User.Education.B);
        user.setAccount("16060208106");
        user.setMajor(602);
        System.out.println(UserMapper.UserSqlProvider.buildInsertUser(user));
    }
}
