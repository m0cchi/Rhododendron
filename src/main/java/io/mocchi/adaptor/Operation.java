package io.mocchi.adaptor;

import java.awt.Image;

public class Operation {
	private Operation.Action action;
	private Adaptor adaptor;
	private int pageNumber;
	
	public Operation(Adaptor adaptor,Operation.Action action){
		this.action = action;
		this.adaptor = adaptor;
	}
	
	public void next(){
		action.next(adaptor.page(++pageNumber));
	}
	
	public void prev(){
		action.prev(adaptor.page(--pageNumber));
	}
	
	public void select(int pageNumber){
		if(pageNumber < adaptor.getMaxPage() && pageNumber >= 0){
			this.pageNumber = pageNumber;
			select(this.pageNumber);
		}else{
			action.message("failed select");
		}
	}
	
	/**
	 * callback
	 */
	public static abstract class Action {
		protected abstract void next(Image image);
		protected abstract void prev(Image image);
		protected abstract void select(Image image);
		protected abstract void ready();
		protected abstract void message(String message);
	}

}
