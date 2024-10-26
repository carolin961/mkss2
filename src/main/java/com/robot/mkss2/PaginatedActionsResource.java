package com.robot.mkss2;

import org.springframework.hateoas.RepresentationModel;
import java.util.List;

public class PaginatedActionsResource extends RepresentationModel<PaginatedActionsResource> {
    private final PageMetadata page;
    private final List<ActionResource> actions;

    public PaginatedActionsResource(List<ActionResource> actions, PageMetadata page) {
        this.actions = actions;
        this.page = page;
    }

    public PageMetadata getPage() { return page; }
    public List<ActionResource> getActions() { return actions; }
}
