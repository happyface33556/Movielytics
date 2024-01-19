package com.example.application.views.list;

import com.example.application.data.entity.Query;
import com.example.application.data.entity.StoredQuery;
import com.example.application.data.repository.QueryRepository;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Route(value = "Storage", layout = MainLayout.class)
public class StorageView extends VerticalLayout {
    public StorageView(QueryRepository repo) {
        VerticalLayout storageLayout = new VerticalLayout();
        Grid<StoredQuery> grid = new Grid<>(StoredQuery.class, false);
        grid.addColumn(StoredQuery::getTitle).setHeader("Title").setSortable(true);
        grid.addColumn(StoredQuery::getActors).setHeader("Actors").setSortable(true);
        grid.addColumn(StoredQuery::getGenre).setHeader("Genre").setSortable(true);
        grid.addColumn(StoredQuery::getLanguage).setHeader("Language").setSortable(true);
        grid.addColumn(StoredQuery::getStartDate).setHeader("Date Range Start").setSortable(true);
        grid.addColumn(StoredQuery::getEndDate).setHeader("Date Range End").setSortable(true);
        grid.setItems(repo.findAll());
        AtomicReference<String> title = new AtomicReference<>("");
        AtomicReference<String> actors = new AtomicReference<>("");
        AtomicReference<String> genre = new AtomicReference<>("");
        AtomicReference<LocalDate> startDate = new AtomicReference<>(LocalDate.now());
        AtomicReference<LocalDate> endDate = new AtomicReference<>(LocalDate.now());
        AtomicReference<String> language = new AtomicReference<>("");
        Set<String> output = null;
        grid.addSelectionListener(selection -> {
            Optional<StoredQuery> optionalQuery = selection.getFirstSelectedItem();
            if (optionalQuery.isPresent()) {
                title.set(optionalQuery.get().getTitle());
                actors.set(optionalQuery.get().getActors());
                genre.set(optionalQuery.get().getGenre());
                startDate.set(optionalQuery.get().getStartDate());
                endDate.set(optionalQuery.get().getEndDate());
                language.set(optionalQuery.get().getLanguage());
            }
        });

        H1 storageTitle = new H1("Past Queries");
        CheckboxGroup<String> outputGroup = new CheckboxGroup<>();
        outputGroup.setLabel("What do you want to view?");
        outputGroup.setItems("Titles", "Actors", "Budget", "Revenue", "Release Date", "Average Score");
        Button button = new Button("Repeat Query");
        storageLayout.add(storageTitle, grid);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(storageLayout);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        button.addClickListener(e -> {
            Set<String> newOutput = outputGroup.getValue();
            Query reQuery = new Query(title.get(), actors.get(), genre.get(), startDate.get(), endDate.get(), language.get(), newOutput);
            ComponentUtil.setData(UI.getCurrent(), Query.class, reQuery);
            UI.getCurrent().navigate("loading");
        });
    }
}
