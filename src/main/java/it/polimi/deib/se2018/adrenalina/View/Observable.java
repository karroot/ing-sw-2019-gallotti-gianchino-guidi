package it.polimi.deib.se2018.adrenalina.View;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the logic of the pattern Observable
 * @param <T> type of the messages that will send at the observers
 */
public class Observable<T>
{


	private List<Observer<T>> observers = new ArrayList<Observer<T>>();

	/**
	 * Add an observer to the list of all observer of this observable
	 * @param observer observe to add
	 */
	public void register(Observer<T> observer){
		synchronized (observers) {
			observers.add(observer);			
		}		
	}

	/**
	 * Remove an observer to the list of all observer of this observable
	 * @param observer observe to remove
	 */
	public void deregister(Observer<T> observer){
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	/**
	 * Notify all the observer with a message of update
	 * @param message message that contains the updates
	 * @throws Exception if the observer launch an exception
	 */
	protected void notify(T message) throws Exception
	{
		synchronized (observers) {
			for(Observer<T> observer : observers){
				observer.update(message);
			}
		}
	}

}
