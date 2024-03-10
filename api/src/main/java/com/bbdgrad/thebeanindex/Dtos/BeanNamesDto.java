package com.bbdgrad.thebeanindex.Dtos;

import java.util.List;

public class BeanNamesDto {
    private List<String> beanNames;

    public BeanNamesDto(List<String> beanNames) {
        this.beanNames = beanNames;
    }

    public List<String> getBeanNames() {
        return beanNames;
    }

    public void setBeanNames(List<String> beanNames) {
        this.beanNames = beanNames;
    }
}
