package com.example.application.views.list;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.jdbc.core.JdbcTemplate;

@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainLayout extends AppLayout {
    private Tabs tabs;
    private JdbcTemplate template;

    public MainLayout() {
        createHeader();
    }
    private void createHeader() {
        RouterLink homeLink = new RouterLink();
        RouterLink quickLink = new RouterLink();
        RouterLink customLink = new RouterLink();
        RouterLink storageLink = new RouterLink();
        RouterLink compLink = new RouterLink();
        RouterLink logoutLink = new RouterLink();

        homeLink.setRoute(HomeView.class);
        quickLink.setRoute(titleQueryView.class);
        customLink.setRoute(CustomQueryView.class);
        storageLink.setRoute(StorageView.class);
        compLink.setRoute(ComparisonView.class);
        logoutLink.setRoute(GoodbyeView.class);

        homeLink.add(VaadinIcon.HOME.create(), new Span("Home"));
        quickLink.add(VaadinIcon.SEARCH.create(), new Span("Quick Query"));
        customLink.add(VaadinIcon.CHART_LINE.create(), new Span("Trend Queries"));
        storageLink.add(VaadinIcon.ARCHIVES.create(), new Span("Storage"));
        compLink.add(VaadinIcon.COPY_O.create(), new Span("Comparison"));
        logoutLink.add(VaadinIcon.SIGN_OUT.create(), new Span("Sign Out"));

        Tab homeTab = new Tab(homeLink);
        Tab quickTab = new Tab(quickLink);
        Tab customTab = new Tab(customLink);
        Tab storageTab = new Tab(storageLink);
        Tab compTab = new Tab(compLink);
        Tab logoutTab = new Tab(logoutLink);

        for (Tab tab : new Tab[] {homeTab, quickTab, customTab, storageTab, logoutTab}) {
            tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        }

        this.tabs = new Tabs(homeTab, quickTab, customTab, storageTab, logoutTab);
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS);
        addToNavbar(tabs);
    }
    public Tabs getTabs() {
        return this.tabs;
    }
    public JdbcTemplate getTemplate() {
        return this.template;
    }
}

