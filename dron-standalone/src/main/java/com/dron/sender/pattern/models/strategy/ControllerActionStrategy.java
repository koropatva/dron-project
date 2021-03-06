package com.dron.sender.pattern.models.strategy;

public enum ControllerActionStrategy {
	EXIM_INITIALIZE,
	ROOT_INITIALIZE,
	ROOT_NEW_UI_SEQUENCE,
	ROOT_FILL_ROOT_CONTROLS,
	ROOT_FILL_UI_PLUGIN_ACCORDION,
	ROOT_MOVE_PLUGIN_DOWN,
	ROOT_MOVE_PLUGIN_UP,
	ROOT_NEW_UI_PLUGIN,
	ROOT_SEND_SEQUENCE,
	ROOT_SEND_PLUGIN,
	ROOT_PREPARE_SEQUENCE,
	ROOT_IMPORT_SEQUENCE,
	ROOT_SHOW_IMPORT_DIALOG_SEQUENCE,
	ROOT_DISABLE_CONTROLS,
	ROOT_ENABLE_CONTROLS,
	ROOT_EXPORT_SEQUENCE;
}
