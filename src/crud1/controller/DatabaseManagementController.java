package crud1.controller;

import crud1.Constants;
import crud1.dto.ActionDTO;
import crud1.dto.Data;
import crud1.dto.PageParameters;
import crud1.model.Model;
import crud1.model.ModelAction;
import crud1.view.DatabaseMenegment;
import crud1.view.PageTemplate;

/**
 * Created by User on 03.07.2016.
 */
public class DatabaseManagementController extends AbstractController {
    public DatabaseManagementController(PageParameters parameters, Model model) {
        super(parameters, model);
    }

    @Override
    public PageTemplate execute() {
        Data data=null;
        if("true".equals(parameters.getStringParameter(Constants.MODE_DATABASE_MANAGEMENT_RENEW))) data=model.doAction(new ActionDTO(ModelAction.DATABASE_RESTORE));
        return new DatabaseMenegment(parameters,data);
    }
}
