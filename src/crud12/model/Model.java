package crud12.model;

import crud10.dto.Action;
import crud10.dto.Data;

/**
 * Declares methods for model implementation
 */
public interface Model {
    Data doAction(Action action);
}
