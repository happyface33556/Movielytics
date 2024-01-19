package com.example.application.views.list;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "Goodbye")
public class GoodbyeView extends VerticalLayout {
    public GoodbyeView() {
        VerticalLayout logoLayout = new VerticalLayout();
        Image logo = new Image("images/cropLogo.gif", "logo");
        logoLayout.add(logo);
        logoLayout.setAlignItems(Alignment.CENTER);
        logoLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(logoLayout);

        //IMPROVE GOODBYE MESSAGE/LAYOUT
        VerticalLayout textLayout = new VerticalLayout();
        H2 goodbyeText = new H2("Thanks for Visiting!");
        textLayout.add(goodbyeText);
        textLayout.setAlignItems(Alignment.CENTER);
        textLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        add(textLayout);
    }
}
