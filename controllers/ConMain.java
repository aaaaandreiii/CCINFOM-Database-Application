package controllers;

import views.ViewMain;

public class ConMain {
    private LogInPage logInPage;
    private SignUp signUp;

    public ConMain(ViewMain gui) {
        this.logInPage = new LogInPage(gui);
        this.signUp = new SignUp(gui);
    }
}
