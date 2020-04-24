CSC242 PROJECT 4 LEARNING LWC

	We used the provided classes for Project 4 to implement our algorithms. For this project, we implement the following forms of machine learning:

(1) Decision tree learning (AIMA 18.3): We implement the core decision tree learning algorithm in DecisionTreeLearner.java. 
	
	a. To test the program using the restuarant example using the command-line, place proj4 folder on desktop, open command prompt and type in the following command:
		cd Desktop
		cd proj4
		cd bin
		java dt/examples/WillWaitProblem dt/examples/WillWait-data.txt
	b. To test the program using the Iris data using the command-line, type in the command:
		java dt/examples/IrisProblem dt/examples/Iris.data.discrete.txt
	c. To test the program using the House Votes data, type in the command:
		java dt/examples/HouseVotesProblem dt/examples/house-votes-84.data.mod.txt
	d. You can also test the examples through Eclipse with arguments:
		src/dt/examples/WillWait-data.txt
		src/dt/examples/iris.data.discrete.txt
		src/dt/examples/house-votes-84.data.mod.txt

(2) Linear classifiers (AIMA 18.6): We implement the update() and threshold() methods for the PerceptionClassifier and LogisticClassifier classes. 

	a. The Earthquake example
		java lc/examples/PerceptronClassifierTest lc/examples/earthquake-clean.data.txt 700 1
		java lc/examples/PerceptronClassifierTest lc/examples/earthquake-noisy.data.txt 100000 1
		java lc/examples/PerceptronClassifierTest lc/examples/earthquake-noisy.data.txt 100000 0
		java lc/examples/LogisticClassifierTest lc/examples/earthquake-clean.data.txt 5000 0.05
		java lc/examples/LogisticClassifierTest lc/examples/earthquake-noisy.data.txt 100000 0.05
		java lc/examples/LogisticClassifierTest lc/examples/earthquake-noisy.data.txt 100000 0
	b. The House Votes example
		java lc/examples/PerceptronClassifierTest lc/examples/house-votes-84.data.num.txt 100000 1
		java lc/examples/PerceptronClassifierTest lc/examples/house-votes-84.data.num.txt 100000 0
		java lc/examples/LogisticClassifierTest lc/examples/house-votes-84.data.num.txt 100000 0.05
		java lc/examples/LogisticClassifierTest lc/examples/house-votes-84.data.num.txt 100000 0