package codetroopers.wicket.web.datagrid.js;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.resource.JQueryPluginResourceReference;

/**
 * User: bcousin
 */
public class EditableGridBehavior extends Behavior {
    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);
        response.render(JavaScriptHeaderItem.forReference(
                new JQueryPluginResourceReference(EditableGridBehavior.class, "editable-grid.js")));
    }
}
