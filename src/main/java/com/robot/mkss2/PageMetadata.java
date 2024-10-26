package com.robot.mkss2;

public class PageMetadata {
    private final int number;
    private final int size;
    private final int totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    public PageMetadata(int number, int size, int totalElements, int totalPages) {
        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.hasNext = number < totalPages - 1;
        this.hasPrevious = number > 0;
    }

    public int getNumber() { return number; }
    public int getSize() { return size; }
    public int getTotalElements() { return totalElements; }
    public int getTotalPages() { return totalPages; }
    public boolean isHasNext() { return hasNext; }
    public boolean isHasPrevious() { return hasPrevious; }
}
