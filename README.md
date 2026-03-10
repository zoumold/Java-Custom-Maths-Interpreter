# Math Expression Interpreter

This is a simple Java-based interpreter that can parse and evaluate mathematical expressions. It supports basic arithmetic operations, variable assignments, and can be run interactively or by reading commands from a file.

## Features

* **Arithmetic Operations:** Supports addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), and exponentiation (`^`).
* **Variables:** Allows assigning results to variables (e.g., `x = 5 + 3;`) and using them in subsequent expressions.
* **Parentheses:** Supports the use of `()` to enforce precedence.
* **Two Execution Modes:**
    * **REL Mode:** An interactive command-line interface.
    * **File Mode:** Reads and executes statements sequentially from a text file.

## Prerequisites

* Java Development Kit (JDK) 8 or higher installed on your system.

## Download and Setup

1.  **Clone the repository** (if you are hosting it on Git):
    ```bash
    git clone <your_repository_URL>
    cd <repository_directory>
    ```
    *(Alternatively, download the source files and ensure they are placed in a `src/ce326/hw1` directory structure).*

2.  **Compile the code:**
    Navigate to the `src` directory and compile the Java files.
    ```bash
    cd src
    javac ce326/hw1/*.java
    ```

## Usage

Make sure you are in the `src` directory where the compiled `.class` files reside.

### 1. Interactive Mode (REL)

To run the interpreter interactively, execute the program without any arguments:

```bash
java ce326.hw1.Interpreter
