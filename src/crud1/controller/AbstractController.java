package crud1.controller;


import crud1.dto.PageParameters;
import crud1.model.Model;
import crud1.view.Page;
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
