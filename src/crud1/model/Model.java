package crud1.model;

import crud1.dto.Action;
import crud1.dto.Data;

/**
 * Declares methods for model implementation
 */
public interface Model {
    Data doAction(Action action);
}
