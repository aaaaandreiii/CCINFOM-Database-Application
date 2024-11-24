import views.ViewMain;
import controllers.ConMain;

public class Main {
    public static void main(String[] args) {

        ViewMain viewMain = new ViewMain();
        ConMain conMain = new ConMain(viewMain);
    }
}
