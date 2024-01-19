package com.example.application.views.list;

import com.example.application.data.entity.*;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Vector;

@Route(value = "trendResults", layout = MainLayout.class)
public class TrendQueryResultView extends VerticalLayout {
    public TrendQueryResultView() {
        H1 queryOneTitle = new H1("Query One Results");
        H1 queryTwoTitle = new H1("Query Two Results");
        H1 queryThreeTitle = new H1("Query Three Results");
        H1 queryFourTitle = new H1("Query Four Results");
        H1 queryFiveTitle = new H1("Query Five Results");

        int queryNum = ComponentUtil.getData(UI.getCurrent(), Integer.class);
        String eventYear = "";
        Vector<Integer> dateRange = null;
        List<QueryOne> resultsOne = null;
        List<QueryTwo> resultsTwo = null;
        List<QueryThree> resultsThree = null;
        List<QueryFour> resultsFour = null;
        List<QueryFive> resultsFive = null;

        //QUERY ONE
        if (queryNum == 1) {
            resultsOne = ComponentUtil.getData(UI.getCurrent(), List.class);
            add(queryOneTitle);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);

            String nameOne = resultsOne.get(0).getGenre();
            String nameTwo = resultsOne.get(resultsOne.size() - 1).getGenre();

            DataSeries genreOne = new DataSeries();
            DataSeries genreTwo = new DataSeries();

            genreOne.setName(nameOne);
            genreTwo.setName(nameTwo);
            for (int i = 0; i < resultsOne.size(); i++) {
                if (resultsOne.get(i).getGenre().equals(nameOne)) {
                    genreOne.add(new DataSeriesItem(Integer.parseInt(resultsOne.get(i).getYear()), resultsOne.get(i).getAverage_revenue()));
                }
                else {
                    genreTwo.add(new DataSeriesItem(Integer.parseInt(resultsOne.get(i).getYear()), resultsOne.get(i).getAverage_revenue()));
                }
            }

            Chart resultChart = new Chart(ChartType.AREASPLINE);
            Configuration conf = resultChart.getConfiguration();
            conf.setTitle("Annual Average Revenue of " + nameOne + " and " + nameTwo + " Films");
            XAxis xAxis = new XAxis();
            YAxis yAxis = new YAxis();
            xAxis.setTitle("Year");
            yAxis.setTitle("Average Annual Revenue");
            conf.addxAxis(xAxis);
            conf.addyAxis(yAxis);
            conf.addSeries(genreOne);
            conf.addSeries(genreTwo);
            add(resultChart);
        }

        //QUERY TWO
        if (queryNum == 2) {
            resultsTwo = ComponentUtil.getData(UI.getCurrent(), List.class);
            eventYear = ComponentUtil.getData(UI.getCurrent(), String.class);
            add(queryTwoTitle);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);

            String nameOne = resultsTwo.get(0).getGenre();
            String nameTwo = resultsTwo.get(20).getGenre();
            String nameThree = resultsTwo.get(40).getGenre();
            String nameFour = resultsTwo.get(60).getGenre();
            //String nameFive = resultsTwo.get(80).getGenre();

            DataSeries genreOne = new DataSeries();
            DataSeries genreTwo = new DataSeries();
            DataSeries genreThree = new DataSeries();
            DataSeries genreFour = new DataSeries();
            //DataSeries genreFive = new DataSeries();

            genreOne.setName(nameOne);
            genreTwo.setName(nameTwo);
            genreThree.setName(nameThree);
            genreFour.setName(nameFour);
            //genreFive.setName(nameFive);

            for (int i = 0; i < resultsTwo.size(); i++) {
                if (resultsTwo.get(i).getGenre().equals(nameOne)) {
                    genreOne.add(new DataSeriesItem(resultsTwo.get(i).getYear(), resultsTwo.get(i).getYearOverYearPercentageChange()));
                }
                else if (resultsTwo.get(i).getGenre().equals(nameTwo)) {
                    genreTwo.add(new DataSeriesItem(resultsTwo.get(i).getYear(), resultsTwo.get(i).getYearOverYearPercentageChange()));
                }
                else if (resultsTwo.get(i).getGenre().equals(nameThree)) {
                    genreThree.add(new DataSeriesItem(resultsTwo.get(i).getYear(), resultsTwo.get(i).getYearOverYearPercentageChange()));
                }
                else if (resultsTwo.get(i).getGenre().equals(nameFour)) {
                    genreFour.add(new DataSeriesItem(resultsTwo.get(i).getYear(), resultsTwo.get(i).getYearOverYearPercentageChange()));
                }
                /*else if (resultsTwo.get(i).getGenre().equals(nameFive)) {
                    genreFive.add(new DataSeriesItem(resultsTwo.get(i).getYear(), resultsTwo.get(i).getYearOverYearPercentageChange()));
                }*/
            }

            Chart resultChart = new Chart(ChartType.AREASPLINE);
            Configuration conf = resultChart.getConfiguration();
            conf.setTitle("Year Over Year Percentage Change in Films Released Across Top Four Genres");
            XAxis xAxis = new XAxis();
            xAxis.setTitle("Year");
            YAxis yAxis = new YAxis();
            yAxis.setTitle("Year Over Year Percentage Change");
            conf.addxAxis(xAxis);
            conf.addyAxis(yAxis);
            conf.addSeries(genreOne);
            conf.addSeries(genreTwo);
            conf.addSeries(genreThree);
            conf.addSeries(genreFour);
            //conf.addSeries(genreFive);
            add(resultChart);
        }

        //QUERY THREE
        if (queryNum == 3) {
            resultsThree = ComponentUtil.getData(UI.getCurrent(), List.class);
            Vector<String> langStrings = ComponentUtil.getData(UI.getCurrent(), Vector.class);
            add(queryThreeTitle);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);

            String nameOne = langStrings.get(0);
            String nameTwo = langStrings.get(1);
            String nameThree = langStrings.get(2);
            String nameFour = langStrings.get(3);
            String nameFive = langStrings.get(4);

            DataSeries langOne = new DataSeries();
            DataSeries langTwo = new DataSeries();
            DataSeries langThree = new DataSeries();
            DataSeries langFour = new DataSeries();
            DataSeries langFive = new DataSeries();
            langOne.setName(nameOne);
            langTwo.setName(nameTwo);
            langThree.setName(nameThree);
            langFour.setName(nameFour);
            langFive.setName(nameFive);

            Vector<Float> one = new Vector<>();
            Vector<Float> two = new Vector<>();
            Vector<Float> three = new Vector<>();
            Vector<Float> four = new Vector<>();
            Vector<Float> five = new Vector<>();

            for (int i = 0; i < resultsThree.size(); i++) {
                one.add(resultsThree.get(i).getLanguageOne());
                two.add(resultsThree.get(i).getLanguageTwo());
                three.add(resultsThree.get(i).getLanguageThree());
                four.add(resultsThree.get(i).getLanguageFour());
                five.add(resultsThree.get(i).getLanguageFive());
            }

            //Stats for smoothing data
            Vector<Float> oneStats = calculateStatistics(one);
            Vector<Float> twoStats = calculateStatistics(two);
            Vector<Float> threeStats = calculateStatistics(three);
            Vector<Float> fourStats = calculateStatistics(four);
            Vector<Float> fiveStats = calculateStatistics(five);

            for (int i = 0; i < resultsThree.size(); i++) {
                //Checks if data point is more than two standard deviations away from the mean
                //If so, replaces data point with mean value
                if (resultsThree.get(i).getLanguageOne() > (oneStats.get(0) + (oneStats.get(1))*2)) {
                    langOne.add(new DataSeriesItem(resultsThree.get(i).getYear(), oneStats.get(0)));
                }
                else {
                    langOne.add(new DataSeriesItem(resultsThree.get(i).getYear(), resultsThree.get(i).getLanguageOne()));
                }

                if (resultsThree.get(i).getLanguageTwo() > (twoStats.get(0) + (twoStats.get(1))*2)) {
                    langOne.add(new DataSeriesItem(resultsThree.get(i).getYear(), twoStats.get(0)));
                }
                else {
                    langTwo.add(new DataSeriesItem(resultsThree.get(i).getYear(), resultsThree.get(i).getLanguageTwo()));
                }

                if (resultsThree.get(i).getLanguageThree() > (threeStats.get(0) + (threeStats.get(1))*2)) {
                    langThree.add(new DataSeriesItem(resultsThree.get(i).getYear(), threeStats.get(0)));
                }
                else {
                    langThree.add(new DataSeriesItem(resultsThree.get(i).getYear(), resultsThree.get(i).getLanguageThree()));
                }

                if (resultsThree.get(i).getLanguageFour() > (fourStats.get(0) + (fourStats.get(1))*2)) {
                    langFour.add(new DataSeriesItem(resultsThree.get(i).getYear(), fourStats.get(0)));
                }
                else {
                    langFour.add(new DataSeriesItem(resultsThree.get(i).getYear(), resultsThree.get(i).getLanguageFour()));
                }

                if (resultsThree.get(i).getLanguageFive() > (fiveStats.get(0) + (fiveStats.get(1))*2)) {
                    langFour.add(new DataSeriesItem(resultsThree.get(i).getYear(), fiveStats.get(0)));
                }
                else {
                    langFive.add(new DataSeriesItem(resultsThree.get(i).getYear(), resultsThree.get(i).getLanguageFive()));
                }
            }

            Chart resultChart = new Chart(ChartType.AREASPLINE);
            Configuration conf = resultChart.getConfiguration();
            String title = "";
            switch (nameOne) {
                case("English"):
                    title = "Most Spoken Languages";
                    break;
                case("French"):
                    title = "Fastest Growing Languages";
                    break;
                case("Russian"):
                    title = "European Languages";
                    break;
                case("Spanish"):
                    title = "South American Languages";
                    break;
            }
            conf.setTitle("Average Return on Investment for Top 5 " + title);
            XAxis xAxis = new XAxis();
            xAxis.setTitle("Year");
            YAxis yAxis = new YAxis();
            yAxis.setTitle("Average Return On Investment");
            conf.addxAxis(xAxis);
            conf.addyAxis(yAxis);
            conf.addSeries(langOne);
            conf.addSeries(langTwo);
            conf.addSeries(langThree);
            conf.addSeries(langFour);
            conf.addSeries(langFive);
            add(resultChart);

        }

        //QUERY FOUR
        if (queryNum == 4) {
            resultsFour = ComponentUtil.getData(UI.getCurrent(), List.class);
            dateRange = ComponentUtil.getData(UI.getCurrent(), Vector.class);
            add(queryFourTitle);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
            int j = dateRange.get(1);
            for (int i = 0; i < resultsFour.size(); i++) {
                resultsFour.get(i).setReleaseYear(j);
                j--;
            }

            DataSeries spring = new DataSeries();
            DataSeries summer = new DataSeries();
            DataSeries autumn = new DataSeries();
            DataSeries winter = new DataSeries();
            spring.setName("Spring");
            summer.setName("Summer");
            autumn.setName("Autumn");
            winter.setName("Winter");

            for (int i = 0; i < resultsFour.size(); i++) {
                spring.add(new DataSeriesItem(resultsFour.get(i).getReleaseYear(), resultsFour.get(i).getSpring()));
                summer.add(new DataSeriesItem(resultsFour.get(i).getReleaseYear(), resultsFour.get(i).getSummer()));
                autumn.add(new DataSeriesItem(resultsFour.get(i).getReleaseYear(), resultsFour.get(i).getAutumn()));
                winter.add(new DataSeriesItem(resultsFour.get(i).getReleaseYear(), resultsFour.get(i).getWinter()));
            }

            Chart resultChart = new Chart(ChartType.AREASPLINE);
            Configuration conf = resultChart.getConfiguration();
            conf.setTitle("Average Yearly Runtime For Films");
            conf.setSubTitle("Grouped By Season");
            XAxis xaxis = new XAxis();
            xaxis.setTitle("Year");
            YAxis yaxis = new YAxis();
            yaxis.setTitle("Average Runtime (Minutes)");
            conf.addxAxis(xaxis);
            conf.addyAxis(yaxis);
            conf.addSeries(spring);
            conf.addSeries(summer);
            conf.addSeries(autumn);
            conf.addSeries(winter);
            add(resultChart);
        }

        //QUERY FIVE
        if (queryNum == 5) {
            resultsFive = ComponentUtil.getData(UI.getCurrent(), List.class);
            Vector<String> companyAverages = ComponentUtil.getData(UI.getCurrent(), Vector.class);
            List<QueryFiveAverages> budgetAverages = new Vector<>();
            String startYear = companyAverages.get(0);
            String endYear = companyAverages.get(companyAverages.size() - 3);
            for (int i = 0; i < companyAverages.size(); i++) {
                QueryFiveAverages avg = new QueryFiveAverages(companyAverages.get(i), companyAverages.get(i+1), Integer.parseInt(companyAverages.get(i+2)));
                budgetAverages.add(avg);
                i = i + 2;
            }
            String productionCompany = ComponentUtil.getData(UI.getCurrent(), String.class);
            Vector<String> genres = new Vector<>();
            add(queryFiveTitle);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);

            for (int i = 0; i < resultsFive.size(); i++) {
                if (!genres.contains(resultsFive.get(i).getGenre())) {
                    genres.add(resultsFive.get(i).getGenre());
                }
            }

            String nameOne = genres.get(0);
            String nameTwo = genres.get(1);
            String nameThree = genres.get(2);
            String nameFour = genres.get(3);
            String nameFive = genres.get(4);

            DataSeries genreOne = new DataSeries();
            DataSeries genreTwo = new DataSeries();
            DataSeries genreThree = new DataSeries();
            DataSeries genreFour = new DataSeries();
            DataSeries genreFive = new DataSeries();

            genreOne.setName(nameOne);
            genreTwo.setName(nameTwo);
            genreThree.setName(nameThree);
            genreFour.setName(nameFour);
            genreFive.setName(nameFive);

            for (int i = 0; i < resultsFive.size(); i++) {
                int budgetDifference = 0;
                if (resultsFive.get(i).getGenre().equals(nameOne)) {
                    for (int j = 0; j < budgetAverages.size(); j++) {
                        if (budgetAverages.get(j).getReleaseYear().equals(resultsFive.get(i).getReleaseYear()) && budgetAverages.get(j).getGenre().equals(nameOne)) {
                            budgetDifference = resultsFive.get(i).getAnnualBudget() - budgetAverages.get(j).getAnnualBudget();
                        }
                    }
                    genreOne.add(new DataSeriesItem(Integer.parseInt(resultsFive.get(i).getReleaseYear()), budgetDifference));
                }
                else if (resultsFive.get(i).getGenre().equals(nameTwo)) {
                    for (int j = 0; j < budgetAverages.size(); j++) {
                        if (budgetAverages.get(j).getReleaseYear().equals(resultsFive.get(i).getReleaseYear()) && budgetAverages.get(j).getGenre().equals(nameTwo)) {
                            budgetDifference = resultsFive.get(i).getAnnualBudget() - budgetAverages.get(j).getAnnualBudget();
                        }
                    }
                    genreTwo.add(new DataSeriesItem(Integer.parseInt(resultsFive.get(i).getReleaseYear()), budgetDifference));
                }
                else if (resultsFive.get(i).getGenre().equals(nameThree)) {
                    for (int j = 0; j < budgetAverages.size(); j++) {
                        if (budgetAverages.get(j).getReleaseYear().equals(resultsFive.get(i).getReleaseYear()) && budgetAverages.get(j).getGenre().equals(nameThree)) {
                            budgetDifference = resultsFive.get(i).getAnnualBudget() - budgetAverages.get(j).getAnnualBudget();
                        }
                    }
                    genreThree.add(new DataSeriesItem(Integer.parseInt(resultsFive.get(i).getReleaseYear()), budgetDifference));
                }
                else if (resultsFive.get(i).getGenre().equals(nameFour)) {
                    for (int j = 0; j < budgetAverages.size(); j++) {
                        if (budgetAverages.get(j).getReleaseYear().equals(resultsFive.get(i).getReleaseYear()) && budgetAverages.get(j).getGenre().equals(nameFour)) {
                            budgetDifference = resultsFive.get(i).getAnnualBudget() - budgetAverages.get(j).getAnnualBudget();
                        }
                    }
                    genreFour.add(new DataSeriesItem(Integer.parseInt(resultsFive.get(i).getReleaseYear()), budgetDifference));
                }
                else if (resultsFive.get(i).getGenre().equals(nameFive)) {
                    for (int j = 0; j < budgetAverages.size(); j++) {
                        if (budgetAverages.get(j).getReleaseYear().equals(resultsFive.get(i).getReleaseYear()) && budgetAverages.get(j).getGenre().equals(nameFive)) {
                            budgetDifference = resultsFive.get(i).getAnnualBudget() - budgetAverages.get(j).getAnnualBudget();
                        }
                    }
                    genreFive.add(new DataSeriesItem(Integer.parseInt(resultsFive.get(i).getReleaseYear()), budgetDifference));
                }
            }

            Chart resultsChart = new Chart(ChartType.AREASPLINE);
            Configuration conf = resultsChart.getConfiguration();
            conf.setTitle("Comparison of " + productionCompany + " Annual Budgets for Top Five Genres Against the Industry Averages");
            conf.setSubTitle("Sorted By Most Popular Genres Between " + startYear + " and " + endYear);
            XAxis xAxis = new XAxis();
            xAxis.setTitle("Year");
            YAxis yAxis = new YAxis();
            yAxis.setTitle("Average Annual Budget");
            conf.addxAxis(xAxis);
            conf.addyAxis(yAxis);
            conf.addSeries(genreOne);
            conf.addSeries(genreTwo);
            conf.addSeries(genreThree);
            conf.addSeries(genreFour);
            conf.addSeries(genreFive);
            add(resultsChart);

        }
        if (queryNum == 6) {
            List<tupleCount> results = ComponentUtil.getData(UI.getCurrent(), List.class);
            String count = results.get(0).getCount();
            String tupCount = String.valueOf(count);
            H1 result = new H1(count);
            add(result);
            setJustifyContentMode(JustifyContentMode.CENTER);
            setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        }

    }
    public Vector<Float> calculateStatistics(Vector<Float> dataSet) {
        Vector<Float> results = new Vector<>();
        float sum = 0;
        float standardDeviation = 0;
        float numElements = dataSet.size();

        for (int i = 0; i < numElements; i++) {
            sum = sum + dataSet.get(i);
        }
        float mean = sum/numElements;
        for (int i = 0; i < numElements; i++) {
            standardDeviation = (float) (standardDeviation + Math.pow(dataSet.get(i) - mean, 2));
        }
        float stdDev = (float) Math.sqrt(standardDeviation/numElements);
        results.add(mean);
        results.add(stdDev);
        return results;
    }
}
