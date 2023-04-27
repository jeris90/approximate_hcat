target=ARIPOTER_HCAT

CLASSES=src/appx/extensionsemantics/approximate/CategorizedBasedApproximateSolver.java src/appx/extensionsemantics/exact/SimpleGroundedSemanticsSolver.java src/appx/gradualsemantics/Categorizer.java src/appx/solver/Solution.java src/appx/solver/NumberSolution.java src/appx/solver/ArgumentSetSolution.java src/appx/solver/Solver.java src/appx/solver/BinarySolution.java src/appx/parser/ParsingException.java src/appx/parser/AFParser.java src/appx/task/Problem.java src/appx/task/Semantics.java src/appx/task/Task.java src/appx/af/ArgumentationFramework.java src/appx/Launcher.java

all: ${CLASSES}
## Compiling the appx.task package
	javac -d bin src/appx/task/Problem.java
	javac -d bin src/appx/task/Semantics.java
	javac -d bin -cp bin src/appx/task/Task.java

## Compiling the appx.af package
	javac -d bin src/appx/af/ArgumentationFramework.java

## Compiling the appx.parser package
	javac -d bin src/appx/parser/ParsingException.java
	javac -d bin -cp bin src/appx/parser/AFParser.java

## Compiling the appx.solver package
	javac -d bin src/appx/solver/Solution.java
	javac -d bin -cp bin src/appx/solver/NumberSolution.java
	javac -d bin -cp bin src/appx/solver/ArgumentSetSolution.java
	javac -d bin -cp bin src/appx/solver/BinarySolution.java
	javac -d bin -cp bin src/appx/solver/Solver.java

## Compiling the appx.gradualsemantics package
	javac -d bin -cp bin src/appx/gradualsemantics/Categorizer.java

## Compiling the appx.extensionsemantics package
	javac -d bin -cp bin src/appx/extensionsemantics/exact/SimpleGroundedSemanticsSolver.java
	javac -d bin -cp bin src/appx/extensionsemantics/approximate/CategorizedBasedApproximateSolver.java

## Compiling the appx package
	javac -d bin -cp bin:commons-cli-1.4.jar src/appx/Launcher.java

## Building the jar
	jar --create --file ${target}.jar --manifest MANIFEST.MF -C bin/ .

clean:
	rm -rf bin ${target}.jar
