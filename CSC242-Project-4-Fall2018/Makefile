
# Spaces not tabs for recipes (note space at end of next line)
.RECIPEPREFIX= 

JAVA = java
JAVAC = javac
JAVADOC = javadoc

build all:
	test -d bin || mkdir bin
	cd src; $(JAVAC) -d ../bin `find . -name '*.java'`

doc:
	-rm -r doc
	-mkdir doc
	cd src; $(JAVADOC) -d ../doc `find . -name '*.java'`
    
#
# dt
#
run-dt-willwait:
	$(JAVA) -cp bin dt.examples.WillWaitProblem src/dt/examples/WillWait-data.txt

run-dt-iris:
	$(JAVA) -cp bin dt.examples.IrisProblem src/dt/examples/iris.data.discrete.txt

run-dt-house-votes:
	$(JAVA) -cp bin dt.examples.HouseVotesProblem src/dt/examples/house-votes-84.data.mod.txt

run-dt-hiv-protease:
	$(JAVA) -cp bin dt.examples.HIVProteaseCleavageProblem src/dt/examples/746Data.txt src/dt/examples/1625Data.txt

#
# lc
#
run-lc-perceptron-earthquake-clean:
	$(JAVA) -cp bin lc.examples.PerceptronClassifierTest src/lc/examples/earthquake-clean.data.txt 10000 0.95

run-lc-logistic-earthquake-clean:
	$(JAVA) -cp bin lc.examples.LogisticClassifierTest src/lc/examples/earthquake-clean.data.txt 10000 0.95

run-lc-perceptron-earthquake-noisy:
	$(JAVA) -cp bin lc.examples.PerceptronClassifierTest src/lc/examples/earthquake-noisy.data.txt 10000 0.95

run-lc-logistic-earthquake-noisy:
	$(JAVA) -cp bin lc.examples.LogisticClassifierTest src/lc/examples/earthquake-noisy.data.txt 10000 0.95

run-lc-perceptron-earthquake-noisy-decaying:
	$(JAVA) -cp bin lc.examples.PerceptronClassifierTest src/lc/examples/earthquake-noisy.data.txt 10000 0

run-lc-logistic-earthquake-noisy-decaying:
	$(JAVA) -cp bin lc.examples.LogisticClassifierTest src/lc/examples/earthquake-noisy.data.txt 10000 0

run-lc-perceptron-house-votes:
	$(JAVA) -cp bin lc.examples.PerceptronClassifierTest src/lc/examples/house-votes-84.data.num.txt 10000 0.95

run-lc-logistic-house-votes:
	$(JAVA) -cp bin lc.examples.LogisticClassifierTest src/lc/examples/house-votes-84.data.num.txt 10000 0.95
	
run-lc-logistic-house-vote-decaying:
	$(JAVA) -cp bin lc.examples.LogisticClassifierTest src/lc/examples/house-votes-84.data.num.txt 10000 0

#
# nn
#
run-nn-majority:
	$(JAVA) -cp bin nn.examples.MajorityPerceptronNN 100 5

run-nn-majority-1000:
	$(JAVA) -cp bin nn.examples.MajorityPerceptronNN 1000 50

run-nn-majority-5000:
	$(JAVA) -cp bin nn.examples.MajorityPerceptronNN 5000 500

run-nn-iris:
	$(JAVA) -cp bin nn.examples.IrisNN

run-nn-mnist:
	$(JAVA) -cp bin nn.examples.MNISTNN

