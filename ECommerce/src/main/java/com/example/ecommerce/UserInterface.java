package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    VBox body;
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    Button buyButton;

    Button signInButton;

    Label welcomeLabel;
    Customer customerLoggedIn;

ProductList productList = new ProductList();
VBox productPage;

Button placeOrderButton = new Button("Place Order");

ObservableList<Product> itemsInCart = FXCollections.observableArrayList();

       public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
//        root.getChildren().add(loginPage);
        root.setTop(headerBar);
//        root.setCenter(loginPage);
        body =new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setCenter(body);

        productPage=productList.getAllProduct();
        body.getChildren().addAll(productPage);

        root.setBottom(footerBar);

        return root;
    }

UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
}

    private void createLoginPage(){
        Text userNameText = new Text("User Name");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("sk@gmail.com");
        userName.setPromptText("Type your user name");
        PasswordField password = new PasswordField();
        password.setText("sk1234");
        password.setPromptText("Type your password");

        Label messageLabel = new Label();

        Button loginButton=new Button("Login");

        loginPage=new GridPane();
        loginPage.setVgap(10);
        loginPage.setHgap(10);
//        loginPage.setStyle("-fx-background-color: grey");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.add(userNameText,0,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(userName,1,0);
        loginPage.add(password,1,1);
        loginPage.add(loginButton,1,2);
        loginPage.add(messageLabel,0,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String name = userName.getText();
                String pass = password.getText();
                Login login =new Login();
               customerLoggedIn= login.customerLogin(name,pass);

                if(customerLoggedIn !=null){
                  messageLabel.setText("Welcome "+ customerLoggedIn.getName() );
                  welcomeLabel.setText("Welcome " + customerLoggedIn.getName());
                  headerBar.getChildren().add(welcomeLabel);
                  body.getChildren().clear();
                  body.getChildren().add(productPage);
//                  footerBar.getChildren().add(buyButton);
                }
                else{
                    messageLabel.setText("Incorrect User name or Password");
                }
            }
        });
    }

    private void  createHeaderBar(){

           Button homeButton = new Button();
        Image image = new Image("C:\\project for resume\\ECommerce\\src\\house-black-silhouette-without-door.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        homeButton.setGraphic(imageView);

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search here");
        searchBar.setPrefWidth(200);

        Button searchButton = new Button("Search");

         signInButton = new Button("Sign In");
         welcomeLabel=new Label();

         Button cartButton = new Button("Cart");


        headerBar = new HBox();
//        loginPage.setStyle("-fx-background-color: grey");

        headerBar.setAlignment(Pos.CENTER);
        headerBar.setSpacing(10);
        headerBar.setPadding(new Insets(10));

        headerBar.getChildren().addAll(homeButton, searchBar,searchButton,signInButton,cartButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                body.getChildren().addAll(loginPage);
                headerBar.getChildren().remove(signInButton);
//                footerBar.setVisible(false);
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductsInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(itemsInCart == null){
                    showDialog("Please select any product");
                    return;
                }
                if(customerLoggedIn==null){
                    showDialog("Login first");
                    return;
                }
                int count = Order.placeMultipleOrder(customerLoggedIn,itemsInCart);
                if(count!=0){
                    showDialog("Order for "+count+" products placed successfully");
                }
                else {
                    showDialog("Order failed");
                }
            }
        });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                body.getChildren().clear();
                body.getChildren().add(productPage);
                footerBar.setVisible(true);
                if(customerLoggedIn==null && headerBar.getChildren().indexOf(signInButton)==-1){
                    headerBar.getChildren().add(signInButton);
                }
            }
        });

    }

    private void  createFooterBar(){

        footerBar = new HBox();
         buyButton = new Button("Buy Now");
//        loginPage.setStyle("-fx-background-color: grey");

        Button addToCartButton = new Button("Add to cart");

        footerBar.setAlignment(Pos.CENTER);
        footerBar.setSpacing(10);
        footerBar.setPadding(new Insets(10));

        footerBar.getChildren().addAll(buyButton,addToCartButton);

        buyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                 showDialog("Please select any product");
                 return;
                }
                if(customerLoggedIn==null){
                    showDialog("Login first");
                    return;
                }
                boolean status = Order.placeOrder(customerLoggedIn,product);
                if(status==true){
                    showDialog("Order Placed successfully");
                }
                else {
                    showDialog("Order failed");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Product product = productList.getSelectedProduct();
                if(product == null){
                    showDialog("Please select any product");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to cart successfully");
            }
        });
    }

    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
