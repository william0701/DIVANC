#DIVANC

Compilation:
-----------

- This project is based on Java 1.7.

- The files in bin/ is the classes compiled.



Usage and Options:
-------------------------

- Import this project with eclipse or myeclipse

- Open the source code of class 'Test' in package com.xidian.Test.

- The variable 'f' represents the name of input network.

- The variable 'networkpath' represents the absolute path of the network file.

- The variable 'outputpath' represents the absolute path of the output files.

- You should change the variables before you run the program.

Input format:
------------

- The input format is that each line contains the names of two nodes which separate by Tab, which represents an edge in the inputting network.

- The inputting network should be an undirected and unweighted simple network(without multi-edges and self-loops).

Output format:
-------------

- The output files include three files.

- The file with 'Community' as the prefix contains the non-overlap communities. In this file, each line represents a non-overlap commmunity.

- The file with 'Overlap' as the prefix contains the overlap communities. In this file, each line represents an overlap community.

- The file with 'Classify' as the prefix contains result of the classification of the overlap communities. Each line represents an overlap community, and at the begining of every type, there is a label.

Contact
-------

If you have any question, please contact Songwei Jia: swjia1027@qq.com or Prof. Gao: lgao@mail.xidian.edu.cn
