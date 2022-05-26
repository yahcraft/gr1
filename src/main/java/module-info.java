module q1 {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;

    opens Program.Model to com.google.gson;

    exports Program.Controller;
    opens Program.Controller to javafx.fxml;
    exports Program.View;
    opens Program.View to javafx.fxml;
    exports Program;
    opens Program to javafx.fxml;
}