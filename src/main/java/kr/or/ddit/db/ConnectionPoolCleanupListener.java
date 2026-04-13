package kr.or.ddit.db;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ConnectionPoolCleanupListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Shutting down datasource before web application unload");
        ConnectionFactoryWithPooling.shutdown(); // 연결 풀 자원 해제
    }
}