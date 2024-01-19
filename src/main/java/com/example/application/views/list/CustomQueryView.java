package com.example.application.views.list;

import com.example.application.views.list.MainLayout;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Route(value = "custom", layout = MainLayout.class)
public class CustomQueryView extends VerticalLayout {

    public CustomQueryView() {
        Accordion queries = new Accordion();
        H1 pageTitle = new H1("Complex Trend Queries");
        add(pageTitle);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        List<String> genres = getGenres();
        List<String> languages = getLanguages();
        List<Integer> queryFourYears = getYears(4);
        List<Integer> queryTwoYears = getYears(2);
        List<String> queryTwoEvents = getEvents(2);
        List<String> queryThreeEvents = getEvents(3);

        VerticalLayout customQueryLayout = new VerticalLayout();


        VerticalLayout queryOneLayout = new VerticalLayout();
        Paragraph queryOneDescription = new Paragraph("How has the average yearly revenue for films from different genres" +
                                                            " changed over a selected period of time?" + " What conclusions can you draw about which genres are the most profitable? "
                                                            + "Select your desired genres and date range below.");
        FormLayout queryOneForm = new FormLayout();
        ComboBox<String> genreOne = new ComboBox<>("First Genre");
        genreOne.setRequired(true);
        ComboBox<String> genreTwo = new ComboBox<>("Second Genre");
        genreTwo.setRequired(true);
        ComboBox<Integer> startDate = new ComboBox<>("Start Year");
        startDate.setRequired(true);
        ComboBox<Integer> endDate = new ComboBox<>("End Year");
        endDate.setRequired(true);
        genreOne.setItems(getGenres());
        genreTwo.setItems(getGenres());
        startDate.setItems(getYears(1));
        endDate.setItems(getYears(1));
        queryOneForm.add(genreOne, genreTwo, startDate, endDate);
        queryOneForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 4));
        Button queryOneButton = new Button("Execute Query One");
        queryOneButton.addClickListener(e -> {
            int queryNum = 1;
            String startYear = startDate.getValue().toString();
            String endYear = endDate.getValue().toString();
            String firstGenre = genreOne.getValue();
            String secondGenre = genreTwo.getValue();
            Vector<String> queryOneInfo = new Vector<>();
            queryOneInfo.add(startYear);
            queryOneInfo.add(endYear);
            queryOneInfo.add(firstGenre);
            queryOneInfo.add(secondGenre);
            ComponentUtil.setData(UI.getCurrent(), Vector.class, queryOneInfo);
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });



        VerticalLayout queryTwoLayout = new VerticalLayout();
        Paragraph queryTwoDescription = new Paragraph("How have viewer's preferences changed in film genres following major geopolitical events? " +
                                                        "Was there a noticeable change in popularity in certain genres in the aftermath? Select a listed event, or "
                                                        + "input a custom year below.");
        FormLayout queryTwoForm = new FormLayout();
        ComboBox<String> targetYear = new ComboBox<>("Event Year");
        List<String> stringYears = new Vector<>();
        for (int i = 0; i < queryTwoYears.size(); i++) {
            if (queryTwoYears.get(i).equals(1991)) {
                stringYears.add("1991: Fall of the Soviet Union");
            }
            else if (queryTwoYears.get(i).equals(2001)) {
                stringYears.add("2001: 9/11 Terror Attack");
            }
            else if (queryTwoYears.get(i).equals(2008)) {
                stringYears.add("2008: Great Recession");
            }
            else {
                stringYears.add(queryTwoYears.get(i).toString());
            }
        }
        targetYear.setItems(stringYears);
        queryTwoForm.add(targetYear);
        Button queryTwoButton = new Button("Execute Query Two");
        queryTwoButton.addClickListener(e -> {
            int queryNum = 2;
            String choice = "";
            choice = targetYear.getValue();
            ComponentUtil.setData(UI.getCurrent(), String.class, choice);
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });



        VerticalLayout queryThreeLayout = new VerticalLayout();
        Paragraph queryThreeDescription = new Paragraph("What are the changes in ROI (Return on Investment) over time for " +
                                                        "the following common language groupings? What conclusions about market growth " +
                                                        "can we draw from these comparisons? Choose a language grouping and date range below.");
        FormLayout queryThreeForm = new FormLayout();
        ComboBox<String> queryThreeList = new ComboBox<>("Language Groupings");
        queryThreeList.setRequired(true);
        Vector<String> languageGroupings = new Vector<>();
        languageGroupings.add("Top 5 Most Spoken Languages");
        languageGroupings.add("Top 5 Fastest Growing Languages");
        languageGroupings.add("Top 5 European Languages");
        languageGroupings.add("Top 5 South American Languages");
        queryThreeList.setItems(languageGroupings);
        queryThreeForm.add(queryThreeList);

        ComboBox<Integer> queryThreeStartYear = new ComboBox<>("Start Year");
        queryThreeStartYear.setRequired(true);
        ComboBox<Integer> queryThreeEndYear = new ComboBox<>("End Year");
        queryThreeEndYear.setRequired(true);
        queryThreeStartYear.setItems(getYears(3));
        queryThreeEndYear.setItems(getYears(3));
        queryThreeForm.add(queryThreeStartYear, queryThreeEndYear);
        queryThreeForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));


        Button queryThreeButton = new Button("Execute Query Three");
        queryThreeButton.addClickListener(e -> {
            int queryNum = 3;
            Vector<Integer> dateRange = new Vector<>(2);
            dateRange.add(0, queryThreeStartYear.getValue());
            dateRange.add(1, queryThreeEndYear.getValue());
            String choice = queryThreeList.getValue();
            ComponentUtil.setData(UI.getCurrent(), Vector.class, dateRange);
            ComponentUtil.setData(UI.getCurrent(), String.class, choice);
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });




        VerticalLayout queryFourLayout = new VerticalLayout();
        Paragraph queryFourDescription = new Paragraph("How has the average runtime for films released in specific seasons (Spring, Fall, Summer, & Winter) " +
                                                        "changed over time? Is there a correlation between the season (typically due to weather patterns) " +
                                                            "and the amount of time viewers spend indoors watching films? Select your desired date range below.");
        FormLayout queryFourForm = new FormLayout();
        ComboBox<Integer> startYear = new ComboBox<>("Start Year");
        startYear.setItems(queryFourYears);
        startYear.setRequired(true);
        ComboBox<Integer> endYear = new ComboBox<>("End Year");
        endYear.setItems(queryFourYears);
        endYear.setRequired(true);
        queryFourForm.add(startYear, endYear);
        queryFourForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2));
        Button queryFourButton = new Button("Execute Query Four");
        queryFourButton.addClickListener(e -> {
            int queryNum = 4;
            Vector<Integer> dateRange = new Vector<>(2);
            dateRange.add(0, startYear.getValue());
            dateRange.add(1, endYear.getValue());
            ComponentUtil.setData(UI.getCurrent(), Vector.class, dateRange);
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });




        VerticalLayout queryFiveLayout = new VerticalLayout();
        Paragraph queryFiveDescription = new Paragraph("Considering different production companies, which genre films " +
                                                        "are granted the largest budgets relative to the industry averages? " +
                                                        "Are certain production companies more willing to finance certain genres over others? " +
                                                        "Select a company and date range below.");
        FormLayout queryFiveForm = new FormLayout();
        ComboBox<String> queryFiveList = new ComboBox<>("Production Companies");
        Vector<String> productionCompanies = new Vector<>();
        productionCompanies.add("Warner Bros. Pictures");
        productionCompanies.add("Columbia Pictures");
        productionCompanies.add("Paramount");
        productionCompanies.add("Universal Pictures");
        productionCompanies.add("20th Century Fox");
        productionCompanies.add("Walt Disney Pictures");
        queryFiveList.setItems(productionCompanies);
        queryFiveList.setRequired(true);
        queryFiveForm.add(queryFiveList);

        ComboBox<Integer> queryFiveStartDate = new ComboBox<>("Start Year");
        queryFiveStartDate.setItems(getYears(5));
        queryFiveStartDate.setRequired(true);
        ComboBox<Integer> queryFiveEndDate = new ComboBox<>("End Year");
        queryFiveEndDate.setItems(getYears(5));
        queryFiveEndDate.setRequired(true);
        queryFiveForm.add(queryFiveStartDate, queryFiveEndDate);
        queryFiveForm.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));
        Button queryFiveButton = new Button("Execute Query Five");
        queryFiveButton.addClickListener(e -> {
            int queryNum = 5;
            Vector<Integer> dateRange = new Vector<>(2);
            dateRange.add(0, queryFiveStartDate.getValue());
            dateRange.add(1, queryFiveEndDate.getValue());
            String choice = queryFiveList.getValue();
            ComponentUtil.setData(UI.getCurrent(), Vector.class, dateRange);
            ComponentUtil.setData(UI.getCurrent(), String.class, choice);
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });

        queryOneLayout.add(queryOneDescription, queryOneForm, queryOneButton);
        queryOneLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        queryOneLayout.setAlignItems(Alignment.CENTER);
        queryTwoLayout.add(queryTwoDescription, queryTwoForm, queryTwoButton);
        queryTwoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        queryTwoLayout.setAlignItems(Alignment.CENTER);
        queryThreeLayout.add(queryThreeDescription, queryThreeForm, queryThreeButton);
        queryThreeLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        queryThreeLayout.setAlignItems(Alignment.CENTER);
        queryFourLayout.add(queryFourDescription, queryFourForm, queryFourButton);
        queryFourLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        queryFourLayout.setAlignItems(Alignment.CENTER);
        queryFiveLayout.add(queryFiveDescription, queryFiveForm, queryFiveButton);
        queryFiveLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        queryFiveLayout.setAlignItems(Alignment.CENTER);

        AccordionPanel queryOnePanel = queries.add("Query One", queryOneLayout);
        AccordionPanel queryTwoPanel = queries.add("Query Two", queryTwoLayout);
        AccordionPanel queryThreePanel = queries.add("Query Three", queryThreeLayout);
        AccordionPanel queryFourPanel = queries.add("Query Four", queryFourLayout);
        AccordionPanel queryFivePanel = queries.add("Query Five", queryFiveLayout);
        queryOnePanel.addThemeVariants(DetailsVariant.FILLED);
        queryTwoPanel.addThemeVariants(DetailsVariant.FILLED);
        queryThreePanel.addThemeVariants(DetailsVariant.FILLED);
        queryFourPanel.addThemeVariants(DetailsVariant.FILLED);
        queryFivePanel.addThemeVariants(DetailsVariant.FILLED);

        //queries.open(0);
        customQueryLayout.add(queries);
        customQueryLayout.setAlignItems(Alignment.STRETCH);
        customQueryLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(customQueryLayout);

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
    public List<String> getEvents(int queryNum) {
        if (queryNum == 3) {
            List<String> events = new Vector<>();
            events.add("World War 1");
            events.add("World War 2");
            events.add("Korean War");
            events.add("Vietnam War");
            events.add("Cold War");
            return events;
        }
        if (queryNum == 2) {
            List<String> events = new Vector<>();
            events.add("1991: Collapse of the Soviet Union");
            events.add("2001: 9/11 Terror Attacks");
            events.add("2008: Great Recession");
            return events;
        }
        else {
            return null;
        }
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
    public List<Integer> getYears(int queryNum) {
        List<Integer> years = new Vector<>();
        if (queryNum == 1) {
            for (int i = 1950; i < 2024; i++) {
                years.add(i);
            }
            return years;
        }
        if (queryNum == 2) {
            for (int i = 1950; i < 2012; i++) {
                years.add(i);
            }
            return years;
        }
        if (queryNum == 3) {
            for (int i = 1995; i < 2024; i++) {
                years.add(i);
            }
            return years;
        }
        if (queryNum == 4) {
            for (int i = 1900; i < 2024; i++) {
                years.add(i);
            }
            return years;
        }
        if (queryNum == 5) {
            for (int i = 1975; i < 2024; i++) {
                years.add(i);
            }
            return years;
        }
        return null;
    }
}
