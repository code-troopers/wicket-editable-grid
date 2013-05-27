package codetroopers.wicket.web.datagrid;

import codetroopers.wicket.web.datagrid.column.EditableTextFieldPropertyColumn;
import codetroopers.wicket.web.datagrid.column.RequiredEditableTextFieldColumn;
import codetroopers.wicket.web.datagrid.provider.EditableListDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: bcousin
 */
public class EditableGridTest {
    @Test
    public void testEmptyDataGrid() throws Exception {
        final EditableListDataProvider<DummyObject, Long> provider =
                new EditableListDataProvider<>(new ArrayList<DummyObject>());
        final List<IColumn<DummyObject, Long>> columns = new ArrayList<>();
        columns.add(new RequiredEditableTextFieldColumn<DummyObject, Long>(Model.of("name"), "name"));
        columns.add(new EditableTextFieldPropertyColumn<DummyObject, Long>(Model.of("password"), "password"));
        WicketTester tester = new WicketTester();
        tester.startComponentInPage(new EditableGrid<>("id", columns, provider, 10, DummyObject.class));
    }

    @Test
    public void testDataGrid() throws Exception {
        final ArrayList<DummyObject> list = new ArrayList<>();
        list.add(new DummyObject(1L,"name1","password1"));
        final EditableListDataProvider<DummyObject, Long> provider =
                new EditableListDataProvider<>(list);
        final List<IColumn<DummyObject, Long>> columns = new ArrayList<>();
        columns.add(new RequiredEditableTextFieldColumn<DummyObject, Long>(Model.of("name"), "name"));
        columns.add(new EditableTextFieldPropertyColumn<DummyObject, Long>(Model.of("password"), "password"));
        WicketTester tester = new WicketTester();
        tester.startComponentInPage(new EditableGrid<>("id", columns, provider, 10, DummyObject.class));
        tester.assertContains("name1");
        tester.assertContains("password1");
    }
}
