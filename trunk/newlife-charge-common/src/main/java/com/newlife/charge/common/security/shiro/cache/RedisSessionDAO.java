package com.newlife.charge.common.security.shiro.cache;

import com.newlife.charge.common.Servlets;
import com.newlife.charge.common.serialize.SerializeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    private RedisManager redisManager;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";

    @Override
    protected Serializable doCreate(Session session) {
        HttpServletRequest request = Servlets.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            // 如果是静态文件，则不创建SESSION
            if (Servlets.isStaticFile(uri)) {
                return null;
            }
        }

        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }


        Session s = (Session) SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));
        return s;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        // 如果会话过期/停止，就不更新了
        if (session == null || session.getId() == null || (session instanceof ValidatingSession && !((ValidatingSession) session).isValid())) {
            return;
        }

        HttpServletRequest request = Servlets.getRequest();
        if (request != null) {
            String uri = request.getServletPath();
            // 如果是静态文件，则不更新SESSION
            if (Servlets.isStaticFile(uri)) {
                return;
            }
            // 如果是视图文件，则不更新SESSION
            if (StringUtils.endsWith(uri, ".jsp")) {
                return;
            }
        }

        this.saveSession(session);
    }

    /**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }

        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtils.serialize(session);
        session.setTimeout(redisManager.getExpire() * 1000);
        this.redisManager.set(key, value, redisManager.getExpire());
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        redisManager.del(this.getByteKey(session.getId()));

    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();

        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if (keys != null && keys.size() > 0) {
            for (byte[] key : keys) {
                Session s = (Session) SerializeUtils.deserialize(redisManager.get(key));
                sessions.add(s);
            }
        }

        return sessions;
    }

    /**
     * 获得byte[]型的key
     *
     * @param sessionId
     * @return
     */
    private byte[] getByteKey(Serializable sessionId) {
        String preKey = this.keyPrefix + sessionId;
        return preKey.getBytes();
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;

        /**
         * 初始化redisManager
         */
        this.redisManager.init();
    }

    /**
     * Returns the Redis session keys
     * prefix.
     *
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     *
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }


}
