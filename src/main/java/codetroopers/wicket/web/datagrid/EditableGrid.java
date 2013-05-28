package codetroopers.wicket.web.datagrid;

import codetroopers.wicket.web.datagrid.column.EditableGridActionsColumn;
import codetroopers.wicket.web.datagrid.component.EditableDataTable;
import codetroopers.wicket.web.datagrid.js.EditableGridBehavior;
import codetroopers.wicket.web.datagrid.provider.IEditableDataProvider;
import codetroopers.wicket.web.datagrid.toolbar.EditableGridBottomToolbar;
import codetroopers.wicket.web.datagrid.toolbar.EditableGridHeadersToolbar;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nadeem Mohammad
 *
 */
public class EditableGrid<T, S> extends Panel {

    private static final long serialVersionUID = 1L;
    private EditableDataTable<T, S> dataTable;
    private FeedbackPanel feedback;

    public EditableGrid(final String id, final List<? extends IColumn<T, S>> columns,
                        final IEditableDataProvider<T, S> dataProvider, final long rowsPerPage, Class<T> clazz) {
        super(id);
        List<IColumn<T, S>> newCols = new ArrayList<>();
        newCols.addAll(columns);
        newCols.add(newActionsColumn());

        add(buildForm(newCols, dataProvider, rowsPerPage, clazz));
        add(new EditableGridBehavior());
    }

    private Component buildForm(final List<? extends IColumn<T, S>> columns,
                                final IEditableDataProvider<T, S> dataProvider, long rowsPerPage, Class<T> clazz) {
        Form<T> form = new NonValidatingForm<>("form");
        form.setOutputMarkupId(true);
        form.add(newDataTable(columns, dataProvider, rowsPerPage, clazz));
        feedback = newFeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        form.add(feedback);
        return form;
    }

    protected FeedbackPanel newFeedbackPanel(final String markupId) {
        return new FeedbackPanel(markupId);
    }

    private static class NonValidatingForm<T> extends Form<T> {
        private static final long serialVersionUID = 1L;

        public NonValidatingForm(String id) {
            super(id);
        }

        @Override
        public void process(IFormSubmitter submittingComponent) {
            delegateSubmit(submittingComponent);
        }

    }

    private Component newDataTable(final List<? extends IColumn<T, S>> columns,
                                   final IEditableDataProvider<T, S> dataProvider, long rowsPerPage, Class<T> clazz) {
        dataTable = new EditableDataTable<T, S>("dataTable", columns,
                                                dataProvider, rowsPerPage, clazz) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onError(AjaxRequestTarget target) {
                EditableGrid.this.onError(target);
            }
        };
        dataTable.setOutputMarkupId(true);

        dataTable.addTopToolbar(new EditableGridHeadersToolbar<>(dataTable, dataProvider));
        if (displayAddFeature()) {
            dataTable.addBottomToolbar(newAddBottomToolbar(dataProvider, clazz, dataTable));
        }

        return dataTable;
    }

    private EditableGridBottomToolbar<T, S> newAddBottomToolbar(
            final IEditableDataProvider<T, S> dataProvider, Class<T> clazz,
            final EditableDataTable<T, S> dataTable) {
        return new EditableGridBottomToolbar<T, S>(dataTable, clazz) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onAdd(AjaxRequestTarget target, T newRow) {
                dataProvider.add(newRow);
                target.add(dataTable);
                target.add(feedback);
                EditableGrid.this.onAdd(target, newRow);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
                super.onError(target);
                target.add(feedback);
                EditableGrid.this.onError(target);
            }

        };
    }

    protected EditableGridActionsColumn<T, S> newActionsColumn() {
        return new EditableGridActionsColumn<T, S>(Model.of("")) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void onError(AjaxRequestTarget target, IModel<T> rowModel) {
                target.add(feedback);
                EditableGrid.this.onError(target);
            }

            @Override
            protected void onSave(AjaxRequestTarget target, IModel<T> rowModel) {
                target.add(feedback);
                EditableGrid.this.onSave(target, rowModel);
            }

            @Override
            protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel) {
                target.add(feedback);
                EditableGrid.this.onDelete(target, rowModel);
            }

            @Override
            protected void onCancel(AjaxRequestTarget target) {
                target.add(feedback);
                EditableGrid.this.onCancel(target);
            }
        };
    }

    protected void onCancel(AjaxRequestTarget target) {

    }


    protected void onDelete(AjaxRequestTarget target, IModel<T> rowModel) {
    }

    protected void onSave(AjaxRequestTarget target, IModel<T> rowModel) {
    }

    protected void onError(AjaxRequestTarget target) {
    }

    protected void onAdd(AjaxRequestTarget target, T newRow) {
    }

    protected boolean displayAddFeature() {
        return true;
    }

    public EditableDataTable<T, S> getDataTable() {
        return dataTable;
    }
}
