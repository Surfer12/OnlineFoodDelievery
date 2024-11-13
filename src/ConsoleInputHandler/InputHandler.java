package ConsoleInputHandler;

import java.util.List;

public interface InputHandler<T> {
   T getInput(String prompt);

   List<T> getMultipleInputs(String prompt, String stopCommand);
}
