package project.view.right.productTable;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import project.model.product.ProductContainer;

public class ProductTable extends TableView<ProductContainer> {
    TableColumn<ProductContainer, String> productColumn = new TableColumn<>("Product");
    TableColumn<ProductContainer, String> priceColumn = new TableColumn<>("Price");
    TableColumn<ProductContainer, String> quantityColumn = new TableColumn<>("Quantity");
    TableColumn<ProductContainer, String> pCodeColumn = new TableColumn<>("Code");


    public ProductTable(List<ProductContainer> productList) {
        setColumns();
        getColumns().addAll(pCodeColumn, productColumn, priceColumn, quantityColumn);
        setItems(getObservableList(productList));
        setMaxHeight(120);
        setColumnResizePolicy(ProductTable.CONSTRAINED_RESIZE_POLICY);
    }

    public void setColumns() {
        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().product().name()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().product().price())));
        quantityColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().quantityLeft())));
        pCodeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().product().code()));
    }

    public ObservableList<ProductContainer> getObservableList(List<ProductContainer> productList) {
        return FXCollections.observableArrayList(productList);
    }

}
