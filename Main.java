import controllers.ConMain;
import views.ViewMain;

public class Main {
    public static void main(String[] args) {

        ViewMain viewMain = new ViewMain();
        ConMain conMain = new ConMain(viewMain);
    }
}
