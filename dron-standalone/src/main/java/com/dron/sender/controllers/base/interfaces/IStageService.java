package com.dron.sender.controllers.base.interfaces;

import javafx.stage.Stage;

import com.dron.sender.controllers.base.models.ControllerEnum;

public interface IStageService {

	void initPrimaryStage(Stage primaryStage);

	void showController(ControllerEnum controllerEnum);

	Stage getPrimaryStage();
}
