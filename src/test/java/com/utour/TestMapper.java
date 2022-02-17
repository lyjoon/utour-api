package com.utour;

public abstract class TestMapper extends TestLocalApplication {

    abstract public void exists();

    abstract public void findById();

    abstract public void findAll();

    abstract public void save();

    abstract public void delete();

    public void allOf() {
        this.exists();
        this.save();
        this.findAll();
        this.delete();
    }
}
