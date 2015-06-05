package com.dron.sender.controllers.root.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;

import org.springframework.context.ApplicationContext;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exceptions.HandlerNotReadyException;
import com.dron.sender.exceptions.RequestException;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.sequence.services.SequenceRunner;

public class SequenceTask extends Task<String> {

	private Sequence sequence;

	private ControllerStrategyContext context;

	private RootController controller;

	public SequenceTask(Sequence sequence, ApplicationContext ctx) {
		this.sequence = sequence;
		this.controller = ctx.getBean(RootController.class);
		this.context = ctx.getBean(ControllerStrategyContext.class);
	}

	@Override
	protected String call() {
		final SequenceRunner sequenceRunner = new SequenceRunner(sequence);

		try {
			sequenceRunner.runSequence();
		} catch (DronSenderException e) {
			if (e instanceof HandlerNotReadyException) {
				System.out.println("Dron ERROR " + e.getMessage());
			}
			if (e instanceof RequestException) {
				System.out.println("Request ERROR " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("System ERROR " + e.getMessage());
		} finally {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					context.execute(controller,
							ControllerActionStrategy.ROOT_ENABLE_CONTROLS);
				}
			});
		}

		return null;
	}
}
