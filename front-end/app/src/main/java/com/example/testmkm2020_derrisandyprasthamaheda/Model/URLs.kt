package com.example.testmkm2020_derrisandyprasthamaheda.Model

object URLs {
    var ips = "10.244.10.59"
    private val ROOT_URL = "http://$ips/TestMKM2020-DerrisandyPrasthamaheda/registrationapi.php?apicall="
    val URL_REGISTER = ROOT_URL + "signup"
    val URL_LOGIN = ROOT_URL + "login"
    val URL_LOGINSET = "http://$ips/TestMKM2020-DerrisandyPrasthamaheda/loginset.php"
}