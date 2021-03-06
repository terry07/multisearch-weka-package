/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * PerformanceComparator.java
 * Copyright (C) 2008-2017 University of Waikato, Hamilton, New Zealand
 */

package weka.classifiers.meta.multisearch;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A concrete Comparator for the Performance class.
 *
 * @author  fracpete (fracpete at waikato dot ac dot nz)
 * @version $Revision: 9516 $
 * @see Performance
 */
public class PerformanceComparator
  implements Comparator<Performance>, Serializable {

  /** for serialization. */
  private static final long serialVersionUID = 6507592831825393847L;

  /** the performance measure to use for comparison. */
  protected int m_Evaluation;

  /** the metrics to use. */
  protected AbstractEvaluationMetrics m_Metrics;

  /**
   * initializes the comparator with the given performance measure.
   *
   * @param evaluation	the performance measure to use
   * @param metrics	the metrics to use
   */
  public PerformanceComparator(int evaluation, AbstractEvaluationMetrics metrics) {
    super();

    m_Evaluation = evaluation;
    m_Metrics    = metrics;
  }

  /**
   * returns the performance measure that's used to compare the objects.
   *
   * @return the performance measure
   */
  public int getEvaluation() {
    return m_Evaluation;
  }

  /**
   * Returns the metrics in use.
   *
   * @return the metrics
   */
  public AbstractEvaluationMetrics getMetrics() {
    return m_Metrics;
  }

  /**
   * Compares its two arguments for order. Returns a negative integer,
   * zero, or a positive integer as the first argument is less than,
   * equal to, or greater than the second.
   *
   * @param o1 	the first performance
   * @param o2 	the second performance
   * @return 		the order
   */
  public int compare(Performance o1, Performance o2) {
    int	result;
    double	p1;
    double	p2;

    if (o1.getEvaluation() != o2.getEvaluation())
      throw new IllegalArgumentException("Comparing different types of performances!");

    p1 = o1.getPerformance();
    p2 = o2.getPerformance();

    if (p1 < p2)
      result = -1;
    else if (p1 > p2)
      result = 1;
    else
      result = o1.getValues().compareTo(o2.getValues());

    // only correlation coefficient/accuracy/kappa obey to this order, for the
    // errors (and the combination of all three), the smaller the number the
    // better -> hence invert them
    if (getMetrics().invert(getEvaluation()))
      result = -result;

    return result;
  }

  /**
   * Indicates whether some other object is "equal to" this Comparator.
   *
   * @param obj	the object to compare with
   * @return		true if the same evaluation type is used
   */
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof PerformanceComparator))
      throw new IllegalArgumentException("Must be PerformanceComparator!");

    return (m_Evaluation == ((PerformanceComparator) obj).m_Evaluation);
  }
}