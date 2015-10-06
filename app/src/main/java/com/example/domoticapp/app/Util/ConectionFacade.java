package com.example.domoticapp.app.Util;

import java.util.List;

/**
 * Facade interface that cover all of the subsystem classes
 * to facilitate the client conection to Blueetoth or Wifi
 * communication mechanisms
 */
public interface ConectionFacade {

    //Tentative method to encapsulate all of the initialization process
    //by trying to ensure a connection
//    public void intializeCommunication();

    //Check if the device support the communication mechanism
    boolean checkDeviceSupport();

    // If the communication mechanism is disabled, returns true if it was
    // posible to enable
    boolean enableDevice();


    boolean performConnection();

    //Read data from the connected device
    List<String> read();

    //Send data to the connected device
    void write(String message);






}
