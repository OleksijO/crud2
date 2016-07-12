package crud10.controller;

import crud10.dto.PageParameters;
import crud10.view.PageTemplate;
import crud10.view.Start;

/**
 * Created by User on 03.07.2016.
 */
public class StartPageController extends AbstractController {
    public StartPageController(PageParameters parameters) {
        super(parameters, null);
    }

    @Override
    public PageTemplate execute() {
        return new Start(parameters);
    }
}
