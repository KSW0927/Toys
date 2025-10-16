package com.demo.toy.dto;

public class ApiSearchParamsDTO {

	private String location;
    private int page = 0;
    private int size = 9;

    public ApiSearchParamsDTO() {}

    public ApiSearchParamsDTO(String location, int page, int size) {
        this.location = location;
        this.page = page;
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
