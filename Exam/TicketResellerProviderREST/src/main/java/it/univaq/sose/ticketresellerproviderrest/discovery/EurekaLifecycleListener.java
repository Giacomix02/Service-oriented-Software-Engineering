package it.univaq.sose.ticketresellerproviderrest.discovery;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class EurekaLifecycleListener implements ServletContextListener {

    private EurekaClient eurekaClient;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        eurekaClient = new EurekaClient();
        eurekaClient.register();
        eurekaClient.startHeartbeat();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (eurekaClient != null) {
            eurekaClient.deregister();
        }
    }
}