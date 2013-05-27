package codetroopers.wicket.web.datagrid.toolbar;

import codetroopers.wicket.web.datagrid.component.EditableDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IStyledColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RefreshingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Nadeem Mohammad
 *
 */
public class EditableGridHeadersToolbar<T, S> extends AbstractEditableGridToolbar {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param <T>
     *            the column data type
     * @param table
     *            data table this toolbar will be attached to
     * @param stateLocator
     *            locator for the ISortState implementation used by sortable headers
     */
    public EditableGridHeadersToolbar(final EditableDataTable<T, S> table, final ISortStateLocator<S> stateLocator) {
        super(table);

        RefreshingView<IColumn<T, S>> headers = new RefreshingView<IColumn<T, S>>("headers") {
            private static final long serialVersionUID = 1L;

            @Override
            protected Iterator<IModel<IColumn<T, S>>> getItemModels() {
                List<IModel<IColumn<T, S>>> columnsModels = new LinkedList<>();

                for (IColumn<T, S> column : table.getColumns()) {
                    columnsModels.add(Model.of(column));
                }

                return columnsModels.iterator();
            }

            @Override
            protected void populateItem(Item<IColumn<T, S>> item) {
                final IColumn<T, S> column = item.getModelObject();

                WebMarkupContainer header;

                if (column.isSortable()) {
                    header = newSortableHeader("header", column.getSortProperty(), stateLocator);
                } else {
                    header = new WebMarkupContainer("header");
                }

                if (column instanceof IStyledColumn) {
                    EditableDataTable.CssAttributeBehavior cssAttributeBehavior =
                            new EditableDataTable.CssAttributeBehavior() {
                                private static final long serialVersionUID = 1L;

                                @Override
                                protected String getCssClass() {
                                    return ((IStyledColumn<?, S>) column).getCssClass();
                                }
                            };

                    header.add(cssAttributeBehavior);
                }

                item.add(header);
                item.setRenderBodyOnly(true);
                header.add(column.getHeader("label"));
            }
        };
        add(headers);
    }

    /**
     * Factory method for sortable header components. A sortable header component must have id of
     * <code>headerId</code> and conform to markup specified in <code>HeadersToolbar.html</code>
     *
     * @param headerId
     *            header component id
     * @param property
     *            property this header represents
     * @param locator
     *            sort state locator
     * @return created header component
     */
    protected WebMarkupContainer newSortableHeader(final String headerId, final S property,
                                                   final ISortStateLocator<S> locator) {
        return new OrderByBorder<S>(headerId, property, locator) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSortChanged() {
                getTable().setCurrentPage(0);
            }
        };
    }
}
