package com.example.domoticapp.app.Modules;

/**
 * Base class for all the modules (light,sound,security,etc)
 * that plays the role of the Abastraction in the Bridge pattern
 * and define all the basic operations for communicating with
 * embedded devices and cloud services
 */
public interface BaseModulesOps {


    void sendData();

    void receiveData();

}
