package crud10.view.navi.decorators;

import crud10.view.AbstractPageTemplate;
import crud10.view.PageTemplate;

/**
 *  Super class for all page decorators
 */
public abstract class AbstractDecorator extends AbstractPageTemplate {
    public PageTemplate original;

    public AbstractDecorator(PageTemplate original) {
        super(original.getParameters());
        this.data=original.getData();
        this.original = original;
    }


}
