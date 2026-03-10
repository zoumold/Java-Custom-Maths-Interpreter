# CE326 HW1 — Expression Interpreter

A simple interpreter for arithmetic expressions written in Java. Supports variable assignment, basic math operations, and two run modes: interactive (REPL) and file-based.

---

## Features

- Interactive REPL mode (Read-Eval-Print Loop)
- File execution mode
- Variable assignment and reuse
- Arithmetic operators: `+`, `-`, `*`, `/`, `^` (power)
- Operator precedence and parentheses
- Reverse Polish Notation (RPN/Shoehorn algorithm) for expression evaluation
- Descriptive error messages with line numbers (in file mode)

---

## Project Structure

```
ce326/hw1/
├── Interpreter.java      # Entry point, handles REPL and file mode
├── Parser.java           # Parses and validates input lines
├── Instruction.java      # Tokenizes, converts to RPN, and evaluates expressions
└── ParserException.java  # Custom exception with optional line number
```

---

## Getting Started

### Prerequisites

- [Java JDK 8+](https://www.oracle.com/java/technologies/downloads/) installed
- [Git](https://git-scm.com/downloads) installed

### Clone the Repository

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPO_NAME.git
cd YOUR_REPO_NAME
```

Or, if you prefer to download without Git, click **Code → Download ZIP** on the GitHub page, then unzip and `cd` into the folder.

---

## How to Compile

From the root of the project (the directory containing the `ce326/` folder):

```bash
javac ce326/hw1/*.java
```

---

## How to Run

### Interactive / REPL Mode

Run with no arguments to start an interactive session:

```bash
java ce326.hw1.Interpreter
```

You'll see a `>` prompt. Type expressions ending with `;` and press Enter.

```
> 2 + 3;
  5
> x = 10;
> x * 2;
  20
> x = x + 5;
> x;
  15
```

Exit with `Ctrl+D` (EOF).

### File Mode

Pass a `.txt` (or any text) file as an argument:

```bash
java ce326.hw1.Interpreter myfile.txt
```

Each line in the file should be a valid expression ending with `;`. Errors will be reported with their line number.

**Example file (`myfile.txt`):**
```
x = 5;
y = x ^ 2;
y + 1;
```

**Output:**
```
  26
```

---

## Syntax Rules

| Rule | Example |
|------|---------|
| Every statement must end with `;` | `x = 5;` |
| Assignment uses `=` | `result = 3 * 4;` |
| Print a value (no assignment) | `result;` or `2 + 2;` |
| Supports parentheses | `(2 + 3) * 4;` |
| Multiple statements per line | `x = 1; y = 2;` |
| Variables are lowercase or uppercase letters | `myVar`, `x`, `ABC` |

---

## Error Handling

The interpreter will report errors without crashing:

| Error | Message |
|-------|---------|
| Missing `;` | `Expecting ; at the end of line` |
| Unknown variable | `Unknown variable x` |
| Mismatched parentheses | `Expecting ( before closing` |
| Consecutive operators | `Expecting operand between operators` |
| Multiple `=` in one expression | `Multiple assignment operator in expression` |

In file mode, errors include the line number, e.g.:
```
Line 3: Unknown variable z
```

---

## Notes

- Integer results are printed without decimals: `5`
- Floating-point results are printed to 6 decimal places: `3.141593`
- Variables persist for the entire session (REPL) or file execution
