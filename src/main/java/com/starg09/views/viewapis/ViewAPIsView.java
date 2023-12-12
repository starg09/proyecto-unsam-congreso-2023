package com.starg09.views.viewapis;

import com.starg09.data.SamplePerson;
import com.starg09.services.SamplePersonService;
import com.starg09.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("View APIs")
@Route(value = "view-APIs", layout = MainLayout.class)
@Uses(Icon.class)
public class ViewAPIsView extends Composite<VerticalLayout> {

    public ViewAPIsView() {
        ComboBox comboBox = new ComboBox();
        Grid basicGrid = new Grid(SamplePerson.class);
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        comboBox.setLabel("Combo Box");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        basicGrid.setWidth("100%");
        basicGrid.getStyle().set("flex-grow", "0");
        setGridSampleData(basicGrid);
        getContent().add(comboBox);
        getContent().add(basicGrid);
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;
}
