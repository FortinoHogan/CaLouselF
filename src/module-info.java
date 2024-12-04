module CaLouselF {
	requires javafx.graphics;
	requires java.sql;
	requires javafx.controls;
	
	opens client;
	opens controller;
	opens model;
	opens util;
	opens view.admin;
	opens view.buyer;
	opens view.guest;
	opens view.seller;
}