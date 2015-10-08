package io.mocchi.adaptor;

public class Operation {
	private Operation.Action action;
	private Adaptor adaptor;
	private int pageNumber;

	public Operation(String path, Operation.Action action)
			throws InstantiationException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		this(Adaptor.createAdaptor(path), action);
	}

	public Operation(Adaptor adaptor, Operation.Action action) {
		this.action = action;
		this.adaptor = adaptor;
		this.adaptor.init();
		this.select(0);
	}

	public void next() {
		if (pageNumber + 1 < this.adaptor.getMaxPage()) {
			action.next(adaptor.page(++pageNumber));
		}
	}

	public void prev() {
		if (pageNumber > 0) {
			action.prev(adaptor.page(--pageNumber));
		}
	}

	public void select(int pageNumber) {
		if (pageNumber < adaptor.getMaxPage() && pageNumber >= 0) {
			this.pageNumber = pageNumber;
			action.select(adaptor.page(this.pageNumber));
		} else {
			action.message("failed select");
		}
	}

	public void setImage(OptimizedImage image) {
		action.setImage(image);
	}

	/**
	 * callback
	 */
	public static abstract class Action {
		protected abstract void next(OptimizedImage image);

		protected abstract void prev(OptimizedImage image);

		protected abstract void select(OptimizedImage image);

		protected abstract void ready();

		protected abstract void message(String message);

		protected abstract void setImage(OptimizedImage image);
	}

}
