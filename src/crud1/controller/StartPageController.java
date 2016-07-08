package crud1.controller;

import crud1.dto.PageParameters;
import crud1.view.PageTemplate;
import crud1.view.Start;

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
