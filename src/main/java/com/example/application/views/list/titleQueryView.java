package com.example.application.views.list;

import com.example.application.data.entity.Query;
import com.example.application.data.entity.StoredQuery;
import com.example.application.data.repository.QueryRepository;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import oracle.sql.DATE;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Vector;

@Route(value = "title", layout = MainLayout.class)
public class titleQueryView extends VerticalLayout {
    public titleQueryView(QueryRepository repo) {
        H1 pageTitle = new H1("Search Filters");
        add(pageTitle);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        List<String> genres;
        genres = getGenres();
        List<String> languages;
        languages = getLanguages();

        TextField titleField = new TextField("Title Name");
        DatePicker startDateField = new DatePicker("Release Date Start");
        DatePicker endDateField = new DatePicker("Release Date End");
        ComboBox<String> genreBox = new ComboBox<>("Genre", genres);
        genreBox.setPlaceholder("SELECT A GENRE");

        TextField actorField = new TextField("Starring");
        ComboBox<String> languageBox = new ComboBox<>("Language", languages);
        languageBox.setPlaceholder("SELECT A LANGUAGE");

        FormLayout formLayout = new FormLayout();
        formLayout.add(titleField, startDateField, endDateField, actorField, genreBox,  languageBox);

        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2)
        );
        formLayout.setColspan(titleField, 2);
        formLayout.setColspan(actorField, 2);
        add(formLayout);

        CheckboxGroup<String> outputGroup = new CheckboxGroup<>();
        outputGroup.setLabel("What do you want to view?");
        outputGroup.setItems("Titles", "Actors", "Budget", "Revenue", "Release Date", "Average Score");
        add(outputGroup);

        VerticalLayout buttonLayout = new VerticalLayout();
        Button submitButton = new Button("Submit");
        buttonLayout.add(submitButton);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(buttonLayout);

        submitButton.addClickListener(e -> {
            Set<String> output = outputGroup.getValue();
            String title = titleField.getValue();
            LocalDate startDate = startDateField.getValue();
            LocalDate endDate = endDateField.getValue();
            String genre = genreBox.getValue();
            String actor = actorField.getValue();
            String language = languageBox.getValue();
            Query newQuery = new Query(title, actor, genre, startDate, endDate, language, output);
            StoredQuery toStore = new StoredQuery(title, actor, genre, startDate, endDate, language);
            repo.save(toStore);
            ComponentUtil.setData(UI.getCurrent(), Query.class, newQuery);
            UI.getCurrent().navigate("loading");
        });
    }
    public List<String> getLanguages() {
        List<String> languages = new Vector<>();
        languages.add("English");
        languages.add("French");
        languages.add("Spanish");
        languages.add("German");
        languages.add("Hindi");
        languages.add("Mandarin");
        languages.add("Japanese");
        languages.add("Italian");
        languages.add("Russian");
        languages.add("Arabic");
        languages.add("Korean");
        languages.add("Hebrew");
        languages.add("Cantonese");
        languages.add("Portuguese");
        languages.add("Swedish");
        languages.add("Latin");
        languages.add("Ukrainian");
        languages.add("Danish");
        languages.add("Persian");
        return languages;
    }
    public List<String> getGenres() {
        List<String> genres = new Vector<>();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Animation");
        genres.add("Comedy");
        genres.add("Crime");
        genres.add("Documentary");
        genres.add("Drama");
        genres.add("Family");
        genres.add("Fantasy");
        genres.add("History");
        genres.add("Horror");
        genres.add("Music");
        genres.add("Mystery");
        genres.add("Romance");
        genres.add("Science Fiction");
        genres.add("TV Movie");
        genres.add("Thriller");
        genres.add("War");
        genres.add("Western");
        return genres;
    }
}
