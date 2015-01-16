package com.dron.sender.pattern.services.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.ModelRootController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class InitializeStrategy extends ModelRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.INITIALIZE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		uiSequence.getUIParams().add(new UIParam());
		uiSequence.getUiPlugins().add(new UIPlugin());
		rootUiPlugin.setMethod(HttpMethod.GET.name());

		tfNewPluginName.textProperty().bindBidirectional(uiSequence.getName());

		tfUrl.textProperty().bindBidirectional(rootUiPlugin.getUrl());

		txaPostBody.textProperty()
				.bindBidirectional(rootUiPlugin.getPostBody());

		cbMethods.getSelectionModel().select(rootUiPlugin.getMethod().get());
		cbMethods.getItems().addAll(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					cbMethods.getSelectionModel().select(newValue);
					RootConfig.bindPostBody(txaPostBody, newValue);

					updateControls();
				});

		context.execute(controller, ControllerActionStrategy.NEW_UI_SEQUENCE);

		tblHeaders = new HeaderTableView().initialize(
				rootUiPlugin.getHeadersList(), tblHeaders);

		tblParams = new ParamTableView().initialize(uiSequence.getUIParams(),
				tblParams);

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.setEditable(true);

		tbtnHeaders.setOnAction(event -> {
			tblHeaders.setVisible(!tblHeaders.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		tbtnParams.setOnAction(event -> {
			tblParams.setVisible(!tblParams.isVisible());
			RootConfig.bindHeaders(tblHeaders.isVisible()
					|| tblParams.isVisible());

			updateControls();
		});

		RootConfig.bindPostBody(txaPostBody, rootUiPlugin.getMethod().get());
		updateControls();
	}

	private void updateControls() {
		hbHeadersParamsTable.setPrefHeight(RootConfig
				.getHbHeadersParamsHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

}
