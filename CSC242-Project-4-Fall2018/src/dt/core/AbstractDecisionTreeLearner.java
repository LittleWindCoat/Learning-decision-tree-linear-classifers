package dt.core;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * This class provides a skeletal implementation of the
 * decision-tree learning algorithm in AIMA Fig 18.5,
 * which is based on ID3 (see AIMA p. 758).
 * It also provides implementations for some of the
 * trickier methods used in the information gain
 * calculations used to select variables for splitting
 * (AIMA Sect. 18.3.4).
 */
abstract public class AbstractDecisionTreeLearner {
	
	protected Problem problem;
	
	/**
	 * Construct and return a new DecisionTreeLearner for the given Problem.
	 */
	public AbstractDecisionTreeLearner(Problem problem) {
		this.problem = problem;
	}
	
	/**
	 * Compute and return a DecisionTree for this DecisionTreeLearner's
	 * Problem using the given Set of Examples.
	 */
	public DecisionTree learn(Set<Example> examples) {
		return learn(examples, problem.getInputs(), Collections.emptySet());
	}
	
	/**
	 * Main recursive decision-tree learning (ID3) method.
	 * This must be implemented by subclasses.  
	 */
	abstract protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples);
	
	/**
	 * Returns the most common output value among a set of Examples,
	 * breaking ties randomly (or not--it may not matter to you).
	 */
	abstract protected String pluralityValue(Set<Example> examples);
	
	/**
	 * Returns the single unique output value among the given examples
	 * is there is only one, otherwise null.
	 */
	abstract protected String uniqueOutputValue(Set<Example> examples);
	
	//
	// Methods for selecting variables (attributes)
	//

	/**
	 * Return the variable with the maximum information gain based on the
	 * given examples, per AIMA Section 18.3.4.
	 * Note that this is not cheap to compute...
	 */
	protected Variable mostImportantVariable(List<Variable> attributes, Set<Example> examples) {
		Variable maxvar = null;
		double maxgain = 0;
		for (Variable a : attributes) {
			double g = gain(a, examples);
			if (maxvar == null || g > maxgain) {
				maxvar = a;
				maxgain = g;
			}
		}
		return maxvar;
	}
	
	//
	// These next methods do the information gain calculation like it's
	// done in AIMA, based on a Boolean output variable. I'm leaving
	// them here so you can compare with the text (or heck, use them
	// if you want).
	//
		
	/**
	 * Return the ``information gain from an attribute test on A,''
	 * which is ``the expected reduction in entropy.'' (AIMA p704)
	 */
	protected double gain_boolean(Variable a, Variable outputVar, Set<Example> examples) {
		int p = countPositiveExamples(examples);
		int n = examples.size() - p;
		return B((double)p/(p+n)) - remainder_boolean(a, outputVar, examples, p, n);
	}
	
	/**
	 * Return the ``expected entropy remaining after testing
	 * attribute A.'' (AIMA p704)
	 * We pass in p and n from gain() to avoid recomputing them.
	 * AIMA doesn't say what to do if Ek is empty. We skip it.
	 */
	protected double remainder_boolean(Variable a, Variable outputVar, Set<Example> examples, int p , int n) {
		double result = 0;
		for (String vk : a.domain) {
			Set<Example> ek = examplesWithValueForAttribute(examples, a, vk);
			if (ek.size() == 0) {
				continue;
			}
			int pk = countPositiveExamples(ek);
			int nk = ek.size() - pk;
			result += (double)(pk+nk)/(p+n) * B((double)pk/(pk+nk));
		}
		return result;
	}
	
	/**
	 * Return the entropy of a Boolean random variable that is true with
	 * probability q. (AIMA p. 704)
	 * AIMA doesn't say what to do when q=0 or 1, but since log2(0)=-Infinity
	 * we have to do something... using 0 matches their results.
	 * Not used in the general procedure, only in the AIMA Boolean example.
	 */
	protected double B(double q) {
		double result;
		if (q == 0.0 || q == 1.0) {
			result = 0;
		} else {
			result = -(q*log2(q) + (1.0-q)*log2(1.0-q));
		}
		return result;
	}
	
	/**
	 * Return the number of examples with "Yes" for the output value.
	 * Not used in the general case, just the AIMA Boolean example.
	 */
	protected int countPositiveExamples(Set<Example> examples) {
		int result = 0;
		for (Example e : examples) {
			if (e.getOutputValue().equals(YesNoDomain.YES)) {
				result += 1;
			}
		}
		return result;
	}

	//
	// These methods do the calculation using the full definition
	// of entropy and information gain.
	//
	
	/**
	 * Return the entropy of the given Variable computing the probabilities
	 * of its values from the given Examples.
	 * For variable V with values vk:
	 * H(V) = \sum_k P(vk)*log_2(1/P(vk)) = -\sum_k P(vk)*log_2(Pvk)
	 */
	protected double H(Variable var, Set<Example> examples) {
		double result = 0;
		int n = examples.size();
		for (String vk : var.domain) {
			int nk = countExamplesWithValueForAttribute(examples, var, vk);
			double pk = (double)nk / n;
			result += pk*log2(pk);
		}
		return -result;
	}
	
	/**
	 * Return the entropy of the output variable using the given set of Examples.
	 */
	protected double H(Set<Example> examples) {
		double result = 0;
		int n = examples.size();
		for (String vk : problem.getOutput().domain) {
			int nk = countExamplesWithValueForOutput(examples, vk);
			if (nk > 0) {
				double pk = (double)nk / n;
				result += pk*log2(pk);
			}
		}
		return -result;
	}

	/**
	 * For set of examples T, attribute (variable) a with values vk,
	 * and Ek = subset of T for which a=vk:
	 * IG(T,a) = H(T) - \sum_vk { |Ek|/|T| * H(Ek) }
	 * This is the general case of the Boolean version done in AIMA.
	 */
	protected double gain(Variable a, Set<Example> examples) {
		return H(examples) - remainder(a, examples);
	}
	
	/**
	 * Return the expected entropy remaining after testing attribute a. 
	 * This is the general case of the Boolean version done in AIMA.
	 */
	protected double remainder(Variable a, Set<Example> examples) {
		double result = 0;
		for (String vk : a.domain) {
			Set<Example> Ek = examplesWithValueForAttribute(examples, a, vk);
			if (Ek.size() == 0) {
				continue;
			}
			result += (double)Ek.size()/examples.size() * H(Ek);
		}
		return result;
	}
	
	/**
	 * Return log base 2 of the given number.
	 */
	protected double log2(double x) {
		return Math.log(x) / Math.log(2.0);
	}
	
	/**
	 * Return the subset of the given examples for which Variable a has value vk.
	 */
	abstract protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk);
	
	/**
	 * Return the number of the given examples for which Variable a has value vk.
	 */
	abstract protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk);

	/**
	 * Return the number of the given examples for which the output has value vk.
	 */
	abstract protected int countExamplesWithValueForOutput(Set<Example> examples, String vk);

}
