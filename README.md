# ARIPOTER-hcat: ARgumentatIon apPrOximaTE Reasoning using the h-Categorizer semantics

This tool can solve all the problems in the approximate track of [ICCMA 2023](https://iccma2023.github.io/)

## Building the tool
To compile the tool, you need to be sure that the `commons-cli-1.4.jar` and `MANIFEST.MF` file are in the current directory. Then, simply type
```bash
make
```
to compile the source code and produce the runnable jar file `ARIPOTER_HCAT.jar`.

## Usage
ARIPOTER-hcat follows the instructions of ICCMA 2023. In particular, the command-line is as follows:
```text
usage: jarfile [-a <argument>] [-f <input_AF>] [-p <task>] [-problems]
 -a,--argument <argument>   Quary argument for credulous and skeptical
                            acceptance
 -f,--input_AF <input_AF>   Path of the file containing the AF.
 -p,--task <task>           <task> is a computational problem supported by
                            the solver (e.g. DC-CO, DS-PR).
 -problems,--problems       Prints the supported computational problems
                            and exits
```

For instance,
```bash
java -jar ARIPOTER_HCAT -jar -p DC-CO -f test.txt -a 1
```
solves the credulous acceptability problem for the argument 1 in the AF described in test.txt, under the complete semantics. The `test.txt` file follows the DIMACS-like format from ICCMA 2023, for instance 
```text
p af 5
1 2
2 4
4 5
5 4
5 5
```
describes an AF with 5 arguments, and attacks 1->2, 2->4, 4->5, 5->4 and 5->5.
