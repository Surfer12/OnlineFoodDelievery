package ConsoleInputHandler;

import java.util.List;

/**
 * The InputHandler interface defines methods for handling console input.
 *
 * @param <T> the type of input to handle
 */
public interface InputHandler<T> {

   /**
    * Prompts the user for input and returns the validated input.
    *
    * @param prompt the prompt to display to the user
    * @return the validated input
    */
   T getInput(String prompt);

   /**
    * Prompts the user for multiple inputs until the stop command is entered, and returns a list of validated inputs.
    *
    * @param prompt the prompt to display to the user
    * @param stopCommand the command to stop input
    * @return a list of validated inputs
    */
   List<T> getMultipleInputs(String prompt, String stopCommand);
}
