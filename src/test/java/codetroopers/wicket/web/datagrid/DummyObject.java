package codetroopers.wicket.web.datagrid;

import java.io.Serializable;

/**
 * User: bcousin
 * Date: 10/05/13
 * Time: 16:30
 */
public class DummyObject implements Serializable {
    public Long id;
    public String name;
    public String password;

    public DummyObject() {
    }

    public DummyObject(final Long id, final String name, final String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
