package com.example.jazzlibrary2025v2.domain.model;


public class DataCache {
    private static DataCache instance;
    private BootStrap bootstrapData;

    // Private constructor to prevent direct instantiation
    private DataCache() {}

    // Singleton accessor
    public static synchronized DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    // Setter for bootstrap data
    public void setBootstrapData(BootStrap data) {
        this.bootstrapData = data;
    }

    // Getter for bootstrap data
    public BootStrap getBootstrapData() {
        return bootstrapData;
    }
}