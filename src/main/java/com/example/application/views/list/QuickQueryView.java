package com.example.application.views.list;

import com.example.application.views.list.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

@Route(value = "quick", layout = MainLayout.class)
public class QuickQueryView extends VerticalLayout {
    public QuickQueryView() {
        VerticalLayout searchBy = new VerticalLayout();
        RadioButtonGroup<String> searchByGroup = new RadioButtonGroup<>();
        searchByGroup.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        searchByGroup.setLabel("What are you searching for?");
        searchByGroup.setItems("Titles", "Actors", "Production Companies");
        searchBy.add(searchByGroup);
        searchBy.setAlignItems(Alignment.CENTER);
        searchBy.setJustifyContentMode(JustifyContentMode.CENTER);
        add(searchBy);

        VerticalLayout buttonLayout = new VerticalLayout();
        Button nextButton = new Button("Next");
        buttonLayout.add(nextButton);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(buttonLayout);

        nextButton.addClickListener(e -> {
            String resultsCategory = searchByGroup.getValue();
            if (resultsCategory.equals("Titles")) {
                UI.getCurrent().navigate("title");
            }
            else if (resultsCategory.equals("Actors")) {
                UI.getCurrent().navigate("actor");
            }
            else if (resultsCategory.equals("Production Companies")) {
                UI. getCurrent().navigate("prodCompany");
            }
        });

    }
}
