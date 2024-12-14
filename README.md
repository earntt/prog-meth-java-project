# Prog Meth Java Project - Solitaire Game

This project is a **Solitaire game** developed as part of the **Program Methodology** course. The game is implemented in **Java** and aims to demonstrate core programming principles, algorithms, and object-oriented programming concepts.

---

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

---

## Overview
This project is a **Solitaire game** developed to showcase various programming methodologies taught in the **Program Methodology** course. The game is designed to provide a fun and educational way to practice programming concepts like control flow, data structures, and object-oriented programming in Java.

The game follows the classic **Solitaire** rules where players must arrange cards in a particular order, and the game continues until the player either wins or the game ends. This project utilizes **JavaFX** for the user interface and various object-oriented programming techniques to create a dynamic and interactive game environment.

---

## Features
- **Interactive Gameplay**: Players can interact with the game using a set of predefined controls.
- **Object-Oriented Design**: The game utilizes classes, inheritance, and polymorphism for clear and maintainable code.
- **Randomized Card Dealing**: Cards are shuffled and dealt randomly for every new game.
- **Scoring System**: Players can track their performance through a scoring system.
- **Simple User Interface**: A basic user interface displays the game state, allowing players to drag and drop cards and view their progress.

---

## Technologies Used
- **Programming Language**: Java
- **Libraries**: JavaFX (for GUI)
- **Development Environment**: Any Java IDE (e.g., IntelliJ IDEA, Eclipse)

---

## Module Path
This project is modularized and requires setting the correct **module path** to run properly. It uses the Java module system introduced in Java 9, along with **JavaFX** libraries.

### Prerequisites
- You must have **Java 21** or later installed on your machine. You can download it from [Oracle's website](https://www.oracle.com/java/technologies/downloads/#java21) or use a package manager like **Homebrew** for macOS.

- You will also need the **JavaFX SDK**. Download it from the [official JavaFX website](https://openjfx.io/) or download directly from [Gluon](https://gluonhq.com/products/javafx/).

### Steps to Run:

1. Clone the repository:
   ```sh
   git clone https://github.com/earntt/prog-meth-java-project.git
   ```

2. Navigate into the project directory:
   ```sh
   cd prog-meth-java-project
   ```

3. **Ensure JavaFX JARs are in a folder**:
   Place all the required JavaFX JAR files (e.g., `javafx-base.jar`, `javafx-controls.jar`, `javafx.fxml.jar`, etc.) into a single folder. For example:
   ```sh
   C:\Program Files\Java\javafx-sdk-21.0.2\lib
   ```

4. **Run the JAR file**:
   You can run the JAR file using the following command:
   ```sh
   java --module-path "C:\Program Files\Java\javafx-sdk-21.0.2\lib" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics,javafx.media,javafx.swing,javafx.web -jar Prog-Meth-Project.jar
   ```

   **Explanation**:
   - `--module-path` specifies the folder where the JavaFX JAR files are located.
   - `--add-modules` includes the necessary JavaFX modules (you can adjust this list based on your project’s needs).

5. Follow the on-screen instructions to interact with the game.

---

## Usage
- Start the Solitaire game by running the `Prog-Meth-Project.jar` file.
- Follow the on-screen instructions to interact with the game.
- Use the controls to arrange the cards according to Solitaire rules. The game will display messages based on your actions and provide feedback.

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---
