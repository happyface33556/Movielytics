package com.example.application.views.list;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

@Route(value = "", layout = MainLayout.class)
@Tag("main")
public class HomeView extends VerticalLayout {

    public HomeView() {
        FlexLayout logoLayout = new FlexLayout();
        Image logo = new Image("images/cropLogo.gif", "logo");
        logoLayout.add(logo);
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout buttonLayout = new VerticalLayout();

        HorizontalLayout bottomButtonLayout = new HorizontalLayout();
        Button companiesButton = new Button("Tuple Count");
        int queryNum = 6;
        companiesButton.addClickListener(e -> {
            ComponentUtil.setData(UI.getCurrent(), Integer.class, queryNum);
            UI.getCurrent().navigate("customLoading");
        });
        bottomButtonLayout.add(companiesButton);
        bottomButtonLayout.setAlignItems(Alignment.CENTER);
        bottomButtonLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        buttonLayout.add(bottomButtonLayout);
        buttonLayout.setAlignItems(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setPadding(true);

        add(logoLayout);

    }
}
