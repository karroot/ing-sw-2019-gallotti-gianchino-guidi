package it.polimi.deib.se2018.adrenalina.View;

/**
 * Interface that implements an observer of a specified type of message
 * @param <T> type of messages that the observer will receive
 */
public interface Observer<T>
{

	/**
	 * Method that takes in input the message of update from observable object and handle the updating
	 * of this observer object
	 * @param message message of update
	 * @throws Exception if the observer launch an exception
	 */
	public void update(T message) throws Exception;
	
}
