package com.example.ClinicaOdontologicaVV;

import com.example.ClinicaOdontologicaVV.dbconnection.H2Connection;

public class Application {
    public static void main(String[] args) {

        H2Connection.ejecutarPrimerScript();
    }
}