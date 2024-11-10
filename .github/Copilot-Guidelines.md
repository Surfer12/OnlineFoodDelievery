Sample instructions
This example of a .github/copilot-instructions.md file contains three instructions that will be added to all chat questions.

Writing effective custom instructions Guidelines to follow are as follows and described next. 
Key points to remember when writing custom instructions:
1. The instructions you add to the .github/copilot-instructions.md file should be short, self-contained statements that add context or relevant information to supplement users' chat questions.
1.1 Example : 
"The following types of instructions are unlikely to work as desired and may cause problems with other areas of Copilot:"

"Requests to refer to external resources when formulating a response
Instructions to answer in a particular style
Requests to always respond with a certain level of detail"

"The following instructions are therefore unlikely to have the intended result:

Always conform to the coding styles defined in styleguide.md in repo my-org/my-repo when generating code.

Use @terminal when answering questions about Git.

Answer all questions in the style of a friendly colleague, using informal language.

Answer all questions in less than 1000 characters, and words of no more than 12 characters."
