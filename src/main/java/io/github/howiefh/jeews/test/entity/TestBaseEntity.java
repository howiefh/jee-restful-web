package io.github.howiefh.jeews.test.entity;

import java.io.Serializable;

public class TestBaseEntity implements Serializable {
	private static final long serialVersionUID = -8300674027063022129L;
    
    protected Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestBaseEntity that = (TestBaseEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
