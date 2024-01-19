package com.example.application.views.list;

import com.example.application.data.entity.Output;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Route(value = "results", layout = MainLayout.class)
public class ResultsView extends VerticalLayout {
    public ResultsView() throws SQLException {
        List<Output> results = ComponentUtil.getData(UI.getCurrent(), List.class);
        for (int i = 0; i < results.size(); i++) {
            if ((results.get(i).getBudget() == null) || results.get(i).getBudget().equals("0")) {
                results.get(i).setBudget("Unavailable");
            }
            if ((results.get(i).getRevenue() == null) || results.get(i).getRevenue().equals("0")) {
                results.get(i).setRevenue("Unavailable");
            }
            if (results.get(i).getReleaseDate() == null) {
                results.get(i).setReleaseDate("Unavailable");
            }
            else {
                results.get(i).setReleaseDate(results.get(i).getReleaseDate().substring(0, 10));
            }
        }
        Grid<Output> grid = new Grid<>(Output.class, false);
        grid.addColumn(Output::getTitle).setHeader("Film Title").setSortable(true);
        grid.addColumn(Output::getName).setHeader("Actors").setSortable(true);
        grid.addColumn(Output::getBudget).setHeader("Budget").setSortable(true);
        grid.addColumn(Output::getRevenue).setHeader("Revenue").setSortable(true);
        grid.addColumn(Output::getReleaseDate).setHeader("Release Date (YYYY-MM-DD)").setSortable(true);
        grid.addColumn(Output::getVote_Average).setHeader("Average Score").setSortable(true);
        grid.setItems(results);
        add(grid);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    }
}
