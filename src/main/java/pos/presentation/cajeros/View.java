package pos.presentation.cajeros;

import pos.logic.Cajero;

public class View {


    Model model;
    Controller controller;
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
