package com.example.application.views.list;

import com.example.application.data.entity.Output;
import com.example.application.data.entity.Query;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route(value = "loading", layout = MainLayout.class)
public class QueryLoadView extends VerticalLayout {
    public QueryLoadView() throws SQLException {
        VerticalLayout logoLayout = new VerticalLayout();
        Image loading = new Image("images/loading.gif", "loading");
        logoLayout.add(loading);
        logoLayout.setAlignItems(Alignment.CENTER);
        logoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(logoLayout);

        VerticalLayout textLayout = new VerticalLayout();
        H2 loadingText = new H2("Query Complete!");
        textLayout.add(loadingText);
        textLayout.setAlignItems(Alignment.CENTER);
        textLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(textLayout);

        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setJdbcUrl("jdbc:oracle:thin:@oracle.cise.ufl.edu:1521:orcl");
        ds.setUsername("happyface33556");
        ds.setPassword("TCRufjbGaPNXorWekmClkRfl");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        Query query = ComponentUtil.getData(UI.getCurrent(), Query.class);
        List<Output> results = executeQuery(query, jdbcTemplate);

        VerticalLayout buttonLayout = new VerticalLayout();
        Button resultsButton = new Button("View Results");
        buttonLayout.add(resultsButton);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(buttonLayout);

        resultsButton.addClickListener(e -> {
            ds.close();
            ComponentUtil.setData(UI.getCurrent(), List.class, results);
            UI.getCurrent().navigate("results");
        });
    }
    public String convertDate(String date) {
        String output = "";
        String day = date.substring(8, 10);
        String dash = "-";
        String monthNum = date.substring(5, 7);
        String month = "";
        String year = date.substring(2, 4);
        switch(monthNum) {
            case "01":
                month = "JAN";
                break;
            case "02":
                month = "FEB";
                break;
            case "03":
                month = "MAR";
                break;
            case "04":
                month = "APR";
                break;
            case "05":
                month = "MAY";
                break;
            case "06":
                month = "JUN";
                break;
            case "07":
                month = "JUL";
                break;
            case "08":
                month = "AUG";
                break;
            case "09":
                month = "SEP";
                break;
            case "10":
                month = "OCT";
                break;
            case "11":
                month = "NOV";
                break;
            case "12":
                month = "DEC";
                break;
        }
        output = day + dash + month + dash + year;
        return output;
    }
    public List<Output> executeQuery(Query query, JdbcTemplate template) throws SQLException {
        String movieTitle = null, actorNames = null, genre = null, language = null, startDate = null, endDate = null;
        Boolean titlePresent = false;
        Boolean startPresent = false;
        Boolean endPresent = false;
        Boolean actorPresent = false;
        Boolean genrePresent = false;
        Boolean languagePresent = false;
        Boolean titleOut = false;
        Boolean actorsOut = false;
        Boolean budgetOut = false;
        Boolean revenueOut = false;
        Boolean releaseOut = false;
        Boolean scoreOut = false;
        ArrayList<String> results = null;
        int numEntries = 0;

        Set<String> output = query.getOutput();
        int numOutputs = output.size();

        if (query.getTitle() != null) {
            movieTitle = query.getTitle();
            titlePresent = true;
            numEntries++;
        }
        if (query.getStartDate() != null) {
            startDate = query.getStartDate().toString();
            startPresent = true;
            numEntries++;
        }
        if (query.getEndDate() != null) {
            endDate = query.getEndDate().toString();
            endPresent = true;
            numEntries++;
        }
        if (query.getActors() != null) {
            actorNames = query.getActors();
            actorPresent = true;
            numEntries++;
        }
        if (query.getGenre() != null) {
            genre = query.getGenre();
            genrePresent = true;
            numEntries++;
        }
        if (query.getLanguage() != null) {
            language = query.getLanguage();
            languagePresent = true;
            numEntries++;
        }
        String select = "SELECT DISTINCT mi.popularity, ";
        String from = "FROM movie m, credits c, financials f, movie_info mi ";

        if (output.contains("Titles")) {
            titleOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "m.title, ";
                }
                else {
                    select = select + "m.title ";
                }
            }

        if (output.contains("Actors")) {
                actorsOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "c.name, ";
                }
                else {
                    select = select + "c.name ";
                }
            }

        if (output.contains("Budget")) {
                budgetOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "f.budget, ";
                }
                else {
                    select = select + "f.budget ";
                }
            }

        if (output.contains("Revenue")) {
                revenueOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "f.revenue, ";
                }
                else {
                    select = select + "f.revenue ";
                }
            }

        if (output.contains("Release Date")) {
                releaseOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "mi.release_date, ";
                }
                else {
                    select = select + "mi.release_date ";
                }
            }

        if (output.contains("Average Score")) {
                scoreOut = true;
                numOutputs--;
                if (numOutputs > 0) {
                    select = select + "mi.vote_average, ";
                }
                else {
                    select = select + "mi.vote_average ";
                }
            }

            String start = select + from;
            String whereClause = "WHERE ((m.MID = c.MID) AND (m.MID = mi.MID) AND (m.MID = f.MID) AND (m.MID = c.MID) AND ";
            String tables = "";
            String actorJoins = "";
            String genreJoins = "";
            String languageJoins = "";
            String titleJoins = "";
            String startJoin = "";
            String endJoin = "";
            String dateRangeJoin = "";
            String actorContains = "";
            String genreContains = "";
            String languageContains = "";
            String end = ")";
            List<Output> queryResults;
            if (actorPresent) {
                numEntries--;
                //tables = tables + ", credits c ";
                actorJoins = "(UPPER(c.name) LIKE UPPER('%" + actorNames + "%'))";
                if (numEntries > 0) {
                    whereClause = whereClause + actorJoins + " AND ";
                }
                else {
                    whereClause = whereClause + actorJoins + end;
                }
            }
            if (genrePresent) {
                numEntries--;
                tables = tables + ", genre_classification g ";
                genreJoins = "(g.MID = m.MID) AND (UPPER(g.g_name) LIKE UPPER('%" + genre + "%'))";
                if (numEntries > 0) {
                    whereClause = whereClause + genreJoins + " AND ";
                }
                else {
                    whereClause = whereClause + actorJoins + end;
                }
            }
            if (languagePresent) {
                numEntries--;
                tables = tables + ", language l ";
                languageJoins = "(l.LID = m.LID) AND (UPPER(l.l_name) LIKE UPPER('%" + language + "%'))";
                if (numEntries > 0) {
                    whereClause = whereClause + languageJoins + " AND ";
                }
                else {
                    whereClause = whereClause + languageJoins + end;
                }
            }
            if (titlePresent) {
                numEntries--;
                titleJoins = "(UPPER(m.title) LIKE UPPER('%" + movieTitle + "%'))";
                if (numEntries > 0) {
                    whereClause = whereClause + titleJoins + " AND ";
                }
                else {
                    whereClause = whereClause + titleJoins + end;
                }
            }
            if (startPresent && endPresent) {
                numEntries--;
                numEntries--;
                String modStartDate = convertDate(startDate);
                String modEndDate = convertDate(endDate);
                //tables = tables + ", movie_info mi ";
                dateRangeJoin = "(mi.RELEASE_DATE BETWEEN to_date('" + modStartDate + "') AND to_date('" + modEndDate + "'))";
                if (numEntries > 0) {
                    whereClause = whereClause + dateRangeJoin + " AND ";
                }
                else {
                    whereClause = whereClause + dateRangeJoin + end;
                }
            }
            if (startPresent && (!endPresent)) {
                numEntries--;
                //tables = tables + ", movie_info mi ";
                String currDate = convertDate(LocalDate.now().toString());
                String modStartDate = convertDate(startDate);
                startJoin = "(mi.RELEASE_DATE BETWEEN to_date('" + modStartDate + "') AND to_date('" + currDate + "'))";
                if (numEntries > 0) {
                    whereClause = whereClause + startJoin + " AND ";
                }
                else {
                    whereClause = whereClause + startJoin + end;
                }
            }
            if (!startPresent && endPresent) {
                numEntries--;
                //tables = tables + ", movie_info mi ";
                String earlyDate = "01-JAN-24";
                String modEndDate = convertDate(endDate);
                endJoin = "(mi.RELEASE_DATE BETWEEN " + earlyDate + " AND " + modEndDate + ")";
                if (numEntries > 0) {
                    whereClause = whereClause + endJoin + " AND ";
                }
                else {
                    whereClause = whereClause + endJoin + end;
                }
            }
            String groupBy = " ORDER BY popularity DESC, title";
            String queryInput = start + tables + whereClause + groupBy;
            queryResults = template.query(queryInput, new BeanPropertyRowMapper<>(Output.class));
            return queryResults;
    }
}
