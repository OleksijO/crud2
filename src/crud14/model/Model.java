package crud14.model;

import crud10.dto.Action;
import crud10.dto.Data;


public interface Model {
    /**
     * Do action one of actions from ModelAction enum, specified inside ActionDTO
     */
    Data doAction(Action action);
}
