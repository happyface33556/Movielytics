package com.example.application.views.list;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "Comparison", layout = MainLayout.class)
public class ComparisonView extends VerticalLayout {
    public ComparisonView() {
        H1 placeholder = new H1("Comparison View Here");
        add(placeholder);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    }
}
