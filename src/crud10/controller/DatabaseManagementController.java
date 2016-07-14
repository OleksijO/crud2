package crud10.controller;

import crud10.Constants;
import crud10.dto.ActionDTO;
import crud10.dto.Data;
import crud10.dto.PageParameters;
import crud14.model.Model;
import crud14.model.ModelAction;
import crud10.view.DatabaseMenegment;
import crud10.view.PageTemplate;

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
