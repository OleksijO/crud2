package crud10.controller;


import crud10.dto.PageParameters;
import crud14.model.Model;
import crud10.view.Page;
import java.io.IOException;

/**
 *  Declares fields, used by inheritants
 */
public abstract class AbstractController {
    protected PageParameters parameters;
    protected Model model;

    public AbstractController(PageParameters parameters, Model model) {
        this.parameters = parameters;
        this.model = model;
    }

    public abstract Page execute() throws IOException;


}
