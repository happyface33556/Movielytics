package com.example.application.views.list;

import com.example.application.data.entity.*;
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

import javax.management.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

@Route(value = "customLoading", layout = MainLayout.class)
public class CustomQueryLoadView extends VerticalLayout {
    public CustomQueryLoadView() {
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
        int queryNum = ComponentUtil.getData(UI.getCurrent(), Integer.class);
        List<QueryOne> queryOneResults = null;
        List<QueryTwo> queryTwoResults = null;
        List<QueryThree> queryThreeResults = null;
        List<QueryFour> queryFourResults = null;
        List<QueryFive> queryFiveResultsOne = null;
        List<QueryFiveAverages> queryFiveResultsTwo = null;
        List<tupleCount> tupleCount = null;
        Vector<Integer> dateRange = null;
        Vector<String> langStrings = new Vector<>();
        Vector<String> companyAverages = new Vector<>();
        String queryTwoEvent = "";
        String productionCompany = "";
        String query = "";
        String avgQuery = "";

        switch(queryNum) {
            case(1):
                Vector<String> queryOneParameters = ComponentUtil.getData(UI.getCurrent(), Vector.class);
                int start = Integer.parseInt(queryOneParameters.get(0));
                int end = Integer.parseInt(queryOneParameters.get(1));
                String genreOne = queryOneParameters.get(2);
                String genreTwo = queryOneParameters.get(3);
                query = "SELECT g_name as Genre,\n" +
                        "     EXTRACT(YEAR FROM release_date) AS YEAR, \n" +
                        "     ROUND(AVG(revenue), 2) as Average_Revenue    \n" +
                        "FROM happyface33556.movie_info i, \n" +
                        "     happyface33556.financials f,\n" +
                        "     happyface33556.genre_classification g\n" +
                        "WHERE i.mID = f.mID\n" +
                        "     AND i.mID = g.mID\n" +
                        "     AND f.revenue != 0\n" +
                        "     AND to_char(release_date, 'YYYY') IS NOT NULL\n" +
                        "     AND extract(YEAR from release_date) >= " + start + "\n" +
                        "     AND extract(YEAR from release_date) <= " + end + "\n" +
                        "     AND (g_name = '" + genreOne + "' OR g_name = '" + genreTwo + "')\n" +
                        "GROUP BY g_name, EXTRACT(YEAR FROM release_date)\n" +
                        "ORDER BY g_name, EXTRACT(YEAR FROM release_date) ASC\n";
                queryOneResults = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(QueryOne.class));
                break;
            case(2):
                queryTwoEvent = ComponentUtil.getData(UI.getCurrent(), String.class);
                if (queryTwoEvent.equals("1991: Fall of the Soviet Union")) {
                    queryTwoEvent = "1991";
                }
                else if (queryTwoEvent.equals("2001: 9/11 Terror Attack")) {
                    queryTwoEvent = "2001";
                }
                else if (queryTwoEvent.equals("2008: Great Recession")) {
                    queryTwoEvent = "2008";
                }
                query = "WITH decades_event AS (\n" +
                        "    SELECT\n" +
                        "        genre_classification.g_name as genre,\n" +
                        "        EXTRACT(YEAR FROM movie_info.release_date) as release_year,\n" +
                        "        COUNT(genre_classification.MID) as count\n" +
                        "    FROM\n" +
                        "        happyface33556.genre_classification\n" +
                        "    JOIN\n" +
                        "        happyface33556.movie_info ON genre_classification.MID = movie_info.MID\n" +
                        "    WHERE\n" +
                        "        movie_info.MID = SOME (\n" +
                        "            SELECT MID\n" +
                        "            FROM happyface33556.movie_info\n" +
                        "            WHERE EXTRACT(YEAR FROM release_date) BETWEEN (" + queryTwoEvent + " - 10) AND (" + queryTwoEvent + " + 10))\n" +
                        "        --AND g_name = 'Action'\n" +
                        "    GROUP BY\n" +
                        "        genre_classification.g_name, EXTRACT(YEAR FROM movie_info.release_date)\n" +
                        "),\n" +
                        "\n" +
                        "countByYear1 AS (\n" +
                        "    SELECT\n" +
                        "        genre,\n" +
                        "        release_year as year,\n" +
                        "        count\n" +
                        "    FROM\n" +
                        "        decades_event\n" +
                        "    ORDER BY\n" +
                        "        release_year DESC, count DESC\n" +
                        "),\n" +
                        "\n" +
                        "countByYear2 AS (\n" +
                        "    SELECT\n" +
                        "        genre,\n" +
                        "        release_year as year,\n" +
                        "        count\n" +
                        "    FROM\n" +
                        "        decades_event\n" +
                        "    ORDER BY\n" +
                        "        release_year DESC, count DESC\n" +
                        "),\n" +
                        "\n" +
                        "yearOverYear AS (\n" +
                        "    SELECT c1.genre,\n" +
                        "        c1.year AS Year,\n" +
                        "        ROUND((c1.count - c2.count)/c1.count * 100, 2) AS Year_Over_Year_Percentage_Change\n" +
                        "    FROM countByYear1 c1, countByYear2 c2\n" +
                        "    WHERE c1.genre = c2.genre\n" +
                        "        AND c1.year = c2.year + 1  \n" +
                        "    ORDER BY Year_Over_Year_Percentage_Change DESC\n" +
                        "),\n" +
                        "\n" +
                        "targetGenres AS (\n" +
                        "    SELECT genre\n" +
                        "    FROM yearOverYear\n" +
                        "    WHERE Year = " + queryTwoEvent + " + 1\n" +
                        "        OR Year = " + queryTwoEvent + " + 2\n" +
                        "        OR Year = " + queryTwoEvent + " + 3\n" +
                        "    ORDER BY Year_Over_Year_Percentage_Change DESC\n" +
                        "    FETCH FIRST 5 ROWS ONLY\n" +
                        ")\n" +
                        "\n" +
                        "SELECT Genre, Year, Year_Over_Year_Percentage_Change\n" +
                        "FROM yearOverYear\n" +
                        "WHERE yearOverYear.genre IN (SELECT genre FROM targetGenres)\n" +
                        "ORDER BY Genre, Year\n";
                queryTwoResults = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(QueryTwo.class));
                break;
            case(3):
                String queryThreeGroup = ComponentUtil.getData(UI.getCurrent(), String.class);
                List<String> languages = new Vector<>();
                dateRange = ComponentUtil.getData(UI.getCurrent(), Vector.class);
                int startYear = dateRange.get(0);
                int endYear = dateRange.get(1);
                switch(queryThreeGroup) {
                    case("Top 5 Most Spoken Languages"):
                        languages.add("en");
                        languages.add("es");
                        languages.add("hi");
                        languages.add("zh");
                        languages.add("ar");
                        langStrings.add("English");
                        langStrings.add("Spanish");
                        langStrings.add("Hindi");
                        langStrings.add("Chinese");
                        langStrings.add("Arabic");
                        break;

                    case("Top 5 Fastest Growing Languages"):
                        languages.add("fr");
                        languages.add("ur");
                        languages.add("pt");
                        languages.add("id");
                        languages.add("ko");
                        langStrings.add("French");
                        langStrings.add("Urdu");
                        langStrings.add("Portuguese");
                        langStrings.add("Indonesian");
                        langStrings.add("Korean");
                        break;

                    case("Top 5 European Languages"):
                        languages.add("ru");
                        languages.add("de");
                        languages.add("fr");
                        languages.add("tr");
                        languages.add("it");
                        langStrings.add("Russian");
                        langStrings.add("German");
                        langStrings.add("French");
                        langStrings.add("Turkish");
                        langStrings.add("Italian");
                        break;

                    case("Top 5 South American Languages"):
                        languages.add("es");
                        languages.add("pt");
                        languages.add("en");
                        languages.add("qu");
                        languages.add("gn");
                        langStrings.add("Spanish");
                        langStrings.add("Portuguese");
                        langStrings.add("English");
                        langStrings.add("Quechua");
                        langStrings.add("Guarani");
                        break;
                }
                query = "SELECT \n" +
                        "    EXTRACT(YEAR FROM i.release_date) AS Year,\n" +
                        "    ROUND(AVG(CASE WHEN f.budget != 0 AND f.revenue != 0 AND m.LID = '" + languages.get(0) + "' THEN ((f.revenue - f.budget) / f.budget) * 100 ELSE 0 END), 2) AS " + "languageOne" + ",\n" +
                        "    ROUND(AVG(CASE WHEN f.budget != 0 AND f.revenue != 0 AND m.LID = '" + languages.get(1) + "' THEN ((f.revenue - f.budget) / f.budget) * 100 ELSE 0 END), 2) AS " + "languageTwo" + ",\n" +
                        "    ROUND(AVG(CASE WHEN f.budget != 0 AND f.revenue != 0 AND m.LID = '" + languages.get(2) + "' THEN ((f.revenue - f.budget) / f.budget) * 100 ELSE 0 END), 2) AS " + "languageThree" + ",\n" +
                        "    ROUND(AVG(CASE WHEN f.budget != 0 AND f.revenue != 0 AND m.LID = '" + languages.get(3) + "' THEN ((f.revenue - f.budget) / f.budget) * 100 ELSE 0 END), 2) AS " + "languageFour" + ",\n" +
                        "    ROUND(AVG(CASE WHEN f.budget != 0 AND f.revenue != 0 AND m.LID = '" + languages.get(4) + "' THEN ((f.revenue - f.budget) / f.budget) * 100 ELSE 0 END), 2) AS " + "languageFive" + "\n" +
                        "FROM \n" +
                        "    happyface33556.financials f\n" +
                        "    JOIN happyface33556.movie_info i ON f.MID = i.MID\n" +
                        "    JOIN happyface33556.movie m ON f.MID = m.MID\n" +
                        "WHERE \n" +
                        "    f.budget != 0\n" +
                        "    AND f.revenue != 0\n" +
                        "    AND i.release_date IS NOT NULL\n" +
                        "    AND m.LID IN ('" + languages.get(0) + "', '" + languages.get(1) + "', '" + languages.get(2) + "', '" + languages.get(3) + "', '" + languages.get(4) + "')\n" +
                        "    AND EXTRACT(YEAR FROM i.release_date) BETWEEN " + startYear + " AND " + endYear + "\n" +
                        "GROUP BY \n" +
                        "    EXTRACT(YEAR FROM i.release_date)\n" +
                        "ORDER BY \n" +
                        "    Year ASC\n";
                queryThreeResults = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(QueryThree.class));
                break;
            case(4):
                dateRange = ComponentUtil.getData(UI.getCurrent(), Vector.class);
                query = "WITH springMovies AS (\n" +
                        "    SELECT mID as SpringMID, \n" +
                        "        extract(YEAR from release_date) as releaseYear \n" +
                        "    FROM happyface33556.movie_info\n" +
                        "    WHERE extract(MONTH from release_date) = 3 \n" +
                        "        OR extract(MONTH from release_date) = 4\n" +
                        "        OR extract(MONTH from release_date) = 5\n" +
                        "        AND runtime != 0\n" +
                        "),\n" +
                        "summerMovies AS (\n" +
                        "    SELECT mID as SummerMID, \n" +
                        "        extract(YEAR from release_date) as releaseYear \n" +
                        "    FROM happyface33556.movie_info\n" +
                        "    WHERE extract(MONTH from release_date) = 6 \n" +
                        "        OR extract(MONTH from release_date) = 7\n" +
                        "        OR extract(MONTH from release_date) = 8\n" +
                        "        AND runtime != 0\n" +
                        "),\n" +
                        "autumnMovies AS (\n" +
                        "    SELECT mID as AutumnMID, \n" +
                        "        extract(YEAR from release_date) as releaseYear \n" +
                        "    FROM happyface33556.movie_info\n" +
                        "    WHERE extract(MONTH from release_date) = 9 \n" +
                        "        OR extract(MONTH from release_date) = 10\n" +
                        "        OR extract(MONTH from release_date) = 11\n" +
                        "        AND runtime != 0\n" +
                        "),\n" +
                        "winterMovies AS (\n" +
                        "    SELECT mID as WinterMID, \n" +
                        "        extract(YEAR from release_date) as releaseYear \n" +
                        "    FROM happyface33556.movie_info\n" +
                        "    WHERE extract(MONTH from release_date) = 12 \n" +
                        "        OR extract(MONTH from release_date) = 1\n" +
                        "        OR extract(MONTH from release_date) = 2\n" +
                        "        AND runtime != 0\n" +
                        ")\n" +
                        "\n" +
                        "SELECT releaseYear1, \n" +
                        "    to_char(SpringAVG, '99.99') as Spring, \n" +
                        "    to_char(SummerAVG, '99.99') as Summer, \n" +
                        "    to_char(AutumnAVG, '99.99') as Autumn, \n" +
                        "    to_char(WinterAVG, '99.99') as Winter\n" +
                        "FROM (SELECT AVG(runtime) as SpringAVG, releaseYear as releaseYear1\n" +
                        "        FROM happyface33556.movie_info i, springMovies sp                                          \n" +
                        "        WHERE i.mID = sp.SpringMID\n" +
                        "            AND releaseYear >= \n" + dateRange.get(0).toString() + " " +
                        "            AND releaseYear <= \n" + dateRange.get(1).toString() + " " +
                        "        GROUP by releaseYear\n" +
                        "        ORDER by releaseYear DESC),\n" +
                        "    (SELECT AVG(runtime) as SummerAVG, releaseYear as releaseYear2\n" +
                        "        FROM happyface33556.movie_info i, summerMovies su\n" +
                        "        WHERE i.mID = su.SummerMID         \n" +
                        "        GROUP by releaseYear\n" +
                        "        ORDER by releaseYear DESC), \n" +
                        "    (SELECT AVG(runtime) as AutumnAVG, releaseYear as releaseYear3\n" +
                        "        FROM happyface33556.movie_info i, autumnMovies a\n" +
                        "        WHERE i.mID = a.AutumnMID\n" +
                        "        GROUP by releaseYear\n" +
                        "        ORDER by releaseYear DESC), \n" +
                        "    (SELECT AVG(runtime) as WinterAVG, releaseYear as releaseYear4\n" +
                        "        FROM happyface33556.movie_info i, winterMovies w\n" +
                        "        WHERE i.mID = w.WinterMID\n" +
                        "        GROUP by releaseYear\n" +
                        "        ORDER by releaseYear DESC)\n" +
                        "WHERE releaseYear1 = releaseYear2 AND releaseYear2 = releaseYear3 AND releaseYear3 = releaseYear4\n" +
                        "ORDER BY releaseYear1 DESC\n";
                queryFourResults = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(QueryFour.class));
                break;
            case(5):
                dateRange = ComponentUtil.getData(UI.getCurrent(), Vector.class);
                productionCompany = ComponentUtil.getData(UI.getCurrent(), String.class);
                query = "WITH producedMovies AS (\n" +
                        "    SELECT i.mID,\n" +
                        "        g.g_name as Genre,\n" +
                        "        extract(YEAR from i.release_date) AS releaseYear,\n" +
                        "        f.budget\n" +
                        "    FROM happyface33556.genre_classification g, \n" +
                        "        happyface33556.produces p,\n" +
                        "        happyface33556.movie_info i,\n" +
                        "        happyface33556.financials f\n" +
                        "    WHERE g.mID = p.mID\n" +
                        "        AND p.mID = i.mID\n" +
                        "        AND i.mID = f.mID\n" +
                        "        AND i.release_date IS NOT NULL\n" +
                        "        AND f.budget != 0\n" +
                        "        AND extract(YEAR from i.release_date) BETWEEN " + dateRange.get(0) + " AND " + dateRange.get(1) + "\n" +
                        "        AND p_name LIKE '%" + productionCompany +"%'\n" +
                        "),\n" +
                        "\n" +
                        "topCategories AS (\n" +
                        "    SELECT Genre\n" +
                        "    FROM producedMovies\n" +
                        "    GROUP BY Genre\n" +
                        "    FETCH FIRST 5 ROWS ONLY\n" +
                        ")\n" +
                        "\n" +
                        "SELECT releaseYear,\n" +
                        "    Genre,\n" +
                        "    ROUND(AVG(budget),2) as Annual_Budget\n" +
                        "FROM producedMovies\n" +
                        "WHERE producedMovies.genre IN (SELECT Genre FROM topCategories)\n" +
                        "GROUP BY releaseYear, Genre\n" +
                        "ORDER BY Genre, releaseYear\n";
                avgQuery = "WITH producedMovies AS (\n" +
                        "    SELECT i.mID,\n" +
                        "        g.g_name as Genre,\n" +
                        "        extract(YEAR from i.release_date) AS Release_Year,\n" +
                        "        f.budget\n" +
                        "    FROM happyface33556.genre_classification g, \n" +
                        "        happyface33556.produces p,\n" +
                        "        happyface33556.movie_info i,\n" +
                        "        happyface33556.financials f\n" +
                        "    WHERE g.mID = p.mID\n" +
                        "        AND p.mID = i.mID\n" +
                        "        AND i.mID = f.mID\n" +
                        "        AND i.release_date IS NOT NULL\n" +
                        "        AND f.budget != 0\n" +
                        "        AND extract(YEAR from i.release_date) BETWEEN " + dateRange.get(0) + " AND " + dateRange.get(1) + "\n" +
                        "        AND p_name LIKE '%" + productionCompany + "%'\n" +
                        "),\n" +
                        "\n" +
                        "topCategories AS (\n" +
                        "    SELECT Genre\n" +
                        "    FROM producedMovies\n" +
                        "    GROUP BY Genre\n" +
                        "    FETCH FIRST 5 ROWS ONLY\n" +
                        ")\n" +
                        "\n" +
                        "SELECT extract(YEAR from i.release_date) AS Release_Year,\n" +
                        "    g.g_name AS Genre,\n" +
                        "    ROUND(AVG(budget),2) as Annual_Budget\n" +
                        "FROM happyface33556.financials f,\n" +
                        "    happyface33556.genre_classification g,\n" +
                        "    happyface33556.movie_info i\n" +
                        "WHERE f.mID = g.mID\n" +
                        "    AND g.mID = i.mID\n" +
                        "    AND f.budget != 0\n" +
                        "    AND i.release_date IS NOT NULL\n" +
                        "    AND g.g_name IN (SELECT Genre FROM topCategories)\n" +
                        "    AND extract(YEAR from i.release_date) BETWEEN " + dateRange.get(0) + " AND " + dateRange.get(1) + "\n" +
                        "GROUP BY extract(YEAR from i.release_date), g.g_name\n" +
                        "ORDER BY Release_Year, Genre\n";

                queryFiveResultsOne = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(QueryFive.class));
                queryFiveResultsTwo = jdbcTemplate.query(avgQuery, new BeanPropertyRowMapper<>(QueryFiveAverages.class));
                for (int i = 0; i < queryFiveResultsTwo.size(); i++) {
                    companyAverages.add(queryFiveResultsTwo.get(i).getReleaseYear());
                    companyAverages.add(queryFiveResultsTwo.get(i).getGenre());
                    String budget = String.valueOf(queryFiveResultsTwo.get(i).getAnnualBudget());
                    companyAverages.add(budget);
                }
                break;
            case(6):
                query = "SELECT count(*) FROM movie";
                tupleCount = jdbcTemplate.query(query, new BeanPropertyRowMapper<>(tupleCount.class));
                break;
        }

        VerticalLayout buttonLayout = new VerticalLayout();
        Button resultsButton = new Button("View Results");
        buttonLayout.add(resultsButton);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(buttonLayout);

        List<QueryOne> finalQueryOneResults = queryOneResults;
        List<QueryTwo> finalQueryTwoResults = queryTwoResults;
        List<QueryThree> finalQueryThreeResults = queryThreeResults;
        List<QueryFour> finalQueryFourResults = queryFourResults;
        List<QueryFive> finalQueryFiveResultsOne = queryFiveResultsOne;
        List<QueryFiveAverages> finalQueryFiveResultsTwo = queryFiveResultsTwo;
        Vector<Integer> finalDateRange = dateRange;
        String finalQueryTwoEvent = queryTwoEvent;
        String finalProductionCompany = productionCompany;
        //Vector<String> finalCompanyAverages = companyAverages;

        List<tupleCount> finalTupleCount = tupleCount;
        resultsButton.addClickListener(e -> {
            ds.close();
            switch(queryNum) {
                case(1):
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalQueryOneResults);
                    break;
                case(2):
                    ComponentUtil.setData(UI.getCurrent(), String.class, finalQueryTwoEvent);
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalQueryTwoResults);
                    break;
                case(3):
                    ComponentUtil.setData(UI.getCurrent(), Vector.class, langStrings);
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalQueryThreeResults);
                    break;
                case(4):
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalQueryFourResults);
                    ComponentUtil.setData(UI.getCurrent(), Vector.class, finalDateRange);
                    break;
                case(5):
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalQueryFiveResultsOne);
                    ComponentUtil.setData(UI.getCurrent(), String.class, finalProductionCompany);
                    ComponentUtil.setData(UI.getCurrent(), Vector.class, companyAverages);
                    break;
                case(6):
                    ComponentUtil.setData(UI.getCurrent(), List.class, finalTupleCount);
                    break;
            }
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("trendResults");
        });
    }
}
