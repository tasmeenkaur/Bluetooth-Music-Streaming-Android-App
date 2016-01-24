package com.example.musicapplication.ma;

import java.util.UUID;

/**
 * Created by tasmeen on 11/13/2015.
 */
public class Constants {
    public static final String SERVICE_NAME = "AUDIO_SYNC_SERVICE";
    public static final UUID SERVICE_UUID = UUID.fromString("85ec8dc9-ff30-471e-b70d-e076bab35227");
    public static final int STATE_CONNECTED = 1;
    public static final int CONNECTION_FAILED = -1;
    public static final int CONNECTION_INTERRUPTED = -2;
    public static final int DATA_READ = 2;
}
