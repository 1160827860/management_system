package xatu.csce.fzs.commonservice;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import xatu.csce.fzs.entity.Token;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>Token 池,为 Token 提供统一性管理</p>
 *
 * @author mars
 * @date 2018.11.11 22:05
 */
@SuppressWarnings("unused")
public class TokenPool {

    // region 实现单例模式，保证池唯一性
    /**
     * TokenPool 单例模式
     */
    private static volatile TokenPool INSTANCE;

    /**
     * 池初始化函数
     */
    private TokenPool() {
        // 初始时将池的大小设置为20
        tokens = new HashMap<>(20);
        readWriteLock = new ReentrantReadWriteLock();

        // 开启定时任务
        TokenTimerTask timerTask = new TokenTimerTask();

        ScheduledExecutorService timer = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("[TokenTimerTokenThread - %d]").daemon(true).build());
        timer.scheduleWithFixedDelay(timerTask, 0, 30, TimeUnit.MINUTES);
    }

    public static TokenPool getInstance() {
        if (TokenPool.INSTANCE == null) {
            synchronized (TokenPool.class) {
                if (TokenPool.INSTANCE == null) {
                    TokenPool.INSTANCE = new TokenPool();
                }
            }
        }

        return TokenPool.INSTANCE;
    }
    //endregion 保证唯一性

    /**
     * <p>池底层容器</p>
     * <p>HashMap 具有较高的查找效率，Token 池执行读操作的频率极高，因此需要较高的查询效率</p>
     */
    private Map<String, Token> tokens;

    /**
     * 池中的 Token 数目
     */
    private int tokenSize = 0;

    /**
     * 读写锁，ReentrantReadWriteLock 偏向于读
     */
    private ReadWriteLock readWriteLock;

    // region 池的基本操作：查询、添加、移除

    /**
     * 获取指定的 Token，并且更新 Token 的时效
     *
     * @param tokenStr Token 字符串
     * @return 如果池中不存在指定的 Token 或制定的 Token 已经过期则返回 Null
     */
    @SuppressWarnings("WeakerAccess")
    public Token getToken(String tokenStr) {
        // 加读锁，保证在读的时候不会出现值被修改的情况
        Lock readLock = this.readWriteLock.readLock();
        readLock.lock();

        if (!this.tokens.containsKey(tokenStr)) {
            // 释放读锁
            readLock.unlock();
            return null;
        }

        Token token = this.tokens.get(tokenStr);

        // 判断 Token 是否有效
        if (!token.isExpired()) {
            // 更新 Token 时效
            token.updateLatsTime();

            // 释放读锁
            readLock.unlock();
            return token;
        }

        // 释放读锁
        readLock.unlock();

        // 清除过期的 Token
        this.removeToken(token.getTokenStr());
        return null;
    }

    /**
     * 向池中添加新的 Token
     *
     * @param token 新添加的 Token，不可为空
     * @return 是否成功添加，true：成功  false：失败
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean addToken(Token token) {
        // 加写锁
        Lock writeLock = this.readWriteLock.writeLock();
        writeLock.lock();

        boolean isSuccess = false;

        // 非空检查，防止重复添加
        if (token != null && !tokens.containsKey(token.getTokenStr())) {
            this.tokens.put(token.getTokenStr(), token);
            this.tokenSize++;
            isSuccess = true;
        }

        // 释放写锁，防止阻塞程序
        writeLock.unlock();

        return isSuccess;
    }

    /**
     * 移除指定的 Token
     *
     * @param tokenStr Token 唯一凭证
     * @return 是否成功移除，true：成功  false：失败
     */
    @SuppressWarnings({"UnusedReturnValue", "WeakerAccess"})
    public boolean removeToken(String tokenStr) {
        // 加写锁
        Lock writeLock = this.readWriteLock.writeLock();
        writeLock.lock();

        boolean isSuccess = false;

        if (this.tokens.containsKey(tokenStr)) {
            this.tokens.remove(tokenStr);
            this.tokenSize--;
        }

        // 释放写锁，防止阻塞程序
        writeLock.unlock();

        //noinspection ConstantConditions
        return isSuccess;
    }
    // endregion

    // region 池的定时移除功能

    private void removeToken() {
        // 加写锁
        Lock writeLock = this.readWriteLock.writeLock();
        writeLock.lock();


        // 释放写锁，防止阻塞程序
        writeLock.unlock();
    }

    // endregion

    // region 定时任务模型

    /**
     * <p>定时任务模型，移除 Token 池中过期的对象</p>
     * <p>使用内部类可以自由的访问外部类的对象</p>
     */
    private class TokenTimerTask extends TimerTask {

        @Override
        public void run() {
            for (int i = 0; i < tokenSize; i++) {
                Set<String> keySet = tokens.keySet();
                for (String key : keySet) {
                    if (tokens.get(key).isExpired()) {
                        tokens.remove(key);
                    }
                }
            }

            tokenSize = tokens.size();
        }
    }
    // endregion
}
