package com.robot.mkss2;

import org.springframework.hateoas.RepresentationModel;
import java.util.List;

public class PaginatedActionsResource extends RepresentationModel<PaginatedActionsResource> {
    private final List<String> actions;
    private final int currentPage;
    private final int totalPages;
    private final int totalActions;

    public PaginatedActionsResource(List<String> actions, int currentPage, int totalPages, int totalActions) {
        this.actions = actions;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalActions = totalActions;
    }

    public List<String> getActions() {
        return actions;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalActions() {
        return totalActions;
    }
}
